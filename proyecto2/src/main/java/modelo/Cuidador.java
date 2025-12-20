package modelo;

/**
 * Representa a un trabajador encargado del cuidado de los animales en el zoológico.
 * Esta clase almacena la información básica de identificación y contacto de los cuidadores.
 */
public class Cuidador {
    /** Identificador único del cuidador en la base de datos. */
    private int cuidador_id;

    /** Nombre completo del cuidador. */
    private String nombre_cuidadores;

    /** Dirección postal o de residencia del cuidador. */
    private String direccion_cuidadores;

    /**
     * Constructor para instanciar un nuevo Cuidador con todos sus atributos.
     *
     * @param cuidador_id          Identificador único asignado al cuidador.
     * @param nombre_cuidadores    Nombre y apellidos del trabajador.
     * @param direccion_cuidadores Domicilio actual del trabajador.
     */
    public Cuidador(int cuidador_id, String nombre_cuidadores, String direccion_cuidadores) {
        this.cuidador_id = cuidador_id;
        this.nombre_cuidadores = nombre_cuidadores;
        this.direccion_cuidadores = direccion_cuidadores;
    }

    /**
     * Obtiene el identificador único del cuidador.
     *
     * @return El ID numérico del cuidador.
     */
    public int getCuidador_id() {
        return cuidador_id;
    }

    /**
     * Obtiene la dirección registrada del cuidador.
     *
     * @return Una cadena de texto con la dirección.
     */
    public String getDireccion_cuidadores() {
        return direccion_cuidadores;
    }

    /**
     * Obtiene el nombre completo del cuidador.
     *
     * @return El nombre del cuidador.
     */
    public String getNombre_cuidadores() {
        return nombre_cuidadores;
    }

    /**
     * Establece o modifica el identificador único del cuidador.
     *
     * @param cuidador_id El nuevo ID numérico a asignar.
     */
    public void setCuidador_id(int cuidador_id) {
        this.cuidador_id = cuidador_id;
    }

    /**
     * Establece o modifica la dirección de residencia del cuidador.
     *
     * @param direccion_cuidadores La nueva dirección del cuidador.
     */
    public void setDireccion_cuidadores(String direccion_cuidadores) {
        this.direccion_cuidadores = direccion_cuidadores;
    }

    /**
     * Establece o modifica el nombre del cuidador.
     *
     * @param nombre_cuidadores El nombre completo corregido o actualizado.
     */
    public void setNombre_cuidadores(String nombre_cuidadores) {
        this.nombre_cuidadores = nombre_cuidadores;
    }

    /**
     * Devuelve una representación en cadena del cuidador.
     * Habitualmente se utiliza para mostrar el nombre en componentes de interfaz (como ComboBox).
     *
     * @return El nombre del cuidador.
     */
    @Override
    public String toString() {
        return nombre_cuidadores;
    }
}