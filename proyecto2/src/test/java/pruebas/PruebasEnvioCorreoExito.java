package pruebas;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import controladorCorreos.EnviarCorreo;

/**
 * Pruebas parametrizadas para casos de envío de correo EXITOSOS.
 * Verifica que con credenciales y datos válidos, el correo se procesa sin errores.
 */
@RunWith(Parameterized.class)
public class PruebasEnvioCorreoExito {

    private String remitente;
    private String asunto;
    private String mensaje;
    private String receptor;
    private String passwordAplicacion;
    private List<File> archivos;

    /**
     * Constructor para la prueba parametrizada.
     */
    public PruebasEnvioCorreoExito(String remitente, String asunto, String mensaje, 
                                    String receptor, String passwordAplicacion, List<File> archivos) {
        this.remitente = remitente;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.receptor = receptor;
        this.passwordAplicacion = passwordAplicacion;
        this.archivos = archivos;
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        System.out.println("Iniciando pruebas de envío exitoso...");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        System.out.println("Pruebas de envío exitoso finalizadas.");
    }

    /**
     * Proporciona los datos de prueba. 
     * NOTA: Para que estos tests pasen realmente, los datos deben ser credenciales
     * reales de Gmail y el receptor debe estar en tu whitelist si pruebas el flujo completo.
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { 
                "pablo.pruebas.mail@gmail.com", 
                "Test Unitario Éxito 1", 
                "Este es un mensaje de prueba automática.", 
                "miguelroblesp.sanjosemlg@fundacionloyola.net", 
                "soxv zcuu txqf mtcx", // Tu contraseña de aplicación real
                null 
            },
            { 
                "pablo.pruebas.mail@gmail.com", 
                "Test con Adjunto", 
                "Revisar el archivo adjunto.", 
                "pablitogonzalez0406@gmail.com", 
                "soxv zcuu txqf mtcx", 
                null // Aquí podrías pasar una lista con un File real si existiera
            }
        });
    }

    /**
     * Verifica que el envío se realice sin lanzar ninguna excepción.
     */
    @Test
    public void testEnvioExitoso() {
        try {
            // Ejecutamos el envío
            EnviarCorreo.enviarCorreo(remitente, asunto, mensaje, receptor, passwordAplicacion, archivos);
            
            // Si llega aquí sin lanzar excepción, el test es exitoso implícitamente.
            // No hace falta un assertTrue porque la ausencia de Exception es el éxito.
            System.out.println("Envío exitoso a: " + receptor);
            
        } catch (Exception e) {
            // Si salta una excepción, forzamos el fallo del test mostrando el error
            fail("El envío debería haber sido exitoso, pero falló: " + e.getMessage());
        }
    }
} 