package demo.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Product {
	@GraphId
	private Long id;

	private Long knownId;

	@RelatedTo(type = "RECOMMENDS", direction = Direction.INCOMING, elementClass = User.class)
	@Fetch
	public User recommender;

	String recommendation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getKnownId() {
		return knownId;
	}

	public void setKnownId(Long knownId) {
		this.knownId = knownId;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", knownId=" + knownId + "]";
	}
}
