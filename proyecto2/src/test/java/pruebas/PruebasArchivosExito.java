package pruebas;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import modelo.ModeloBaseDatos;

/**
 * Pruebas parametrizadas para casos de gestion de archivos EXITOSOS.
 * Verifica que las operaciones de insercion, renombrado y eliminacion de
 * archivos funcionen correctamente.
 */
@RunWith(Parameterized.class)
public class PruebasArchivosExito {

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
    public PruebasArchivosExito(String nombre, String directorio, String extension, String tipo, Integer idPadre,
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
     * Tambien asegura que el usuario de prueba exista y limpia datos residuales.
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        modeloDB = new ModeloBaseDatos();

        // Asegurar que el usuario de prueba existe
        ArrayList<String> params = new ArrayList<>();
        params.add("admin@admin");
        if (!modeloDB.existeRegistro("SELECT * FROM usuarios WHERE email = ?", params)) {
            modeloDB.registrarUsuario("admin@admin", "Admin Test", "password123", "");
        }

        // Limpiar datos residuales de pruebas anteriores
        modeloDB.eliminarArchivo("archivoTest1.txt", "/test/ruta1");
        modeloDB.eliminarArchivo("renombrado_archivoTest1.txt", "/test/ruta1");
        modeloDB.eliminarArchivo("archivoTest2.pdf", "/test/ruta2");
        modeloDB.eliminarArchivo("renombrado_archivoTest2.pdf", "/test/ruta2");
        modeloDB.eliminarArchivo("carpetaTest", "/test/ruta3");
        modeloDB.eliminarArchivo("renombrado_carpetaTest", "/test/ruta3");
    }

    /**
     * Configuración inicial antes de cada prueba.
     * Limpia cualquier estado residual para evitar conflictos.
     */
    @Before
    public void setUp() {
        tearDown();
    }

    /**
     * Limpia los datos de prueba despues de cada ejecucion.
     * Esto asegura que los tests no interfieran entre si si fallan.
     */
    @After
    public void tearDown() {
        if (modeloDB != null) {
            String nuevoNombre = "renombrado_" + nombre;
            // Intentar eliminar tanto el original como el renombrado
            // eliminarArchivo no lanza excepcion si no existe (captura SQLException
            // internamente)
            modeloDB.eliminarArchivo(nombre, directorio);
            modeloDB.eliminarArchivo(nuevoNombre, directorio);
        }
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
     * Proporciona los datos de prueba para casos de gestion de archivos exitosos.
     * 
     * @return Coleccion de arrays con datos de archivo validos
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "archivoTest1.txt", "/test/ruta1", "txt", "File", null, "admin@admin" },
                { "archivoTest2.pdf", "/test/ruta2", "pdf", "File", null, "admin@admin" },
                { "carpetaTest", "/test/ruta3", "", "Folder", null, "admin@admin" }
        });
    }

    /**
     * Verifica que la insercion de archivo sea exitosa con datos validos.
     */
    @Test
    public void testInsercionArchivoExitoso() {
        boolean resultado = modeloDB.insertarArchivo(nombre, directorio, extension, tipo, idPadre, emailUsuario);
        assertTrue("Insercion exitosa para archivo: " + nombre, resultado);

        // Limpiar: eliminar el archivo creado
        modeloDB.eliminarArchivo(nombre, directorio);
    }

    /**
     * Verifica que el renombrado de archivo sea exitoso.
     */
    @Test
    public void testRenombradoArchivoExitoso() {
        // Primero insertar el archivo
        boolean insertado = modeloDB.insertarArchivo(nombre, directorio, extension, tipo, idPadre, emailUsuario);
        assertTrue("El archivo debe insertarse correctamente antes de renombrar: " + nombre, insertado);

        String nuevoNombre = "renombrado_" + nombre;
        boolean resultado = modeloDB.renombrarArchivo(nombre, nuevoNombre, directorio, extension);
        assertTrue("Renombrado exitoso de " + nombre + " a " + nuevoNombre, resultado);

        // Limpiar: eliminar el archivo renombrado
        modeloDB.eliminarArchivo(nuevoNombre, directorio);
    }

    /**
     * Verifica que la eliminacion de archivo sea exitosa.
     */
    @Test
    public void testEliminacionArchivoExitoso() {
        // Primero insertar el archivo
        boolean insertado = modeloDB.insertarArchivo(nombre, directorio, extension, tipo, idPadre, emailUsuario);
        assertTrue("Debe insertarse para poder eliminar: " + nombre, insertado);

        // Eliminar el archivo
        modeloDB.eliminarArchivo(nombre, directorio);

        // Verificar que NO existe
        ArrayList<String> params = new ArrayList<>();
        params.add(nombre);
        params.add(directorio);
        boolean existe = modeloDB.existeRegistro("SELECT * FROM archivos WHERE nombre_archivo = ? AND directorio = ?",
                params);

        assertTrue("El archivo deberia haber sido eliminado: " + nombre, !existe);
    }
}
