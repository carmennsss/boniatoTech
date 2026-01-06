package pruebas;

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
 * Pruebas parametrizadas para casos de gestion de usuarios EXITOSOS.
 * Verifica que las operaciones de registro y validacion de usuarios funcionen
 * correctamente.
 */
@RunWith(Parameterized.class)
public class PruebasUsuariosExito {

    private static ModeloBaseDatos modeloDB;

    private String correo;
    private String nombre;
    private String password;
    private String claveCorreo;

    /**
     * Constructor para la prueba parametrizada.
     * 
     * @param correo      El correo del usuario
     * @param nombre      El nombre del usuario
     * @param password    La contrasena del usuario
     * @param claveCorreo La clave de correo del usuario
     */
    public PruebasUsuariosExito(String correo, String nombre, String password, String claveCorreo) {
        this.correo = correo;
        this.nombre = nombre;
        this.password = password;
        this.claveCorreo = claveCorreo;
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
     * Proporciona los datos de prueba para casos de registro de usuarios exitosos.
     * 
     * @return Coleccion de arrays con datos de usuario validos
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "test.usuario1@test.com", "Usuario Test 1", "password123", "claveCorreo123" },
                { "test.usuario2@test.com", "Usuario Test 2", "pass456", "clave456" }
        });
    }

    /**
     * Verifica que el registro de usuario sea exitoso con datos validos.
     */
    @Test
    public void testRegistroUsuarioExitoso() {
        boolean resultado = modeloDB.registrarUsuario(correo, nombre, password, claveCorreo);
        assertTrue("Registro exitoso para usuario: " + nombre, resultado);

        // Limpiar: eliminar el usuario creado
        modeloDB.eliminarUsuario(correo);
    }

    /**
     * Verifica que la validacion de usuario sea exitosa despues del registro.
     */
    @Test
    public void testValidacionUsuarioExitoso() {
        // Primero registrar el usuario
        modeloDB.registrarUsuario(correo, nombre, password, claveCorreo);

        // Validar que el usuario puede autenticarse
        boolean resultado = modeloDB.validarUsuario(correo, password);
        assertTrue("Validacion exitosa para usuario: " + correo, resultado);

        // Limpiar: eliminar el usuario creado
        modeloDB.eliminarUsuario(correo);
    }
}
