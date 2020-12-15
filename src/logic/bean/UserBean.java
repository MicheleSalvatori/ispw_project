package logic.bean;


/*
 * Singleton UserBean
 */

public class UserBean {
	private static UserBean usbInstance = null;
	private String usbUsername;
	private String usbPassword;
	
	
	public static UserBean getUserInstance(String username) {
		if (UserBean.usbInstance == null) {
			UserBean.usbInstance = new UserBean();
			UserBean.usbInstance.usbUsername = username;
		}
		return usbInstance;
	}
	
	public static UserBean getUsbInstance() {
		return usbInstance;
	}
	
	public static void setInstance(UserBean userBean) {
		UserBean.usbInstance = userBean;
	}

	public String getUsbUsername() {
		return usbUsername;
	}

	public void setUsbUsername(String usbUsername) {
		this.usbUsername = usbUsername;
	}

	public String getUsbPassword() {
		return usbPassword;
	}

	public void setUsbPassword(String usbPassword) {
		this.usbPassword = usbPassword;
	}
	
	
}
