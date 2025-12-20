package vista;

import java.awt.Color;
import java.awt.Font;

/**
 * Clase que define los estilos globales (Colores y Fuentes) para la aplicación.
 */
public class Estilos {
    /** Color verde oscuro para elementos principales. */
    public static final Color DARK_SPRUCE = new Color(40, 80, 46);

    /** Color beige claro para fondos. */
    public static final Color BEIGE_CANVAS = new Color(242, 235, 226);

    /** Color azul grisáceo para textos secundarios. */
    public static final Color BLUE_SLATE = new Color(92, 103, 132);

    /** Color verde salvia para acentos. */
    public static final Color SAGE_GREEN = new Color(132, 161, 93);

    /** Color fucsia para resaltados. */
    public static final Color FUCHSIA_PLUM = new Color(170, 98, 147);

    /** Color del texto principal. */
    public static final Color TEXTO_PRINCIPAL = DARK_SPRUCE;

    /** Color del fondo principal. */
    public static final Color FONDO_PRINCIPAL = BEIGE_CANVAS;

    /** Color del título de la aplicación. */
    public static final Color COLOR_TITULO_APP = DARK_SPRUCE;

    /** Color del fondo del menú. */
    public static final Color COLOR_MENU_FONDO = new Color(255, 255, 255, 150);

    /** Color de los botones del menú. */
    public static final Color COLOR_BOTON_MENU = DARK_SPRUCE;

    /** Color de los botones al pasar el ratón. */
    public static final Color COLOR_BOTON_HOVER = FUCHSIA_PLUM;

    /** Color del encabezado de las tablas. */
    public static final Color COLOR_TABLA_HEADER = DARK_SPRUCE;

    /** Color de selección en las tablas. */
    public static final Color COLOR_TABLA_SELECCION = FUCHSIA_PLUM;

    /** Color de las etiquetas. */
    public static final Color COLOR_LABEL = DARK_SPRUCE;

    /** Color de fondo de los campos de entrada. */
    public static final Color COLOR_INPUT_BG = Color.WHITE;

    /** Color del texto en los campos de entrada. */
    public static final Color COLOR_INPUT_TEXT = BLUE_SLATE;

    /** Fuente para títulos. */
    public static final Font FONT_TITULO = new Font("SansSerif", Font.BOLD, 24);

    /** Fuente para texto normal. */
    public static final Font FONT_TEXTO = new Font("SansSerif", Font.PLAIN, 14);

    /** Fuente para botones. */
    public static final Font FONT_BOTON = new Font("SansSerif", Font.BOLD, 14);
}