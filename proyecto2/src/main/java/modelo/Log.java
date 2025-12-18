package modelo;

public class Log {

	private int id;
	private String action;
	private String date;
	private String correo;
	private String result;

	public Log(String action, User user , boolean exito) {
		String resultado = exito ? "success" : "error";

		this.action = action;
		this.user = user;
		this.setResult(resultado);
	}

	public Log(int id, String action, User user, String date, String result) {
		this.id = id;
		this.action = action;
		this.user = user;
		this.date = date;
		this.result = result;
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
	

}
