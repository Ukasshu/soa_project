package pl.agh.soa.entity;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: CategoryType
 *
 */
@Entity
@Table(name = "category_types")
@NamedQueries({ @NamedQuery(name = CategoryType.ALL, query = "SELECT c FROM CategoryType c") })
public class CategoryType implements Serializable {

	public static final String ALL = "CategoryType.All";

	private Integer id;
	private String categoryName;
	private String dataName;
	private String elementName;
	private String elementData1Name;
	private String elementData2Name;
	private static final long serialVersionUID = 1L;

	public CategoryType() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoryTypeID")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(unique = true)
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDataName() {
		return this.dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getElementData1Name() {
		return elementData1Name;
	}

	public void setElementData1Name(String elementData1Name) {
		this.elementData1Name = elementData1Name;
	}

	public String getElementData2Name() {
		return elementData2Name;
	}

	public void setElementData2Name(String elementData2Name) {
		this.elementData2Name = elementData2Name;

	}

}
