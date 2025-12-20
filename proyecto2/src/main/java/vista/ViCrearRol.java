package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import modelo.MoTextos;

/**
 * Vista para la creación de nuevos roles en el sistema.
 * Muestra una tabla con los roles existentes y permite añadir uno nuevo
 * mediante un diálogo.
 */
public class ViCrearRol extends JFrame {
    /** Lista de etiquetas de texto de la interfaz. */
    private ArrayList<JLabel> textos;

    /** Campo de texto para el nombre del rol. */
    private JTextField txtNombre;

    /** Campo de texto para la descripción del rol. */
    private JTextField txtDescripcion;

    /** Lista de botones de la interfaz. */
    private ArrayList<JButton> botones;

    /** Panel que contiene la tabla de roles. */
    private ViTabla panelTabla;

    public ViCrearRol() {
        propiedades();
    }

    private void propiedades() {
        configurarVentana();
        configurarFondo();
        configurarTitulo();
        configurarTabla();
        configurarBotones();
    }

    private void configurarVentana() {
        this.setTitle(MoTextos.roles_title_create);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

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
                    int width = getWidth();
                    int height = getHeight();
                    g.drawImage(imagen, 0, 0, width, height, this);
                } else {
                    g.setColor(Estilos.FONDO_PRINCIPAL);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelFondo.setLayout(new BorderLayout(20, 20));
        this.setContentPane(panelFondo);
    }

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

    private void configurarTabla() {
        panelTabla = new ViTabla();
        panelTabla.setOpaque(false);

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setOpaque(false);
        centerContainer.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
        centerContainer.add(panelTabla, BorderLayout.CENTER);

        getContentPane().add(centerContainer, BorderLayout.CENTER);
    }

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
     * Muestra un diálogo modal para ingresar el nombre y descripción del nuevo rol.
     *
     * @return 0 si se crea el rol, 1 si se cancela.
     */
    public int mostrarAgregarRol() {
        final JDialog dialog = new javax.swing.JDialog(
                this,
                MoTextos.roles_dialog_title,
                true);

        dialog.setUndecorated(true);
        dialog.setLayout(new java.awt.BorderLayout());

        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setBackground(Estilos.BEIGE_CANVAS);
        panel.setBorder(javax.swing.BorderFactory.createLineBorder(Estilos.DARK_SPRUCE, 2));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));

        javax.swing.JLabel lblTitulo = new javax.swing.JLabel(MoTextos.roles_dialog_title);
        lblTitulo.setFont(Estilos.FONT_TITULO);
        lblTitulo.setForeground(Estilos.COLOR_TITULO_APP);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(javax.swing.Box.createVerticalStrut(20));

        JLabel lblNombre = new JLabel(MoTextos.roles_lbl_name);
        lblNombre.setFont(Estilos.FONT_BOTON);
        lblNombre.setForeground(Estilos.COLOR_LABEL);

        txtNombre = new JTextField();
        aniadirEstiloInput(txtNombre);

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(javax.swing.Box.createVerticalStrut(20));

        JLabel lblDescripcion = new JLabel(MoTextos.roles_lbl_desc);
        lblDescripcion.setFont(Estilos.FONT_BOTON);
        lblDescripcion.setForeground(Estilos.COLOR_LABEL);

        txtDescripcion = new JTextField();
        aniadirEstiloInput(txtDescripcion);

        panel.add(lblDescripcion);
        panel.add(txtDescripcion);
        panel.add(javax.swing.Box.createVerticalStrut(20));

        final int[] result = { -1 };

        JButton btnNew = crearBotonDialogo(MoTextos.btn_dialog_add, Estilos.COLOR_BOTON_MENU);
        JButton btnCancel = crearBotonDialogo(MoTextos.btn_dialog_cancel, new java.awt.Color(200, 100, 100));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result[0] = 0;
                dialog.dispose();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result[0] = 1;
                dialog.dispose();
            }
        });

        buttonPanel.add(btnNew);
        buttonPanel.add(btnCancel);
        panel.add(buttonPanel);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        return result[0];
    }

    private void aniadirEstiloInput(javax.swing.JComponent input) {
        input.setFont(Estilos.FONT_TEXTO);
        input.setBackground(Estilos.COLOR_INPUT_BG);
        input.setForeground(Estilos.COLOR_INPUT_TEXT);
        input.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(Estilos.BLUE_SLATE, 1),
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        input.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    }

    private javax.swing.JButton crearBotonDialogo(String texto, Color color) {
        javax.swing.JButton btn = new javax.swing.JButton(texto);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 40));
        return btn;
    }

    /**
     * Muestra la ventana.
     */
    public void mostrar() {
        setVisible(true);
    }

    /**
     * Hace visible la ventana.
     */
    public void hacerVisible() {
        setVisible(true);
    }

    /**
     * Obtiene la lista de botones.
     * 
     * @return Lista de botones.
     */
    public ArrayList<JButton> getBotones() {
        return botones;
    }

    /**
     * Obtiene el campo de texto del nombre del rol.
     * 
     * @return Campo de texto del nombre.
     */
    public JTextField getTextFieldNombre() {
        return txtNombre;
    }

    /**
     * Obtiene el campo de texto de la descripción del rol.
     * 
     * @return Campo de texto de la descripción.
     */
    public JTextField getTextFieldDescripcion() {
        return txtDescripcion;
    }

    /**
     * Obtiene un botón específico por índice.
     * 
     * @param i Índice del botón.
     * @return El botón en la posición indicada.
     */
    public JButton getBoton(int i) {
        return botones.get(i);
    }

    /**
     * Obtiene el panel que contiene la tabla de roles.
     * 
     * @return Panel de la tabla.
     */
    public ViTabla getPanelTabla() {
        return panelTabla;
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void actualizarTextos() {
        this.setTitle(MoTextos.roles_title_create);
        if (!textos.isEmpty()) {
            textos.get(0).setText(MoTextos.roles_title_available);
        }
        if (botones.size() >= 2) {
            botones.get(0).setText(MoTextos.roles_btn_create);
            botones.get(1).setText(MoTextos.btn_back_whitelist);
        }
        repaint();
    }
}
