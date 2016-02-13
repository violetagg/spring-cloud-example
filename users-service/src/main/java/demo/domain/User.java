package demo.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class User {
	@GraphId
	private Long id;
	private String firstName;
	private String lastName;

	@RelatedTo(type = "FOLLOWS", direction = Direction.OUTGOING, elementClass = User.class)
	@Fetch
	Set<User> follows = new HashSet<User>();

	@RelatedTo(type = "FOLLOWS", direction = Direction.INCOMING, elementClass = User.class)
	@Fetch
	Set<User> followers = new HashSet<User>();

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

	public Long getId() {
		return id;
	}

	public void doFollow(User user) {
		if (follows == null) {
			follows = new HashSet<User>();
		}
		follows.add(user);
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
