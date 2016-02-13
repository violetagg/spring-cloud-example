package demo.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import demo.domain.User;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	@Override
	@Query("MATCH (u:User) WHERE id(u) = {id} RETURN u")
	User findOne(@Param(value = "id") Long id);

	@Override
	@Query("MATCH (u:User) RETURN u")
	List<User> findAll();

}
