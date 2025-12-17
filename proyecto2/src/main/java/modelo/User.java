package modelo;

public class User {

	private String username;
	private String correo;
	private String password;

	public User(String username, String correo, String password) {
		this.username = username;
		this.correo = correo;
		this.password = password;
	}

	public User(String usernameString, String correoString) {
		this.username = usernameString;
		this.correo = correoString;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
