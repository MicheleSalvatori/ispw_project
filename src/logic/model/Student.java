package logic.model;

public class Student extends User{
	
	public Student(String username, String password, String name, String surname, String email) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}
	
	public Student() {
		
	}

	// Metodi unici per student
	
}
