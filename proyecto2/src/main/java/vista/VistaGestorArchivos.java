package vista;

import javax.swing.*;
import org.apache.commons.net.ftp.FTPFile;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.net.URL;

import controlador.OyenteArchivos;
import modelo.MoTextos;

/**
 * Vista para la gestión de archivos mediante FTP.
 * Muestra el repositorio remoto y permite subir, descargar y eliminar archivos
 * o carpetas.
 */
public class VistaGestorArchivos extends JFrame {

	private DefaultListModel<FTPFile> listaModel;
	private JList<FTPFile> listaArchivos;

	private JButton botonSubida;
	private JButton botonDescarga;
	private JButton botonEliminar;
	private JButton botonCrearCarpeta;
	private JButton botonBorrarCarpeta;
	private JButton botonRenombrar;
	private JButton botonVolver;
	private JButton botonVolverMenuPrincipal;

	public VistaGestorArchivos() {
		this.setTitle(MoTextos.file_manager_title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 650);
		this.setLocationRelativeTo(null);

		Color colorFondo = new Color(248, 245, 242);
		Color colorTexto = new Color(74, 88, 89);
		Color colorBotonAccion = new Color(110, 137, 115);
		Color colorBotonNav = new Color(200, 190, 170);

		JPanel mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBackground(colorFondo);
		setContentPane(mainPanel);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		JPanel sidePanel = new JPanel() {
			private Image imagen;
			{
				URL url = getClass().getResource("/lateral_files.jpg");
				if (url != null) {
					imagen = new ImageIcon(url).getImage();
				}
			}

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagen != null) {
					double scale = Math.max((double) getWidth() / imagen.getWidth(this),
							(double) getHeight() / imagen.getHeight(this));
					int w = (int) (imagen.getWidth(this) * scale);
					int h = (int) (imagen.getHeight(this) * scale);
					g.drawImage(imagen, 0, 0, w, h, this);
				}
			}
		};

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.3;
		gbc.weighty = 1.0;
		mainPanel.add(sidePanel, gbc);

		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBackground(colorFondo);
		contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.7;
		gbc.weighty = 1.0;
		mainPanel.add(contentPanel, gbc);

		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
		header.setBackground(colorFondo);
		header.setAlignmentX(Component.LEFT_ALIGNMENT);

		title = new JLabel(MoTextos.file_repo_title);
		title.setFont(new Font("Segoe UI", Font.BOLD, 28));
		title.setForeground(colorTexto);

		subtitle = new JLabel(MoTextos.file_repo_subtitle);
		subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		subtitle.setForeground(new Color(150, 150, 150));

		header.add(title);
		header.add(Box.createVerticalStrut(5));
		header.add(subtitle);
		header.add(Box.createVerticalStrut(20));

		contentPanel.add(header, BorderLayout.NORTH);

		listaModel = new DefaultListModel<>();
		listaArchivos = new JList<>(listaModel);
		listaArchivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaArchivos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		listaArchivos.setFixedCellHeight(30);
		listaArchivos.setBackground(Color.WHITE);
		listaArchivos.setBorder(new EmptyBorder(5, 5, 5, 5));

		JScrollPane scrollPane = new JScrollPane(listaArchivos);
		scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel centerContainer = new JPanel(new BorderLayout(20, 0));
		centerContainer.setBackground(colorFondo);
		centerContainer.add(scrollPane, BorderLayout.CENTER);

		JPanel botonesPanel = new JPanel();
		botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
		botonesPanel.setBackground(colorFondo);

		botonSubida = new JButton(MoTextos.btn_upload);
		botonDescarga = new JButton(MoTextos.btn_download);
		botonEliminar = new JButton(MoTextos.btn_delete);
		botonCrearCarpeta = new JButton(MoTextos.btn_new_folder);
		botonBorrarCarpeta = new JButton(MoTextos.btn_delete_folder);
		botonRenombrar = new JButton(MoTextos.btn_rename);

		estilarBoton(botonSubida, colorBotonAccion, Color.WHITE);
		estilarBoton(botonDescarga, colorBotonAccion, Color.WHITE);
		estilarBoton(botonEliminar, new Color(200, 100, 100), Color.WHITE);
		estilarBoton(botonCrearCarpeta, colorBotonAccion, Color.WHITE);
		estilarBoton(botonBorrarCarpeta, new Color(200, 100, 100), Color.WHITE);
		estilarBoton(botonRenombrar, colorBotonAccion, Color.WHITE);

		lblActions = new JLabel(MoTextos.lbl_actions);
		botonesPanel.add(lblActions);
		botonesPanel.add(Box.createVerticalStrut(10));
		botonesPanel.add(botonSubida);
		botonesPanel.add(Box.createVerticalStrut(10));
		botonesPanel.add(botonDescarga);
		botonesPanel.add(Box.createVerticalStrut(10));
		botonesPanel.add(botonEliminar);
		botonesPanel.add(Box.createVerticalStrut(10));
		botonesPanel.add(botonCrearCarpeta);
		botonesPanel.add(Box.createVerticalStrut(10));
		botonesPanel.add(botonBorrarCarpeta);
		botonesPanel.add(Box.createVerticalStrut(10));
		botonesPanel.add(botonRenombrar);
		botonesPanel.add(Box.createVerticalGlue());

		centerContainer.add(botonesPanel, BorderLayout.EAST);
		contentPanel.add(centerContainer, BorderLayout.CENTER);

		JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		footer.setBackground(colorFondo);

		botonVolver = new JButton(MoTextos.btn_back);
		botonVolverMenuPrincipal = new JButton(MoTextos.btn_main_menu);

		estilarBoton(botonVolver, colorBotonNav, Color.BLACK);
		estilarBoton(botonVolverMenuPrincipal, colorBotonNav, Color.BLACK);

		footer.add(botonVolverMenuPrincipal);
		footer.add(botonVolver);

		contentPanel.add(footer, BorderLayout.SOUTH);
	}

	// UI Components for text update
	private JLabel title;
	private JLabel subtitle;
	private JLabel lblActions;

	private void estilarBoton(JButton btn, Color bg, Color fg) {
		btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btn.setBackground(bg);
		btn.setForeground(fg);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setAlignmentX(Component.LEFT_ALIGNMENT);
		btn.setMaximumSize(new Dimension(120, 35));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	private OyenteArchivos controlador;

	public void setControlador(OyenteArchivos c) {
		this.controlador = c;
		botonSubida.addActionListener(c);
		botonDescarga.addActionListener(c);
		botonEliminar.addActionListener(c);
		botonCrearCarpeta.addActionListener(c);
		botonBorrarCarpeta.addActionListener(c);
		botonRenombrar.addActionListener(c);
		botonVolver.addActionListener(c);
		botonVolverMenuPrincipal.addActionListener(c);
	}

	/**
	 * Inicializa el gestor de archivos llamando al controlador.
	 * Carga la lista de archivos inicial.
	 */
	public void inicializarFileManager() {
		if (controlador != null) {
			controlador.inicializarFileManager();
		}
	}

	public void hacerVisible() {
		setVisible(true);
	}

	public DefaultListModel<FTPFile> getListaModel() {
		return listaModel;
	}

	public JList<FTPFile> getListaArchivos() {
		return listaArchivos;
	}

	public JButton getBotonSubida() {
		return botonSubida;
	}

	public JButton getBotonDescarga() {
		return botonDescarga;
	}

	public JButton getBotonEliminar() {
		return botonEliminar;
	}

	public JButton getBotonCrearCarpeta() {
		return botonCrearCarpeta;
	}

	public JButton getBotonBorrarCarpeta() {
		return botonBorrarCarpeta;
	}

	public JButton getBotonRenombrar() {
		return botonRenombrar;
	}

	public JButton getBotonVolver() {
		return botonVolver;
	}

	public JButton getBotonVolverMenuPrincipal() {
		return botonVolverMenuPrincipal;
	}

	/**
	 * Actualiza los textos de la interfaz según el idioma seleccionado.
	 */
	public void actualizarTextos() {
		setTitle(MoTextos.file_manager_title);
		title.setText(MoTextos.file_repo_title);
		subtitle.setText(MoTextos.file_repo_subtitle);

		botonSubida.setText(MoTextos.btn_upload);
		botonDescarga.setText(MoTextos.btn_download);
		botonEliminar.setText(MoTextos.btn_delete);
		botonCrearCarpeta.setText(MoTextos.btn_new_folder);
		botonBorrarCarpeta.setText(MoTextos.btn_delete_folder);
		botonRenombrar.setText(MoTextos.btn_rename);

		lblActions.setText(MoTextos.lbl_actions);

		botonVolver.setText(MoTextos.btn_back);
		botonVolverMenuPrincipal.setText(MoTextos.btn_main_menu);

		repaint();
	}
}
