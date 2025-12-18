package vista;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import modelo.MoTextos;

public class VistaCRUD extends JFrame {
    private ViBotones panelMenu;
    private ViTabla panelTabla;
    private ViBotones panelAcciones;
    private JPanel panelFondo;

    public VistaCRUD() {
        propiedades();
    }

    private void propiedades() {
        configurarVentana();
        configurarFondo();
        configurarComponentes();
    }

    private void configurarVentana() {
        this.setTitle("Gestión Serwo - Animales");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void configurarFondo() {
        panelFondo = new JPanel() {
            private Image imagen;
            {
                java.net.URL url = getClass().getResource("/fondo.jpg");
                if (url != null) {
                    imagen = new ImageIcon(url).getImage();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagen != null) {
                    int imgW = imagen.getWidth(this);
                    int imgH = imagen.getHeight(this);

                    if (imgW > 0 && imgH > 0) {
                        int panelW = getWidth();
                        int panelH = getHeight();

                        double scale = Math.max((double) panelW / imgW, (double) panelH / imgH);

                        int newW = (int) (imgW * scale);
                        int newH = (int) (imgH * scale);

                        int x = (panelW - newW) / 2;
                        int y = (panelH - newH) / 2;

                        g.drawImage(imagen, x, y, newW, newH, this);
                    }
                }
            }
        };
        panelFondo.setLayout(new BorderLayout());
        setContentPane(panelFondo);
    }

    private void configurarComponentes() {
        ArrayList<String> textosMenu = new ArrayList<>(Arrays.asList(
                "ESPECIES", "RECINTOS", "CUIDADORES", "ANIMALES", "TRASLADOS", "ESPECIES_RECINTOS", "ELEMENTOS"));
        panelMenu = new ViBotones(textosMenu);

        panelTabla = new ViTabla();

        ArrayList<String> textosAcciones = new ArrayList<>(Arrays.asList(MoTextos.btn_new, MoTextos.btn_main_menu));
        panelAcciones = new ViBotones(textosAcciones);

        panelFondo.add(panelMenu, BorderLayout.NORTH);
        panelFondo.add(panelTabla, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setOpaque(false);

        lblInstrucciones = new javax.swing.JLabel(MoTextos.lbl_crud_instructions,
                javax.swing.SwingConstants.CENTER);
        lblInstrucciones.setForeground(java.awt.Color.WHITE);
        lblInstrucciones.setFont(Estilos.FONT_BOTON);
        lblInstrucciones.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 5, 0));

        panelSur.add(lblInstrucciones, BorderLayout.NORTH);
        panelSur.add(panelAcciones, BorderLayout.CENTER);

        panelFondo.add(panelSur, BorderLayout.SOUTH);
    }

    private javax.swing.JLabel lblInstrucciones;

    public ViBotones getPanelMenu() {
        return panelMenu;
    }

    public ViTabla getPanelTabla() {
        return panelTabla;
    }

    public ViBotones getPanelAcciones() {
        return panelAcciones;
    }

    public void hacerVisible() {
        setVisible(true);
    }

    public void actualizarTextos() {
        lblInstrucciones.setText(MoTextos.lbl_crud_instructions);
        if (panelAcciones.getBotones().size() > 0)
            panelAcciones.getBotones().get(0).setText(MoTextos.btn_new);
        if (panelAcciones.getBotones().size() > 1)
            panelAcciones.getBotones().get(1).setText(MoTextos.btn_main_menu);
        repaint();
    }
}
