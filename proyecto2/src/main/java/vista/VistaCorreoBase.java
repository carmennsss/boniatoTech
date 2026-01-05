package vista;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import modelo.Correo;
import modelo.MoTextos;

/**
 * Vista base para la visualizaci�n y composici�n de correos electr�nicos. Act�a
 * como una plantilla din�mica que se adapta mediante sus constructores para el
 * env�o de nuevos mensajes o la lectura de correos recibidos.
 */
public class VistaCorreoBase extends JFrame {

	/** Panel principal que organiza la distribuci�n de la ventana. */
	private JPanel panelPrincipal;

	/** Campo para introducir el destinatario o visualizar el remitente. */
	private JTextField textoPara;

	/** Campo para el t�tulo o tema del mensaje. */
	private JTextField textoAsunto;

	/** �rea principal para el contenido de texto del mensaje. */
	private JTextArea textoCuerpo;

	/** Bot�n para procesar el env�o del correo redactado. */
	private JButton botonEnviar;

	/** Bot�n para eliminar el correo visualizado de la bandeja. */
	private JButton botonEliminar;

	/** Bot�n para guardar el correo localmente en formato EML. */
	private JButton botonExportar;

	/** Bot�n para revertir el estado del correo a "No le�do". */
	private JButton botonNoLeido;

	/** Bot�n para abrir el selector de archivos y adjuntar ficheros. */
	private JButton botonAdjuntar;

	/** Direcci�n de correo electr�nico del usuario que utiliza la aplicaci�n. */
	private String remitente;

	/** Lista de archivos locales seleccionados para ser enviados como adjuntos. */
	private List<File> adjuntos = new ArrayList<>();

	/** Etiqueta descriptiva para el campo de destinatario/remitente. */
	private JLabel lblPara;

	/** Etiqueta descriptiva para el campo de asunto. */
	private JLabel lblAsunto;

	/** Etiqueta descriptiva para el �rea del cuerpo del mensaje. */
	private JLabel lblMessage;

	/**
	 * Constructor para el modo de redacci�n de nuevo correo.
	 *
	 * @param remitente Direcci�n de correo del usuario que env�a el mensaje.
	 */
	public VistaCorreoBase(String remitente) {
		this.remitente = remitente;
		propiedades(true);
	}

	/**
	 * Constructor para el modo de lectura de correo existente.
	 * 
	 * @param correo Objeto Correo con los datos a visualizar.
	 */
	public VistaCorreoBase(Correo correo) {
		propiedades(false);
		textoPara.setText(correo.getRemitente());
		textoAsunto.setText(correo.getAsunto());
		textoCuerpo.setText(correo.getCuerpo());
		textoPara.setEditable(false);
		textoAsunto.setEditable(false);
		textoCuerpo.setEditable(false);
	}

	/**
	 * Configura el comportamiento inicial de la ventana seg�n el modo de operaci�n.
	 * 
	 * @param esEnvio true si la vista es para redactar, false si es para lectura.
	 */
	private void propiedades(boolean esEnvio) {
		this.setTitle(esEnvio ? MoTextos.mail_title_compose : MoTextos.mail_title_check);
		inicializarComponentes();
		propiedadesGenerales();
		ensamblarVista(esEnvio);
	}

	/**
	 * Crea las instancias de los componentes Swing necesarios.
	 */
	private void inicializarComponentes() {
		textoPara = new JTextField(40);
		textoAsunto = new JTextField(40);
		textoCuerpo = new JTextArea(15, 50);
		textoCuerpo.setLineWrap(true);
		textoCuerpo.setWrapStyleWord(true);
		botonEnviar = new JButton(MoTextos.btn_send);
		botonEliminar = new JButton(MoTextos.btn_delete);
		botonNoLeido = new JButton(MoTextos.btn_mark_unread);
		botonExportar = new JButton(MoTextos.btn_export);
		botonAdjuntar = new JButton(MoTextos.btn_attach);
		lblPara = new JLabel();
		lblAsunto = new JLabel(MoTextos.mail_lbl_subject);
		lblMessage = new JLabel(MoTextos.mail_lbl_message);
	}

