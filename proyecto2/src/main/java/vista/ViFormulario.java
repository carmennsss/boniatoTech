package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

import modelo.Especie;
import modelo.Cuidador;
import modelo.Recinto;
import modelo.Animal;
import modelo.MoTextos;

/**
 * Ventana de formulario din�mica utilizada para crear o editar entidades.
 * Genera campos de texto o comboboxes seg�n la configuraci�n proporcionada,
 * adapt�ndose a las distintas tablas del sistema.
 */
public class ViFormulario extends JFrame {
    /** Lista de etiquetas de los campos del formulario. */
    private ArrayList<JLabel> etiquetas;

    /** Lista de componentes de entrada (campos de texto o comboboxes). */
    private ArrayList<JComponent> campos;

    /** Lista de botones del formulario. */
    private ArrayList<JButton> botones;

    /** Panel central que contiene los campos del formulario. */
    private JPanel panelCentral;

    /** Imagen de fondo de la ventana. */
    private Image imagenFondo;

    /**
     * Constructor que inicializa las listas y la configuraci�n b�sica de la
     * ventana.
     */
    public ViFormulario() {
        super(MoTextos.form_title);
        etiquetas = new ArrayList<>();
        campos = new ArrayList<>();
        botones = new ArrayList<>();

        URL url = getClass().getResource("/fondo_verde.png");
        if (url != null) {
            imagenFondo = new ImageIcon(url).getImage();
        }

        propiedades();
    }

