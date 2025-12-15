package modelo; 

import java.util.Date;

import javax.mail.Message; 

public class Correo {

    private String remitente; 
    private String asunto;
    private Date fecha;
    private String cuerpo;
    private Message message;
    

  
    public Correo(String remitente, String asunto, Date fecha, String cuerpo, Message message) {
        this.remitente = remitente;
        this.asunto = asunto;
        this.fecha = fecha;
        this.cuerpo = cuerpo;
        this.message = message;
    }


    public String getRemitente() {
        return remitente;
    }

    public String getAsunto() {
        return asunto;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getCuerpo() {
        return cuerpo;
    }
    
    
    public Message getMessage() {
		return message;
	}


	public void setMessage(Message message) {
		this.message = message;
	}


	@Override
    public String toString() {
        return "Correo [De=" + remitente + ", Asunto=" + asunto + ", Fecha=" + fecha + "]";
    }
}