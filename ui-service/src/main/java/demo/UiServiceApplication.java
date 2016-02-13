package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.client.HystrixApartmentClient;
import demo.client.HystrixRecommendationClient;
import demo.client.HystrixUserClient;

@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
@EnableFeignClients
@EnableWebMvc
@SpringBootApplication
public class UiServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(UiServiceApplication.class, args);
	}

	@Bean
	public HystrixUserClient hystrixUserClient() {
		return new HystrixUserClient();
	}

	@Bean
	public HystrixApartmentClient hystrixApartmentClient() {
		return new HystrixApartmentClient();
	}

	@Bean
	public HystrixRecommendationClient hystrixRecommendationClient() {
		return new HystrixRecommendationClient();
	}

	@Bean
	AlwaysSampler alwaysSampler() {
		return new AlwaysSampler();
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.registerModule(new Jackson2HalModule());
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}
}
