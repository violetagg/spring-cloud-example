package demo.ui;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;

import demo.client.HystrixApartmentClient;
import demo.client.HystrixRecommendationClient;
import demo.model.Apartment;
import demo.model.Product;

@SpringUI(path = "/apartments")
@Title("Apartments For Rent")
@Theme("valo")
public class ApartmentsUI extends UI {
	private static final long serialVersionUID = 1L;

	private static final String RECOMMENDATIONS_FEATURE_ENABLED = "recommendations.feature.enabled";

	TextField filter = new TextField();
	Grid items = new Grid();

	@Autowired
	HystrixRecommendationClient recommendationClient;

	@Autowired
	HystrixApartmentClient apartmentClient;

	@Autowired
	private Environment env;

	@Override
	protected void init(VaadinRequest request) {
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		boolean flag = Boolean.valueOf(env.getProperty(RECOMMENDATIONS_FEATURE_ENABLED));

		filter.setInputPrompt("Filter apartments...");
		filter.addTextChangeListener(e -> refreshApartments(e.getText()));

		items.setContainerDataSource(new BeanItemContainer<>(Apartment.class));
		items.setColumnOrder("id", "title", "location", "description", "recommendations");
		items.setSelectionMode(Grid.SelectionMode.SINGLE);
		if (!flag) {
			items.removeColumn("recommendations");
		} else {
			items.getColumn("recommendations").setRenderer(new HtmlRenderer(),
					new Converter<String, String[]>() {
						private static final long serialVersionUID = 1L;

						@Override
						public String[] convertToModel(String value,
								Class<? extends String[]> targetType, Locale locale)
								throws ConversionException {
							return null;
						}

						@Override
						public String convertToPresentation(String[] value,
								Class<? extends String> targetType, Locale locale)
								throws ConversionException {
							StringBuilder sb = new StringBuilder();
							Arrays.asList(value)
									.forEach(
											a -> sb.append(
													"<span class=\"v-label v-widget v-panel\" style=\"margin: -4px 0.5em 0px; color: gray; padding: 0.25em; display: inline-block; font-size: 0.75em; font-weight: 300;\">")
													.append(a).append("</span>"));
							return sb.toString();
						}

						@Override
						public Class<String[]> getModelType() {
							return String[].class;
						}

						@Override
						public Class<String> getPresentationType() {
							return String.class;
						}
					});
		}

		refreshApartments();
	}

	private void buildLayout() {
		HorizontalLayout actions = new HorizontalLayout(filter);
		actions.setWidth("100%");
		filter.setWidth("100%");
		actions.setExpandRatio(filter, 1);

		VerticalLayout left = new VerticalLayout(actions, items);
		left.setSizeFull();
		items.setSizeFull();
		left.setExpandRatio(items, 1);

		HorizontalLayout mainLayout = new HorizontalLayout(left);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(left, 1);

		// Split and allow resizing
		setContent(mainLayout);
	}

	void refreshApartments() {
		refreshApartments(filter.getValue());
	}

	private void refreshApartments(String stringFilter) {
		Collection<Apartment> apartments = apartmentClient.findAll().getContent();
		apartments.forEach(a -> {
			Collection<Product> products = recommendationClient.findByProductId(
					a.getId().toString()).getContent();
			if (products != null)
				products.forEach(p -> a.addRecommendation(p.getRecommendation()));
		});
		items.setContainerDataSource(new BeanItemContainer<>(Apartment.class, apartments));
	}

}
