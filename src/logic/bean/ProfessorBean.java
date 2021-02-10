package logic.bean;

public class ProfessorBean {
	
	private String pbSsername;
	private String pbPassword;
	private String pbName;
	private String pbSurname;
	private String pbEmail;
	
	public String getUsername() {
		return pbSsername;
	}

	public void setUsername(String username) {
		this.pbSsername = username;
	}

	public String getPassword() {
		return pbPassword;
	}

	public void setPassword(String password) {
		this.pbPassword = password;
	}

	public String getName() {
		return pbName;
	}

	public void setName(String name) {
		this.pbName = name;
	}
	
	public String getSurname() {
		return pbSurname;
	}

	public void setSurname(String surname) {
		this.pbSurname = surname;
	}

	public String getEmail() {
		return pbEmail;
	}

	public void setEmail(String email) {
		this.pbEmail = email;
	}
}
