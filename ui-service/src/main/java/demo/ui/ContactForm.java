package demo.ui;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import demo.model.User;

public class ContactForm extends FormLayout {

	private static final long serialVersionUID = 1L;

	Button save = new Button("Save", this::save);
	Button cancel = new Button("Cancel", this::cancel);
	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");

	User contact;

	BeanFieldGroup<User> formFieldBindings;

	public ContactForm() {
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		setVisible(false);
	}

	private void buildLayout() {
		setSizeUndefined();
		setMargin(true);

		HorizontalLayout actions = new HorizontalLayout(save, cancel);
		actions.setSpacing(true);

		addComponents(actions, firstName, lastName);
	}

	public void save(Button.ClickEvent event) {
		try {
			formFieldBindings.commit();

			getUI().userClient.createUser(contact);

			String msg = String.format("Saved '%s %s'.", contact.getFirstName(),
					contact.getLastName());
			Notification.show(msg, Type.TRAY_NOTIFICATION);
			getUI().refreshContacts();
		} catch (FieldGroup.CommitException e) {
		}
	}

	public void cancel(Button.ClickEvent event) {
		Notification.show("Cancelled", Type.TRAY_NOTIFICATION);
		getUI().contactList.select(null);
	}

	void edit(User contact) {
		this.contact = contact;
		if (contact != null) {
			formFieldBindings = BeanFieldGroup.bindFieldsBuffered(contact, this);
			firstName.focus();
		}
		setVisible(contact != null);
	}

	@Override
	public UsersUI getUI() {
		return (UsersUI) super.getUI();
	}
}
