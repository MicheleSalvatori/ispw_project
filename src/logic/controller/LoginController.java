package logic.controller;

import logic.Session;
import logic.bean.UserBean;
import logic.model.User;

public class LoginController {

	
	public boolean login(UserBean usrBean) {
		User user = new User();
		user.setPassword(usrBean.getUsbPassword());
		user.setUsername(usrBean.getUsbUsername());
		
		Session.getSession().setUserLogged(user);
		return true;
	}
}
