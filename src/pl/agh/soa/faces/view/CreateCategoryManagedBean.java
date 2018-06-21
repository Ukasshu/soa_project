package pl.agh.soa.faces.view;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import pl.agh.soa.entity.Category;
import pl.agh.soa.entity.CategoryType;
import pl.agh.soa.faces.CategoryService;
import pl.agh.soa.faces.CategoryTypeService;

@ManagedBean(name = "createCategory")
public class CreateCategoryManagedBean {

	@ManagedProperty(value="#{categoryTypeService}")
	private CategoryTypeService categoryTypeService;
	
	@ManagedProperty(value="#{categoryService}")
	private CategoryService categoryService;
	
	
	public void setCategoryTypeService(CategoryTypeService categoryTypeService) {
		this.categoryTypeService = categoryTypeService;
	}
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	private String message;

	private String categoryTypeId;
	private Integer data;
	private String dataName = "";
	
    @PostConstruct
    public void init() {
		if (getCategoryTypes().size() > 0) {
			categoryTypeId = (String) getCategoryTypes().get(0).getValue();
			categoryTypeChanged();
		}

		categoryTypeId = "";
	}

	public String getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(String categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}

	public Integer getData() {
		return data;
	}

	public void setData(Integer data) {
		this.data = data;
	}

	public List<SelectItem> getCategoryTypes() {
		System.out.println(categoryTypeService);
		return categoryTypeService.getAllCategoryTypes().stream().map(ct -> new SelectItem("" + ct.getId(), ct.getCategoryName()))
				.collect(Collectors.toList());
	}

	public String getDataName() {
		return this.dataName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void categoryTypeChanged() {
		CategoryType ct = categoryTypeService.findCategoryType(Integer.parseInt(categoryTypeId));
		dataName = ct.getDataName();
	}

	public void submit() {
		CategoryType ct = categoryTypeService.findCategoryType(Integer.parseInt(categoryTypeId));
		
		System.out.println(ct);
		System.out.println(ct.getId());
		System.out.println(ct.getCategoryName());

		Category category = new Category();
		category.setCategoryType(ct);
		category.setData(data);
		category.setOwner(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().toString());

		categoryService.saveCategory(category);

		message = "Dodano instancjê kategorii";

	}
}
