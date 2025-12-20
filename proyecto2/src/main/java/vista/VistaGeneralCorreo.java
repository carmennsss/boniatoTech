package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import modelo.Correo;
import modelo.MoTextos;

/**
 * Vista principal o "Buzón de Entrada" para la gestión de correos electrónicos.
 * Muestra una lista de mensajes recibidos en una tabla y permite la navegación
 * hacia las funciones de redacción, lectura y refresco de datos.
 */
public class VistaGeneralCorreo extends JFrame {

	/** Panel principal que contiene la organización de la ventana. */
	private JPanel panel;

	/** Dirección de correo electrónico del usuario actual. */
	private String correo;

	/** Botón para abrir la ventana de redacción de correos. */
	private JButton botonEnviarCorreo;

	/** Modelo de datos que gestiona las filas y columnas de la tabla. */
	private DefaultTableModel tablaModelo;

	/** Componente de tabla para la visualización de los correos. */
	private JTable emailTabla;

	/** Botón para regresar al menú principal de la aplicación. */
	private JButton btnVolver;

	/** Botón para solicitar una nueva sincronización con el servidor de correo. */
	private JButton btnRefrescar;

	/** Etiqueta que identifica al usuario y el título de la sección. */
	private JLabel etiquetaCorreo;

	/** Lista interna de objetos Correo sincronizada con la tabla visual. */
	private ArrayList<Correo> correosActuales = new ArrayList<>();

	/**
	 * Constructor que inicializa la vista del buzón de correo.
	 * * @param correo Dirección de correo electrónico del usuario logueado.
	 */
	public VistaGeneralCorreo(String correo) {
		this.correo = correo;
		propiedades();
	}

	/**
	 * Orquesta la configuración inicial de componentes y estilos.
	 */
	private void propiedades() {
		inicializarPanel();
		propiedadesVentana();
		inicializarVista();
		inicializarTabla();
	}

