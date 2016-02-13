package demo.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import demo.model.Apartment;

@FeignClient("apartment")
public interface ApartmentClient {
	@RequestMapping(method = RequestMethod.GET, value = "/apartments")
	PagedResources<Apartment> findAll();
}