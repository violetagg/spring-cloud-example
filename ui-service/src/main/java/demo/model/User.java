package demo.model;

public class User {
	private Long id;
	private String firstName = "";
	private String lastName = "";
	private User[] follows = new User[0];

	public User(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public User[] getFollows() {
		return follows;
	}

	public void setFollows(User[] follows) {
		this.follows = follows;
	}

}
