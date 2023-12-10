package Controlador;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import Modelo.*;
import Vista.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class ControladorMascotas extends MouseAdapter implements ActionListener, KeyListener, FocusListener {
    private InterfazMascotas vista;
    private GestorMascotas modelo;

    DefaultTableModel modeloTabla;

    public ControladorMascotas(GestorMascotas modelo, InterfazMascotas vista) {
        this.modelo = modelo;
        this.vista = vista;
        modelo.recuperarMascotas();
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
        vista.txtColor.addKeyListener(this);
        vista.txtBuscar.addFocusListener(this);
        vista.btnBuscar.setFocusable(false);
        vista.txtBuscar.setEnabled(false);

        String[] columnas = {"ID", "NOMBRE", "RAZA", "DUEÑO","EDAD","COLOR","SEXO"};
        modeloTabla = new DefaultTableModel(null, columnas);
        vista.btnMostrarMascotas.setEnabled(false);
        vista.btnBuscar.setEnabled(false);
        vista.btnSubirFotoCarnet.addActionListener(this);
        vista.btnRegresar.addMouseListener(this);
        vista.btnModificar.addMouseListener(this);
        vista.btnBuscar.addMouseListener(this);
        vista.btnAgregar.addMouseListener(this);
        vista.btnEliminar.addMouseListener(this);
        vista.btnMostrarMascotas.addMouseListener(this);
        vista.setUndecorated(true);
        activarBotones();

    }

    public void mostrarInterfazMascotas() {
        vista.setLocationRelativeTo(null);
        vista.setResizable(false);
        vista.setTitle("MASCOTAS");
        vista.setVisible(true);
        vista.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    public void activarBotones(){
        if(modelo.getListaMascotas().isEmpty()){
            vista.txtBuscar.setEnabled(false);
            vista.btnMostrarMascotas.setEnabled(false);
            vista.btnBuscar.setEnabled(false);
        }else{
            vista.txtBuscar.setEnabled(true);
            vista.btnMostrarMascotas.setEnabled(true);
            vista.btnBuscar.setEnabled(true);
        }
    }

    public void limpiar(){
        vista.txtID.setText("");
        vista.txtNombre.setText("");
        vista.txtRaza.setText("");
        vista.txtDuenio.setText("");
        vista.txtBuscar.setText("");
        vista.txtColor.setText("");
        vista.spnEdad.setValue(0);
        vista.panelImagen.removeAll();
        vista.panelImagen.revalidate();
        vista.panelImagen.repaint();
    }

    public void agregar(){

        try {
            int ID = Integer.parseInt(vista.txtID.getText());
            String nombreMascota= vista.txtNombre.getText();
            String raza = vista.txtRaza.getText();
            String duenio = vista.txtDuenio.getText();
            int edad= (int) vista.spnEdad.getValue();
            String sexo= vista.cboSexo.getSelectedItem().toString();
            System.out.println(sexo);
            String color=vista.txtColor.getText();
            Image foto = null;
            Image fotoCarnet = null;
            boolean vacunas= Boolean.parseBoolean(vista.chkVacunas.getText());
            boolean esterilizacion= Boolean.parseBoolean(vista.chkEsterilizacion.getText());
            boolean desparacitaciones= Boolean.parseBoolean(vista.chkDesparacitaciones.getText());
            boolean otrasCirugias= Boolean.parseBoolean(vista.chkCirugias.getText());

            if (!nombreMascota.isEmpty() && !raza.isEmpty() && !duenio.isEmpty() && !color.isEmpty()) {
                modelo.agregarMascota(ID,edad, nombreMascota, sexo,raza,color, duenio, fotoCarnet, vacunas, desparacitaciones, esterilizacion, otrasCirugias);
                modelo.guardarMascotas();

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
        int valor= (int) vista.tablaMascotas.getValueAt(fila,0);
        modelo.eliminarMascota(fila);
        modelo.guardarMascotas();
        mostrarMascotas();
    }

    public void buscarMascota(){
        String valor = vista.txtBuscar.getText();
        int indice= modelo.buscarMascota(Integer.parseInt(valor));
        if(indice!=-1){
            JOptionPane.showMessageDialog(null, "Mascota Encontrada", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Mascota No Encontrada", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
                int nuevoAncho = 200;
                int nuevoAlto = 200;

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
        // Obtener el ID de la mascota a buscar
        String textoID = vista.txtBuscar.getText();

        // Verificar si el campo ID no está vacío
        if (!textoID.isEmpty()) {
            try {
                int idBusqueda = Integer.parseInt(textoID);
                int indice = modelo.buscarMascota(idBusqueda);

                if (indice != -1) {
                    // Limpiar el modelo de la tabla antes de agregar columnas y filas
                    modeloTabla.setRowCount(0);

                    // Verificar si las columnas ya han sido agregadas al modelo de la tabla
                    if (modeloTabla.getColumnCount() == 0) {
                        modeloTabla.addColumn("ID");
                        modeloTabla.addColumn("NOMBRE");
                        modeloTabla.addColumn("RAZA");
                        modeloTabla.addColumn("EDAD");
                        modeloTabla.addColumn("DUEÑO");
                        modeloTabla.addColumn("SEXO");
                        modeloTabla.addColumn("COLOR");
                        modeloTabla.addColumn("FOTO CARNET");
                    }

                    Object[] fila = {
                            modelo.mostrarMascotas().get(indice).getID(),
                            modelo.mostrarMascotas().get(indice).getNombreMascota(),
                            modelo.mostrarMascotas().get(indice).getRaza(),
                            modelo.mostrarMascotas().get(indice).getEdad(),
                            modelo.mostrarMascotas().get(indice).getDuenio(),
                            modelo.mostrarMascotas().get(indice).getSexo(),
                            modelo.mostrarMascotas().get(indice).getColor(),
                            (modelo.mostrarMascotas().get(indice).getFotoCarnet() != null)
                                    ? new ImageIcon(modelo.mostrarMascotas().get(indice).getFotoCarnet())
                                    : null
                    };

                    modeloTabla.addRow(fila);
                    vista.tablaMascotas.setModel(modeloTabla);
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró la mascota con ese ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese un ID para buscar mascota", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void modificarMascota() {
        String textoID = vista.txtID.getText();
        String nombreMascota = vista.txtNombre.getText();
        String raza = vista.txtRaza.getText();
        String duenio = vista.txtDuenio.getText();
        int edad = (int) vista.spnEdad.getValue();
        String sexo = vista.cboSexo.getSelectedItem().toString();
        String color = vista.txtColor.getText();
        Image foto = null;
        Image fotoCarnet = null;
        boolean vacunas = Boolean.parseBoolean(vista.chkVacunas.getText());
        boolean esterilizacion = Boolean.parseBoolean(vista.chkEsterilizacion.getText());
        boolean desparacitaciones = Boolean.parseBoolean(vista.chkDesparacitaciones.getText());
        boolean otrasCirugias = Boolean.parseBoolean(vista.chkCirugias.getText());

        if (!textoID.isEmpty() && !nombreMascota.isEmpty() && !raza.isEmpty() && !duenio.isEmpty() && !color.isEmpty()) {
            try {
                int ID = Integer.parseInt(textoID);
                int indice = modelo.buscarMascota(ID);

                if (indice != -1) {
                    modelo.modificarMascota(ID, edad, nombreMascota, sexo, raza, color, duenio, fotoCarnet, vacunas, desparacitaciones, esterilizacion, otrasCirugias, indice);
                    modelo.guardarMascotas();
                    JOptionPane.showMessageDialog(null, "Mascota modificada con éxito.");
                    limpiar();
                    mostrarMascotas();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró la mascota con ese ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarMascotas() {
        // Limpia el modelo de la tabla antes de agregar columnas y filas
        modeloTabla.setRowCount(0);

        if (!modelo.mostrarMascotas().isEmpty()) {
            // Verifica si las columnas ya han sido agregadas al modelo de la tabla
            if (modeloTabla.getColumnCount() == 0) {
                modeloTabla.addColumn("ID");
                modeloTabla.addColumn("NOMBRE");
                modeloTabla.addColumn("RAZA");
                modeloTabla.addColumn("DUEÑO");
                modeloTabla.addColumn("EDAD");
                modeloTabla.addColumn("COLOR");
                modeloTabla.addColumn("SEXO");
                modeloTabla.addColumn("FOTO CARNET");
            }

            for (Mascota mascota : modelo.mostrarMascotas()) {
                //Image fotoCarnet = mascota.getFotoCarnet();
                //ImageIcon imagenIcon = (fotoCarnet != null) ? new ImageIcon(fotoCarnet) : null;

                Object[] fila = {
                        mascota.getID(),
                        mascota.getNombreMascota(),
                        mascota.getRaza(),
                        mascota.getDuenio(),
                        mascota.getEdad(),
                        mascota.getColor(),
                        mascota.getSexo(),
                        //imagenIcon  // Esto será la imagen en la celda
                };
                modeloTabla.addRow(fila);
            }

            // Actualiza el modelo de la tabla después de agregar filas
            vista.tablaMascotas.setModel(modeloTabla);

            // Ajusta el ancho de la columna de la foto carnet
            //vista.tablaMascotas.getColumnModel().getColumn(7).setPreferredWidth(100);
        } else {
            JOptionPane.showMessageDialog(null, "No existen mascotas ingresadas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void cargarImagen() {
        JFileChooser file = new JFileChooser();
        file.showOpenDialog(null);
        File archivo = file.getSelectedFile();

        if (archivo != null) {
            String origen = archivo.getPath();
            try {
                Image image = ImageIO.read(archivo);
                Image imagenEscalada = image.getScaledInstance(vista.lblImagen.getWidth(), vista.lblImagen.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(imagenEscalada);
                vista.lblImagen.setIcon(icon);
                // Guardar la ruta de la imagen en el modelo o en una variable local según tus necesidades
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una Imagen");
        }
    }





    public void mouseEntered(MouseEvent e){
        Color bg = new Color(4, 148, 156);
        Color fg = new Color(255,255,255);

        if (e.getSource() == vista.btnAgregar) {
            vista.btnAgregar.setBackground(bg);
            vista.btnAgregar.setForeground(fg);
            vista.btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnModificar) {
            vista.btnModificar.setBackground(bg);
            vista.btnModificar.setForeground(fg);
            vista.btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnMostrarMascotas){
            vista.btnMostrarMascotas.setBackground(bg);
            vista.btnMostrarMascotas.setForeground(fg);
            vista.btnMostrarMascotas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnEliminar){
            vista.btnEliminar.setBackground(bg);
            vista.btnEliminar.setForeground(fg);
            vista.btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnBuscar){
            vista.btnBuscar.setBackground(bg);
            vista.btnBuscar.setForeground(fg);
            vista.btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnRegresar){
            vista.btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

    }
    public void mouseExited(MouseEvent e) {

        Color bg2 = new Color(229, 236, 186);
        Color fg2 = new Color(0, 0, 0);

        if (e.getSource() == vista.btnAgregar) {
            vista.btnAgregar.setBackground(bg2);
            vista.btnAgregar.setForeground(fg2);
            vista.btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnModificar) {
            vista.btnModificar.setBackground(bg2);
            vista.btnModificar.setForeground(fg2);
            vista.btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnMostrarMascotas){
            vista.btnMostrarMascotas.setBackground(bg2);
            vista.btnMostrarMascotas.setForeground(fg2);
            vista.btnMostrarMascotas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnEliminar){
            vista.btnEliminar.setBackground(bg2);
            vista.btnEliminar.setForeground(fg2);
            vista.btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnBuscar){
            vista.btnBuscar.setBackground(bg2);
            vista.btnBuscar.setForeground(fg2);
            vista.btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource()==vista.btnRegresar){
            vista.btnRegresar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    //validaciones
    public void keyTyped(KeyEvent e){
        char c = e.getKeyChar();
        if(e.getSource()==vista.txtID || e.getSource()==vista.txtBuscar || e.getSource()==vista.txtDuenio){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()==vista.txtNombre  || e.getSource()==vista.txtRaza || e.getSource()==vista.txtColor){
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
        if(e.getSource()==vista.btnAgregar) agregar();
        if(e.getSource()==vista.btnMostrarMascotas) mostrarMascotas();
        if(e.getSource()==vista.btnEliminar)eliminarTabla();
        if(e.getSource()==vista.btnBuscar)cargarMascota();
        if(e.getSource()==vista.btnModificar)modificarMascota();
        if(e.getSource()==vista.btnSubirFotoCarnet)cargarImagen();
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==vista.btnRegresar){
            limpiar();
            vista.dispose();
        }
    }

}
