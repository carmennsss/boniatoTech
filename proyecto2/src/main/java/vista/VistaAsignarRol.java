package vista;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.Rol;

public class VistaAsignarRol extends JFrame {
    private ArrayList<JLabel> textos;
    private ArrayList<JButton> botones;
    private ViTabla tabla;
    private JComboBox<Rol> comboRoles = new JComboBox<>();

    public VistaAsignarRol() {
        this.textos = new ArrayList<>();
        this.botones = new ArrayList<>();
        this.tabla = new ViTabla();

        this.setTitle("Asignar Rol");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new java.awt.BorderLayout(20, 20));

        // Use a background color or image consistent with other views
        JPanel panelPrincipal = new JPanel(new java.awt.BorderLayout(20, 20));
        panelPrincipal.setBackground(Estilos.BEIGE_CANVAS);
        panelPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setContentPane(panelPrincipal);

        JLabel titulo = new JLabel("Asignar Roles a Usuarios");
        titulo.setFont(Estilos.FONT_TITULO);
        titulo.setForeground(Estilos.COLOR_TITULO_APP);
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        this.textos.add(titulo);
        panelPrincipal.add(titulo, java.awt.BorderLayout.NORTH);

        // Table in Center
        this.tabla.setOpaque(false);
        panelPrincipal.add(this.tabla, java.awt.BorderLayout.CENTER);

        // Bottom panel for controls
        JPanel panelSur = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 10));
        panelSur.setOpaque(false);

        this.comboRoles.setFont(Estilos.FONT_TEXTO);
        this.comboRoles.setPreferredSize(new java.awt.Dimension(200, 30));

        JButton btnAsignar = new JButton("Asignar");
        JButton btnDesasignar = new JButton("Desasignar");
        JButton btnVolver = new JButton("Volver");

        estilarBoton(btnAsignar);
        estilarBoton(btnDesasignar);
        estilarBoton(btnVolver);
        btnVolver.setBackground(new java.awt.Color(200, 100, 100));

        this.botones.add(btnAsignar);
        this.botones.add(btnDesasignar);
        this.botones.add(btnVolver);

        panelSur.add(new JLabel("Rol: "));
        panelSur.add(this.comboRoles);
        panelSur.add(btnAsignar);
        panelSur.add(btnDesasignar);
        panelSur.add(btnVolver);

        panelPrincipal.add(panelSur, java.awt.BorderLayout.SOUTH);
    }

    private void estilarBoton(JButton btn) {
        btn.setFont(Estilos.FONT_BOTON);
        btn.setBackground(Estilos.COLOR_BOTON_MENU);
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new java.awt.Dimension(150, 40));
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
