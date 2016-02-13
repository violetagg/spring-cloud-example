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

	private Long knownId;

	@RelatedTo(type = "RECOMMENDS", direction = Direction.OUTGOING, elementClass = Product.class)
	@Fetch
	Set<Product> recommends = new HashSet<>();

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

	public void addRecommendation(Product product) {
		recommends.add(product);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", knownId=" + knownId + "]";
	}
}
