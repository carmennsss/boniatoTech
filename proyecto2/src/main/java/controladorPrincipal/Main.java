package controladorPrincipal;

import java.io.IOException;

/**
 * Clase principal que inicia la aplicaci�n.
 * Punto de entrada del programa que crea el controlador principal.
 */
public class Main {

    /**
     * M�todo de entrada de la aplicaci�n.
     * Crea una instancia del controlador principal.
     *
     * @param args Argumentos de la l�nea de comandos (no utilizados).
     * @throws IOException Si ocurre un error de E/S.
     */
    public static void main(String[] args) throws IOException {
        new CoPrincipal();
    }
}