package pl.agh.soa.faces.view;

import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;

import pl.agh.soa.faces.CategoryTypeService;

@ManagedBean(name = "deleteCategoryType")
public class DeleteCategoryTypeManagedBean {
	
	@ManagedProperty(value="#{categoryTypeService}")
	private CategoryTypeService categoryTypeService;
	
	private String message;

	private String categoryTypeId;
	
	public void setCategoryTypeService(CategoryTypeService categoryTypeService) {
        this.categoryTypeService = categoryTypeService;
    }

	public String getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(String categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<SelectItem> getCategoryTypes() {
		return categoryTypeService.getAllCategoryTypes().stream().map(ct -> new SelectItem("" + ct.getId(), ct.getCategoryName()))
				.collect(Collectors.toList());
	}

	public void submit() {
		categoryTypeService.deleteById(Integer.parseInt(this.categoryTypeId));
		
		message = "Usuniêto";
	}

}
