package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import modelo.ModeloBaseDatos;
import modelo.Animal;
import modelo.Cuidador;
import modelo.Recinto;
import modelo.Especie;
import modelo.Log;

class PruebasModelo {

    private static ModeloBaseDatos modeloDB;

    @BeforeAll
    static void setUp() {
        modeloDB = new ModeloBaseDatos();
        System.out.println("Iniciando pruebas... Conectando a Base de Datos.");
    }

    @AfterAll
    static void tearDown() {
        modeloDB.cerrarConexion();
        System.out.println("Finalizando pruebas... Cerrando conexión.");
    }

    @Test
    @DisplayName("Prueba de Conexión a Base de Datos")
    void testConexion() {
        Connection conn = ModeloBaseDatos.getConexion();
        assertNotNull(conn, "La conexión no debería ser nula");
        try {
            assertFalse(conn.isClosed(), "La conexión debería estar abierta");
        } catch (SQLException e) {
            fail("Excepción al comprobar estado de la conexión: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Prueba de validación de usuario (Login)")
    void testValidarUsuario() {
        boolean resultado = modeloDB.validarUsuario("usuario_inexistente@test.com", "clavefalsa");
        assertFalse(resultado, "Usuario inexistente no debería validarse");
    }

    @Test
    @DisplayName("Obtención de lista de Animales")
    void testGetAnimales() {
        ArrayList<Animal> animales = modeloDB.getAnimales();
        assertNotNull(animales, "La lista de animales no debe ser nula");
        // No podemos asegurar el tamaño si la BD cambia, pero no debe fallar.
        if (!animales.isEmpty()) {
            Animal a = animales.get(0);
            assertNotNull(a.getNombre_animales(), "El nombre del animal no debe ser nulo");
        }
    }

    @Test
    @DisplayName("Obtención de lista de Cuidadores")
    void testGetCuidadores() {
        ArrayList<Cuidador> cuidadores = modeloDB.getCuidadores();
        assertNotNull(cuidadores, "La lista de cuidadores no debe ser nula");
    }

    @Test
    @DisplayName("Obtención de lista de Recintos")
    void testGetRecintos() {
        ArrayList<Recinto> recintos = modeloDB.getRecintos();
        assertNotNull(recintos, "La lista de recintos no debe ser nula");
    }

    @Test
    @DisplayName("Obtención de lista de Especies")
    void testGetEspecies() {
        ArrayList<Especie> especies = modeloDB.getEspecies();
        assertNotNull(especies, "La lista de especies no debe ser nula");
    }

    @Test
    @DisplayName("Prueba de Entidad Log")
    void testEntidadLog() {
        Log log = new Log(1, "LOGIN", "test_user", "2025-12-18 10:00:00", "success");
        assertEquals(1, log.getId());
        assertEquals("test_user", log.getCorreo());
        assertEquals("LOGIN", log.getAction());
        assertEquals("2025-12-18 10:00:00", log.getDate());
    }

    @Test
    @DisplayName("Prueba de Entidad Animal")
    void testEntidadAnimal() {
        // int animal_id, String nombre_animales, String tipo, int especie_id, int
        // cuidador_id
        Animal animal = new Animal(999, "León Test", "Mamífero", 1, 1);
        assertEquals(999, animal.getAnimal_id());
        assertEquals("León Test", animal.getNombre_animales());
        assertEquals("Mamífero", animal.getTipo());
        assertEquals(1, animal.getEspecie_id());
        assertEquals(1, animal.getCuidador_id());
    }
}
