package entities;

/**
 * 
 * @author Giuseppe FEDERICO
 *
 */

public class User {

	private Integer id;
	private String email;
	private String password;
	private String firstname;
	private String name;
	
	public User(Integer id, String email, String password, String name, String firstname) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.name = name;
	}
	
	public User(String email, String password, String name, String firstname) {
		this.id = null;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.name = name;
	}	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
