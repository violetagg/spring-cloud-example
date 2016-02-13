package demo.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import demo.domain.Product;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	@Query("MATCH (p:Product) WHERE p.knownId = {id} RETURN p")
	List<Product> findById(@Param(value = "id") Long id);

	@Override
	@Query("MATCH (p:Product) RETURN p")
	List<Product> findAll();
}
