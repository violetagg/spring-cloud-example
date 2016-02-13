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

import demo.domain.User;

@EnableDiscoveryClient
@EnableNeo4jRepositories
@EnableZuulProxy
@EnableHystrix
@SpringBootApplication
public class UsersServiceApplication extends Neo4jConfiguration {

	public UsersServiceApplication() {
		setBasePackage("demo");
	}

	@Bean(destroyMethod = "shutdown")
	public GraphDatabaseService graphDatabaseService() {
		return new GraphDatabaseFactory().newEmbeddedDatabase("target/users.db");
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(UsersServiceApplication.class,
				args);

		RepositoryRestConfiguration restConfiguration = ctx.getBean("config",
				RepositoryRestConfiguration.class);
		restConfiguration.exposeIdsFor(User.class);
	}

	@Bean
	AlwaysSampler alwaysSampler() {
		return new AlwaysSampler();
	}
}
