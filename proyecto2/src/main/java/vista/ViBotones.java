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
 * Utiliza FlowLayout para alinear los componentes de forma centralizada.
 */
public class ViBotones extends JPanel {
    /** Lista de botones del panel. */
    private ArrayList<JButton> botones;

    /**
     * Constructor que crea el panel con los botones especificados.
     *
     * @param textos Lista de etiquetas para los botones.
     */
    public ViBotones(ArrayList<String> textos) {
        propiedades(textos);
    }

    /**
     * Configura el panel con los botones especificados.
     *
     * @param textos Lista de etiquetas para los botones.
     */
    private void propiedades(ArrayList<String> textos) {
        this.botones = new ArrayList<>();
        configurarPanel();
        crearBotones(textos);
    }

    /**
     * Configura las propiedades visuales del panel.
     */
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

    // --- GETTERS Y SETTERS ---

    /**
     * Obtiene la lista de botones del panel.
     * * @return Lista de botones.
     */
    public ArrayList<JButton> getBotones() {
        return botones;
    }

    /**
     * Establece la lista de botones del panel.
     * * @param botones Nueva lista de botones.
     */
    public void setBotones(ArrayList<JButton> botones) {
        this.botones = botones;
    }
}