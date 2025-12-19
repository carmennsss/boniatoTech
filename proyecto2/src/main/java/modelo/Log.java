package modelo;

/**
 * Representa un registro de log (actividad) en el sistema.
 */
public class Log {

	private int id;
	private String action;
	private String date;
	private String correo;
	private String result;

	/**
	 * Constructor para crear un nuevo Log (sin ID, normalmente para inserción).
	 *
	 * @param action Acción realizada.
	 * @param correo Correo del usuario que realizó la acción.
	 * @param exito  Indica si la acción fue exitosa (true) o fallida (false).
	 */
	public Log(String action, String correo, boolean exito) {
		String resultado = exito ? "success" : "error";

		this.action = action;
		this.correo = correo;
		this.setResult(resultado);
	}

	/**
	 * Constructor para instanciar un Log recuperado de la base de datos.
	 *
	 * @param id     Identificador único del log.
	 * @param action Acción realizada.
	 * @param correo Correo del usuario que realizó la acción.
	 * @param date   Fecha en la que se realizó la acción.
	 * @param result Resultado de la acción (e.g., "success", "error").
	 */
	public Log(int id, String action, String correo, String date, String result) {
		this.id = id;
		this.action = action;
		this.correo = correo;
		this.date = date;
		this.result = result;
	}

	/**
	 * Obtiene el ID del log.
	 *
	 * @return El ID del log.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el ID del log.
	 *
	 * @param id El nuevo ID del log.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene la acción registrada.
	 *
	 * @return La acción.
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Establece la acción del log.
	 *
	 * @param action La nueva acción.
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Obtiene la fecha del log.
	 *
	 * @return La fecha.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Establece la fecha del log.
	 *
	 * @param date La nueva fecha.
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Obtiene el resultado de la acción.
	 *
	 * @return El resultado.
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Establece el resultado de la acción.
	 *
	 * @param result El nuevo resultado.
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * Obtiene el correo asociado al log.
	 *
	 * @return El correo.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo asociado al log.
	 *
	 * @param correo El nuevo correo.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@Override
	public String toString() {
		return csv(date) + "," +
				csv(correo) + "," +
				csv(action) + "," +
				csv(result);
	}

	/**
	 * Formatea un valor para formato CSV, escapando comillas dobles.
	 *
	 * @param value El valor a formatear.
	 * @return El valor formateado como string CSV.
	 */
	private String csv(Object value) {
		if (value == null)
			return "";
		String text = value.toString().replace("\"", "\"\"");
		return "\"" + text + "\"";
	}

}
