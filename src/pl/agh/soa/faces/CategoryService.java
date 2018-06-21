package pl.agh.soa.faces;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import pl.agh.soa.entity.Category;

@ManagedBean(name="categoryService")
@ViewScoped
public class CategoryService implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	EntityManager em;
	
	@Resource
	UserTransaction utx;
	
	public List<Category> getAllCategories()	{
		return em.createNamedQuery(Category.ALL, Category.class).getResultList();
	}
	
	public Category findCategory(int id) {
		return em.find(Category.class, id);
	}
	
	public List<Category> getCategoriesByType(String typeId) {
		return em.createNamedQuery(Category.BY_TYPE, Category.class).setParameter("categoryTypeId", typeId).getResultList();
	}
	
	public List<Category> getCategoriesByUsername(String username) {
		return em.createNamedQuery(Category.BY_USERNAME, Category.class).setParameter("username", username).getResultList();
	}
	
	public List<Category> getCategoriesByUsernameAndType(String username, String typeId) {
		return em.createNamedQuery(Category.BY_USERNAME_AND_TYPE, Category.class).setParameter("username", username).setParameter("categoryTypeId", typeId).getResultList();
	}
	
	public void saveCategory(Category category) {
		try {
			utx.begin();
			if(category.getId() == null) {
				em.merge(category);
			} else {
				em.merge(category);
			}
			utx.commit();
		} catch (Exception e) {
			System.err.println(e);
			try {
				utx.rollback();
			} catch (Exception e2) {
				System.err.println(e2);
			}
		}
		
	}

}
