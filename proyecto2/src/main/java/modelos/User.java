package modelos;

public class User {
	
	private String username;
	private String correo;
	private String password;
	private Rol rol;
	
	
	public User(String username, String correo, String password, Rol rol) {
		this.username = username;
		this.correo = correo;
		this.password = password;
		this.rol = rol;
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
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	
	

}
