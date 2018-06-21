package pl.agh.soa.faces.view;

import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.persistence.Query;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import pl.agh.soa.entity.Category;
import pl.agh.soa.entity.CategoryType;
import pl.agh.soa.entity.Element;

@ManagedBean(name = "createElement")
public class CreateElementManagedBean {
	
	private String message;

	private String categoryTypeId;
	private String categoryId;

	private String field1Name;
	private String field2Name;

	private String name;
	private Integer field1;
	private Integer field2;
	private Integer power;

	{
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
		EntityManager em = Persistence.createEntityManagerFactory("Projekt_1").createEntityManager();

		Query query = em.createQuery("FROM CategoryType", CategoryType.class);

		List<CategoryType> cts = query.getResultList();

		return cts.stream().map(ct -> new SelectItem(ct.getId(), ct.getCategoryName())).collect(Collectors.toList());

	}

	public List<SelectItem> getCategories() {
		//TODO: Uzupe³niæ o zwracanie tylko kategorii danego usera
		
		EntityManager em = Persistence.createEntityManagerFactory("Projekt_1").createEntityManager();

		Query q = em.createQuery("FROM Category WHERE categoryTypeId = " + categoryTypeId, Category.class);

		List<Category> categories = q.getResultList();

		return categories.stream().map(c -> new SelectItem(c.getId(), c.getCategoryType().getCategoryName() + "("
				+ c.getId() + ": " + c.getData() + ") [" + c.getOwner() + "]")).collect(Collectors.toList());

	}

	public void categoryTypeChanged() {
		EntityManager em = Persistence.createEntityManagerFactory("Projekt_1").createEntityManager();

		Query query = em.createQuery("FROM CategoryType WHERE id=" + categoryTypeId, CategoryType.class);

		List<CategoryType> ct = query.getResultList();

		field1Name = ct.get(0).getElementData1Name();
		field2Name = ct.get(0).getElementData2Name();
	}

	public void submit() {
		EntityManager em = Persistence.createEntityManagerFactory("Projekt_1").createEntityManager();

		Query q = em.createQuery("FROM Category WHERE id=" + categoryId, Category.class);

		List<Category> cs = q.getResultList();

		Category category = cs.get(0);
		
		Element element = new pl.agh.soa.entity.Element();
		element.setName(this.name);
		element.setData1(this.field1);
		element.setData2(this.field2);
		element.setPower(this.power);
		element.setCategory(category);
		
		em.getTransaction().begin();;
		em.persist(element);
		em.getTransaction().commit();
		message="Dodano element";
		
	}
}
