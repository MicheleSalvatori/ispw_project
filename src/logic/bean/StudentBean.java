package logic.bean;

public class StudentBean {
	
	private String sbUsername;
	private String sbPassword;
	private String sbName;
	private String sbSurname;
	private String sbEmail;
	
	public String getUsername() {
		return sbUsername;
	}

	public void setUsername(String username) {
		this.sbUsername = username;
	}

	public String getPassword() {
		return sbPassword;
	}

	public void setPassword(String password) {
		this.sbPassword = password;
	}

	public String getName() {
		return sbName;
	}

	public void setName(String name) {
		this.sbName = name;
	}
	
	public String getSurname() {
		return sbSurname;
	}

	public void setSurname(String surname) {
		this.sbSurname = surname;
	}

	public String getEmail() {
		return sbEmail;
	}

	public void setEmail(String email) {
		this.sbEmail = email;
	}
}
