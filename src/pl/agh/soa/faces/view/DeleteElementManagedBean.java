package pl.agh.soa.faces.view;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import pl.agh.soa.faces.CategoryService;
import pl.agh.soa.faces.CategoryTypeService;
import pl.agh.soa.faces.ElementService;

@ManagedBean(name = "deleteElement")
public class DeleteElementManagedBean {
	
	@ManagedProperty(value="#{categoryTypeService}")
	private CategoryTypeService categoryTypeService;
	
	@ManagedProperty(value="#{categoryService}")
	private CategoryService categoryService;
	
	@ManagedProperty(value="#{elementService}")
	private ElementService elementService;
	
	public void setCategoryTypeService(CategoryTypeService categoryTypeService) {
		this.categoryTypeService = categoryTypeService;
	}
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	public void setElementService(ElementService elementService) {
		this.elementService = elementService;
	}
	

	private String message;

	private String categoryTypeId;
	private String categoryId;
	private String elementId;
	
	{
		if(getCategoryTypes().size()>0) {
			categoryTypeId = (String)getCategoryTypes().get(0).getValue();
			if(getCategories().size()>0) {
				categoryId = (String)getCategories().get(0).getValue();
				if (getElements().size()>0) {
					elementId = (String) getElements().get(0).getValue();
				}
			}
		}
	}

	public String getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(String categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<SelectItem> getCategoryTypes() {
		return categoryTypeService.getAllCategoryTypes().stream().map(c -> new SelectItem(Integer.toString(c.getId()), c.getCategoryName()))
				.collect(Collectors.toList());

	}

	public List<SelectItem> getCategories() {
		return categoryService.getCategoriesByUsername(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().toString()).stream()
				.map(c -> new SelectItem(Integer.toString(c.getId()),
						c.getCategoryType().getCategoryName() + " [id: " + c.getId() + ", "
								+ c.getCategoryType().getDataName() + "= " + c.getData() + "]"))
				.collect(Collectors.toList());
	}
	
	public List<SelectItem> getElements() {
		return elementService.getElementsByCategoryId(this.categoryId).stream().map(e -> new SelectItem(Integer.toString(e.getId()), e.getName()+" ["+e.getData1()+"/"+e.getData2()+"/"+e.getPower()+"]")).collect(Collectors.toList());
	}
	
	public void categoryTypeChanged() {
		
	}
	
	public void categoryChanged() {
		
	}
	
	public void submit() {
		
		elementService.deleteById(this.elementId);
				
		message = "Usuniêto element " + new Date().toString(); 
		
	}

}
