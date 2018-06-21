package pl.agh.soa.faces;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import pl.agh.soa.entity.CategoryType;

@ManagedBean(name="categoryTypeService")
@ViewScoped
public class CategoryTypeService implements Serializable{
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	EntityManager em;
	
	@Resource
	UserTransaction utx;
	
	public List<CategoryType> getAllCategoryTypes() {
		return em.createNamedQuery(CategoryType.ALL, CategoryType.class).getResultList();
	}
	
	public CategoryType findCategoryType(int id) {
		return em.find(CategoryType.class, id);
	}
	
	public void saveCategoryType(CategoryType categoryType) {
		try {
			utx.begin();
			if(categoryType.getId() == null) {
				em.persist(categoryType);
			} else {
				em.merge(categoryType);
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
