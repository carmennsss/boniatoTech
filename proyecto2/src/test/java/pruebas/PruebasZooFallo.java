package pruebas;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import controladorCRUD.ControladorCRUD;
import modelo.Animal;
import modelo.Cuidador;
import modelo.Especie;
import modelo.ModeloBaseDatos;
import modelo.Recinto;

/**
 * Pruebas parametrizadas para operaciones FALLIDAS del zoologico (CRUD).
 * Verifica que las operaciones con datos invalidos fallen correctamente.
 */
@RunWith(Parameterized.class)
public class PruebasZooFallo {

    private static ModeloBaseDatos modeloDB;
    private static ControladorCRUD controladorCRUD;

    private String tabla;
    private Object objeto;
    private String accion;
    private String descripcion;

    /**
     * Constructor para la prueba parametrizada.
     * 
     * @param tabla       Nombre de la tabla a probar
     * @param objeto      Objeto con los datos de prueba
     * @param accion      Accion a realizar (ANIADIR o ACTUALIZAR)
     * @param descripcion Descripcion del caso de prueba fallido
     */
    public PruebasZooFallo(String tabla, Object objeto, String accion, String descripcion) {
        this.tabla = tabla;
        this.objeto = objeto;
        this.accion = accion;
        this.descripcion = descripcion;
    }

    /**
     * Inicializa la conexion a la base de datos y el controlador CRUD antes de
     * ejecutar las pruebas.
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        modeloDB = new ModeloBaseDatos();
        controladorCRUD = new ControladorCRUD(null, modeloDB, null, null, null);
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
     * Proporciona los datos de prueba para operaciones fallidas del zoologico.
     * 
     * @return Coleccion de arrays con datos de prueba invalidos
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "animales", new Animal(9100, "Animal Invalido", "Male", 99999, 1), "ANIADIR",
                        "FK especie inexistente" },
                { "animales", new Animal(9101, "Animal Invalido 2", "Female", 1, 99999), "ANIADIR",
                        "FK cuidador inexistente" },
                { "animales", new Animal(88888, "No Existe", "Male", 1, 1), "ACTUALIZAR", "ID inexistente" },
                { "cuidadores", new Cuidador(9100, null, "Direccion"), "ANIADIR", "Nombre null" },
                { "especies", null, "ANIADIR", "Nombre vacio" },
        });
    }

    /**
     * Verifica que las operaciones con datos invalidos fallen correctamente.
     */
    @Test
    public void testOperacionFallida() {
        System.out.println("Ejecutando " + accion + " en " + tabla + " (debe fallar): " + descripcion);

        int filas = 0;
        boolean valoresValidos = true;

        try {
            switch (accion) {
                case "ANIADIR":
                    ArrayList<String> columnas = modeloDB.getNombresColumnas(tabla);
                    columnas.remove(0);
                    String[] valores = obtenerValoresObjeto(objeto);

                    valoresValidos = controladorCRUD.comprobarValores(valores);

                    if (valoresValidos) {
                        filas = controladorCRUD.crearRegistroInserccion(tabla, columnas, valores);
                    } else {
                        filas = -1;
                    }
                    break;

                case "ACTUALIZAR":
                    ArrayList<String> columnasUpd = modeloDB.getNombresColumnas(tabla);
                    columnasUpd.remove(0);
                    String idCol = modeloDB.getNombresColumnas(tabla).get(0);
                    Object id = obtenerIdObjeto(objeto);
                    String[] valoresUpd = obtenerValoresObjeto(objeto);

                    valoresValidos = controladorCRUD.comprobarValores(valoresUpd);

                    if (valoresValidos) {
                        filas = controladorCRUD.crearRegistroActualizacion(tabla, id, idCol, columnasUpd, valoresUpd);
                    } else {
                        filas = -1;
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("Excepcion capturada (esperada): " + e.getMessage());
            filas = -1;
        }

        assertTrue("Operacion fallida: " + accion + " en " + tabla + " (" + descripcion + ")", filas <= 0);
    }

    /**
     * Extrae los valores de un objeto para usarlos en operaciones de base de datos.
     * 
     * @param obj Objeto del cual extraer los valores
     * @return Array de strings con los valores del objeto
     */
    private String[] obtenerValoresObjeto(Object obj) {
        ArrayList<String> valores = new ArrayList<>();

        if (obj instanceof Animal) {
            Animal a = (Animal) obj;
            valores.add(a.getNombre_animales());
            valores.add(a.getTipo());
            valores.add(String.valueOf(a.getEspecie_id()));
            valores.add(String.valueOf(a.getCuidador_id()));
        } else if (obj instanceof Cuidador) {
            Cuidador c = (Cuidador) obj;
            valores.add(c.getNombre_cuidadores());
            valores.add(c.getDireccion_cuidadores());
        } else if (obj instanceof Recinto) {
            Recinto r = (Recinto) obj;
            valores.add(r.getNombre_recintos());
            valores.add(r.getDireccion_recintos());
            valores.add(String.valueOf(r.getCantidad_origen()));
            valores.add(String.valueOf(r.getCantidad_destino()));
        } else if (obj instanceof Especie) {
            Especie e = (Especie) obj;
            valores.add(e.getNombre_especies());
        }

        return valores.toArray(new String[0]);
    }

    /**
     * Obtiene el ID de un objeto.
     * 
     * @param obj Objeto del cual obtener el ID
     * @return ID del objeto o null si no se reconoce el tipo
     */
    private Object obtenerIdObjeto(Object obj) {
        if (obj instanceof Animal) {
            Animal animal = (Animal) obj;
            return animal.getAnimal_id();
        } else if (obj instanceof Cuidador) {
            Cuidador cuidador = (Cuidador) obj;
            return cuidador.getCuidador_id();
        } else if (obj instanceof Recinto) {
            Recinto recinto = (Recinto) obj;
            return recinto.getRecinto_id();
        } else if (obj instanceof Especie) {
            Especie especie = (Especie) obj;
            return especie.getEspecie_id();
        }
        return null;
    }
}
