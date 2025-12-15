package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ViBotones extends JPanel {
    private ArrayList<JButton> botones;

    public ViBotones(ArrayList<String> textos) {
        this.botones = new ArrayList<>();
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setBackground(Estilos.COLOR_MENU_FONDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        crearBotones(textos);
    }

    private void crearBotones(ArrayList<String> textos) {
        for (String texto : textos) {
            JButton btn = new JButton(texto);
            estilarBoton(btn);
            botones.add(btn);
            this.add(btn);
        }
    }

    private void estilarBoton(JButton btn) {
        btn.setFont(Estilos.FONT_BOTON);
        btn.setBackground(Estilos.DARK_SPRUCE);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public ArrayList<JButton> getBotones() {
        return botones;
    }
}
