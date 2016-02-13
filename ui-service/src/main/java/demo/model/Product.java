package demo.model;

public class Product {
	private Long id;
	private String knownId;
	private String recommendation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKnownId() {
		return knownId;
	}

	public void setKnownId(String knownId) {
		this.knownId = knownId;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
}