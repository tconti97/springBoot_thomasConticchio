/**
 * 
 */
package p3.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web
.bind.annotation.RestController;

import p3.jdbctemplate.model.Cat;
import p3.jpa.model.Dog;
import p3.jpa.repo.DogRepository;

/**
 * @author tcont
 *
 */

@RestController
@RequestMapping(path = "/rest/v1/dogs")
public class DogRestController 
{
	@Autowired
	private DogRepository dogRepository;
	
	@RequestMapping(value="/echoMessage", method=RequestMethod.GET)
	public String echoMessage( @RequestParam(value="msg", defaultValue="Thomas") String message)
	{
		return "echoMessage echoes:" + message;
	}

	@GetMapping("")
	public Page<Dog> findAll(@RequestParam(defaultValue="0") int page, @RequestParam(value="rowsPerPage", defaultValue="2") int size) 
	{
		Page<Dog> dogs = dogRepository.findAll(PageRequest.of(page, size));
		return dogs;
	}
	
	@GetMapping("/all")
	public  List<Dog> findAll() {
		List<Dog> dogs = dogRepository.findAll();
		return dogs;
	}
	
	@GetMapping("/{id}")
	public  Optional<Dog> findById(@PathVariable Long id) {
		Optional<Dog> dog = dogRepository.findById(id);
		return dog;
	}
	
	@GetMapping("/byType/{type}")
	public Optional<List<Dog>> findByType(@PathVariable String type)
	{
		Optional<List<Dog>> dog = dogRepository.findByType(type);
		return dog;
	}
}
