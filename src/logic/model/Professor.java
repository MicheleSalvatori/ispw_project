package logic.model;

public class Professor extends User {
	
	
	public Professor(String username, String password, String name, String surname, String email) {
		this.setUsername(username);
		this.setPassword(password);
		this.setName(name);
		this.setSurname(surname);
		this.setEmail(email);
	}
	
	public Professor() {
		
	}
	
	// Metodi unici per Professor
}
