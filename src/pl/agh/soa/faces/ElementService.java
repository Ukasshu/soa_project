package pl.agh.soa.faces;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import pl.agh.soa.entity.Element;

@ManagedBean(name="elementService")
@ViewScoped
public class ElementService implements Serializable{
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	EntityManager em;
	
	@Resource
	UserTransaction utx;
	
	public List<Element> getAllElements() {
		return em.createNamedQuery(Element.ALL, Element.class).getResultList();
	}
	
	public Element findElement(int id) {
		return em.find(Element.class, id);
	}
	
	public List<Element> getElementsByCategoryTypeId(Integer typeId){
		return em.createNamedQuery(Element.BY_CATEGORY_TYPE_ID, Element.class).setParameter("categoryTypeId", typeId).getResultList();
	}
	
	public List<Element> getElementsByCategoryId(Integer catId) {
		return em.createNamedQuery(Element.BY_CATEGORY_ID, Element.class).setParameter("categoryId", catId).getResultList();
	}
	
	public List<Element> getElementsByUsername(String username){
		return em.createNamedQuery(Element.BY_USERNAME, Element.class).setParameter("username", username).getResultList();
	}
	
	public List<Element> getElementsByCategoryTypeIdAndUsername(Integer typeId, Integer username){
		return em.createNamedQuery(Element.BY_CATEGORY_TYPE_ID_AND_USERNAME, Element.class).setParameter("categoryTypeId", typeId).setParameter("username", username).getResultList();
	}
	
	public void deleteById(Integer id) {
		try {
			Element element = findElement(id);
	
			utx.begin();
			
			em.remove(em.merge(element));
			
			utx.commit();
		}catch(Exception e) {
			System.err.println(e);
			try {
			utx.rollback();
			} catch (Exception e2) {
				System.err.println(e2);
			}
		}
	}
	
	
	public void saveElement(Element element) {
		try {
			utx.begin();
			if(element.getId() == null) {
				em.merge(element);
			} else {
				em.merge(element);
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
