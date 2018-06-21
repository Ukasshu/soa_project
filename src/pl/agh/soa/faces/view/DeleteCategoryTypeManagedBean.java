package pl.agh.soa.faces.view;

import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pl.agh.soa.entity.Category;
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
		EntityManager em = Persistence.createEntityManagerFactory("Projekt_1").createEntityManager();

		Query q = em.createQuery("FROM Category WHERE categoryTypeId = " + categoryTypeId, Category.class);
		List<Category> cs = q.getResultList();

		Query deleteQuery2 = em.createQuery("DELETE FROM Category WHERE categoryTypeId = " + categoryTypeId);
		

		Query deleteQuery3 = em.createQuery("DELETE FROM CategoryType WHERE categoryTypeId = " + categoryTypeId);
		em.getTransaction().begin();
		cs.stream().forEach(c -> em.createQuery("DELETE Element WHERE categoryId = " + c.getId()).executeUpdate());
		deleteQuery2.executeUpdate();
		deleteQuery3.executeUpdate();
		em.getTransaction().commit();

	}

}
