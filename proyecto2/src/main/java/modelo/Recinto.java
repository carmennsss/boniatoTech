package modelo;

/**
 * Representa un recinto dentro del zoológico.
 */
public class Recinto {
    private int recinto_id;
    private String nombre_recintos;
    private String direccion_recintos;
    private int cantidad_origen;
    private int cantidad_destino;

    /**
     * Constructor de la clase Recinto.
     *
     * @param recinto_id         Identificador único del recinto.
     * @param nombre_recintos    Nombre del recinto.
     * @param direccion_recintos Ubicación o dirección del recinto.
     * @param cantidad_origen    Capacidad o cantidad relacionada al origen (uso
     *                           específico del dominio).
     * @param cantidad_destino   Capacidad o cantidad relacionada al destino (uso
     *                           específico del dominio).
     */
    public Recinto(int recinto_id, String nombre_recintos, String direccion_recintos, int cantidad_origen,
            int cantidad_destino) {
        this.recinto_id = recinto_id;
        this.nombre_recintos = nombre_recintos;
        this.direccion_recintos = direccion_recintos;
        this.cantidad_origen = cantidad_origen;
        this.cantidad_destino = cantidad_destino;
    }

    /**
     * Obtiene el ID del recinto.
     *
     * @return El ID del recinto.
     */
    public int getRecinto_id() {
        return recinto_id;
    }

    /**
     * Establece el ID del recinto.
     *
     * @param recinto_id El nuevo ID del recinto.
     */
    public void setRecinto_id(int recinto_id) {
        this.recinto_id = recinto_id;
    }

    /**
     * Obtiene el nombre del recinto.
     *
     * @return El nombre del recinto.
     */
    public String getNombre_recintos() {
        return nombre_recintos;
    }

    /**
     * Establece el nombre del recinto.
     *
     * @param nombre_recintos El nuevo nombre del recinto.
     */
    public void setNombre_recintos(String nombre_recintos) {
        this.nombre_recintos = nombre_recintos;
    }

    /**
     * Obtiene la dirección del recinto.
     *
     * @return La dirección del recinto.
     */
    public String getDireccion_recintos() {
        return direccion_recintos;
    }

    /**
     * Establece la dirección del recinto.
     *
     * @param direccion_recintos La nueva dirección del recinto.
     */
    public void setDireccion_recintos(String direccion_recintos) {
        this.direccion_recintos = direccion_recintos;
    }

    /**
     * Obtiene la cantidad origen.
     *
     * @return La cantidad origen.
     */
    public int getCantidad_origen() {
        return cantidad_origen;
    }

    /**
     * Establece la cantidad origen.
     *
     * @param cantidad_origen La nueva cantidad origen.
     */
    public void setCantidad_origen(int cantidad_origen) {
        this.cantidad_origen = cantidad_origen;
    }

    /**
     * Obtiene la cantidad destino.
     *
     * @return La cantidad destino.
     */
    public int getCantidad_destino() {
        return cantidad_destino;
    }

    /**
     * Establece la cantidad destino.
     *
     * @param cantidad_destino La nueva cantidad destino.
     */
    public void setCantidad_destino(int cantidad_destino) {
        this.cantidad_destino = cantidad_destino;
    }

    @Override
    public String toString() {
        return nombre_recintos;
    }
}
