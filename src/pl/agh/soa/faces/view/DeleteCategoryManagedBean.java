package pl.agh.soa.faces.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import pl.agh.soa.faces.CategoryService;
import pl.agh.soa.faces.CategoryTypeService;

@ManagedBean(name = "deleteCategory")
@RequestScoped
public class DeleteCategoryManagedBean {

	@ManagedProperty(value = "#{categoryTypeService}")
	private CategoryTypeService categoryTypeService;

	@ManagedProperty(value = "#{categoryService}")
	private CategoryService categoryService;

	@PostConstruct
	public void init() {
		if(getCategoryTypes().size()>0) {
			categoryTypeId = (String) getCategoryTypes().get(0).getValue();			
		}
	}
	
	private String message;

	private String categoryTypeId;

	private String categoryId;

	public void categoryTypeChanged() {
		
	}
	public List<SelectItem> getCategories() {
		if(this.categoryTypeId != null) {
		return categoryService
				.getCategoriesByUsernameAndType(
						FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().toString(),
						Integer.parseInt(this.categoryTypeId))
				.stream().map(c -> new SelectItem(c.getId(), c.getCategoryType().getCategoryName() + "(" + c.getId()
						+ ": " + c.getData() + ") [" + c.getOwner() + "]"))
				.collect(Collectors.toList());
		}
		else {
			return new ArrayList<>();
		}

	}

	public String getCategoryId() {
		return categoryId;
	}

	public String getCategoryTypeId() {
		return categoryTypeId;
	}

	public List<SelectItem> getCategoryTypes() {
		System.out.println(categoryTypeService);
		return categoryTypeService.getAllCategoryTypes().stream()
				.map(ct -> new SelectItem("" + ct.getId(), ct.getCategoryName())).collect(Collectors.toList());
	}

	public String getMessage() {
		return message;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setCategoryTypeId(String categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}

	public void setCategoryTypeService(CategoryTypeService categoryTypeService) {
		this.categoryTypeService = categoryTypeService;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void submit() {
		categoryService.deleteById(Integer.parseInt(this.categoryId));
		
		message = "Usuniêto";
	}

}
