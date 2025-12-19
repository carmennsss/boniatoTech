package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Panel personalizado que contiene y organiza una lista horizontal de botones.
 */
public class ViBotones extends JPanel {
    private ArrayList<JButton> botones;

    public ViBotones(ArrayList<String> textos) {
        propiedades(textos);
    }

    private void propiedades(ArrayList<String> textos) {
        this.botones = new ArrayList<>();
        configurarPanel();
        crearBotones(textos);
    }

    private void configurarPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        setBackground(Estilos.COLOR_MENU_FONDO);
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    /**
     * Crea e inicializa los botones basados en la lista de textos proporcionada.
     *
     * @param textos Lista de etiquetas para los botones.
     */
    private void crearBotones(ArrayList<String> textos) {
        for (String texto : textos) {
            JButton btn = new JButton(texto);
            estilarBoton(btn);
            botones.add(btn);
            this.add(btn);
        }
    }

    /**
     * Aplica el estilo visual estándar a un botón.
     *
     * @param btn El botón a estilar.
     */
    private void estilarBoton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 11));
        btn.setBackground(Estilos.DARK_SPRUCE);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new java.awt.Dimension(115, 35));
    }

    /**
     * Obtiene la lista de botones del panel.
     * 
     * @return Lista de botones.
     */
    public ArrayList<JButton> getBotones() {
        return botones;
    }
}
