package bazhen.examples.warehouse.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity implementation class for Entity: Material
 * 
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NamedQuery(name = "findMaterialByName", query = "select m FROM Material m WHERE m.name = :name")
public class Material implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	@Id
	@GeneratedValue
	private int id;

	public Material() {
		super();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
