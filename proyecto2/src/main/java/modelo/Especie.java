package modelo;

/**
 * Representa una especie animal en el sistema.
 */
public class Especie {
    /** Identificador único de la especie. */
    private int especie_id;

    /** Nombre de la especie. */
    private String nombre_especies;

    /**
     * Constructor de la clase Especie.
     *
     * @param especie_id      Identificador único de la especie.
     * @param nombre_especies Nombre de la especie.
     */
    public Especie(int especie_id, String nombre_especies) {
        this.especie_id = especie_id;
        this.nombre_especies = nombre_especies;
    }

    /**
     * Obtiene el ID de la especie.
     *
     * @return El ID de la especie.
     */
    public int getEspecie_id() {
        return especie_id;
    }

    /**
     * Obtiene el nombre de la especie.
     *
     * @return El nombre de la especie.
     */
    public String getNombre_especies() {
        return nombre_especies;
    }

    /**
     * Establece el ID de la especie.
     *
     * @param especie_id El nuevo ID de la especie.
     */
    public void setEspecie_id(int especie_id) {
        this.especie_id = especie_id;
    }

    /**
     * Establece el nombre de la especie.
     *
     * @param nombre_especies El nuevo nombre de la especie.
     */
    public void setNombre_especies(String nombre_especies) {
        this.nombre_especies = nombre_especies;
    }

    @Override
    public String toString() {
        return nombre_especies;
    }
}
