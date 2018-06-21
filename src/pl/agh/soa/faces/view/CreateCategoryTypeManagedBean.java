package pl.agh.soa.faces.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import pl.agh.soa.entity.CategoryType;
import pl.agh.soa.faces.CategoryTypeService;

@ManagedBean(name = "createCategoryType")
public class CreateCategoryTypeManagedBean {
	
	@ManagedProperty(value="#{categoryTypeService}")
	private CategoryTypeService categoryTypeService;
	
	public void setCategoryTypeService(CategoryTypeService categoryTypeService) {
		this.categoryTypeService = categoryTypeService;
	}
	
	
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
		
		
		CategoryType ct = new CategoryType();
		ct.setCategoryName(this.categoryName);
		ct.setDataName(this.dataName);
		ct.setElementName(this.elementName);
		ct.setElementData1Name(this.elementData1Name);
		ct.setElementData2Name(this.elementData2Name);
		
		categoryTypeService.saveCategoryType(ct);
		
		this.categoryName = "";
		this.dataName = "";
		this.elementName = "";
		this.elementData1Name = "";
		this.elementData2Name = "";
		
		this.message = "Dodano";
	}
}
