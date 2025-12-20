package vista;

import javax.swing.*;
import org.apache.commons.net.ftp.FTPFile;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.net.URL;

import controladorArchivos.OyenteArchivos;
import modelo.MoTextos;

/**
 * Vista para la gestión de archivos mediante el protocolo FTP. Permite
 * visualizar el repositorio remoto y realizar operaciones de subida, descarga,
 * borrado, creación de carpetas y renombrado.
 */
public class VistaGestorArchivos extends JFrame {

	/** Modelo de datos para la lista de archivos FTP. */
	private DefaultListModel<FTPFile> listaModel;

	/** Componente visual de lista para mostrar archivos y carpetas. */
	private JList<FTPFile> listaArchivos;

	/** Botón para cargar archivos desde el sistema local al servidor. */
	private JButton botonSubida;

	/** Botón para bajar archivos del servidor al sistema local. */
	private JButton botonDescarga;

	/** Botón para eliminar archivos seleccionados del servidor. */
	private JButton botonEliminar;

	/** Botón para crear un nuevo directorio en la ruta actual del servidor. */
	private JButton botonCrearCarpeta;

	/** Botón para eliminar un directorio seleccionado (debe estar vacío). */
	private JButton botonBorrarCarpeta;

	/** Botón para cambiar el nombre de un archivo o carpeta. */
	private JButton botonRenombrar;

	/** Botón para navegar al directorio superior (padre). */
	private JButton botonVolver;

	/** Botón para cerrar la vista y regresar al menú principal. */
	private JButton botonVolverMenuPrincipal;

	/** Etiqueta que muestra el título principal. */
	private JLabel title;

	/** Etiqueta que muestra una breve descripción de la vista. */
	private JLabel subtitle;

	/** Etiqueta que indica la ruta actual en el servidor FTP. */
	private JLabel pathLabel;

	/** Etiqueta para la sección de acciones de la barra lateral. */
	private JLabel lblActions;

	/** Referencia al controlador encargado de procesar los eventos. */
	private OyenteArchivos controlador;

	/**
	 * Constructor que inicializa la ventana de gestión de archivos y configura el
	 * diseño.
	 */
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

		// Panel lateral con imagen decorativa
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

		// Configuración de la cabecera
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

		pathLabel = new JLabel(MoTextos.lbl_current_path + " /");
		pathLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		pathLabel.setForeground(new Color(100, 100, 100));

		header.add(title);
		header.add(Box.createVerticalStrut(5));
		header.add(pathLabel);
		header.add(Box.createVerticalStrut(3));
		header.add(subtitle);
		header.add(Box.createVerticalStrut(20));

		contentPanel.add(header, BorderLayout.NORTH);

		// Lista de archivos con renderizador de iconos
		listaModel = new DefaultListModel<>();
		listaArchivos = new JList<>(listaModel);
		listaArchivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaArchivos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		listaArchivos.setFixedCellHeight(30);
		listaArchivos.setBackground(Color.WHITE);
		listaArchivos.setBorder(new EmptyBorder(5, 5, 5, 5));
		listaArchivos.setCellRenderer(new FileListCellRenderer());

		JScrollPane scrollPane = new JScrollPane(listaArchivos);
		scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

		JPanel centerContainer = new JPanel(new BorderLayout(20, 0));
		centerContainer.setBackground(colorFondo);
		centerContainer.add(scrollPane, BorderLayout.CENTER);

		// Panel de botones de acción lateral
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

		// Pie de página con botones de navegación
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

	/**
	 * Aplica estilos visuales a un botón de la interfaz.
	 */
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

	/**
	 * Vincula el controlador a los botones de la vista y configura sus listeners.
	 * 
	 * @param c El controlador OyenteArchivos.
	 */
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
	 * Inicializa la carga de datos del gestor de archivos llamando al controlador.
	 */
	public void inicializarFileManager() {
		if (controlador != null) {
			controlador.inicializarFileManager();
		}
	}

	/**
	 * Hace visible la ventana del gestor de archivos.
	 */
	public void hacerVisible() {
		setVisible(true);
	}

	/**
	 * Actualiza los textos de la interfaz según el idioma seleccionado.
	 */
	public void actualizarTextos() {
		setTitle(MoTextos.file_manager_title);
		title.setText(MoTextos.file_repo_title);
		subtitle.setText(MoTextos.file_repo_subtitle);
		String currentPath = pathLabel.getText().substring(pathLabel.getText().indexOf(" ") + 1);
		pathLabel.setText(MoTextos.lbl_current_path + " " + currentPath);

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

	/**
	 * Actualiza la etiqueta visual que muestra la ubicación actual en el servidor.
	 * 
	 * @param ruta La cadena de texto con la ruta del directorio.
	 */
	public void actualizarRutaActual(String ruta) {
		pathLabel.setText(MoTextos.lbl_current_path + " " + ruta);
	}

	// --- GETTERS Y SETTERS CON JAVADOC ---

	/**
	 * Obtiene el modelo de la lista de archivos.
	 * 
	 * @return El objeto DefaultListModel de FTPFile.
	 */
	public DefaultListModel<FTPFile> getListaModel() {
		return listaModel;
	}

	/**
	 * Obtiene el componente visual JList de archivos.
	 * 
	 * @return El componente JList.
	 */
	public JList<FTPFile> getListaArchivos() {
		return listaArchivos;
	}

	/**
	 * Obtiene el botón de subida de archivos.
	 * 
	 * @return El objeto JButton correspondiente.
	 */
	public JButton getBotonSubida() {
		return botonSubida;
	}

	/**
	 * Obtiene el botón de descarga de archivos.
	 * 
	 * @return El objeto JButton correspondiente.
	 */
	public JButton getBotonDescarga() {
		return botonDescarga;
	}

	/**
	 * Obtiene el botón de eliminación de archivos.
	 * 
	 * @return El objeto JButton correspondiente.
	 */
	public JButton getBotonEliminar() {
		return botonEliminar;
	}

	/**
	 * Obtiene el botón para crear nuevas carpetas.
	 * 
	 * @return El objeto JButton correspondiente.
	 */
	public JButton getBotonCrearCarpeta() {
		return botonCrearCarpeta;
	}

	/**
	 * Obtiene el botón para borrar carpetas.
	 * 
	 * @return El objeto JButton correspondiente.
	 */
	public JButton getBotonBorrarCarpeta() {
		return botonBorrarCarpeta;
	}

	/**
	 * Obtiene el botón de renombrado.
	 * 
	 * @return El objeto JButton correspondiente.
	 */
	public JButton getBotonRenombrar() {
		return botonRenombrar;
	}

	/**
	 * Obtiene el botón para volver al directorio padre.
	 * 
	 * @return El objeto JButton correspondiente.
	 */
	public JButton getBotonVolver() {
		return botonVolver;
	}

	/**
	 * Obtiene el botón para regresar al menú principal.
	 * 
	 * @return El objeto JButton correspondiente.
	 */
	public JButton getBotonVolverMenuPrincipal() {
		return botonVolverMenuPrincipal;
	}
}