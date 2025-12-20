package modelo;

/**
 * Representa un usuario autenticado en el sistema.
 * Almacena las credenciales de acceso a la aplicación y los parámetros
 * necesarios para la integración con servicios de correo electrónico.
 */
public class User {

	/** Nombre de usuario para la identificación en el sistema. */
	private String nombre;

	/** Dirección de correo electrónico asociada a la cuenta. */
	private String correo;

	/** Clave específica de aplicación para servicios de correo (ej. SMTP/POP3). */
	private String claveCorreo;

	/** Contraseña principal para el acceso a la aplicación. */
	private String contrasena;

	/**
	 * Constructor completo para la creación de un nuevo usuario con todos sus parámetros.
	 *
	 * @param nombre      Nombre de usuario único.
	 * @param correo      Dirección de correo electrónico.
	 * @param claveCorreo Clave específica para el servidor de correo.
	 * @param contrasena  Contraseña de acceso al sistema.
	 */
	public User(String nombre, String correo, String claveCorreo, String contrasena) {
		this.nombre = nombre;
		this.correo = correo;
		this.claveCorreo = claveCorreo;
		this.contrasena = contrasena;
	}

	/**
	 * Constructor simplificado para la representación básica de un usuario.
	 *
	 * @param nombre Nombre de usuario.
	 * @param correo Dirección de correo electrónico.
	 */
	public User(String nombre, String correo) {
		this.nombre = nombre;
		this.correo = correo;
	}

	// --- GETTERS Y SETTERS AL FINAL ---

	/**
	 * Obtiene la clave específica para los servicios de correo.
	 *
	 * @return La clave del correo en formato cadena.
	 */
	public String getClaveCorreo() {
		return claveCorreo;
	}

	/**
	 * Establece la clave específica para los servicios de correo.
	 *
	 * @param claveCorreo La nueva clave de aplicación para el correo.
	 */
	public void setClaveCorreo(String claveCorreo) {
		this.claveCorreo = claveCorreo;
	}

	/**
	 * Obtiene la contraseña de acceso al sistema (alias de getPassword).
	 *
	 * @return La contraseña del usuario.
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Establece la contraseña de acceso al sistema (alias de setPassword).
	 *
	 * @param contrasena La nueva contraseña a asignar.
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * Obtiene la dirección de correo electrónico del usuario.
	 *
	 * @return El correo electrónico.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece la dirección de correo electrónico del usuario.
	 *
	 * @param correo El nuevo correo electrónico.
	 */
	public void setCorreo(String correo) {
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
	 * @param nombre El nuevo nombre de identificación.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la contraseña de acceso.
	 *
	 * @return La contraseña actual.
	 */
	public String getPassword() {
		return contrasena;
	}

	/**
	 * Establece la contraseña de acceso.
	 *
	 * @param contrasena La nueva contraseña de acceso.
	 */
	public void setPassword(String contrasena) {
		this.contrasena = contrasena;
	}
}