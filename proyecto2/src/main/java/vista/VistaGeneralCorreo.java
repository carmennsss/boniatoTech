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

public class VistaGeneralCorreo extends JFrame {

	private JPanel panel;
	private String correo;
	private JButton botonEnviarCorreo;
	private DefaultTableModel tablaModelo;
	private JTable emailTabla;
	private JButton btnVolver;
	private JButton btnRefrescar;

	public VistaGeneralCorreo(String correo) {
		this.correo = correo;
		inicializarPanel();
		propiedadesVentana();
		inicializarVista();
		inicializarTabla();

	}

	private void inicializarTabla() {
		JPanel panelTabla = new JPanel(new BorderLayout());
		panelTabla.setOpaque(false); // Transparent to show background

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

		// Semi-transparent selection
		emailTabla.setSelectionBackground(new Color(170, 98, 147, 100)); // Fuchsia with alpha
		emailTabla.setSelectionForeground(Color.WHITE);

		// Make table body transparent if desired, or keep white?
		// User said "background with low opacity", so seeing it through the table would
		// be cool.
		emailTabla.setOpaque(false);
		((DefaultTableCellRenderer) emailTabla.getDefaultRenderer(Object.class)).setOpaque(false);

		// Header Styling
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

		// _________
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

				// Make cells semi-transparent or alternate
				if (!isSelected) {
					// Use a very light semi-transparent white for readability
					comp.setBackground(row % 2 == 0 ? new Color(255, 255, 255, 150) : new Color(245, 245, 245, 150));
					comp.setForeground(Estilos.TEXTO_PRINCIPAL);
				} else {
					// Keep selection opaque or semi
					comp.setBackground(Estilos.COLOR_TABLA_SELECCION);
					comp.setForeground(Color.WHITE);
				}

				// Ensure opacity is true for the component so background color shows
				// But since we want to see the underlying panel image, we might need a trick.
				// Actually, JTable painting is tricky with transparency.
				// Laying a "semi transparent white" on top of the image is safer.
				((javax.swing.JComponent) comp).setOpaque(true);

				// Obtenemos el correo correspondiente a esa fila
				Correo correoFila = ((VistaGeneralCorreo) SwingUtilities
						.getWindowAncestor(table))
						.getCorreoPorFila(row);

				if (correoFila != null && !correoFila.isLeido()) {
					comp.setFont(Estilos.FONT_TEXTO.deriveFont(Font.BOLD));
				} else {
					comp.setFont(Estilos.FONT_TEXTO.deriveFont(Font.PLAIN));
				}

				setBorder(noFocusBorder); // Remove focus border

				return comp;
			}
		});

	}

	private void propiedadesVentana() {
		this.setTitle("Inbox");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(600, 400));
	}

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

					// Set opacity (0.15f is subtle)
					g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.15f));
					g2d.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
					g2d.dispose();
				} catch (Exception e) {
					// Ignore
				}
			}
		};
		panel.setBackground(Estilos.FONDO_PRINCIPAL); // Base color
		this.add(panel, BorderLayout.CENTER);
	}

	private void inicializarVista() {
		// HEADER (Solid Color)
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 20));
		panelSuperior.setBackground(Estilos.DARK_SPRUCE);

		JLabel etiquetaCorreo = new JLabel(correo + " Inbox");
		etiquetaCorreo.setFont(Estilos.FONT_TITULO);
		etiquetaCorreo.setForeground(Estilos.BEIGE_CANVAS); // Light text on Dark background
		etiquetaCorreo.setAlignmentX(SwingConstants.CENTER);

		panelSuperior.add(etiquetaCorreo);

		panel.add(panelSuperior, BorderLayout.NORTH);

		// FOOTER (Actions)
		JPanel panelMedio = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
		panelMedio.setOpaque(false); // Make transparent to see background

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