package pruebas;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite de pruebas que agrupa todas las pruebas del proyecto.
 * Ejecuta todas las clases de prueba en un solo comando.
 */
@RunWith(Suite.class)
@SuiteClasses({
        PruebasLoginExito.class,
        PruebasLoginFallo.class,
        PruebasZooExito.class,
        PruebasZooFallo.class,
        PruebasRolesExito.class,
        PruebasRolesFallo.class,
        PruebasEnvioCorreoExito.class,
        PruebasEnvioCorreoFallo.class
})
public class TodasLasPruebas {
    // Esta clase permanece vacia, solo sirve como contenedor para la suite
}
