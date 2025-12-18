package modelo;

public class Log {

	private int id;
	private String action;
	private String date;
	private String correo;
	private String result;

	public Log(String action, String correo, boolean exito) {
		String resultado = exito ? "success" : "error";

		this.action = action;
		this.correo = correo;
		this.setResult(resultado);
	}

	public Log(int id, String action, String correo, String date, String result) {
		this.id = id;
		this.action = action;
		this.correo = correo;
		this.date = date;
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCorreo() {
		return correo;
	}

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

	private String csv(Object value) {
	    if (value == null) return "";
	    String text = value.toString().replace("\"", "\"\"");
	    return "\"" + text + "\"";
	}



}
