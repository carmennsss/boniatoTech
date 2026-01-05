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
 * Pruebas parametrizadas para casos de login EXITOSOS.
 * Verifica que usuarios con credenciales validas puedan autenticarse.
 */
@RunWith(Parameterized.class)
public class PruebasLoginExito {

    private static ModeloBaseDatos modeloDB;

    private String email;
    private String contrasena;

    /**
     * Constructor para la prueba parametrizada.
     * 
     * @param email      El email del usuario a validar
     * @param contrasena La contrasena del usuario a validar
     */
    public PruebasLoginExito(String email, String contrasena) {
        this.email = email;
        this.contrasena = contrasena;
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
     * Proporciona los datos de prueba para casos de login exitosos.
     * 
     * @return Coleccion de arrays con pares de email y contrasena validos
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "admin@admin", "admin" },
                { "pablo.pruebas.mail@gmail.com", "1234" }
        });
    }

    /**
     * Verifica que el login sea exitoso con credenciales validas.
     */
    @Test
    public void testLoginExitoso() {
        boolean resultado = modeloDB.validarUsuario(email, contrasena);
        assertTrue("Login exitoso para " + email, resultado);
    }
}
