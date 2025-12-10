package modelos;

public class Log {
	
	private int id;
	private String action;
	private User user;
	private String date;
	private String result;
	
	public Log(int id, String action, User user, String date, String result) {
		this.id = id;
		this.action = action;
		this.user = user;
		this.date = date;
		this.result = result;
	}
	
	public Log(String action, String correo, String date, String result) {
		this.action = action;
		this.user.setCorreo(correo);
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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


	@Override
	public String toString() {
		return this.date + "," + this.getUser().getCorreo() + "," + this.getAction() + "," + this.getResult() + "\n";
	}
	
	

}
