package demo.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import demo.model.User;

public class HystrixUserClient {

	@Autowired
	UserClient userClient;

	private List<User> users;

	public HystrixUserClient() {
		users = getCachedContent();
	}

	@HystrixCommand(commandKey = "Find All Users", fallbackMethod = "fallbackFindAll")
	public Collection<User> findAll() {
		return userClient.findAll().getContent();
	}

	@HystrixCommand(commandKey = "Find All Follows Per User", fallbackMethod = "fallbackFindFollowsById")
	public Collection<User> findFollowsById(String id) {
		return userClient.findFollowsById(id).getContent();
	}

	@HystrixCommand(commandKey = "Create User", fallbackMethod = "fallbackCreateUser")
	public void createUser(User user) {
		userClient.createUser(user);
	}

	@HystrixCommand(commandKey = "Fallback Find All Users")
	public Collection<User> fallbackFindAll() {
		return users;
	}

	@HystrixCommand(commandKey = "Fallback Find All Follows Per User")
	public Collection<User> fallbackFindFollowsById(String id) {
		User filteredUser = users.stream().filter(u -> u.getId() == Long.valueOf(id))
				.collect(Collectors.toList()).get(0);
		return Arrays.asList(filteredUser.getFollows());
	}

	@HystrixCommand(commandKey = "Fallback Create User")
	public void fallbackCreateUser(User user) {
		users.add(user);
	}

	private List<User> getCachedContent() {
		List<User> usersContent = new ArrayList<>();
		User user1 = new User(0L, "John", "Doe");
		User user2 = new User(1L, "Harry", "Oakley");
		User user3 = new User(2L, "Mary", "Johnson");
		User user4 = new User(3L, "William", "Barker");
		User user5 = new User(4L, "Martha", "Taylor");
		User user6 = new User(5L, "David", "Carroll");
		user1.setFollows(new User[] { user2, user3 });
		user2.setFollows(new User[] { user3, user4 });
		user3.setFollows(new User[] { user4, user5 });
		user4.setFollows(new User[] { user5, user6 });
		user5.setFollows(new User[] { user6, user1 });
		user6.setFollows(new User[] { user1, user2 });
		usersContent.add(user1);
		usersContent.add(user2);
		usersContent.add(user3);
		usersContent.add(user4);
		usersContent.add(user5);
		usersContent.add(user6);
		return usersContent;
	}
}
