package pl.agh.soa.faces.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.jboss.security.auth.spi.Util;

import pl.agh.soa.entity.auth.User;
import pl.agh.soa.faces.UserService;

@ManagedBean(name="changePassword")
public class ChangePasswordManagedBean {

	@ManagedProperty(value = "#{userService}")
	UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private String password1 = "";
	private String password2 = "";
	private String message;

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void submit() {
		System.out.println(password1);
		System.out.println(password1);
		if(password1.equals(password2) && !password1.equals("")) {
			User user = userService.findUser(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().toString());
			
			user.setPasswd(Util.createPasswordHash("MD5", Util.BASE64_ENCODING, null, user.getLogin(), password1));
			
			userService.saveUser(user);
			
			message = "Has³o zmienione";
			password1 = "";
			password2 = "";
		}
		else {
			message = "Has³a musz¹ byæ takie same";
		}
	}

}
