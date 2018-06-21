package pl.agh.soa.faces.view;

import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pl.agh.soa.entity.Category;

@ManagedBean(name = "deleteCategory")
public class DeleteCategoryManagedBean {
	
	private String message;

	private String categoryId;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<SelectItem> getCategories() {
		//TODO: Uzupe³niæ o zwracanie kategorii tylko danego usera 
		
		EntityManager em = Persistence.createEntityManagerFactory("Projekt_1").createEntityManager();
		Query q = em.createQuery("FROM Category", Category.class);

		List<Category> categories = q.getResultList();

		return categories.stream().map(c -> new SelectItem(c.getId(), c.getCategoryType().getCategoryName() + "("
				+ c.getId() + ": " + c.getData() + ") [" + c.getOwner() + "]")).collect(Collectors.toList());

	}

	public void submit() {
		try {
			EntityManager em = Persistence.createEntityManagerFactory("Projekt_1").createEntityManager();
			Query query1 = em.createQuery("DELETE FROM Element WHERE categoryId = " + categoryId);
			Query query2 = em.createQuery("DELETE FROM Category WHERE categoryId = " + categoryId);

			em.getTransaction().begin();
			query1.executeUpdate();
			query2.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
