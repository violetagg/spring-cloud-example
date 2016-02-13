package demo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import demo.model.Apartment;

public class HystrixApartmentClient {

	@Autowired
	ApartmentClient apartmentClient;

	@HystrixCommand(commandKey = "Find All Apartments")
	public PagedResources<Apartment> findAll() {
		return apartmentClient.findAll();
	}
}
