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
 * Entity implementation class for Entity: Category
 *
 */
@Entity
@Table(name = "categories")
@NamedQueries({
	@NamedQuery(name=Category.ALL, query="SELECT c From Category c"),
	@NamedQuery(name=Category.BY_TYPE, query="SELECT c From Category c WHERE c.categoryType.id=:categoryTypeId"),
	@NamedQuery(name=Category.BY_USERNAME, query="SELECT c From Category c WHERE c.owner=:username"),
	@NamedQuery(name=Category.BY_USERNAME_AND_TYPE, query="SELECT c From Category c WHERE c.owner=:username AND c.categoryType.id=:categoryTypeId"),
	@NamedQuery(name=Category.DELETE_BY_ID, query="DELETE FROM Category c WHERE c.id=:id"),
	@NamedQuery(name=Category.DELETE_BY_CATEGORY_TYPE_ID, query="DELETE FROM Category c WHERE c.categoryType.id=:categoryTypeId"),
})
public class Category implements Serializable {
	
	public static final String ALL = "Category.All";
	public static final String BY_TYPE = "Category.ByType";
	public static final String BY_USERNAME = "Category.ByUsername";
	public static final String BY_USERNAME_AND_TYPE = "Category.ByUsernameAndType";
	
	public static final String DELETE_BY_ID = "Category.DeleteById";
	public static final String DELETE_BY_CATEGORY_TYPE_ID = "Category.DeleteByCategoryTypeId";

	private Integer id;
	private int data;
	private CategoryType categoryType;
	private String owner;
	private static final long serialVersionUID = 1L;

	public Category() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="categoryID")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getData() {
		return this.data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="categoryTypeId")
	public CategoryType getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(CategoryType categoryType) {
		this.categoryType = categoryType;
	}

}
