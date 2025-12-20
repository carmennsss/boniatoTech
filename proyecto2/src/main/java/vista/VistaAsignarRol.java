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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modelo.Rol;
import modelo.MoTextos;

/**
 * Vista para la asignación y desasignación de roles a los usuarios existentes.
 */
public class VistaAsignarRol extends JFrame {
    /** Lista de etiquetas de texto de la interfaz. */
    private ArrayList<JLabel> textos;

    /** Lista de botones de la interfaz. */
    private ArrayList<JButton> botones;

    /** Panel que contiene la tabla de usuarios. */
    private ViTabla tabla;

    /** ComboBox para seleccionar el rol a asignar. */
    private JComboBox<Rol> comboRoles = new JComboBox<>();

    /** Imagen de fondo de la ventana. */
    private Image imagenFondo;

    /** Etiqueta para el selector de rol. */
    private JLabel lblRol;

    /** Etiqueta del título de la ventana. */
    private JLabel titulo;

    public VistaAsignarRol() {
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
        this.textos = new ArrayList<>();
        this.botones = new ArrayList<>();

        this.setTitle(MoTextos.roles_title_assign);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
    }

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

    private void configurarTitulo() {
        titulo = new JLabel(MoTextos.roles_title_assign);
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

    private void configurarBotones() {
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelSur.setOpaque(false);

        this.comboRoles.setFont(Estilos.FONT_TEXTO);
        this.comboRoles.setPreferredSize(new Dimension(200, 35));

        JButton btnAsignar = new JButton(MoTextos.roles_btn_assign);
        JButton btnDesasignar = new JButton(MoTextos.btn_unassign);
        JButton btnVolver = new JButton(MoTextos.btn_back_whitelist);

        estilarBoton(btnAsignar, Estilos.COLOR_BOTON_MENU);
        estilarBoton(btnDesasignar, Estilos.BLUE_SLATE);
        estilarBoton(btnVolver, new Color(200, 100, 100));

        this.botones.add(btnAsignar);
        this.botones.add(btnDesasignar);
        this.botones.add(btnVolver);

        lblRol = new JLabel(MoTextos.roles_lbl_role);
        lblRol.setFont(Estilos.FONT_BOTON);
        lblRol.setForeground(Estilos.COLOR_LABEL);

        panelSur.add(lblRol);
        panelSur.add(this.comboRoles);
        panelSur.add(btnAsignar);
        panelSur.add(btnDesasignar);
        panelSur.add(btnVolver);

        getContentPane().add(panelSur, BorderLayout.SOUTH);
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void actualizarTextos() {
        this.setTitle(MoTextos.roles_title_assign);
        titulo.setText(MoTextos.roles_title_assign);
        botones.get(0).setText(MoTextos.roles_btn_assign);
        botones.get(1).setText(MoTextos.btn_unassign);
        botones.get(2).setText(MoTextos.btn_back_whitelist);
        lblRol.setText(MoTextos.roles_lbl_role);
        repaint();
    }

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
     * Obtiene el panel que contiene la tabla de usuarios.
     * 
     * @return Panel de la tabla.
     */
    public ViTabla getTabla() {
        return this.tabla;
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
     * Establece el panel de tabla.
     * 
     * @param tabla Nuevo panel de tabla.
     */
    public void setTabla(ViTabla tabla) {
        this.tabla = tabla;
    }

    /**
     * Obtiene el combo box de roles.
     * 
     * @return Combo box de roles.
     */
    public JComboBox<Rol> getComboRoles() {
        return this.comboRoles;
    }

    /**
     * Hace visible la tabla.
     */
    public void hacerVisible() {
        this.tabla.setVisible(true);
    }

}
