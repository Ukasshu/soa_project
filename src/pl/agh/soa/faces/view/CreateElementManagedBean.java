package pl.agh.soa.faces.view;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import pl.agh.soa.entity.Category;
import pl.agh.soa.entity.CategoryType;
import pl.agh.soa.entity.Element;
import pl.agh.soa.faces.CategoryService;
import pl.agh.soa.faces.CategoryTypeService;
import pl.agh.soa.faces.ElementService;

@ManagedBean(name = "createElement")
public class CreateElementManagedBean {

	@ManagedProperty(value = "#{categoryTypeService}")
	CategoryTypeService categoryTypeService;

	@ManagedProperty(value = "#{categoryService}")
	CategoryService categoryService;

	@ManagedProperty(value = "#{elementService}")
	ElementService elementService;

	public void setCategoryTypeService(CategoryTypeService categoryTypeService) {
		this.categoryTypeService = categoryTypeService;
	}

	public void setElementService(ElementService elementService) {
		this.elementService = elementService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	private String message;

	private String categoryTypeId;
	private String categoryId;

	private String field1Name;
	private String field2Name;

	private String name;
	private Integer field1;
	private Integer field2;
	private Integer power;

	@PostConstruct
	public void init() {
		if (getCategoryTypes().size() > 0) {
			this.categoryTypeId = getCategoryTypes().get(0).getValue().toString();
			categoryTypeChanged();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getField1() {
		return field1;
	}

	public void setField1(Integer field1) {
		this.field1 = field1;
	}

	public Integer getField2() {
		return field2;
	}

	public void setField2(Integer field2) {
		this.field2 = field2;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getField1Name() {
		return field1Name;
	}

	public String getField2Name() {
		return field2Name;
	}

	public List<SelectItem> getCategoryTypes() {

		return categoryTypeService.getAllCategoryTypes().stream()
				.map(ct -> new SelectItem(ct.getId(), ct.getCategoryName())).collect(Collectors.toList());

	}

	public List<SelectItem> getCategories() {

		return categoryService
				.getCategoriesByUsernameAndType(
						FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().toString(),
						Integer.parseInt(this.categoryTypeId))
				.stream().map(c -> new SelectItem(c.getId(), c.getCategoryType().getCategoryName() + "(" + c.getId()
						+ ": " + c.getData() + ") [" + c.getOwner() + "]"))
				.collect(Collectors.toList());

	}

	public void categoryTypeChanged() {
		CategoryType ct = categoryTypeService.findCategoryType(Integer.parseInt(categoryTypeId));

		field1Name = ct.getElementData1Name();
		field2Name = ct.getElementData2Name();
	}

	public void submit() {

		Category category = categoryService.findCategory(Integer.parseInt(categoryId));

		Element element = new pl.agh.soa.entity.Element();
		element.setName(this.name);
		element.setData1(this.field1);
		element.setData2(this.field2);
		element.setPower(this.power);
		element.setCategory(category);

		elementService.saveElement(element);

		message = "Dodano element";

	}
}
