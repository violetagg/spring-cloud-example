package demo.model;

import java.util.HashSet;
import java.util.Set;

public class Apartment {
	private Long id;
	private String title = "";
	private String location = "";
	private String description = "";
	private Set<String> recommendations = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getRecommendations() {
		return recommendations.toArray(new String[recommendations.size()]);
	}

	public void addRecommendation(String recommendation) {
		this.recommendations.add(recommendation);
	}

	@Override
	public String toString() {
		return "Apartment [id=" + id + ", title=" + title + ", location=" + location
				+ ", description=" + description + ", recommendations=" + recommendations + "]";
	}

}
