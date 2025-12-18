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

public class VistaAsignarRol extends JFrame {
    private ArrayList<JLabel> textos;
    private ArrayList<JButton> botones;
    private ViTabla tabla;
    private JComboBox<Rol> comboRoles = new JComboBox<>();
    private Image imagenFondo;

    public VistaAsignarRol() {
        this.textos = new ArrayList<>();
        this.botones = new ArrayList<>();
        this.tabla = new ViTabla();

        this.setTitle("Asignar Rol");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

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

        JLabel titulo = new JLabel("Asignar Roles a Usuarios");
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
        panelFondo.add(panelTitulo, BorderLayout.NORTH);

        // Container for table with semi-transparent background
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
        panelFondo.add(panelTablaContenedor, BorderLayout.CENTER);

        // Bottom panel for controls
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelSur.setOpaque(false);

        this.comboRoles.setFont(Estilos.FONT_TEXTO);
        this.comboRoles.setPreferredSize(new Dimension(200, 35));

        JButton btnAsignar = new JButton("Asignar");
        JButton btnDesasignar = new JButton("Desasignar");
        JButton btnVolver = new JButton("Volver");

        estilarBoton(btnAsignar, Estilos.COLOR_BOTON_MENU);
        estilarBoton(btnDesasignar, Estilos.BLUE_SLATE);
        estilarBoton(btnVolver, new Color(200, 100, 100));

        this.botones.add(btnAsignar);
        this.botones.add(btnDesasignar);
        this.botones.add(btnVolver);

        JLabel lblRol = new JLabel("Rol: ");
        lblRol.setFont(Estilos.FONT_BOTON);
        lblRol.setForeground(Estilos.COLOR_LABEL);

        panelSur.add(lblRol);
        panelSur.add(this.comboRoles);
        panelSur.add(btnAsignar);
        panelSur.add(btnDesasignar);
        panelSur.add(btnVolver);

        panelFondo.add(panelSur, BorderLayout.SOUTH);
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

    public ViTabla getTabla() {
        return this.tabla;
    }

    public ArrayList<JButton> getBotones() {
        return botones;
    }

    public void setTabla(ViTabla tabla) {
        this.tabla = tabla;
    }

    public JComboBox<Rol> getComboRoles() {
        return this.comboRoles;
    }

    public void hacerVisible() {
        this.tabla.setVisible(true);
    }

}
