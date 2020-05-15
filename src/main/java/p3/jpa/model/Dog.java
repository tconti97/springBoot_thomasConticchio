/**
 * 
 */
package p3.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tcont
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "dogs")
@EqualsAndHashCode
public class Dog 
{
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String type;
	
	public Dog(String _name, String _type)
	{
		super();
		name = _name;
		type = _type;
	}
}
