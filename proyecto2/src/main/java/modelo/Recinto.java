package modelo;

/**
 * Representa un recinto o hábitat dentro del sistema de gestión del zoológico.
 * Esta clase almacena información sobre la ubicación del recinto y métricas 
 * específicas de capacidad para el control de traslados de animales.
 */
public class Recinto {
    /** Identificador único del recinto en la base de datos. */
    private int recinto_id;

    /** Nombre descriptivo del recinto (ej. Aviario, Sabana). */
    private String nombre_recintos;

    /** Ubicación geográfica o dirección interna del recinto en el zoo. */
    private String direccion_recintos;

    /** Capacidad o cantidad actual relacionada al origen para procesos de logística. */
    private int cantidad_origen;

    /** Capacidad o cantidad relacionada al destino para la gestión de traslados. */
    private int cantidad_destino;

    /**
     * Constructor para instanciar un objeto Recinto con todos sus atributos.
     *
     * @param recinto_id         Identificador único del recinto.
     * @param nombre_recintos    Nombre asignado al hábitat.
     * @param direccion_recintos Ubicación física del recinto.
     * @param cantidad_origen    Valor métrico de origen (capacidad/población).
     * @param cantidad_destino   Valor métrico de destino (capacidad/población).
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
     * Devuelve la representación textual del recinto.
     *
     * @return El nombre del recinto, ideal para su visualización en componentes de lista.
     */
    @Override
    public String toString() {
        return nombre_recintos;
    }

    // --- GETTERS Y SETTERS AL FINAL ---

    /**
     * Obtiene el identificador único del recinto.
     * @return El ID numérico.
     */
    public int getRecinto_id() {
        return recinto_id;
    }

    /**
     * Establece el identificador único del recinto.
     * @param recinto_id El nuevo ID a asignar.
     */
    public void setRecinto_id(int recinto_id) {
        this.recinto_id = recinto_id;
    }

    /**
     * Obtiene el nombre del recinto.
     * @return Cadena con el nombre.
     */
    public String getNombre_recintos() {
        return nombre_recintos;
    }

    /**
     * Establece el nombre del recinto.
     * @param nombre_recintos El nuevo nombre descriptivo.
     */
    public void setNombre_recintos(String nombre_recintos) {
        this.nombre_recintos = nombre_recintos;
    }

    /**
     * Obtiene la dirección o ubicación del recinto.
     * @return Cadena con la dirección.
     */
    public String getDireccion_recintos() {
        return direccion_recintos;
    }

    /**
     * Establece la dirección o ubicación del recinto.
     * @param direccion_recintos La nueva ubicación física.
     */
    public void setDireccion_recintos(String direccion_recintos) {
        this.direccion_recintos = direccion_recintos;
    }

    /**
     * Obtiene la cantidad o capacidad de origen.
     * @return Valor entero de origen.
     */
    public int getCantidad_origen() {
        return cantidad_origen;
    }

    /**
     * Establece la cantidad o capacidad de origen.
     * @param cantidad_origen El nuevo valor de origen.
     */
    public void setCantidad_origen(int cantidad_origen) {
        this.cantidad_origen = cantidad_origen;
    }

    /**
     * Obtiene la cantidad o capacidad de destino.
     * @return Valor entero de destino.
     */
    public int getCantidad_destino() {
        return cantidad_destino;
    }

    /**
     * Establece la cantidad o capacidad de destino.
     * @param cantidad_destino El nuevo valor de destino.
     */
    public void setCantidad_destino(int cantidad_destino) {
        this.cantidad_destino = cantidad_destino;
    }
}