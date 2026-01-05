package modelo;

/**
 * Representa un rol de usuario dentro del sistema.
 * Se utiliza para la gestión de niveles de acceso y permisos, tanto en la base
 * de datos como en la configuración de usuarios del servidor FTP.
 */
public class Rol {

	/** Identificador único del rol en la base de datos. */
	private int id_roles;

	/** Nombre descriptivo del rol (ej. Administrador, Cuidador). */
	private String nombre_roles;

	/** Explicación detallada de las funciones y alcances del rol. */
	private String descripcion_roles;

	/**
	 * Constructor para instanciar un objeto Rol con todos sus atributos.
	 *
	 * @param id_roles          Identificador numérico único del rol.
	 * @param nombre_roles      Nombre asignado al rol.
	 * @param descripcion_roles Descripción de las capacidades del rol.
	 */
	public Rol(int id_roles, String nombre_roles, String descripcion_roles) {
		this.id_roles = id_roles;
		this.nombre_roles = nombre_roles;
		this.descripcion_roles = descripcion_roles;
	}

	/**
	 * Devuelve la representación textual del rol.
	 * * @return El nombre del rol, facilitando su uso en componentes de interfaz gráfica.
	 */
	@Override
	public String toString() {
		return nombre_roles;
	}

	// --- GETTERS Y SETTERS AL FINAL ---

	/**
	 * Obtiene la descripción detallada del rol.
	 *
	 * @return Cadena de texto con la descripción.
	 */
	public String getDescripcion_roles() {
		return descripcion_roles;
	}

	/**
	 * Establece una nueva descripción para el rol.
	 *
	 * @param descripcion_roles La nueva descripción a asignar.
	 */
	public void setDescripcion_roles(String descripcion_roles) {
		this.descripcion_roles = descripcion_roles;
	}

	/**
	 * Obtiene el identificador único del rol.
	 *
	 * @return El ID del rol.
	 */
	public int getId_roles() {
		return id_roles;
	}

	/**
	 * Establece el identificador único del rol.
	 *
	 * @param id_roles El nuevo ID numérico.
	 */
	public void setId_roles(int id_roles) {
		this.id_roles = id_roles;
	}

	/**
	 * Obtiene el nombre del rol.
	 *
	 * @return El nombre del rol.
	 */
	public String getNombre_roles() {
		return nombre_roles;
	}

	/**
	 * Establece el nombre del rol.
	 *
	 * @param nombre_roles El nuevo nombre para el rol.
	 */
	public void setNombre_roles(String nombre_roles) {
		this.nombre_roles = nombre_roles;
	}
}