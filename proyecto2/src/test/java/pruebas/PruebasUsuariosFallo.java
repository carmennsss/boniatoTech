package pruebas;

import static org.junit.Assert.assertFalse;

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
 * Pruebas parametrizadas para casos de gestion de usuarios FALLIDOS.
 * Verifica que las operaciones con datos invalidos fallen correctamente.
 */
@RunWith(Parameterized.class)
public class PruebasUsuariosFallo {

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
    public PruebasUsuariosFallo(String correo, String nombre, String password, String claveCorreo) {
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
     * Proporciona los datos de prueba para casos de validacion de usuarios
     * fallidos.
     * 
     * @return Coleccion de arrays con datos de usuario invalidos
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "usuario.inexistente@test.com", "Usuario Inexistente", "wrongpass", "clave" },
                { "", "Usuario Vacio", "password", "clave" },
                { "test@test.com", "", "password", "clave" }
        });
    }

    /**
     * Verifica que la validacion falle con credenciales invalidas.
     */
    @Test
    public void testValidacionUsuarioFallido() {
        boolean resultado = modeloDB.validarUsuario(correo, password);
        assertFalse("Validacion fallida para usuario inexistente: " + correo, resultado);
    }

    /**
     * Verifica que la eliminacion de un usuario inexistente falle.
     */
    @Test
    public void testEliminacionUsuarioInexistenteFallido() {
        boolean resultado = modeloDB.eliminarUsuario(correo);
        assertFalse("Eliminacion fallida para usuario inexistente: " + correo, resultado);
    }
}
