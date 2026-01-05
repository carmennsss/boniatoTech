package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modelo.MoTextos;

/**
 * Vista para la creación de nuevos roles en el sistema.
 * Esta ventana muestra una tabla con los roles disponibles y permite abrir un
 * diálogo modal para registrar nuevas categorías de acceso.
 */
public class ViCrearRol extends JFrame {
    /** Lista de etiquetas de texto de la interfaz para la gestión dinámica de idiomas. */
    private ArrayList<JLabel> textos;

    /** Campo de entrada para el nombre del nuevo rol en el diálogo modal. */
    private JTextField txtNombre;

    /** Campo de entrada para la descripción de funciones del nuevo rol. */
    private JTextField txtDescripcion;

    /** Lista de botones de control de la ventana (Añadir, Volver). */
    private ArrayList<JButton> botones;

    /** Componente de tabla personalizado para visualizar los roles existentes. */
    private ViTabla panelTabla;

    /**
     * Constructor de la vista. Inicializa todos los componentes gráficos.
     */
    public ViCrearRol() {
        propiedades();
    }

    /**
     * Orquesta la configuración de la ventana invocando los métodos de diseño.
     */
    private void propiedades() {
        configurarVentana();
        configurarFondo();
        configurarTitulo();
        configurarTabla();
        configurarBotones();
    }

    /**
     * Establece los parámetros básicos del marco (título, tamaño y posición).
     */
    private void configurarVentana() {
        this.setTitle(MoTextos.roles_title_create);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    /**
     * Configura el panel principal con una imagen de fondo escalada o un color sólido.
     */
    private void configurarFondo() {
        JPanel panelFondo = new JPanel() {
            private java.awt.Image imagen;
            {
                java.net.URL url = getClass().getResource("/fondo_zoo_1.png");
                if (url != null) {
                    imagen = new javax.swing.ImageIcon(url).getImage();
                }
            }

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (imagen != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Estilos.FONDO_PRINCIPAL);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelFondo.setLayout(new BorderLayout(20, 20));
        this.setContentPane(panelFondo);
    }

    /**
     * Configura la cabecera de la ventana con un título estilizado y redondeado.
     */
    private void configurarTitulo() {
        this.textos = new ArrayList<>();
        JLabel titulo = new JLabel(MoTextos.roles_title_available, SwingConstants.CENTER);
        titulo.setFont(Estilos.FONT_TITULO);
        titulo.setForeground(Estilos.COLOR_TITULO_APP);
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
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelTitulo.add(titulo);

        JPanel northContainer = new JPanel(new BorderLayout());
        northContainer.setOpaque(false);
        northContainer.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));
        northContainer.add(panelTitulo, BorderLayout.CENTER);

        getContentPane().add(northContainer, BorderLayout.NORTH);
    }

    /**
     * Inicializa el panel de la tabla de datos y lo posiciona en el centro.
     */
    private void configurarTabla() {
        panelTabla = new ViTabla();
        panelTabla.setOpaque(false);

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setOpaque(false);
        centerContainer.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
        centerContainer.add(panelTabla, BorderLayout.CENTER);

        getContentPane().add(centerContainer, BorderLayout.CENTER);
    }

