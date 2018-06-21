package pl.agh.soa.faces;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import pl.agh.soa.entity.Category;
import pl.agh.soa.entity.CategoryType;
import pl.agh.soa.entity.Element;

@ManagedBean(name = "categoryTypeService")
@ViewScoped
public class CategoryTypeService implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	EntityManager em;

	@Resource
	UserTransaction utx;
	
	@ManagedProperty(value="#{categoryService}")
	CategoryService categoryService;
	
	@ManagedProperty(value="#{elementService}")
	ElementService elementService;
	

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setElementService(ElementService elementService) {
		this.elementService = elementService;
	}

	public List<CategoryType> getAllCategoryTypes() {
		return em.createNamedQuery(CategoryType.ALL, CategoryType.class).getResultList();
	}

	public CategoryType findCategoryType(int id) {
		return em.find(CategoryType.class, id);
	}

	public void saveCategoryType(CategoryType categoryType) {
		try {
			utx.begin();
			if (categoryType.getId() == null) {
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

	public void deleteById(Integer id) {
		try {
			List<Element> elements = elementService.getElementsByCategoryTypeId(id);
			List<Category> categories = categoryService.getCategoriesByType(id);
			CategoryType ct = findCategoryType(id);
			
			utx.begin();
			elements.stream().forEach(e -> em.remove(em.merge(e)));
			categories.stream().forEach(c -> em.remove(em.merge(c)));
			
			em.remove(em.merge(ct));
				
			utx.commit();
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			try {
				utx.rollback();
			} catch (Exception e2) {
				System.err.println(e2);
				e.printStackTrace();
			}
		}

	}
}
