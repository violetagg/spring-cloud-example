package demo.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import demo.domain.Apartment;

@RepositoryRestResource(collectionResourceRel = "apartments", path = "apartments")
public interface ApartmentRepository extends PagingAndSortingRepository<Apartment, Long> {

	List<Apartment> findByTitle(@Param("0") String title);

	List<Apartment> findByLocation(@Param("0") String location);

	@Query("MATCH (a:Apartment) RETURN a")
	List<Apartment> findAll();

}
