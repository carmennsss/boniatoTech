package pruebas;

import static org.junit.Assert.assertEquals;
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
 * Pruebas parametrizadas para la autenticación de usuarios.
 * Verifica casos de éxito y fallo en el login.
 */
@RunWith(Parameterized.class)
public class PruebasLogin {

    private String email;
    private String password;
    private boolean resultadoEsperado;

    private static ModeloBaseDatos modeloDB;
    private static final String TEST_EMAIL_FIJO = "usuario_test_login@test.com";

    @BeforeClass
    public static void setUpBeforeClass() {
        modeloDB = new ModeloBaseDatos();
        modeloDB.eliminarUsuario(TEST_EMAIL_FIJO);
        modeloDB.registrarUsuario(TEST_EMAIL_FIJO, "UsuarioTestLogin", "1234", "clave");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        modeloDB.eliminarUsuario(TEST_EMAIL_FIJO);
        modeloDB.cerrarConexion();
    }

    /**
     * Constructor para la inyección de parámetros de prueba.
     *
     * @param email             Correo electrónico a probar.
     * @param password          Contraseña a probar.
     * @param resultadoEsperado Resultado booleano esperado (true=éxito,
     *                          false=fallo).
     */
    public PruebasLogin(String email, String password, boolean resultadoEsperado) {
        this.email = email;
        this.password = password;
        this.resultadoEsperado = resultadoEsperado;
    }

    /**
     * Provee los datos de prueba para la ejecución parametrizada.
     *
     * @return Colección de arrays de objetos con los parámetros de prueba.
     */
    @Parameters
    public static Collection<Object[]> datos() {
        return Arrays.asList(new Object[][] {
                { TEST_EMAIL_FIJO, "1234", true },
                { TEST_EMAIL_FIJO, "incorrecta", false },
                { "noexiste@email.com", "1234", false },
                { "", "", false }
        });
    }

    /**
     * Ejecuta y verifica la validación de usuario con los parámetros actuales.
     */
    @Test
    public void testValidarUsuario() {
        boolean resultado = modeloDB.validarUsuario(this.email, this.password);
        assertEquals("Fallo en login para usuario: " + this.email, this.resultadoEsperado, resultado);
    }
}