	/**
	 * Organiza los componentes en paneles y establece el dise�o visual.
	 * 
	 * @param esEnvio Determina qu� botones y etiquetas mostrar.
	 */
	private void ensamblarVista(boolean esEnvio) {
		panelPrincipal = new JPanel(new BorderLayout());
		panelPrincipal.setBackground(Estilos.FONDO_PRINCIPAL);

		JPanel sidebar = new JPanel() {
			private java.awt.Image bgImage;
			{
				try {
					java.net.URL url = getClass().getResource("/fondo_verde.png");
					if (url != null)
						bgImage = javax.imageio.ImageIO.read(url);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				if (bgImage != null) {
					Graphics2D g2d = (Graphics2D) g;
					g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					g2d.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
				} else {
					g.setColor(Estilos.DARK_SPRUCE);
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		sidebar.setPreferredSize(new Dimension(250, 0));
		panelPrincipal.add(sidebar, BorderLayout.WEST);

		JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
		contentPanel.setBackground(Estilos.FONDO_PRINCIPAL);
		contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel panelDatosSuperiores = new JPanel(new GridLayout(0, 1, 5, 10));
		panelDatosSuperiores.setBackground(Estilos.FONDO_PRINCIPAL);
		lblPara.setText(esEnvio ? MoTextos.mail_lbl_for : MoTextos.mail_lbl_from);
		panelDatosSuperiores.add(crearCampoLabel(lblPara, textoPara));
		panelDatosSuperiores.add(crearCampoLabel(lblAsunto, textoAsunto));
		contentPanel.add(panelDatosSuperiores, BorderLayout.NORTH);

		JPanel panelCuerpo = new JPanel(new BorderLayout(5, 5));
		panelCuerpo.setBackground(Estilos.FONDO_PRINCIPAL);
		lblMessage.setFont(Estilos.FONT_TEXTO.deriveFont(Font.BOLD));
		lblMessage.setForeground(Estilos.TEXTO_PRINCIPAL);
		panelCuerpo.add(lblMessage, BorderLayout.NORTH);

		JScrollPane scrollCuerpo = new JScrollPane(textoCuerpo);
		scrollCuerpo.setBorder(BorderFactory.createLineBorder(Estilos.DARK_SPRUCE));
		panelCuerpo.add(scrollCuerpo, BorderLayout.CENTER);
		contentPanel.add(panelCuerpo, BorderLayout.CENTER);

		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelBoton.setBackground(Estilos.FONDO_PRINCIPAL);
		if (esEnvio) {
			estilarBoton(botonAdjuntar);
			estilarBoton(botonEnviar);
			panelBoton.add(botonAdjuntar);
			panelBoton.add(botonEnviar);
		} else {
			estilarBoton(botonNoLeido);
			estilarBoton(botonExportar);
			estilarBoton(botonEliminar);
			panelBoton.add(botonNoLeido);
			panelBoton.add(botonExportar);
			panelBoton.add(botonEliminar);
		}
		contentPanel.add(panelBoton, BorderLayout.SOUTH);
		panelPrincipal.add(contentPanel, BorderLayout.CENTER);
		this.add(panelPrincipal);
	}

	private JPanel crearCampoLabel(JLabel lbl, JComponent field) {
		JPanel p = new JPanel(new BorderLayout(5, 5));
		p.setBackground(Estilos.FONDO_PRINCIPAL);
		lbl.setFont(Estilos.FONT_TEXTO.deriveFont(Font.BOLD));
		lbl.setForeground(Estilos.TEXTO_PRINCIPAL);
		lbl.setPreferredSize(new Dimension(80, 25));
		if (field instanceof JTextField) {
			((JTextField) field).setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Estilos.DARK_SPRUCE), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			field.setFont(Estilos.FONT_TEXTO);
			field.setBackground(Estilos.COLOR_INPUT_BG);
			field.setForeground(Estilos.COLOR_INPUT_TEXT);
		}
		p.add(lbl, BorderLayout.WEST);
		p.add(field, BorderLayout.CENTER);
		return p;
	}

	private void estilarBoton(JButton btn) {
		btn.setFont(Estilos.FONT_BOTON);
		btn.setBackground(Estilos.DARK_SPRUCE);
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btn.setPreferredSize(new Dimension(btn.equals(botonNoLeido) ? 130 : 100, 35));
	}

	public void propiedadesGenerales() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(700, 400);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(700, 400));
	}

	public File exportarCorreo(String nombreSugerido) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Export EML");
		fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Files (*.eml)", "eml"));
		fileChooser.setSelectedFile(new File(nombreSugerido + ".eml"));
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			return f.getName().toLowerCase().endsWith(".eml") ? f : new File(f.getAbsolutePath() + ".eml");
		}
		return null;
	}

	public void mostrarMensaje(String mensaje, boolean esError) {
		JOptionPane.showMessageDialog(this, mensaje, esError ? MoTextos.msg_error_title : MoTextos.msg_success_title,
				esError ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
	}

	public File mostrarSelectorAdjuntos() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}

	public void agregarAdjunto(File archivo) {
		this.adjuntos.add(archivo);
	}

	public void actualizarTextos() {
		boolean esEnvio = getTitle().equals(MoTextos.mail_title_compose) || getTitle().contains("Compose")
				|| getTitle().contains("Redactar");
		this.setTitle(esEnvio ? MoTextos.mail_title_compose : MoTextos.mail_title_check);
		lblPara.setText(esEnvio ? MoTextos.mail_lbl_for : MoTextos.mail_lbl_from);
		lblAsunto.setText(MoTextos.mail_lbl_subject);
		lblMessage.setText(MoTextos.mail_lbl_message);
		botonEnviar.setText(MoTextos.btn_send);
		botonEliminar.setText(MoTextos.btn_delete);
		botonNoLeido.setText(MoTextos.btn_mark_unread);
		botonExportar.setText(MoTextos.btn_export);
		botonAdjuntar.setText(MoTextos.btn_attach);
		repaint();
	}

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene la lista de archivos adjuntos cargados.
	 * 
	 * @return Lista de objetos File.
	 */
	public List<File> getAdjuntos() {
		return adjuntos;
	}

	/**
	 * Obtiene el campo de texto del destinatario o remitente.
	 * 
	 * @return El componente JTextField correspondiente.
	 */
	public JTextField getTextoPara() {
		return textoPara;
	}

	/**
	 * Obtiene el campo de texto del asunto.
	 * 
	 * @return El componente JTextField correspondiente.
	 */
	public JTextField getTextoAsunto() {
		return textoAsunto;
	}

	/**
	 * Obtiene el �rea de texto del cuerpo del mensaje.
	 * 
	 * @return El componente JTextArea correspondiente.
	 */
	public JTextArea getTextoCuerpo() {
		return textoCuerpo;
	}

	/**
	 * Obtiene el bot�n de env�o de correo.
	 * 
	 * @return El objeto JButton para enviar.
	 */
	public JButton getBotonEnviar() {
		return botonEnviar;
	}

	/**
	 * Obtiene la direcci�n del remitente actual.
	 * 
	 * @return Cadena con el correo del remitente.
	 */
	public String getRemitente() {
		return remitente;
	}

	/**
	 * Establece la direcci�n del remitente del correo.
	 * 
	 * @param remitente Nuevo correo del remitente.
	 */
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	/**
	 * Obtiene el bot�n de eliminaci�n de correo.
	 * 
	 * @return El objeto JButton para eliminar.
	 */
	public JButton getBotonEliminar() {
		return botonEliminar;
	}

	/**
	 * Establece una nueva instancia para el bot�n de eliminar.
	 * 
	 * @param botonEliminar El nuevo bot�n JButton.
	 */
	public void setBotonEliminar(JButton botonEliminar) {
		this.botonEliminar = botonEliminar;
	}

	/**
	 * Obtiene el bot�n de exportaci�n a formato EML.
	 * 
	 * @return El objeto JButton para exportar.
	 */
	public JButton getBotonExportar() {
		return botonExportar;
	}

	/**
	 * Establece una nueva instancia para el bot�n de exportar.
	 * 
	 * @param botonExportar El nuevo bot�n JButton.
	 */
	public void setBotonExportar(JButton botonExportar) {
		this.botonExportar = botonExportar;
	}

	/**
	 * Obtiene el bot�n para marcar correos como no le�dos.
	 * 
	 * @return El objeto JButton correspondiente.
	 */
	public JButton getBotonNoLeido() {
		return botonNoLeido;
	}

	/**
	 * Establece el bot�n para la funci�n de "No le�do".
	 * 
	 * @param botonNoLeido El nuevo bot�n JButton.
	 */
	public void setBotonNoLeido(JButton botonNoLeido) {
		this.botonNoLeido = botonNoLeido;
	}

	/**
	 * Obtiene el bot�n para adjuntar archivos locales.
	 * 
	 * @return El objeto JButton correspondiente.
	 */
	public JButton getBotonAdjuntar() {
		return botonAdjuntar;
	}

	/**
	 * Establece el bot�n para la funci�n de adjuntos.
	 * 
	 * @param botonAdjuntar El nuevo bot�n JButton.
	 */
	public void setBotonAdjuntar(JButton botonAdjuntar) {
		this.botonAdjuntar = botonAdjuntar;
	}
}