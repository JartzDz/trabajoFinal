package Controlador;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import Modelo.*;
import Vista.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class ControladorMascotas extends MouseAdapter implements ActionListener, KeyListener, FocusListener {
    private InterfazMascotas vista;
    private GestorMascotas modelo;

    DefaultTableModel modeloTabla;

    public ControladorMascotas(GestorMascotas modelo, InterfazMascotas vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.btnAgregar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.btnMostrarMascotas.addActionListener(this);
        vista.btnBuscar.addActionListener(this);
        vista.txtID.addKeyListener(this);
        vista.txtDuenio.addKeyListener(this);
        vista.txtBuscar.addKeyListener(this);
        vista.txtRaza.addKeyListener(this);
        vista.txtNombre.addKeyListener(this);
        vista.txtBuscar.addFocusListener(this);
        vista.btnBuscar.setFocusable(false);
        vista.txtBuscar.setEnabled(false);
        String[] columnas = {"ID", "NOMBRE", "RAZA", "DUEÑO"};
        modeloTabla = new DefaultTableModel(null, columnas);
        vista.btnMostrarMascotas.setEnabled(false);
        vista.btnBuscar.setEnabled(false);
        vista.btnCargarFoto.addActionListener(this);
    }

    public void mostrarInterfazMascotas() {
        vista.setLocationRelativeTo(null);
        vista.setResizable(false);
        vista.setVisible(true);
        vista.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }
    public void limpiar(){
        vista.txtID.setText("");
        vista.txtNombre.setText("");
        vista.txtRaza.setText("");
        vista.txtDuenio.setText("");
    }
    public void agregar(){

        try {
            int ID = Integer.parseInt(vista.txtID.getText());
            String nombre = vista.txtNombre.getText();
            String raza = vista.txtRaza.getText();
            String duenio = vista.txtDuenio.getText();

            if (!nombre.isEmpty() && !raza.isEmpty() && !duenio.isEmpty()) {
                modelo.agregarMascota(ID, nombre, raza, duenio);
                vista.btnMostrarMascotas.setEnabled(true);
                vista.btnBuscar.setEnabled(true);
                vista.txtBuscar.setEnabled(true);

                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }
    public void eliminarTabla(){
        int fila=vista.tablaMascotas.getSelectedRow();
        //String valor= (String) vista.tablaPersonas.getValueAt(fila,0);
        modelo.eliminarMascota(fila);
    }
    public void buscarMascota(){
        String valor = vista.txtBuscar.getText();

    }
    public void cargarFoto(){
        JFileChooser jf = new JFileChooser();
        FileNameExtensionFilter filtrado = new FileNameExtensionFilter("Imágenes", "jpg", "png", "gif");
        jf.setFileFilter(filtrado);

        int seleccion = jf.showOpenDialog(null);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                // Obtener el archivo seleccionado
                File archivo = jf.getSelectedFile();

                JLabel imagenLabel = new JLabel();

                // Escalar la imagen manteniendo la relación de aspecto original
                ImageIcon imagenIcon = new ImageIcon(archivo.getPath());
                Image imagenOriginal = imagenIcon.getImage();

                // Calcular el nuevo tamaño manteniendo la relación de aspecto
                int nuevoAncho = 120;
                int nuevoAlto = 120;

                // Escalar la imagen al nuevo tamaño
                Image imagenEscalada = imagenOriginal.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
                imagenIcon = new ImageIcon(imagenEscalada);

                imagenLabel.setIcon(imagenIcon);

                // Establecer la posición y tamaño del JLabel en el JPanel para centrarlo
                int x = (vista.panelImagen.getWidth() - nuevoAncho) / 2;  // Centrar la imagen horizontalmente
                int y = (vista.panelImagen.getHeight() - nuevoAlto) / 2;    // Centrar la imagen verticalmente

                imagenLabel.setBounds(x, y, nuevoAncho, nuevoAlto);

                // Establecer un diseño nulo en el panelImagen
                vista.panelImagen.setLayout(null);

                // Limpiar el panel y agregar el JLabel con la imagen
                vista.panelImagen.removeAll();
                vista.panelImagen.add(imagenLabel);

                vista.panelImagen.revalidate();
                vista.panelImagen.repaint();



            } else if (seleccion == JFileChooser.CANCEL_OPTION) {

            System.out.println("Operación cancelada");
        }
    }
    public void cargarMascota() {
        int valor = modelo.buscarMascota(Integer.parseInt(vista.txtBuscar.getText()));

        if (!modelo.mostrarMascotas().isEmpty()) {
            if (valor != -1) {
                String data[][] = {};
                String col[] = {"ID", "NOMBRE", "RAZA", "DUEÑO"};
                modeloTabla = new DefaultTableModel(data, col);
                vista.tablaMascotas.setModel(modeloTabla);
                modeloTabla.insertRow(modeloTabla.getRowCount(), new Object[]{});
                modeloTabla.setValueAt(modelo.mostrarMascotas().get(valor).getID(), modeloTabla.getRowCount() - 1, 0);
                modeloTabla.setValueAt(modelo.mostrarMascotas().get(valor).getNombreMascota(), modeloTabla.getRowCount() - 1, 1);
                modeloTabla.setValueAt(modelo.mostrarMascotas().get(valor).getRaza(), modeloTabla.getRowCount() - 1, 2);
                modeloTabla.setValueAt(modelo.mostrarMascotas().get(valor).getDuenio(), modeloTabla.getRowCount() - 1, 3);
                vista.btnMostrarMascotas.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la mascota con ese ID", "Error", JOptionPane.ERROR_MESSAGE);
                vista.btnMostrarMascotas.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se han ingresado mascotas", "Error", JOptionPane.ERROR_MESSAGE);
            vista.btnMostrarMascotas.setEnabled(false);
        }


}
    public void mostrarMascotas() {
        if (!modelo.mostrarMascotas().isEmpty()) {
            if (modeloTabla.getColumnCount() == 0) {
                modeloTabla.addColumn("ID");
                modeloTabla.addColumn("NOMBRE");
                modeloTabla.addColumn("RAZA");
                modeloTabla.addColumn("DUEÑO");
            }
            modeloTabla.setRowCount(0);
            for (Mascota p : modelo.mostrarMascotas()) {
                Object[] fila = {p.getID(), p.getNombreMascota(), p.getRaza(), p.getDuenio()};
                modeloTabla.addRow(fila);
            }
            vista.tablaMascotas.setModel(modeloTabla);
            vista.btnMostrarMascotas.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "No existen mascotas ingresadas", "Error", JOptionPane.ERROR_MESSAGE);
            modeloTabla.setRowCount(0);
            vista.btnMostrarMascotas.setEnabled(false);
        }
    }
    //validaciones
    public void keyTyped(KeyEvent e){
        char c = e.getKeyChar();
        if(e.getSource()==vista.txtID || e.getSource()==vista.txtBuscar){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()==vista.txtNombre  || e.getSource()==vista.txtRaza  || e.getSource()==vista.txtDuenio){
            if(Character.isLetter(c) || (e.getKeyChar()==KeyEvent.VK_SPACE) ||  (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) ) {
                e.setKeyChar(Character.toUpperCase(c));

            }else{
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista.btnAgregar) {
            agregar();
        }
        if (e.getSource() == vista.btnMostrarMascotas) mostrarMascotas();
        if (e.getSource()==vista.btnEliminar)eliminarTabla();
        if(e.getSource()==vista.btnBuscar)cargarMascota();
        if(e.getSource()==vista.btnCargarFoto)cargarFoto();
    }

    @Override
    public void focusGained(FocusEvent e) {
            vista.txtBuscar.setText("");
            vista.txtBuscar.setForeground(Color.BLACK);
            vista.btnBuscar.setEnabled(true);
    }

    @Override
    public void focusLost(FocusEvent e) {
            vista.txtBuscar.setForeground(Color.GRAY);
            vista.txtBuscar.setText("Ingrese el ID de la mascota");
            vista.btnBuscar.setEnabled(false);

    }
}
