package vista;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VistaCRUD extends JFrame {
    private ViBotones panelMenu;
    private ViTabla panelTabla;
    private ViBotones panelAcciones;
    private JPanel panelFondo;

    public VistaCRUD() {
        super("Gestión Serwo - Animales");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

        ArrayList<String> textosMenu = new ArrayList<>(Arrays.asList("ESPECIES", "RECINTOS", "CUIDADORES", "ANIMALES",
                "TRASLADOS", "ESPECIES_RECINTOS", "ELEMENTOS"));
        panelMenu = new ViBotones(textosMenu);

        panelTabla = new ViTabla();

        ArrayList<String> textosAcciones = new ArrayList<>(Arrays.asList("New", "Main menu"));
        panelAcciones = new ViBotones(textosAcciones);

        panelFondo.add(panelMenu, BorderLayout.NORTH);
        panelFondo.add(panelTabla, BorderLayout.CENTER);
        panelFondo.add(panelAcciones, BorderLayout.SOUTH);
    }

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
}
