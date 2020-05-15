/**
 * 
 */
package p3.jpa.repo;

import java.util.Optional;
import java.util.List;
//import p3.jdbctemplate.dao.JpaRepository;
import p3.jpa.model.Dog;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tcont
 *
 */
public interface DogRepository extends JpaRepository<Dog, Long>
{
	public Optional<List<Dog>> findByType(String type);
}
