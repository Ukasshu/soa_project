package pl.agh.soa.faces;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import pl.agh.soa.entity.auth.User;

@ManagedBean(name="userService")
public class UserService {

	@PersistenceContext
	EntityManager em;

	@Resource
	UserTransaction utx;

	public User findUser(String username) {
		User user;
		try {
		user = em.createNamedQuery(User.GET_USER_BY_NAME, User.class).setParameter("username", username).getSingleResult();
		}
		catch(Exception e) {
			user = null;
		}
		return user;
	}
	
	public void saveUser(User user) {
		try {
			utx.begin();
			
			em.merge(user);
			
			utx.commit();
		} catch (Exception e) {
			System.err.println(e);
			try {
				utx.rollback();
			}catch (Exception e2) {
				System.err.println(e2);
			}
		}
	}

}
