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
 * Vista principal o "Buzón de Entrada" para la gestión de correos.
 * Muestra una lista de correos recibidos y permite navegar a la redacción o
 * lectura.
 */
public class VistaGeneralCorreo extends JFrame {

	private JPanel panel;
	private String correo;
	private JButton botonEnviarCorreo;
	private DefaultTableModel tablaModelo;
	private JTable emailTabla;
	private JButton btnVolver;
	private JButton btnRefrescar;
	private JLabel etiquetaCorreo;

	public VistaGeneralCorreo(String correo) {
		this.correo = correo;
		propiedades();
	}

	private void propiedades() {
		inicializarPanel();
		propiedadesVentana();
		inicializarVista();
		inicializarTabla();
	}

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
			public Component getTableCellRendererComponent(
					JTable table,
					Object value,
					boolean isSelected,
					boolean hasFocus,
					int row,
					int column) {

				Component comp = super.getTableCellRendererComponent(
						table, value, isSelected, hasFocus, row, column);

				if (!isSelected) {
					comp.setBackground(row % 2 == 0 ? new Color(255, 255, 255, 150) : new Color(245, 245, 245, 150));
					comp.setForeground(Estilos.TEXTO_PRINCIPAL);
				} else {
					comp.setBackground(Estilos.COLOR_TABLA_SELECCION);
					comp.setForeground(Color.WHITE);
				}

				((javax.swing.JComponent) comp).setOpaque(true);

				Correo correoFila = ((VistaGeneralCorreo) SwingUtilities
						.getWindowAncestor(table))
						.getCorreoPorFila(row);

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

	private void propiedadesVentana() {
		this.setTitle(MoTextos.mail_title_inbox);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(600, 400));
	}

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

				}
			}
		};
		panel.setBackground(Estilos.FONDO_PRINCIPAL);
		this.add(panel, BorderLayout.CENTER);
	}

	private void inicializarVista() {
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 20));
		panelSuperior.setBackground(Estilos.DARK_SPRUCE);

		etiquetaCorreo = new JLabel(correo + " " + MoTextos.mail_title_inbox);
		etiquetaCorreo.setFont(Estilos.FONT_TITULO);
		etiquetaCorreo.setForeground(Estilos.BEIGE_CANVAS);
		etiquetaCorreo.setAlignmentX(SwingConstants.CENTER);

		panelSuperior.add(etiquetaCorreo);

		panel.add(panelSuperior, BorderLayout.NORTH);

		JPanel panelMedio = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
		panelMedio.setOpaque(false);

		botonEnviarCorreo = crearBotonEstilizado("Compose");
		btnVolver = crearBotonEstilizado("Back");
		btnRefrescar = crearBotonEstilizado("Refresh");

		panelMedio.add(btnVolver);
		panelMedio.add(btnRefrescar);
		panelMedio.add(botonEnviarCorreo);

		panel.add(panelMedio, BorderLayout.SOUTH);
	}

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

	private ArrayList<Correo> correosActuales = new ArrayList<>();

	/**
	 * Carga y muestra una lista de correos en la tabla.
	 * Ordena los correos por fecha descendente.
	 *
	 * @param correos Lista de objetos Correo a visualizar.
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

	public void hacerVisible() {
		this.setVisible(true);
	}

	/**
	 * Actualiza los textos de la interfaz según el idioma seleccionado.
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

	public JButton getBotonEnviarCorreo() {
		return botonEnviarCorreo;
	}

	public void setBotonEnviarCorreo(JButton botonEnviarCorreo) {
		this.botonEnviarCorreo = botonEnviarCorreo;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public JTable getEmailTabla() {
		return emailTabla;
	}

	public void setEmailTabla(JTable emailTabla) {
		this.emailTabla = emailTabla;
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public void setBtnVolver(JButton btnVolver) {
		this.btnVolver = btnVolver;
	}

	public JButton getBtnRefrescar() {
		return btnRefrescar;
	}

	public void setBtnRefrescar(JButton btnRefrescar) {
		this.btnRefrescar = btnRefrescar;
	}

}
