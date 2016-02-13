package demo.ui;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;

import demo.client.HystrixUserClient;
import demo.model.User;

@SpringUI(path = "/users")
@Title("Users")
@Theme("valo")
public class UsersUI extends UI {
	private static final long serialVersionUID = 1L;

	TextField filter = new TextField();
	Grid contactList = new Grid();
	Button newContact = new Button("New contact");

	ContactForm contactForm = new ContactForm();

	@Autowired
	HystrixUserClient userClient;

	@Override
	protected void init(VaadinRequest request) {
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		newContact.addClickListener(e -> contactForm.edit(new User()));

		filter.setInputPrompt("Filter contacts...");
		filter.addTextChangeListener(e -> refreshContacts(e.getText()));

		contactList.setContainerDataSource(new BeanItemContainer<>(User.class));
		contactList.setColumnOrder("id", "firstName", "lastName", "follows");
		contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
		contactList
				.addSelectionListener(e -> contactForm.edit((User) contactList.getSelectedRow()));

		contactList.getColumn("follows").setRenderer(new HtmlRenderer(),
				new Converter<String, User[]>() {
					private static final long serialVersionUID = 1L;

					@Override
					public User[] convertToModel(String value, Class<? extends User[]> targetType,
							Locale locale) throws ConversionException {
						return null;
					}

					@Override
					public String convertToPresentation(User[] value,
							Class<? extends String> targetType, Locale locale)
							throws ConversionException {
						StringBuilder sb = new StringBuilder();
						Arrays.asList(value)
								.forEach(
										a -> sb.append(
												"<span class=\"v-label v-widget v-panel\" style=\"margin: -4px 0.5em 0px; color: gray; padding: 0.25em; display: inline-block; font-size: 0.75em; font-weight: 300;\">")
												.append(a.getFirstName()).append(" ")
												.append(a.getLastName()).append("</span>"));
						return sb.toString();
					}

					@Override
					public Class<User[]> getModelType() {
						return User[].class;
					}

					@Override
					public Class<String> getPresentationType() {
						return String.class;
					}
				});

		refreshContacts();
	}

	private void buildLayout() {
		HorizontalLayout actions = new HorizontalLayout(filter, newContact);
		actions.setWidth("100%");
		filter.setWidth("100%");
		actions.setExpandRatio(filter, 1);

		VerticalLayout left = new VerticalLayout(actions, contactList);
		left.setSizeFull();
		contactList.setSizeFull();
		left.setExpandRatio(contactList, 1);

		HorizontalLayout mainLayout = new HorizontalLayout(left, contactForm);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(left, 1);

		// Split and allow resizing
		setContent(mainLayout);
	}

	void refreshContacts() {
		refreshContacts(filter.getValue());
	}

	private void refreshContacts(String stringFilter) {
		Collection<User> users = userClient.findAll();
		users.forEach(u -> {
			Collection<User> follows = userClient.findFollowsById(u.getId().toString());
			if (follows != null)
				u.setFollows(follows.toArray(new User[follows.size()]));
		});
		contactList.setContainerDataSource(new BeanItemContainer<>(User.class, users));
		contactForm.setVisible(false);
	}

	@WebServlet(urlPatterns = "/*")
	@VaadinServletConfiguration(ui = UsersUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
	}
}
