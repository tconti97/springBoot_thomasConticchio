/**
 * 
 */
package p3.jdbctemplate.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import p3.jdbctemplate.model.Cat;
import p3.jdbctemplate.model.CatRowMapper;



/**
 * @author tcont
 *
 */
@Qualifier("CatDAOImpl")
@Repository
@Transactional
public class CatDAOImpl implements CatDAO
{
	private static final Logger logger = LoggerFactory.getLogger(CatDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Optional<List<Cat>> findByType(String type) 
	{
		Optional<List<Cat>> oCat = Optional.ofNullable(null);
		List<Cat> cats = new ArrayList<Cat>();
		String sql = "SELECT c.id, c.name, c.type FROM cats c WHERE c.type = ?";
		if (type != null) 
		{
			RowMapper<Cat> rowMapper = new BeanPropertyRowMapper<Cat>(Cat.class);
			cats = jdbcTemplate.query(sql, rowMapper, type);
			oCat = Optional.ofNullable(cats);
		} 
		else 
		{
			logger.warn("findByType unExpected null input, will do nothing, will return nulled Optional");			
		}
		return oCat;

	}

	@Override
	public List<Cat> findAll() 
	{
		String sql = "SELECT c.id, c.name, c.type FROM cats c";
		RowMapper<Cat> rowMapper = new BeanPropertyRowMapper<Cat>(Cat.class);
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public Optional<Cat> findById(Long id) {
		Optional<Cat> oCat = Optional.ofNullable(null);
		String sql = "SELECT c.id, c.name, c.type FROM cats c WHERE c.id = ?";
		if (id != null) {
			RowMapper<Cat> rowMapper = new BeanPropertyRowMapper<Cat>(Cat.class);
			Cat cat = jdbcTemplate.queryForObject(sql, rowMapper, id);
			oCat = Optional.ofNullable(cat);
		} else {
			logger.warn("findById unExpected null input, will do nothing, will return nulled Optional");			
		}
		return oCat;
	}

}
