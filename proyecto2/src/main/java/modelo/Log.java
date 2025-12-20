package modelo;

/**
 * Representa un registro de actividad (log) dentro del sistema.
 * Esta clase se utiliza para auditar las acciones realizadas por los usuarios,
 * almacenando detalles como la fecha, el autor y el resultado de la operación.
 */
public class Log {

    /** Identificador único del registro en la base de datos. */
    private int id;

    /** Descripción de la acción realizada (ej: LOGIN, UPLOAD, DELETE). */
    private String action;

    /** Marca de tiempo en la que se registró la actividad. */
    private String date;

    /** Correo electrónico del usuario responsable de la acción. */
    private String correo;

    /** Resultado final de la acción (normalmente "success" o "error"). */
    private String result;

    /**
     * Constructor para crear un nuevo objeto Log destinado a ser insertado.
     * La fecha no se incluye aquí ya que suele ser generada por la base de datos.
     *
     * @param action La operación o evento que se desea registrar.
     * @param correo Dirección de correo del usuario actuante.
     * @param exito  Estado de la operación: true para éxito, false para error.
     */
    public Log(String action, String correo, boolean exito) {
        String resultado = exito ? "success" : "error";
        this.action = action;
        this.correo = correo;
        this.setResult(resultado);
    }

    /**
     * Constructor completo para instanciar un Log recuperado desde la base de datos.
     *
     * @param id     Identificador único incremental del log.
     * @param action Descripción de la actividad registrada.
     * @param correo Email del usuario que ejecutó la acción.
     * @param date   Fecha y hora del registro en formato cadena.
     * @param result Estado de finalización de la actividad.
     */
    public Log(int id, String action, String correo, String date, String result) {
        this.id = id;
        this.action = action;
        this.correo = correo;
        this.date = date;
        this.result = result;
    }

    /**
     * Obtiene la descripción de la acción del log.
     *
     * @return Cadena de texto con la acción.
     */
    public String getAction() {
        return action;
    }

    /**
     * Obtiene el correo electrónico del usuario asociado.
     *
     * @return El correo del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Obtiene la fecha de creación del registro.
     *
     * @return La fecha como cadena de texto.
     */
    public String getDate() {
        return date;
    }

    /**
     * Obtiene el identificador numérico del log.
     *
     * @return El ID del registro.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el resultado (estado) de la acción registrada.
     *
     * @return El resultado ("success" o "error").
     */
    public String getResult() {
        return result;
    }

    /**
     * Establece o modifica la acción del registro.
     *
     * @param action Nueva descripción de la acción.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param correo Nuevo correo electrónico a asociar.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Establece la fecha del registro de actividad.
     *
     * @param date Nueva fecha en formato cadena.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Establece el identificador único del log.
     *
     * @param id Nuevo ID para el registro.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece el resultado final de la acción registrada.
     *
     * @param result El estado de la acción (ej: "success").
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Devuelve una representación del log formateada para archivos CSV.
     * Los campos se separan por comas y los valores se escapan automáticamente.
     * * @return Línea de texto compatible con formato CSV.
     */
    @Override
    public String toString() {
        return csv(date) + "," +
                csv(correo) + "," +
                csv(action) + "," +
                csv(result);
    }

    /**
     * Formatea un valor para su correcta inclusión en un archivo CSV.
     * Escapa las comillas dobles y encierra el valor entre comillas.
     *
     * @param value Objeto a formatear.
     * @return Cadena de texto escapada para CSV.
     */
    private String csv(Object value) {
        if (value == null)
            return "";
        String text = value.toString().replace("\"", "\"\"");
        return "\"" + text + "\"";
    }

}