    /**
     * Establece las propiedades visuales y de comportamiento b�sicas de la v
     * ntana.
     */
    private void propiedades() {
        setSize(450, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    /**
     * Construye y muestra los campos del formulario bas�ndose en una lista de
     * nombres de etiquetas.
     *
     * @param nombresCampos Lista de nombres para las etiquetas de los campos.
     */
    public void crearFormulario(ArrayList<String> nombresCampos) {
        getContentPane().removeAll();
        etiquetas.clear();
        campos.clear();
        botones.clear();

        panelCentral = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());

                if (imagenFondo != null) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
                    g2.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                    g2.dispose();
                }
            }
        };
        panelCentral.setBorder(new EmptyBorder(30, 40, 30, 40));
        panelCentral.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        for (String nombre : nombresCampos) {
            JLabel label = new JLabel(nombre + ":");
            label.setFont(Estilos.FONT_BOTON);
            label.setForeground(Estilos.COLOR_LABEL);
            etiquetas.add(label);

            gbc.gridx = 0;
            gbc.gridy = row;
            gbc.weightx = 0.3;
            panelCentral.add(label, gbc);

            JTextField campo = new JTextField(20);
            campo.setFont(Estilos.FONT_TEXTO);
            campos.add(campo);

            gbc.gridx = 1;
            gbc.gridy = row;
            gbc.weightx = 0.7;
            panelCentral.add(campo, gbc);

            row++;
        }

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(Color.WHITE);

        JButton btnGuardar = new JButton(MoTextos.btn_save);
        estilarBoton(btnGuardar, Estilos.COLOR_TITULO_APP);

        JButton btnCancelar = new JButton(MoTextos.btn_cancel);
        estilarBoton(btnCancelar, Estilos.BLUE_SLATE);

        botones.add(btnGuardar);
        botones.add(btnCancelar);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        panelCentral.add(panelBotones, gbc);

        add(panelCentral);

        pack();
        if (getWidth() < 400)
            setSize(400, getHeight());
        setLocationRelativeTo(null);

        revalidate();
        repaint();
    }

    /**
     * Aplica el estilo visual estandarizado a los botones del formulario.
     *
     * @param btn     El bot�n a estilar.
     * @param bgColor Color de fondo para el bot�n.
     */
    private void estilarBoton(JButton btn, Color bgColor) {
        btn.setFont(Estilos.FONT_BOTON);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 35));
    }

    /**
     * Reemplaza un campo de texto por un JComboBox de recintos.
     *
     * @param nombre    Nombre del campo a reemplazar.
     * @param elementos Lista de recintos para el combo.
     */
    public void agregarComboRecintos(String nombre, ArrayList<Recinto> elementos) {
        int indice = -1;
        for (int i = 0; i < etiquetas.size(); i++) {
            if (etiquetas.get(i).getText().equals(nombre + ":")) {
                indice = i;
                break;
            }
        }

        if (indice != -1) {
            JComponent campoAntiguo = campos.get(indice);
            GridBagLayout layout = (GridBagLayout) panelCentral.getLayout();
            GridBagConstraints gbc = layout.getConstraints(campoAntiguo);

            panelCentral.remove(campoAntiguo);

            JComboBox<Recinto> combo = new JComboBox<>();
            combo.setFont(new Font("Arial", Font.PLAIN, 14));
            for (Recinto elemento : elementos) {
                combo.addItem(elemento);
            }

            campos.set(indice, combo);
            panelCentral.add(combo, gbc);

            revalidate();
            repaint();
        }
    }

    /**
     * Reemplaza un campo de texto por un JComboBox de especies.
     *
     * @param nombre    Nombre del campo a reemplazar.
     * @param elementos Lista de especies para el combo.
     */
    public void agregarComboEspecies(String nombre, ArrayList<Especie> elementos) {
        int indice = -1;
        for (int i = 0; i < etiquetas.size(); i++) {
            if (etiquetas.get(i).getText().equals(nombre + ":")) {
                indice = i;
                break;
            }
        }

        if (indice != -1) {
            JComponent campoAntiguo = campos.get(indice);
            GridBagLayout layout = (GridBagLayout) panelCentral.getLayout();
            GridBagConstraints gbc = layout.getConstraints(campoAntiguo);

            panelCentral.remove(campoAntiguo);

            JComboBox<Especie> combo = new JComboBox<>();
            combo.setFont(new Font("Arial", Font.PLAIN, 14));
            for (Especie elemento : elementos) {
                combo.addItem(elemento);
            }

            campos.set(indice, combo);
            panelCentral.add(combo, gbc);

            revalidate();
            repaint();
        }
    }

    /**
     * Reemplaza un campo de texto por un JComboBox de cuidadores.
     *
     * @param nombre    Nombre del campo a reemplazar.
     * @param elementos Lista de cuidadores para el combo.
     */
    public void agregarComboCuidadores(String nombre, ArrayList<Cuidador> elementos) {
        int indice = -1;
        for (int i = 0; i < etiquetas.size(); i++) {
            if (etiquetas.get(i).getText().equals(nombre + ":")) {
                indice = i;
                break;
            }
        }

        if (indice != -1) {
            JComponent campoAntiguo = campos.get(indice);
            GridBagLayout layout = (GridBagLayout) panelCentral.getLayout();
            GridBagConstraints gbc = layout.getConstraints(campoAntiguo);

            panelCentral.remove(campoAntiguo);

            JComboBox<Cuidador> combo = new JComboBox<>();
            combo.setFont(new Font("Arial", Font.PLAIN, 14));
            for (Cuidador elemento : elementos) {
                combo.addItem(elemento);
            }

            campos.set(indice, combo);
            panelCentral.add(combo, gbc);

            revalidate();
            repaint();
        }
    }

    /**
     * Reemplaza un campo de texto por un JComboBox de tipos (strings).
     *
     * @param nombre    Nombre del campo a reemplazar.
     * @param elementos Lista de strings para el combo.
     */
    public void agregarComboTipos(String nombre, java.util.List<String> elementos) {
        int indice = -1;
        for (int i = 0; i < etiquetas.size(); i++) {
            if (etiquetas.get(i).getText().equals(nombre + ":")) {
                indice = i;
                break;
            }
        }

        if (indice != -1) {
            JComponent campoAntiguo = campos.get(indice);
            GridBagLayout layout = (GridBagLayout) panelCentral.getLayout();
            GridBagConstraints gbc = layout.getConstraints(campoAntiguo);

            panelCentral.remove(campoAntiguo);

            JComboBox<String> combo = new JComboBox<>();
            combo.setFont(new Font("Arial", Font.PLAIN, 14));
            for (String elemento : elementos) {
                combo.addItem(elemento);
            }

            campos.set(indice, combo);
            panelCentral.add(combo, gbc);

            revalidate();
            repaint();
        }
    }

    /**
     * Reemplaza un campo de texto por un JComboBox de animales.
     *
     * @param nombre    Nombre del campo a reemplazar.
     * @param elementos Lista de animales para el combo.
     */
    public void agregarComboAnimales(String nombre, ArrayList<Animal> elementos) {
        int indice = -1;
        for (int i = 0; i < etiquetas.size(); i++) {
            if (etiquetas.get(i).getText().equals(nombre + ":")) {
                indice = i;
                break;
            }
        }

        if (indice != -1) {
            JComponent campoAntiguo = campos.get(indice);
            GridBagLayout layout = (GridBagLayout) panelCentral.getLayout();
            GridBagConstraints gbc = layout.getConstraints(campoAntiguo);

            panelCentral.remove(campoAntiguo);

            JComboBox<Animal> combo = new JComboBox<>();
            combo.setFont(new Font("Arial", Font.PLAIN, 14));
            for (Animal elemento : elementos) {
                combo.addItem(elemento);
            }

            campos.set(indice, combo);
            panelCentral.add(combo, gbc);

            revalidate();
            repaint();
        }
    }

    /**
     * Rellena los campos del formulario con valores existentes (para edici�n).
     * Maneja tanto JTextFields como JComboBoxes, seleccionando el item correcto por
     * ID.
     *
     * @param valores Array de strings con los valores a pre-cargar.
     */
    public void rellenarDatos(String[] valores) {
        for (int i = 0; i < valores.length && i < campos.size(); i++) {
            JComponent campo = campos.get(i);
            String valor = valores[i];

            if (campo instanceof JTextField) {
                ((JTextField) campo).setText(valor != null ? valor : "");
            } else if (campo instanceof JComboBox) {
                JComboBox combo = (JComboBox) campo;
                try {
                    int id = Integer.parseInt(valor);
                    for (int j = 0; j < combo.getItemCount(); j++) {
                        Object item = combo.getItemAt(j);

                        boolean idCoincide = false;
                        if (item instanceof Especie) {
                            Especie especie = (Especie) item;
                            idCoincide = especie.getEspecie_id() == id;
                        } else if (item instanceof Cuidador) {
                            Cuidador cuidador = (Cuidador) item;
                            idCoincide = cuidador.getCuidador_id() == id;
                        } else if (item instanceof Recinto) {
                            Recinto recinto = (Recinto) item;
                            idCoincide = recinto.getRecinto_id() == id;
                        } else if (item instanceof Animal) {
                            Animal animal = (Animal) item;
                            idCoincide = animal.getAnimal_id() == id;
                        }
                        if (idCoincide) {
                            combo.setSelectedIndex(j);
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    combo.setSelectedItem(valor);
                }
            }
        }
    }

    /**
     * Obtiene los valores introducidos por el usuario en todos los campos.
     * Para JComboBox, obtiene el ID de la entidad seleccionada.
     *
     * @return Array de strings con los valores de los campos.
     */
    public String[] obtenerValores() {
        String[] valores = new String[campos.size()];
        for (int i = 0; i < campos.size(); i++) {
            JComponent campo = campos.get(i);
            if (campo instanceof JTextField) {
                valores[i] = ((JTextField) campo).getText();
            } else if (campo instanceof JComboBox) {
                JComboBox combo = (JComboBox) campo;
                Object selected = combo.getSelectedItem();

                if (selected instanceof Especie) {
                    Especie especie = (Especie) selected;
                    valores[i] = String.valueOf(especie.getEspecie_id());
                } else if (selected instanceof Cuidador) {
                    Cuidador cuidador = (Cuidador) selected;
                    valores[i] = String.valueOf(cuidador.getCuidador_id());
                } else if (selected instanceof Recinto) {
                    Recinto recinto = (Recinto) selected;
                    valores[i] = String.valueOf(recinto.getRecinto_id());
                } else if (selected instanceof Animal) {
                    Animal animal = (Animal) selected;
                    valores[i] = String.valueOf(animal.getAnimal_id());
                } else {
                    valores[i] = selected != null ? selected.toString() : "";
                }
            }
        }
        return valores;
    }

    /**
     * Actualiza los textos del formulario seg�n el idioma seleccionado.
     */
    public void actualizarTextos() {
        this.setTitle(MoTextos.form_title);
        if (botones.size() > 0)
            botones.get(0).setText(MoTextos.btn_save);
        if (botones.size() > 1)
            botones.get(1).setText(MoTextos.btn_cancel);
        repaint();
    }

    // --- GETTERS Y SETTERS ---

    /**
     * Hace visible el formulario.
     */
    public void hacerVisible() {
        setVisible(true);
    }

    /**
     * Obtiene la lista de botones del formulario.
     *
     * @return Lista de botones.
     */
    public ArrayList<JButton> getBotones() {
        return botones;
    }

    /**
     * Obtiene la lista de campos del formulario.
     *
     * @return Lista de componentes (campos).
     */
    public ArrayList<JComponent> getCampos() {
        return campos;
    }

    /**
     * Obtiene el bot�n de guardar.
     *
     * @return Bot�n de guardar o null si no existe.
     */
    public JButton getBtnGuardar() {
        if (botones.size() > 0)
            return botones.get(0);
        return null;
    }

    /**
     * Obtiene el bot�n de cancelar.
     *
     * @return Bot�n de cancelar o null si no existe.
     */
    public JButton getBtnCancelar() {
        if (botones.size() > 1)
            return botones.get(1);
        return null;
    }
}