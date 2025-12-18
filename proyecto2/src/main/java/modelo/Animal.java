package modelo;

/**
 * Representa un animal dentro del zoológico.
 * Contiene información sobre su identificación, nombre, tipo, y relaciones con
 * especie y cuidador.
 */
public class Animal {
    private int animal_id;
    private String nombre_animales;
    private String tipo;
    private int especie_id;
    private int cuidador_id;

    /**
     * Constructor de la clase Animal.
     *
     * @param animal_id       Identificador único del animal.
     * @param nombre_animales Nombre del animal.
     * @param tipo            Tipo de animal.
     * @param especie_id      Identificador de la especie a la que pertenece.
     * @param cuidador_id     Identificador del cuidador asignado.
     */
    public Animal(int animal_id, String nombre_animales, String tipo, int especie_id, int cuidador_id) {
        this.animal_id = animal_id;
        this.nombre_animales = nombre_animales;
        this.tipo = tipo;
        this.especie_id = especie_id;
        this.cuidador_id = cuidador_id;
    }

    /**
     * Obtiene el ID del animal.
     *
     * @return El ID del animal.
     */
    public int getAnimal_id() {
        return animal_id;
    }

    /**
     * Establece el ID del animal.
     *
     * @param animal_id El nuevo ID del animal.
     */
    public void setAnimal_id(int animal_id) {
        this.animal_id = animal_id;
    }

    /**
     * Obtiene el nombre del animal.
     *
     * @return El nombre del animal.
     */
    public String getNombre_animales() {
        return nombre_animales;
    }

    /**
     * Establece el nombre del animal.
     *
     * @param nombre_animales El nuevo nombre del animal.
     */
    public void setNombre_animales(String nombre_animales) {
        this.nombre_animales = nombre_animales;
    }

    /**
     * Obtiene el tipo de animal.
     *
     * @return El tipo de animal.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de animal.
     *
     * @param tipo El nuevo tipo de animal.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el ID de la especie del animal.
     *
     * @return El ID de la especie.
     */
    public int getEspecie_id() {
        return especie_id;
    }

    /**
     * Establece el ID de la especie del animal.
     *
     * @param especie_id El nuevo ID de la especie.
     */
    public void setEspecie_id(int especie_id) {
        this.especie_id = especie_id;
    }

    /**
     * Obtiene el ID del cuidador del animal.
     *
     * @return El ID del cuidador.
     */
    public int getCuidador_id() {
        return cuidador_id;
    }

    /**
     * Establece el ID del cuidador del animal.
     *
     * @param cuidador_id El nuevo ID del cuidador.
     */
    public void setCuidador_id(int cuidador_id) {
        this.cuidador_id = cuidador_id;
    }
}
