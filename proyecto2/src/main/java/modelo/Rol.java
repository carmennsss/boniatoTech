package modelo;

public class Rol {

	private int id_roles;
	private String nombre_roles;
	private String descripcion_roles;

	public Rol(int id_roles, String nombre_roles, String descripcion_roles) {
		this.id_roles = id_roles;
		this.nombre_roles = nombre_roles;
		this.descripcion_roles = descripcion_roles;
	}

	public int getId_roles() {
		return id_roles;
	}

	@Override
	public String toString() {
		return nombre_roles;
	}

	public void setId_roles(int id_roles) {
		this.id_roles = id_roles;
	}

	public String getNombre_roles() {
		return nombre_roles;
	}

	public void setNombre_roles(String nombre_roles) {
		this.nombre_roles = nombre_roles;
	}

	public String getDescripcion_roles() {
		return descripcion_roles;
	}

	public void setDescripcion_roles(String descripcion_roles) {
		this.descripcion_roles = descripcion_roles;
	}

}
