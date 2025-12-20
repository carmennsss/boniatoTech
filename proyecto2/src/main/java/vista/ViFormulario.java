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
 * Ventana de formulario dinámica utilizada para crear o editar entidades.
 * Genera campos de texto o comboboxes según la configuración proporcionada.
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

    private void propiedades() {
        setSize(450, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    /**
     * Construye y muestra los campos del formulario basándose en una lista de
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
     * Rellena los campos del formulario con valores existentes (para edición).
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

                        boolean match = false;
                        if (item instanceof Especie && ((Especie) item).getEspecie_id() == id)
                            match = true;
                        else if (item instanceof Cuidador && ((Cuidador) item).getCuidador_id() == id)
                            match = true;
                        else if (item instanceof Recinto && ((Recinto) item).getRecinto_id() == id)
                            match = true;
                        else if (item instanceof Animal && ((Animal) item).getAnimal_id() == id)
                            match = true;

                        if (match) {
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
                    valores[i] = String.valueOf(((Especie) selected).getEspecie_id());
                } else if (selected instanceof Cuidador) {
                    valores[i] = String.valueOf(((Cuidador) selected).getCuidador_id());
                } else if (selected instanceof Recinto) {
                    valores[i] = String.valueOf(((Recinto) selected).getRecinto_id());
                } else if (selected instanceof Animal) {
                    valores[i] = String.valueOf(((Animal) selected).getAnimal_id());
                } else {
                    valores[i] = selected != null ? selected.toString() : "";
                }
            }
        }
        return valores;
    }

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
     * Obtiene el botón de guardar.
     * 
     * @return Botón de guardar o null si no existe.
     */
    public JButton getBtnGuardar() {
        if (botones.size() > 0)
            return botones.get(0);
        return null;
    }

    /**
     * Obtiene el botón de cancelar.
     * 
     * @return Botón de cancelar o null si no existe.
     */
    public JButton getBtnCancelar() {
        if (botones.size() > 1)
            return botones.get(1);
        return null;
    }

    /**
     * Actualiza los textos del formulario según el idioma seleccionado.
     */
    public void actualizarTextos() {
        this.setTitle(MoTextos.form_title);
        if (botones.size() > 0)
            botones.get(0).setText(MoTextos.btn_save);
        if (botones.size() > 1)
            botones.get(1).setText(MoTextos.btn_cancel);
        repaint();
    }
}
