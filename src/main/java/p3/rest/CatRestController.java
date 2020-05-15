/**
 * 
 */
package p3.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import p3.jdbctemplate.dao.CatDAO;
import p3.jdbctemplate.model.Cat;

/**
 * @author tcont
 *
 */
@RestController
@RequestMapping(path = "/rest/v1/cats")
public class CatRestController 
{
	@Autowired
	@Qualifier("CatDAOImpl")	
	private CatDAO catDAO;
	
	@RequestMapping(value="/echoMessage", method=RequestMethod.GET)
	public String echoMessage( @RequestParam(value="msg", defaultValue="Thomas") String message)
	{
		return "echoMessage echoes:" + message;
	}

	@GetMapping({"", "/all"})
	public  List<Cat> findAll() {
		List<Cat> cats = catDAO.findAll();
		return cats;
	}
	
	@GetMapping("/{id}")
	public  Optional<Cat> findById(@PathVariable Long id) {
		Optional<Cat> cat = catDAO.findById(id);
		return cat;
	}
	
	@GetMapping("/byType/{type}")
	public Optional<List<Cat>> findByType(@PathVariable String type)
	{
		Optional<List<Cat>> cat = catDAO.findByType(type);
		return cat;
	}
}
