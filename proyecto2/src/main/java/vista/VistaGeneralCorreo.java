package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import modelo.Correo;

public class VistaGeneralCorreo extends JFrame {

	private JPanel panel;
	private String correo;
	private JButton botonEnviarCorreo;
	private DefaultTableModel tablaModelo;
	private JTable emailTabla;

	public VistaGeneralCorreo(String correo) {
		this.correo = correo;
		inicializarPanel();
		propiedadesVentana();
		inicializarVista();
		inicializarTabla();

	}

	private void inicializarPanel() {
		// Main container with clean background
		JPanel mainContent = new JPanel(new BorderLayout());
		mainContent.setBackground(Estilos.BEIGE_CANVAS);
		this.setContentPane(mainContent);

		// Header Panel (Solid background to show title clearly)
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 15));
		panelSuperior.setBackground(Estilos.BEIGE_CANVAS);

		JLabel etiquetaCorreo = new JLabel("Bandeja de entrada de " + correo);
		etiquetaCorreo.setFont(Estilos.FONT_TITULO);
		etiquetaCorreo.setForeground(Estilos.COLOR_TITULO_APP);
		panelSuperior.add(etiquetaCorreo);

		mainContent.add(panelSuperior, BorderLayout.NORTH);

		// Center Panel with Image Background for the table
		panel = new JPanel() {
			private java.awt.Image imagen;
			{
				java.net.URL url = getClass().getResource("/fondo.jpg");
				if (url != null) {
					imagen = new javax.swing.ImageIcon(url).getImage();
				}
			}

			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				if (imagen != null) {
					int width = getWidth();
					int height = getHeight();
					g.drawImage(imagen, 0, 0, width, height, this);
				} else {
					g.setColor(Estilos.FONDO_PRINCIPAL);
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		panel.setLayout(new BorderLayout());
		mainContent.add(panel, BorderLayout.CENTER);
	}

	private void inicializarVista() {
		// Buttons panel stays on the image background (South of center panel)
		JPanel panelMedio = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
		panelMedio.setOpaque(false);

		botonEnviarCorreo = new JButton("Enviar correo");
		botonEnviarCorreo.setPreferredSize(new Dimension(180, 40));
		estilarBoton(botonEnviarCorreo);

		panelMedio.add(botonEnviarCorreo);
		panel.add(panelMedio, BorderLayout.SOUTH);
	}

	private void estilarBoton(JButton btn) {
		btn.setFont(Estilos.FONT_BOTON);
		btn.setBackground(Estilos.COLOR_BOTON_MENU);
		btn.setForeground(java.awt.Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	}

	private void inicializarTabla() {
		JPanel panelTabla = new JPanel(new BorderLayout());
		panelTabla.setOpaque(false);
		panelTabla.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 40, 0, 40));

		String[] nombresColumnas = { "Asunto", "De", "Fecha" };

		tablaModelo = new DefaultTableModel(nombresColumnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		emailTabla = new JTable(tablaModelo);
		emailTabla.setRowHeight(35);
		emailTabla.getTableHeader().setFont(Estilos.FONT_BOTON);
		emailTabla.getTableHeader().setBackground(Estilos.COLOR_TABLA_HEADER);
		emailTabla.getTableHeader().setForeground(java.awt.Color.WHITE);
		emailTabla.setFont(Estilos.FONT_TEXTO);
		emailTabla.setSelectionBackground(Estilos.COLOR_TABLA_SELECCION);
		emailTabla.setSelectionForeground(java.awt.Color.WHITE);
		emailTabla.setShowGrid(false);
		emailTabla.setIntercellSpacing(new Dimension(0, 0));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < 3; i++)
			emailTabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

		JScrollPane scrollPane = new JScrollPane(emailTabla);
		scrollPane.getViewport().setBackground(java.awt.Color.WHITE);
		scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(Estilos.COLOR_TABLA_HEADER, 1));

		panelTabla.add(scrollPane, BorderLayout.CENTER);
		panel.add(panelTabla, BorderLayout.CENTER);
	}

	private void propiedadesVentana() {
		this.setTitle("Correos");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(630, 500);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(630, 500));
	}

	public void cargarCorreos(ArrayList<Correo> correos) {
		tablaModelo.setRowCount(0);
		// Ordenar el array por fecha
		Collections.sort(
				correos,
				Comparator.comparing(Correo::getFecha).reversed());
		for (Correo c : correos) {
			Object[] fila = new Object[3];
			fila[0] = c.getAsunto();
			fila[1] = c.getRemitente();

			fila[2] = c.getFecha();

			tablaModelo.addRow(fila);
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

}
