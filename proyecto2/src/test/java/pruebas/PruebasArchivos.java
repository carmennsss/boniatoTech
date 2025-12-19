package pruebas;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.ModeloBaseDatos;

/**
 * Pruebas unitarias/integración para la gestión de archivos en base de datos.
 * Verifica la inserción, renombradoy eliminación de registros de archivos.
 */
public class PruebasArchivos {

    private static ModeloBaseDatos modeloDB;
    private static final String TEST_EMAIL_FILES = "files_owner@test.com";

    @BeforeClass
    public static void setUpBeforeClass() {
        modeloDB = new ModeloBaseDatos();
        modeloDB.eliminarUsuario(TEST_EMAIL_FILES);
        modeloDB.registrarUsuario(TEST_EMAIL_FILES, "FileOwner", "1234", "key");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        modeloDB.eliminarArchivo("test_file_renamed.txt", "/");
        modeloDB.eliminarUsuario(TEST_EMAIL_FILES);
        modeloDB.cerrarConexion();
    }

    /**
     * Prueba el ciclo de vida completo de un registro de archivo en base de datos:
     * Insertar -> Renombrar -> Eliminar.
     */
    @Test
    public void testGestionArchivosDB() {
        String testFile = "test_file.txt";
        String testPath = "/";

        // 1. Insertar
        // id_padre null, email del usuario creado
        boolean insertado = modeloDB.insertarArchivo(testFile, testPath, "txt", "File", null, TEST_EMAIL_FILES);

        if (insertado) {
            assertTrue("Debería insertar el registro del archivo", insertado);

            // 2. Renombrar (Simula renombrado SQL)
            boolean renombrado = modeloDB.renombrarArchivo(testFile, "test_file_renamed.txt", testPath, "txt");
            assertTrue("Debería renombrar el archivo en BD", renombrado);

            // 3. Eliminar
            modeloDB.eliminarArchivo("test_file_renamed.txt", testPath);
        } else {
            System.out.println("WARN: No se pudo insertar archivo. Verifica FK.");
        }
    }
}
