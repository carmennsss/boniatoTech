package modelo;

/**
 * Representa un rol de usuario dentro del sistema, utilizado para la gestión de
 * permisos en el servidor FTP.
 */
public class Rol {

	/** Identificador único del rol. */
	private int id_roles;

	/** Nombre del rol. */
	private String nombre_roles;

	/** Descripción del rol y sus permisos. */
	private String descripcion_roles;

	/**
	 * Constructor de la clase Rol.
	 *
	 * @param id_roles          Identificador único del rol.
	 * @param nombre_roles      Nombre del rol.
	 * @param descripcion_roles Descripción del rol y sus permisos.
	 */
	public Rol(int id_roles, String nombre_roles, String descripcion_roles) {
		this.id_roles = id_roles;
		this.nombre_roles = nombre_roles;
		this.descripcion_roles = descripcion_roles;
	}

	/**
	 * Obtiene la descripción del rol.
	 *
	 * @return La descripción del rol.
	 */
	public String getDescripcion_roles() {
		return descripcion_roles;
	}

	/**
	 * Obtiene el ID del rol.
	 *
	 * @return El ID del rol.
	 */
	public int getId_roles() {
		return id_roles;
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
	 * Establece la descripción del rol.
	 *
	 * @param descripcion_roles La nueva descripción del rol.
	 */
	public void setDescripcion_roles(String descripcion_roles) {
		this.descripcion_roles = descripcion_roles;
	}

	/**
	 * Establece el ID del rol.
	 *
	 * @param id_roles El nuevo ID del rol.
	 */
	public void setId_roles(int id_roles) {
		this.id_roles = id_roles;
	}

	/**
	 * Establece el nombre del rol.
	 *
	 * @param nombre_roles El nuevo nombre del rol.
	 */
	public void setNombre_roles(String nombre_roles) {
		this.nombre_roles = nombre_roles;
	}

	@Override
	public String toString() {
		return nombre_roles;
	}

}
