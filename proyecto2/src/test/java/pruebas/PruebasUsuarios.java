package pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.ModeloBaseDatos;

/**
 * Pruebas del ciclo de vida de usuarios.
 * Verifica registro, verificación, eliminación y bloqueo post-eliminación.
 */
public class PruebasUsuarios {

    private static ModeloBaseDatos modeloDB;
    private static final String TEST_EMAIL = "usuario_lifecycle@test.com";

    @BeforeClass
    public static void setUpBeforeClass() {
        modeloDB = new ModeloBaseDatos();
        modeloDB.eliminarUsuario(TEST_EMAIL); // Limpieza preventiva
    }

    @AfterClass
    public static void tearDownAfterClass() {
        modeloDB.eliminarUsuario(TEST_EMAIL); // Limpieza final
        modeloDB.cerrarConexion();
    }

    /**
     * Prueba secuencial del manejo de usuarios:
     * 1. Registrar usuario nuevo.
     * 2. Verificar que se guardó correctamente.
     * 3. Eliminar usuario.
     * 4. Verificar que no puede iniciar sesión tras ser eliminado.
     */
    @Test
    public void testRegistroYEliminacion() {
        // 1. Registro
        boolean registrado = modeloDB.registrarUsuario(TEST_EMAIL, "UserLifecycle", "1234", "key");
        assertTrue("El usuario debería registrarse", registrado);

        // 2. Verificación existencia
        String emailRecuperado = modeloDB.obtenerEmailPorUsuario("UserLifecycle");
        assertEquals(TEST_EMAIL, emailRecuperado);

        // 3. Eliminación
        boolean eliminado = modeloDB.eliminarUsuario(TEST_EMAIL);
        assertTrue("El usuario debería eliminarse", eliminado);

        // 4. Verificación post-eliminación
        boolean existe = modeloDB.validarUsuario(TEST_EMAIL, "1234");
        assertFalse("El usuario eliminado no debe poder loguearse", existe);
    }
}
