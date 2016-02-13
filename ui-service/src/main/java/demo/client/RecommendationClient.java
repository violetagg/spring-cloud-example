package demo.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import demo.model.Product;

@FeignClient("recommendation")
public interface RecommendationClient {
	@RequestMapping(method = RequestMethod.GET, value = "/products/search/findById?id={id}")
	PagedResources<Product> findByProductId(@PathVariable("id") String id);
}