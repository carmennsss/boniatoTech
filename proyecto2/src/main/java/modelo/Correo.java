package modelo; 

import java.util.Date; 

public class Correo {

    private String remitente; 
    private String asunto;
    private Date fecha;
    private String cuerpo;
    private boolean leido;
    

  
    public Correo(String remitente, String asunto, Date fecha, String cuerpo, boolean leido) {
        this.remitente = remitente;
        this.asunto = asunto;
        this.fecha = fecha;
        this.cuerpo = cuerpo;
        this.leido = leido;
    }
    
    public Correo(String remitente, String asunto, Date fecha, String cuerpo) {
    	this.remitente = remitente;
    	this.asunto = asunto;
    	this.fecha = fecha;
    	this.cuerpo = cuerpo;
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
    
    
    
    public boolean isLeido() {
		return leido;
	}


	public void setLeido(boolean leido) {
		this.leido = leido;
	}


	@Override
    public String toString() {
        return "Correo [De=" + remitente + ", Asunto=" + asunto + ", Fecha=" + fecha + "]";
    }
}