package pruebas;

import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.ModeloBaseDatos;
import modelo.Animal;
import modelo.Cuidador;
import modelo.Recinto;
import modelo.Especie;

/**
 * Pruebas de recuperación de datos principales del zoológico.
 * Asegura que se pueden obtener listas no nulas de las entidades principales.
 */
public class PruebasZoo {

    private static ModeloBaseDatos modeloDB;

    @BeforeClass
    public static void setUpBeforeClass() {
        modeloDB = new ModeloBaseDatos();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        modeloDB.cerrarConexion();
    }

    /**
     * Verifica que la recuperación de animales retorna una lista instanciada.
     */
    @Test
    public void testGetAnimales() {
        ArrayList<Animal> animales = modeloDB.getAnimales();
        assertNotNull("La lista de animales no debe ser nula", animales);
    }

    /**
     * Verifica que la recuperación de cuidadores retorna una lista instanciada.
     */
    @Test
    public void testGetCuidadores() {
        ArrayList<Cuidador> cuidadores = modeloDB.getCuidadores();
        assertNotNull("La lista de cuidadores no debe ser nula", cuidadores);
    }

    /**
     * Verifica que la recuperación de recintos retorna una lista instanciada.
     */
    @Test
    public void testGetRecintos() {
        ArrayList<Recinto> recintos = modeloDB.getRecintos();
        assertNotNull("La lista de recintos no debe ser nula", recintos);
    }

    /**
     * Verifica que la recuperación de especies retorna una lista instanciada.
     */
    @Test
    public void testGetEspecies() {
        ArrayList<Especie> especies = modeloDB.getEspecies();
        assertNotNull("La lista de especies no debe ser nula", especies);
    }
}
