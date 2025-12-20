package modelo;

import java.util.Date;

/**
 * Representa un correo electrónico dentro del sistema.
 * Almacena la información relativa al mensaje, incluyendo metadatos de red
 * y el estado de lectura para la interfaz de usuario.
 */
public class Correo {

    /** El remitente del correo electrónico. */
    private String remitente;

    /** El asunto o título del mensaje. */
    private String asunto;

    /** La fecha y hora de recepción del correo. */
    private Date fecha;

    /** El contenido textual del cuerpo del mensaje. */
    private String cuerpo;

    /** El identificador único universal (Message-ID) del mensaje. */
    private String messageId;

    /** Indica si el mensaje ha sido visualizado por el usuario. */
    private boolean leido;

    /**
     * Constructor para crear un objeto Correo con estado de lectura explícito.
     *
     * @param remitente Dirección de correo de quien envía.
     * @param asunto    Título o tema del mensaje.
     * @param fecha     Instante de recepción.
     * @param cuerpo    Contenido del mensaje.
     * @param leido     Estado inicial de lectura (true para leído).
     */
    public Correo(String remitente, String asunto, Date fecha, String cuerpo, boolean leido) {
        this.remitente = remitente;
        this.asunto = asunto;
        this.fecha = fecha;
        this.cuerpo = cuerpo;
        this.leido = leido;
    }

    /**
     * Constructor para crear un objeto Correo utilizando el ID de mensaje de red.
     *
     * @param remitente Dirección de correo de quien envía.
     * @param asunto    Título o tema del mensaje.
     * @param fecha     Instante de recepción.
     * @param cuerpo    Contenido del mensaje.
     * @param messageId Identificador único de los encabezados del correo.
     */
    public Correo(String remitente, String asunto, Date fecha, String cuerpo, String messageId) {
        this.remitente = remitente;
        this.asunto = asunto;
        this.fecha = fecha;
        this.cuerpo = cuerpo;
        this.messageId = messageId;
    }

    /**
     * Obtiene el asunto del correo electrónico.
     *
     * @return Una cadena con el asunto.
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * Obtiene el contenido o cuerpo del mensaje.
     *
     * @return El texto del mensaje.
     */
    public String getCuerpo() {
        return cuerpo;
    }

    /**
     * Obtiene la fecha de recepción del correo.
     *
     * @return El objeto Date con la fecha.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Obtiene el identificador Message-ID del correo.
     *
     * @return El ID del mensaje.
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Obtiene la dirección del remitente del correo.
     *
     * @return El email del remitente.
     */
    public String getRemitente() {
        return remitente;
    }

    /**
     * Verifica si el correo ha sido marcado como leído.
     *
     * @return true si el correo está leído, false en caso contrario.
     */
    public boolean isLeido() {
        return leido;
    }

    /**
     * Establece el estado de lectura del correo.
     *
     * @param leido true para marcar como leído, false para marcar como no leído.
     */
    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    /**
     * Establece el identificador Message-ID del correo.
     *
     * @param messageId El nuevo identificador único.
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * Establece la dirección del remitente del correo.
     *
     * @param remitente La dirección de correo del remitente.
     */
    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    /**
     * Establece el asunto del correo electrónico.
     *
     * @param asunto El nuevo asunto para el mensaje.
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * Establece la fecha de recepción del correo.
     *
     * @param fecha El nuevo objeto Date de recepción.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Establece el contenido del cuerpo del mensaje.
     *
     * @param cuerpo El nuevo texto para el cuerpo del correo.
     */
    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    /**
     * Devuelve una representación textual simplificada del correo.
     * * @return Una cadena formateada con el remitente, asunto y fecha.
     */
    @Override
    public String toString() {
        return "Correo [De=" + remitente + ", Asunto=" + asunto + ", Fecha=" + fecha + "]";
    }
}