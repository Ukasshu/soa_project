package pl.agh.soa.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Element
 *
 */
@Entity
@Table(name = "elements")
@NamedQueries({
	@NamedQuery(name=Element.ALL, query="SELECT e FROM Element e"),
	@NamedQuery(name=Element.BY_CATEGORY_ID, query="SELECT e FROM Element e WHERE e.category.id=:categoryId"),
	@NamedQuery(name=Element.BY_CATEGORY_TYPE_ID, query="SELECT e FROM Element e WHERE e.category.categoryType.id=:categoryTypeId"),
	@NamedQuery(name=Element.BY_USERNAME, query="SELECT e FROM Element e WHERE e.category.owner=:username"),
	@NamedQuery(name=Element.BY_CATEGORY_TYPE_ID_AND_USERNAME, query="SELECT e FROM Element e WHERE e.category.id=:categoryId AND e.category.owner=:username")
})
public class Element implements Serializable {
	
	public static final String ALL = "Element.All";
	public static final String BY_CATEGORY_ID = "Element.ByCategoryId";
	public static final String BY_CATEGORY_TYPE_ID = "Element.ByCategoryTypeId";
	public static final String BY_USERNAME = "Element.Username";
	public static final String BY_CATEGORY_TYPE_ID_AND_USERNAME = "Element.ByCategoryTypeIdAndUsername";
	
	
	private Integer id;
	private String name;
	private int power;
	private int data1;
	private int data2;
	private Category category;
	private static final long serialVersionUID = 1L;

	public Element() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="elementID")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPower() {
		return this.power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getData1() {
		return this.data1;
	}

	public void setData1(int data1) {
		this.data1 = data1;
	}

	public int getData2() {
		return this.data2;
	}

	public void setData2(int data2) {
		this.data2 = data2;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="categoryId")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