	/**
	 * Configura la tabla de correos con un renderizador personalizado para
	 * destacar mensajes no leídos.
	 */
	private void inicializarTabla() {
		JPanel panelTabla = new JPanel(new BorderLayout());
		panelTabla.setOpaque(false);

		String[] nombresColumnas = { "Subject", "From", "Date" };

		tablaModelo = new DefaultTableModel(nombresColumnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		emailTabla = new JTable(tablaModelo);
		emailTabla.setRowHeight(40);
		emailTabla.setFont(Estilos.FONT_TEXTO);
		emailTabla.setShowGrid(false);
		emailTabla.setIntercellSpacing(new Dimension(0, 0));

		emailTabla.setSelectionBackground(new Color(170, 98, 147, 100));
		emailTabla.setSelectionForeground(Color.WHITE);

		emailTabla.setOpaque(false);
		((DefaultTableCellRenderer) emailTabla.getDefaultRenderer(Object.class)).setOpaque(false);

		emailTabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
		emailTabla.getTableHeader().setBackground(Estilos.SAGE_GREEN);
		emailTabla.getTableHeader().setForeground(Color.WHITE);
		emailTabla.getTableHeader().setPreferredSize(new Dimension(0, 40));

		JScrollPane scrollPane = new JScrollPane(emailTabla);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(null);

		panelTabla.add(scrollPane, BorderLayout.CENTER);
		panel.add(panelTabla, BorderLayout.CENTER);

		emailTabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				if (!isSelected) {
					comp.setBackground(row % 2 == 0 ? new Color(255, 255, 255, 150) : new Color(245, 245, 245, 150));
					comp.setForeground(Estilos.TEXTO_PRINCIPAL);
				} else {
					comp.setBackground(Estilos.COLOR_TABLA_SELECCION);
					comp.setForeground(Color.WHITE);
				}

				((javax.swing.JComponent) comp).setOpaque(true);

				Correo correoFila = getCorreoPorFila(row);
				if (correoFila != null && !correoFila.isLeido()) {
					comp.setFont(Estilos.FONT_TEXTO.deriveFont(Font.BOLD));
				} else {
					comp.setFont(Estilos.FONT_TEXTO.deriveFont(Font.PLAIN));
				}

				setBorder(noFocusBorder);
				return comp;
			}
		});
	}

	/**
	 * Configura los parámetros básicos del JFrame.
	 */
	private void propiedadesVentana() {
		this.setTitle(MoTextos.mail_title_inbox);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(600, 400));
	}

	/**
	 * Inicializa el contenedor principal con una imagen de fondo decorativa.
	 */
	private void inicializarPanel() {
		panel = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				try {
					java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
					java.awt.Image bgImage = javax.imageio.ImageIO.read(getClass().getResource("/olas_verdes.png"));
					g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.15f));
					g2d.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
					g2d.dispose();
				} catch (Exception e) {
					// Fallback si la imagen no carga
				}
			}
		};
		panel.setBackground(Estilos.FONDO_PRINCIPAL);
		this.add(panel, BorderLayout.CENTER);
	}

	/**
	 * Crea los paneles superior e inferior y añade los botones de control.
	 */
	private void inicializarVista() {
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 20));
		panelSuperior.setBackground(Estilos.DARK_SPRUCE);

		etiquetaCorreo = new JLabel(correo + " " + MoTextos.mail_title_inbox);
		etiquetaCorreo.setFont(Estilos.FONT_TITULO);
		etiquetaCorreo.setForeground(Estilos.BEIGE_CANVAS);

		panelSuperior.add(etiquetaCorreo);
		panel.add(panelSuperior, BorderLayout.NORTH);

		JPanel panelMedio = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
		panelMedio.setOpaque(false);

		botonEnviarCorreo = crearBotonEstilizado(MoTextos.mail_btn_compose);
		btnVolver = crearBotonEstilizado(MoTextos.btn_back);
		btnRefrescar = crearBotonEstilizado(MoTextos.mail_btn_refresh);

		panelMedio.add(btnVolver);
		panelMedio.add(btnRefrescar);
		panelMedio.add(botonEnviarCorreo);

		panel.add(panelMedio, BorderLayout.SOUTH);
	}

	/**
	 * Crea un botón con el estilo visual unificado de la aplicación.
	 * * @param texto Etiqueta del botón.
	 * @return JButton configurado.
	 */
	private JButton crearBotonEstilizado(String texto) {
		JButton btn = new JButton(texto);
		btn.setPreferredSize(new Dimension(150, 40));
		btn.setFont(Estilos.FONT_BOTON);
		btn.setBackground(Estilos.DARK_SPRUCE);
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		return btn;
	}

	/**
	 * Carga una lista de correos en la tabla y los ordena cronológicamente.
	 * * @param correos Lista de objetos Correo a visualizar.
	 */
	public void cargarCorreos(ArrayList<Correo> correos) {
		correosActuales.clear();
		correosActuales.addAll(correos);

		tablaModelo.setRowCount(0);
		Collections.sort(correos, Comparator.comparing(Correo::getFecha).reversed());

		for (Correo c : correos) {
			tablaModelo.addRow(new Object[] {
					c.getAsunto(),
					c.getRemitente(),
					c.getFecha()
			});
		}
	}

	/**
	 * Hace visible la ventana del buzón.
	 */
	public void hacerVisible() {
		this.setVisible(true);
	}

	/**
	 * Actualiza dinámicamente los textos de la interfaz según el idioma seleccionado.
	 */
	public void actualizarTextos() {
		this.setTitle(MoTextos.mail_title_inbox);
		if (etiquetaCorreo != null) {
			etiquetaCorreo.setText(correo + " " + MoTextos.mail_title_inbox);
		}
		if (botonEnviarCorreo != null)
			botonEnviarCorreo.setText(MoTextos.mail_btn_compose);
		if (btnRefrescar != null)
			btnRefrescar.setText(MoTextos.mail_btn_refresh);
		if (btnVolver != null)
			btnVolver.setText(MoTextos.btn_back);

		if (emailTabla != null) {
			emailTabla.getColumnModel().getColumn(0).setHeaderValue(MoTextos.mail_col_subject);
			emailTabla.getColumnModel().getColumn(1).setHeaderValue(MoTextos.mail_col_from);
			emailTabla.getColumnModel().getColumn(2).setHeaderValue(MoTextos.mail_col_date);
			emailTabla.getTableHeader().repaint();
		}
		repaint();
	}

	// --- GETTERS Y SETTERS AL FINAL ---

	/**
	 * Recupera el objeto Correo asociado a una fila específica de la tabla visual.
	 *
	 * @param fila Índice de la fila.
	 * @return El objeto Correo correpondiente o null si el índice es inválido.
	 */
	public Correo getCorreoPorFila(int fila) {
		if (fila >= 0 && fila < correosActuales.size()) {
			return correosActuales.get(fila);
		}
		return null;
	}

	/**
	 * Obtiene el botón de redacción de correo.
	 * @return Objeto JButton.
	 */
	public JButton getBotonEnviarCorreo() {
		return botonEnviarCorreo;
	}

	/**
	 * Establece el botón de redacción.
	 * @param botonEnviarCorreo Nuevo botón de composición.
	 */
	public void setBotonEnviarCorreo(JButton botonEnviarCorreo) {
		this.botonEnviarCorreo = botonEnviarCorreo;
	}

	/**
	 * Obtiene la dirección de correo del usuario.
	 * @return String con el correo.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece la dirección de correo del usuario.
	 * @param correo Nueva dirección de correo.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene el componente de tabla de correos.
	 * @return Objeto JTable.
	 */
	public JTable getEmailTabla() {
		return emailTabla;
	}

	/**
	 * Establece la tabla de correos.
	 * @param emailTabla Nueva tabla JTable.
	 */
	public void setEmailTabla(JTable emailTabla) {
		this.emailTabla = emailTabla;
	}

	/**
	 * Obtiene el botón para volver al menú anterior.
	 * @return Objeto JButton.
	 */
	public JButton getBtnVolver() {
		return btnVolver;
	}

	/**
	 * Establece el botón de retorno.
	 * @param btnVolver Nuevo botón JButton.
	 */
	public void setBtnVolver(JButton btnVolver) {
		this.btnVolver = btnVolver;
	}

	/**
	 * Obtiene el botón de refresco de bandeja de entrada.
	 * @return Objeto JButton.
	 */
	public JButton getBtnRefrescar() {
		return btnRefrescar;
	}

	/**
	 * Establece el botón de refresco.
	 * @param btnRefrescar Nuevo botón JButton.
	 */
	public void setBtnRefrescar(JButton btnRefrescar) {
		this.btnRefrescar = btnRefrescar;
	}

}