package controladorPrincipal;

import java.io.IOException;

/**
 * Clase principal que inicia la aplicación.
 * Punto de entrada del programa que crea el controlador principal.
 */
public class Main {

    /**
     * Método de entrada de la aplicación.
     * Crea una instancia del controlador principal.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     * @throws IOException Si ocurre un error de E/S.
     */
    public static void main(String[] args) throws IOException {
        new CoPrincipal();
    }
}