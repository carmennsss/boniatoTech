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
 * Pruebas parametrizadas para casos de envío de correo FALLIDOS.
 * Verifica que el sistema maneje correctamente errores de autenticación o datos inválidos.
 */
@RunWith(Parameterized.class)
public class PruebasEnvioCorreoFallo {

    // Parámetros para la prueba
    private String remitente;
    private String asunto;
    private String mensaje;
    private String receptor;
    private String passwordAplicacion;
    private List<File> archivos;

    /**
     * Constructor para la prueba parametrizada.
     */
    public PruebasEnvioCorreoFallo(String remitente, String asunto, String mensaje, 
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
        // Aquí podrías inicializar logs o configuraciones si fuera necesario
        System.out.println("Iniciando pruebas de error de envío...");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        System.out.println("Pruebas de error de envío finalizadas.");
    }

    /**
     * Proporciona datos que deberían causar un error en el envío.
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            // Caso 1: Password de aplicación vacío
            { "remitente@gmail.com", "Asunto", "Mensaje", "receptor@gmail.com", "", null },
            
            // Caso 2: Receptor con formato inválido (sin @)
            { "remitente@gmail.com", "Asunto", "Mensaje", "receptor_invalido", "clave123", null },
            
            // Caso 3: Remitente nulo
            { null, "Asunto", "Mensaje", "receptor@gmail.com", "clave123", null },
            
            // Caso 4: Intentar enviar a un string vacío
            { "remitente@gmail.com", "Asunto", "Mensaje", "", "clave123", null }
        });
    }

    /**
     * Verifica que el método enviarCorreo lance una excepción ante datos erróneos.
     */
    @Test(expected = Exception.class)
    public void testEnvioDeberiaFallar() throws Exception {
        // Intentamos enviar el correo con los parámetros erróneos del dataset
        // Al tener (expected = Exception.class), el test pasará si el código lanza una excepción.
        EnviarCorreo.enviarCorreo(remitente, asunto, mensaje, receptor, passwordAplicacion, archivos);
        
        // Si por alguna razón no lanza excepción, forzamos el fallo del test
        fail("El envío debería haber fallado para el receptor: " + receptor);
    }
}