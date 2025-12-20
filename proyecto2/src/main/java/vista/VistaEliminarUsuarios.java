package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modelo.ModeloBaseDatos;
import modelo.ModeloClienteFTP;

/**
 * Vista encargada de la interfaz para la eliminación de usuarios del sistema.
 * Proporciona una tabla para visualizar los usuarios registrados y botones 
 * para ejecutar la baja tanto en la base de datos como en el servidor FTP.
 */
public class VistaEliminarUsuarios extends JFrame {
    /** Referencia a la vista de registro para permitir la navegación. */
    private VistaRegistroUsuarios vistaUsuarios;

    /** Modelo encargado de las operaciones en el servidor FTP. */
    private ModeloClienteFTP modeloFTP;

    /** Modelo encargado de las operaciones en la base de datos. */
    private ModeloBaseDatos bd;

    /** Lista de etiquetas de texto para la gestión de internacionalización. */
    private ArrayList<JLabel> textos;

    /** Lista de botones de la interfaz. */
    private ArrayList<JButton> botones;

    /** Botón para ejecutar la acción de eliminar. */
    private JButton btnEliminar;

    /** Botón para cancelar la operación y volver. */
    private JButton btnVolver;

    /** Componente de tabla personalizado para mostrar la lista de usuarios. */
    private ViTabla tabla;

    /** Imagen de fondo para la personalización estética de la ventana. */
    private Image imagenFondo;

    /**
     * Constructor que inicializa la vista con las dependencias necesarias.
     *
     * @param vistaUsuarios Vista de registro previa.
     * @param modeloFTP     Lógica del cliente FTP.
     * @param bd            Lógica de la base de datos.
     */
    public VistaEliminarUsuarios(VistaRegistroUsuarios vistaUsuarios, ModeloClienteFTP modeloFTP, ModeloBaseDatos bd) {
        this.vistaUsuarios = vistaUsuarios;
        this.modeloFTP = modeloFTP;
        this.bd = bd;
        propiedades();
    }

    /**
     * Configura la ventana y orquesta la creación de todos los componentes visuales.
     */
    private void propiedades() {
        configurarVentana();
        configurarFondo();
        configurarTitulo();
        configurarTabla();
        configurarBotones();
    }

    /**
     * Establece los parámetros básicos del JFrame.
     */
    private void configurarVentana() {
        this.textos = new ArrayList<>();
        this.botones = new ArrayList<>();

        this.setTitle(modelo.MoTextos.del_user_title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
    }

    /**
     * Carga y configura el panel de fondo con imagen o color sólido.
     */
    private void configurarFondo() {
        URL url = getClass().getResource("/fondo_abstracto_2.png");
        if (url != null) {
            imagenFondo = new ImageIcon(url).getImage();
        }

        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    int width = getWidth();
                    int height = getHeight();
                    g.drawImage(imagenFondo, 0, 0, width, height, this);
                } else {
                    g.setColor(Estilos.FONDO_PRINCIPAL);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelFondo.setLayout(new BorderLayout(20, 20));
        panelFondo.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.setContentPane(panelFondo);
    }

    /**
     * Configura la sección superior con el título dinámico.
     */
    private void configurarTitulo() {
        JLabel titulo = new JLabel(modelo.MoTextos.del_user_title);
        titulo.setFont(Estilos.FONT_TITULO);
        titulo.setForeground(Estilos.DARK_SPRUCE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.textos.add(titulo);

        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        panelTitulo.setOpaque(false);
        panelTitulo.setBorder(new EmptyBorder(10, 20, 10, 20));
        panelTitulo.add(titulo);
        getContentPane().add(panelTitulo, BorderLayout.NORTH);
    }

    /**
     * Inicializa el contenedor central con la tabla de datos.
     */
    private void configurarTabla() {
        this.tabla = new ViTabla();

        JPanel panelTablaContenedor = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 180));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        panelTablaContenedor.setOpaque(false);
        panelTablaContenedor.setBorder(new EmptyBorder(20, 20, 20, 20));

        this.tabla.setOpaque(false);
        panelTablaContenedor.add(this.tabla, BorderLayout.CENTER);
        getContentPane().add(panelTablaContenedor, BorderLayout.CENTER);
    }

    /**
     * Configura la zona inferior con los botones de acción principal.
     */
    private void configurarBotones() {
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelSur.setOpaque(false);

        btnEliminar = new JButton(modelo.MoTextos.del_btn_delete);
        btnVolver = new JButton(modelo.MoTextos.del_btn_back);

        estilarBoton(btnEliminar, Estilos.COLOR_BOTON_MENU);
        estilarBoton(btnVolver, new Color(200, 100, 100));

        this.botones.add(btnEliminar);
        this.botones.add(btnVolver);

        panelSur.add(btnEliminar);
        panelSur.add(btnVolver);

        getContentPane().add(panelSur, BorderLayout.SOUTH);
    }

    /**
     * Aplica el esquema de diseño institucional a los botones.
     *
     * @param btn     Botón a procesar.
     * @param bgColor Color de fondo deseado.
     */
    private void estilarBoton(JButton btn, Color bgColor) {
        btn.setFont(Estilos.FONT_BOTON);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 40));
    }

    /**
     * Hace visible la ventana y refresca la tabla interna.
     */
    public void hacerVisible() {
        this.setVisible(true);
        this.tabla.setVisible(true);
    }

    /**
     * Actualiza dinámicamente los textos de la interfaz según el idioma.
     */
    public void actualizarTextos() {
        this.setTitle(modelo.MoTextos.del_user_title);
        if (textos.size() > 0 && textos.get(0) instanceof JLabel) {
            ((JLabel) textos.get(0)).setText(modelo.MoTextos.del_user_title);
        }
        btnEliminar.setText(modelo.MoTextos.del_btn_delete);
        btnVolver.setText(modelo.MoTextos.del_btn_back);
    }

    // --- GETTERS Y SETTERS AL FINAL ---

    /**
     * Obtiene el botón de eliminar.
     * @return El objeto JButton.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Establece el botón de eliminar.
     * @param btnEliminar Nueva instancia de botón.
     */
    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    /**
     * Obtiene el botón de volver.
     * @return El objeto JButton.
     */
    public JButton getBtnVolver() {
        return btnVolver;
    }

    /**
     * Establece el botón de volver.
     * @param btnVolver Nueva instancia de botón.
     */
    public void setBtnVolver(JButton btnVolver) {
        this.btnVolver = btnVolver;
    }

    /**
     * Obtiene el panel de la tabla.
     * @return El objeto ViTabla.
     */
    public ViTabla getTabla() {
        return this.tabla;
    }

    /**
     * Establece la tabla de usuarios.
     * @param tabla Nueva instancia de ViTabla.
     */
    public void setTabla(ViTabla tabla) {
        this.tabla = tabla;
    }

    /**
     * Obtiene la lista completa de botones de la vista.
     * @return ArrayList de botones.
     */
    public ArrayList<JButton> getBotones() {
        return botones;
    }
}