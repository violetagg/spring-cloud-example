package demo;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import demo.domain.Product;
import demo.domain.User;

@EnableNeo4jRepositories
@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
@SpringBootApplication
public class RecommendationsServiceApplication extends Neo4jConfiguration {

	public RecommendationsServiceApplication() {
		setBasePackage("demo");
	}

	@Bean(destroyMethod = "shutdown")
	public GraphDatabaseService graphDatabaseService() {
		return new GraphDatabaseFactory().newEmbeddedDatabase("target/recommendations.db");
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(
				RecommendationsServiceApplication.class, args);

		RepositoryRestConfiguration restConfiguration = ctx.getBean("config",
				RepositoryRestConfiguration.class);
		restConfiguration.exposeIdsFor(User.class);
		restConfiguration.exposeIdsFor(Product.class);
	}

	@Bean
	AlwaysSampler alwaysSampler() {
		return new AlwaysSampler();
	}
}
