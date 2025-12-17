package modelo;

public class User {

	String nombre;
	String correo;
	String claveCorreo;
	String contrasena;

	public User(String nombre, String correo, String claveCorreo, String contrasena) {
		this.nombre = nombre;
		this.correo = correo;
		this.claveCorreo = claveCorreo;
		this.contrasena = contrasena;
	}

	public User(String nombre, String correo) {
		this.nombre = nombre;
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return contrasena;
	}

	public void setPassword(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getClaveCorreo() {
		return claveCorreo;
	}

	public void setClaveCorreo(String claveCorreo) {
		this.claveCorreo = claveCorreo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