    /**
     * Define y posiciona los botones de acción en la parte inferior.
     */
    private void configurarBotones() {
        this.botones = new ArrayList<>();
        this.botones.add(new JButton(MoTextos.roles_btn_create));
        this.botones.add(new JButton(MoTextos.btn_back_whitelist));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setOpaque(false);

        aniadirEstiloBoton(this.botones.get(0));
        aniadirEstiloBoton(this.botones.get(1));
        this.botones.get(1).setBackground(new java.awt.Color(200, 100, 100));

        panelBotones.add(this.botones.get(0));
        panelBotones.add(this.botones.get(1));

        getContentPane().add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Aplica el esquema de colores y fuentes de la aplicación a un botón.
     * @param btn El botón al que se le aplicará el estilo.
     */
    private void aniadirEstiloBoton(JButton btn) {
        btn.setFont(Estilos.FONT_BOTON);
        btn.setBackground(Estilos.COLOR_BOTON_MENU);
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 40));
    }

    /**
     * Despliega un diálogo modal para la captura de datos del nuevo rol.
     * @return 0 si el usuario pulsó "Añadir", 1 si pulsó "Cancelar".
     */
    public int mostrarAgregarRol() {
        final JDialog dialog = new JDialog(this, MoTextos.roles_dialog_title, true);
        dialog.setUndecorated(true);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Estilos.BEIGE_CANVAS);
        panel.setBorder(BorderFactory.createLineBorder(Estilos.DARK_SPRUCE, 2));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel(MoTextos.roles_dialog_title);
        lblTitulo.setFont(Estilos.FONT_TITULO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(javax.swing.Box.createVerticalStrut(20));

        txtNombre = new JTextField();
        aniadirEstiloInput(txtNombre);
        panel.add(new JLabel(MoTextos.roles_lbl_name));
        panel.add(txtNombre);
        panel.add(javax.swing.Box.createVerticalStrut(20));

        txtDescripcion = new JTextField();
        aniadirEstiloInput(txtDescripcion);
        panel.add(new JLabel(MoTextos.roles_lbl_desc));
        panel.add(txtDescripcion);
        panel.add(javax.swing.Box.createVerticalStrut(20));

        final int[] result = { -1 };
        JButton btnNew = crearBotonDialogo(MoTextos.btn_dialog_add, Estilos.COLOR_BOTON_MENU);
        JButton btnCancel = crearBotonDialogo(MoTextos.btn_dialog_cancel, new java.awt.Color(200, 100, 100));

        btnNew.addActionListener(e -> { result[0] = 0; dialog.dispose(); });
        btnCancel.addActionListener(e -> { result[0] = 1; dialog.dispose(); });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnNew);
        buttonPanel.add(btnCancel);
        panel.add(buttonPanel);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        return result[0];
    }

    /**
     * Estiliza los campos de texto del diálogo de creación.
     * @param input El componente de entrada a estilar.
     */
    private void aniadirEstiloInput(javax.swing.JComponent input) {
        input.setFont(Estilos.FONT_TEXTO);
        input.setBackground(Estilos.COLOR_INPUT_BG);
        input.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Estilos.BLUE_SLATE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        input.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    }

    /**
     * Crea un botón específico para ventanas de diálogo.
     * @param texto El texto del botón.
     * @param color El color de fondo.
     * @return El objeto JButton configurado.
     */
    private JButton crearBotonDialogo(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 40));
        return btn;
    }

    /**
     * Hace visible la ventana principal.
     */
    public void hacerVisible() {
        setVisible(true);
    }

    /**
     * Actualiza dinámicamente las etiquetas y títulos según el idioma configurado.
     */
    public void actualizarTextos() {
        this.setTitle(MoTextos.roles_title_create);
        if (!textos.isEmpty()) textos.get(0).setText(MoTextos.roles_title_available);
        if (botones.size() >= 2) {
            botones.get(0).setText(MoTextos.roles_btn_create);
            botones.get(1).setText(MoTextos.btn_back_whitelist);
        }
        repaint();
    }

    // --- GETTERS ---

    /**
     * Obtiene la lista de botones de acción de la ventana.
     * @return ArrayList de JButtons.
     */
    public ArrayList<JButton> getBotones() { 
        return botones; 
    }

    /**
     * Obtiene el campo de texto donde se introduce el nombre del rol.
     * @return JTextField para el nombre.
     */
    public JTextField getTextFieldNombre() { 
        return txtNombre; 
    }

    /**
     * Obtiene el campo de texto donde se introduce la descripción del rol.
     * @return JTextField para la descripción.
     */
    public JTextField getTextFieldDescripcion() { 
        return txtDescripcion; 
    }

    /**
     * Obtiene un botón específico de la lista mediante su índice.
     * @param i Índice del botón deseado.
     * @return El objeto JButton correspondiente.
     */
    public JButton getBoton(int i) {
        return botones.get(i);
    }

    /**
     * Obtiene el panel que contiene la tabla de visualización de datos.
     * @return El componente ViTabla.
     */
    public ViTabla getPanelTabla() { 
        return panelTabla; 
    }
}