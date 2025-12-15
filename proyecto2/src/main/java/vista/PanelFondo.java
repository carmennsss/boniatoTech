package vista;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {
    private Image imagen;

    public PanelFondo() {
        URL url = getClass().getResource("/fondo.jpg");
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
}
