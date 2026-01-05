package modelo;

/**
 * Representa una especie animal dentro del catálogo del zoológico.
 * Esta clase se utiliza para clasificar a los animales y gestionar la 
 * relación jerárquica entre los ejemplares y su taxonomía básica.
 */
public class Especie {
    /** Identificador único de la especie en la base de datos. */
    private int especie_id;

    /** Nombre común o científico de la especie. */
    private String nombre_especies;

    /**
     * Constructor para instanciar una nueva Especie con sus atributos básicos.
     *
     * @param especie_id      Identificador único asignado a la especie.
     * @param nombre_especies Denominación de la especie.
     */
    public Especie(int especie_id, String nombre_especies) {
        this.especie_id = especie_id;
        this.nombre_especies = nombre_especies;
    }

    /**
     * Obtiene el identificador único de la especie.
     *
     * @return El ID numérico de la especie.
     */
    public int getEspecie_id() {
        return especie_id;
    }

    /**
     * Obtiene el nombre registrado para la especie.
     *
     * @return Una cadena de texto con el nombre de la especie.
     */
    public String getNombre_especies() {
        return nombre_especies;
    }

    /**
     * Establece o modifica el identificador único de la especie.
     *
     * @param especie_id El nuevo ID numérico a asignar.
     */
    public void setEspecie_id(int especie_id) {
        this.especie_id = especie_id;
    }

    /**
     * Establece o modifica el nombre de la especie.
     *
     * @param nombre_especies El nuevo nombre descriptivo de la especie.
     */
    public void setNombre_especies(String nombre_especies) {
        this.nombre_especies = nombre_especies;
    }

    /**
     * Devuelve una representación en cadena de la especie.
     * Utilizado principalmente para mostrar la información en componentes de selección (ComboBox).
     *
     * @return El nombre de la especie.
     */
    @Override
    public String toString() {
        return nombre_especies;
    }
}