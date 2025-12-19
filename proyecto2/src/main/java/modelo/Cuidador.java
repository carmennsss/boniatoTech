package modelo;

/**
 * Representa un cuidador en el sistema del zoológico.
 */
public class Cuidador {
    private int cuidador_id;
    private String nombre_cuidadores;
    private String direccion_cuidadores;

    /**
     * Constructor de la clase Cuidador.
     *
     * @param cuidador_id          Identificador único del cuidador.
     * @param nombre_cuidadores    Nombre del cuidador.
     * @param direccion_cuidadores Dirección del cuidador.
     */
    public Cuidador(int cuidador_id, String nombre_cuidadores, String direccion_cuidadores) {
        this.cuidador_id = cuidador_id;
        this.nombre_cuidadores = nombre_cuidadores;
        this.direccion_cuidadores = direccion_cuidadores;
    }

    /**
     * Obtiene el ID del cuidador.
     *
     * @return El ID del cuidador.
     */
    public int getCuidador_id() {
        return cuidador_id;
    }

    /**
     * Establece el ID del cuidador.
     *
     * @param cuidador_id El nuevo ID del cuidador.
     */
    public void setCuidador_id(int cuidador_id) {
        this.cuidador_id = cuidador_id;
    }

    /**
     * Obtiene el nombre del cuidador.
     *
     * @return El nombre del cuidador.
     */
    public String getNombre_cuidadores() {
        return nombre_cuidadores;
    }

    /**
     * Establece el nombre del cuidador.
     *
     * @param nombre_cuidadores El nuevo nombre del cuidador.
     */
    public void setNombre_cuidadores(String nombre_cuidadores) {
        this.nombre_cuidadores = nombre_cuidadores;
    }

    /**
     * Obtiene la dirección del cuidador.
     *
     * @return La dirección del cuidador.
     */
    public String getDireccion_cuidadores() {
        return direccion_cuidadores;
    }

    /**
     * Establece la dirección del cuidador.
     *
     * @param direccion_cuidadores La nueva dirección del cuidador.
     */
    public void setDireccion_cuidadores(String direccion_cuidadores) {
        this.direccion_cuidadores = direccion_cuidadores;
    }

    @Override
    public String toString() {
        return nombre_cuidadores;
    }
}
