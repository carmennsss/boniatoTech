package pruebas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import modelo.ModeloBaseDatos;

/**
 * Pruebas parametrizadas para casos de gestion de archivos FALLIDOS.
 * Verifica que las operaciones con datos invalidos o archivos inexistentes
 * fallen correctamente.
 */
@RunWith(Parameterized.class)
public class PruebasArchivosFallo {

    private static ModeloBaseDatos modeloDB;

    private String nombre;
    private String directorio;
    private String extension;
    private String tipo;
    private Integer idPadre;
    private String emailUsuario;

    /**
     * Constructor para la prueba parametrizada.
     * 
     * @param nombre       Nombre del archivo
     * @param directorio   Directorio del archivo
     * @param extension    Extension del archivo
     * @param tipo         Tipo de archivo (archivo/carpeta)
     * @param idPadre      ID del directorio padre
     * @param emailUsuario Email del usuario propietario
     */
    public PruebasArchivosFallo(String nombre, String directorio, String extension, String tipo, Integer idPadre,
            String emailUsuario) {
        this.nombre = nombre;
        this.directorio = directorio;
        this.extension = extension;
        this.tipo = tipo;
        this.idPadre = idPadre;
        this.emailUsuario = emailUsuario;
    }

    /**
     * Inicializa la conexion a la base de datos antes de ejecutar las pruebas.
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        modeloDB = new ModeloBaseDatos();
    }

    /**
     * Cierra la conexion a la base de datos despues de ejecutar todas las pruebas.
     */
    @AfterClass
    public static void tearDownAfterClass() {
        if (modeloDB != null) {
            modeloDB.cerrarConexion();
        }
    }

    /**
     * Proporciona los datos de prueba para casos de gestion de archivos fallidos.
     * 
     * @return Coleccion de arrays con datos de archivo invalidos
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "archivoInexistente.txt", "/ruta/inexistente", "txt", "archivo", null, "usuario@inexistente.com" },
                { "", "/test/ruta", "txt", "archivo", null, "admin@admin" },
                { "archivo.txt", "", "txt", "archivo", null, "admin@admin" }
        });
    }

    /**
     * Verifica que la eliminacion de un archivo inexistente no cause errores.
     */
    @Test
    public void testEliminacionArchivoInexistenteFallido() {
        // El metodo eliminarArchivo es void, solo verificamos que no lance excepciones
        try {
            modeloDB.eliminarArchivo(nombre, directorio);
            assertTrue("Eliminacion de archivo inexistente no causa errores", true);
        } catch (Exception e) {
            assertFalse("Error inesperado al eliminar archivo inexistente: " + nombre, true);
        }
    }

    /**
     * Verifica que el renombrado de un archivo inexistente falle.
     */
    @Test
    public void testRenombradoArchivoInexistenteFallido() {
        String nuevoNombre = "nuevo_" + nombre;
        boolean resultado = modeloDB.renombrarArchivo(nombre, nuevoNombre, directorio, extension);
        assertFalse("Renombrado fallido para archivo inexistente: " + nombre, resultado);
    }
}
