package pruebas;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite de Pruebas que ejecuta todas las pruebas del proyecto.
 * Incluye pruebas parametrizadas (Login) y pruebas estándar (Zoo, Usuarios,
 * Archivos).
 */
@RunWith(Suite.class)
@SuiteClasses({
        PruebasLogin.class,
        PruebasZoo.class,
        PruebasUsuarios.class,
        PruebasArchivos.class
})
public class TodasLasPruebas {
}
