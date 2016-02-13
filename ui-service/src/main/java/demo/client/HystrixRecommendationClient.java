package demo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import demo.model.Product;

public class HystrixRecommendationClient {

	@Autowired
	RecommendationClient recommendationClient;

	@HystrixCommand(commandKey = "Find All Recommendations Per Product")
	public PagedResources<Product> findByProductId(String id) {
		return recommendationClient.findByProductId(id);
	}
}
