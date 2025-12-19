package modelo;

import java.util.Date;

/**
 * Representa un correo electrónico en el sistema.
 */
public class Correo {

    private String remitente;
    private String asunto;
    private Date fecha;
    private String cuerpo;
    private String messageId;
    private boolean leido;

    /**
     * Constructor para crear un objeto Correo con estado de lectura.
     *
     * @param remitente El remitente del correo.
     * @param asunto    El asunto del correo.
     * @param fecha     La fecha de recepción del correo.
     * @param cuerpo    El contenido del correo.
     * @param leido     Indica si el correo ha sido leído.
     */
    public Correo(String remitente, String asunto, Date fecha, String cuerpo, boolean leido) {
        this.remitente = remitente;
        this.asunto = asunto;
        this.fecha = fecha;
        this.cuerpo = cuerpo;
        this.leido = leido;
    }

    /**
     * Constructor para crear un objeto Correo con ID de mensaje.
     *
     * @param remitente El remitente del correo.
     * @param asunto    El asunto del correo.
     * @param fecha     La fecha de recepción del correo.
     * @param cuerpo    El contenido del correo.
     * @param messageId El ID único del mensaje.
     */
    public Correo(String remitente, String asunto, Date fecha, String cuerpo, String messageId) {
        this.remitente = remitente;
        this.asunto = asunto;
        this.fecha = fecha;
        this.cuerpo = cuerpo;
        this.messageId = messageId;
    }

    /**
     * Obtiene el remitente del correo.
     *
     * @return El remitente.
     */
    public String getRemitente() {
        return remitente;
    }

    /**
     * Obtiene el asunto del correo.
     *
     * @return El asunto.
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * Obtiene la fecha del correo.
     *
     * @return La fecha.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Obtiene el cuerpo del mensaje.
     *
     * @return El cuerpo del mensaje.
     */
    public String getCuerpo() {
        return cuerpo;
    }

    /**
     * Verifica si el correo ha sido leído.
     *
     * @return true si ha sido leído, false en caso contrario.
     */
    public boolean isLeido() {
        return leido;
    }

    /**
     * Establece el estado de lectura del correo.
     *
     * @param leido true para marcar como leído, false para no leído.
     */
    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    /**
     * Obtiene el Message-ID del correo.
     *
     * @return El Message-ID.
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Establece el Message-ID del correo.
     *
     * @param messageId El nuevo Message-ID.
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "Correo [De=" + remitente + ", Asunto=" + asunto + ", Fecha=" + fecha + "]";
    }
}