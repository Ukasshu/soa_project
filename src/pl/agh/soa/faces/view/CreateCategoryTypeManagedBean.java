package pl.agh.soa.faces.view;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import pl.agh.soa.entity.CategoryType;

@ManagedBean(name = "createCategoryType")
public class CreateCategoryTypeManagedBean {
	
	private String message="";

	private String categoryName = "";
	private String dataName = "";
	private String elementName = "";
	private String elementData1Name = "";
	private String elementData2Name = "";

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDataName() {
		return dataName;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void submit() {
		
		EntityManager em = Persistence.createEntityManagerFactory("Projekt_1").createEntityManager();
		CategoryType ct = new CategoryType();
		ct.setCategoryName(this.categoryName);
		ct.setDataName(this.dataName);
		ct.setElementName(this.elementName);
		ct.setElementData1Name(this.elementData1Name);
		ct.setElementData2Name(this.elementData2Name);
		try {
			em.getTransaction().begin();
			em.persist(ct);
			em.getTransaction().commit();
			message = "Dodano kategoriê";
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			this.categoryName = "";
			this.dataName = "";
			this.elementName = "";
			this.elementData1Name = "";
			this.elementData2Name = "";
		}
	}
}
