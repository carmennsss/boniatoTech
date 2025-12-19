package modelo;

/**
 * Representa un usuario del sistema (no confundir con un cuidador o empleado
 * del zoo, sino un usuario de la aplicación).
 */
public class User {

	String nombre;
	String correo;
	String claveCorreo;
	String contrasena;

	/**
	 * Constructor completo de la clase User.
	 *
	 * @param nombre      Nombre de usuario.
	 * @param correo      Dirección de correo electrónico.
	 * @param claveCorreo Clave específica para servicios de correo (ej. contraseña
	 *                    de aplicación).
	 * @param contrasena  Contraseña de acceso al sistema.
	 */
	public User(String nombre, String correo, String claveCorreo, String contrasena) {
		this.nombre = nombre;
		this.correo = correo;
		this.claveCorreo = claveCorreo;
		this.contrasena = contrasena;
	}

	/**
	 * Constructor simplificado para User (solo nombre y correo).
	 *
	 * @param nombre Nombre de usuario.
	 * @param correo Dirección de correo electrónico.
	 */
	public User(String nombre, String correo) {
		this.nombre = nombre;
		this.correo = correo;
	}

	/**
	 * Obtiene el nombre de usuario.
	 *
	 * @return El nombre de usuario.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre de usuario.
	 *
	 * @param nombre El nuevo nombre de usuario.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el correo electrónico.
	 *
	 * @return El correo.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo electrónico.
	 *
	 * @param correo El nuevo correo.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene la contraseña de acceso.
	 *
	 * @return La contraseña.
	 */
	public String getPassword() {
		return contrasena;
	}

	/**
	 * Establece la contraseña de acceso.
	 *
	 * @param contrasena La nueva contraseña.
	 */
	public void setPassword(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * Obtiene la clave específica para el correo.
	 *
	 * @return La clave del correo.
	 */
	public String getClaveCorreo() {
		return claveCorreo;
	}

	/**
	 * Establece la clave específica para el correo.
	 *
	 * @param claveCorreo La nueva clave del correo.
	 */
	public void setClaveCorreo(String claveCorreo) {
		this.claveCorreo = claveCorreo;
	}

	/**
	 * Obtiene la contraseña de acceso (alias de getPassword).
	 *
	 * @return La contraseña.
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Establece la contraseña de acceso (alias de setPassword).
	 *
	 * @param contrasena La nueva contraseña.
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
