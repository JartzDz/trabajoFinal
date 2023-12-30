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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class ControladorMascotas extends MouseAdapter implements ActionListener, KeyListener, FocusListener {
    private InterfazMascotas vistaMascota;
    private GestorMascotas modeloMascota;
    private HashSet<String> idSet;
    private GestorUsuario modeloDuenios;
    private DefaultTableModel modeloTabla;
    private File dirImagen;
    private String usuario="";
    private String rutaDestino;

    public ControladorMascotas(GestorMascotas modeloMascota, InterfazMascotas vistaMascota,GestorUsuario modeloDuenios) {
        this.modeloMascota = modeloMascota;
        this.vistaMascota = vistaMascota;
        this.modeloDuenios = modeloDuenios;
        idSet = new HashSet<>();
        modeloMascota.recuperarMascotas();
        modeloDuenios.recuperarUsuarios();
        vistaMascota.btnAgregarMascota.addActionListener(this);
        vistaMascota.btnEliminarMascota.addActionListener(this);
        vistaMascota.btnModificarMascota.addActionListener(this);
        vistaMascota.btnMostrarMascotas.addActionListener(this);
        vistaMascota.btnBuscarMascota.addActionListener(this);
        vistaMascota.txtIDMascota.addKeyListener(this);
        vistaMascota.txtDuenio.addKeyListener(this);
        vistaMascota.txtBuscarMascota.addKeyListener(this);
        vistaMascota.txtRaza.addKeyListener(this);
        vistaMascota.txtNombreMascota.addKeyListener(this);
        vistaMascota.txtColor.addKeyListener(this);
        vistaMascota.txtBuscarMascota.addFocusListener(this);
        vistaMascota.btnBuscarMascota.setFocusable(false);
        vistaMascota.txtBuscarMascota.setEnabled(false);

        String[] columnas = {"ID", "NOMBRE", "RAZA", "DUEÑO","EDAD","COLOR","SEXO","VACUNAS","DESPARASITACIONES","ESTERILIZACION","OTRAS CIRUGIAS"};
        modeloTabla = new DefaultTableModel(null, columnas);
        vistaMascota.btnMostrarMascotas.setEnabled(false);
        vistaMascota.btnBuscarMascota.setEnabled(false);
        vistaMascota.btnSubirFotoCarnet.addActionListener(this);
        vistaMascota.btnRegresar.addMouseListener(this);
        vistaMascota.btnModificarMascota.addMouseListener(this);
        vistaMascota.btnBuscarMascota.addMouseListener(this);
        vistaMascota.btnAgregarMascota.addMouseListener(this);
        vistaMascota.btnEliminarMascota.addMouseListener(this);
        vistaMascota.btnMostrarMascotas.addMouseListener(this);
        vistaMascota.btnSubirFotoCarnet.addMouseListener(this);
        vistaMascota.setUndecorated(true);
        activarBotones();

    }

    public void mostrarInterfazMascotas() {
        cargarCedula();
        generarYMostrarID();
        vistaMascota.setUndecorated(true);
        vistaMascota.setTitle("USUARIOS");
        vistaMascota.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vistaMascota.setLocationRelativeTo(null);
        vistaMascota.setResizable(true);
        vistaMascota.setVisible(true);
    }

    public void activarBotones(){
        if(modeloMascota.getListaMascotas().isEmpty()){
            vistaMascota.txtBuscarMascota.setEnabled(false);
            vistaMascota.btnMostrarMascotas.setEnabled(false);
            vistaMascota.btnBuscarMascota.setEnabled(false);
        }else{
            vistaMascota.txtBuscarMascota.setEnabled(true);
            vistaMascota.btnMostrarMascotas.setEnabled(true);
            vistaMascota.btnBuscarMascota.setEnabled(true);
        }
    }

    public void limpiar(){
        vistaMascota.txtIDMascota.setText("");
        vistaMascota.txtNombreMascota.setText("");
        vistaMascota.txtRaza.setText("");
        vistaMascota.txtDuenio.setText("");
        vistaMascota.txtBuscarMascota.setText("");
        vistaMascota.txtColor.setText("");
        vistaMascota.spnEdad.setValue(0);
        vistaMascota.lblImagen.setIcon(null);
        DefaultTableModel modelo = (DefaultTableModel)  vistaMascota.tablaMascotas.getModel();
        modelo.setRowCount(0);
        vistaMascota.chkCirugias.setSelected(false);
        vistaMascota.chkDesparacitaciones.setSelected(false);
        vistaMascota.chkEsterilizacion.setSelected(false);
        vistaMascota.chkVacunas.setSelected(false);

    }

    public void agregar() {
        try {
            String idMascota = vistaMascota.txtIDMascota.getText();
            String nombreMascota = vistaMascota.txtNombreMascota.getText();
            String raza = vistaMascota.txtRaza.getText();
            String duenio = vistaMascota.txtDuenio.getText();
            int edad = (int) vistaMascota.spnEdad.getValue();
            String sexo = vistaMascota.cboSexo.getSelectedItem().toString();
            System.out.println(sexo);
            String color = vistaMascota.txtColor.getText();
            boolean vacunas = vistaMascota.chkVacunas.isSelected();
            boolean esterilizacion = vistaMascota.chkEsterilizacion.isSelected();
            boolean desparacitaciones = vistaMascota.chkDesparacitaciones.isSelected();
            boolean otrasCirugias = vistaMascota.chkCirugias.isSelected();

            if (!nombreMascota.isEmpty() && !raza.isEmpty() && !duenio.isEmpty() && !color.isEmpty()) {
                if (ControladorUsuarios.validarCedula(duenio)) {
                    // Verificar si la imagen en lblImagen no es null
                    if (vistaMascota.lblImagen.getIcon() == null) {
                        JOptionPane.showMessageDialog(null, "Por favor, seleccione una imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    guardarImagen(dirImagen);
                    modeloMascota.agregarMascota(idMascota, edad, nombreMascota, sexo, raza, color, duenio, dirImagen, vacunas, desparacitaciones, esterilizacion, otrasCirugias);
                    modeloMascota.guardarMascotas();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Cédula del propietario INCORRECTA.", "Error", JOptionPane.ERROR_MESSAGE);
                    vistaMascota.txtDuenio.setText("");
                    vistaMascota.txtDuenio.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void generarYMostrarID() {
        String idMascota = generarID();
        vistaMascota.txtIDMascota.setText(idMascota);
    }
    private String generarID() {

        char letra = (char) ('A' + new Random().nextInt(26));
        String digitos = generarDigitosUnicos();
        return letra + digitos;
    }


    private String generarDigitosUnicos() {
        Random random = new Random();
        StringBuilder digitos = new StringBuilder();

        while (digitos.length() < 4) {
            int digito = random.nextInt(10);
            if (!digitos.toString().contains(String.valueOf(digito))) {
                digitos.append(digito);
            }
        }

        return digitos.toString();
    }


    public boolean esDuenio() {
        int indice = modeloDuenios.buscarUsuario(usuario);
        if(modeloDuenios.getUsuarios().get(indice) instanceof Administrador){
            return false;
        }
        return true;
    }


    public void cargarCedula() {
            if (esDuenio()) {
                vistaMascota.txtDuenio.setText(usuario);
            } else {
                for (Persona p : modeloDuenios.getUsuarios()) {
                    if (p instanceof DuenioMascota) {
                        vistaMascota.txtDuenio.setText(p.getCedula());
                    }
                }
            }
    }


    public void eliminarTabla() {
        // Verifica si hay una fila seleccionada
        int fila = vistaMascota.tablaMascotas.getSelectedRow();
        if (fila != -1) {
            String idMascota = (String) vistaMascota.tablaMascotas.getValueAt(fila, 0);
            try {
                int pos = modeloMascota.buscarMascota(idMascota);
                modeloMascota.eliminarMascota(pos);
                modeloMascota.guardarMascotas();

                // Remueve la fila seleccionada del modelo de la tabla
                modeloTabla.removeRow(fila);

                JOptionPane.showMessageDialog(null, "Mascota eliminada con éxito.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error al convertir el ID a entero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila antes de eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    public void buscarMascota(){
        String valor = vistaMascota.txtBuscarMascota.getText();
        int indice= modeloMascota.buscarMascota(valor);
        if(indice!=-1){
            JOptionPane.showMessageDialog(null, "Mascota Encontrada", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Mascota No Encontrada", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            if (archivo != null) {
                String origen = archivo.getPath();
                Path org = Paths.get(origen);

                try {
                    Image image = ImageIO.read(archivo);
                    Image imagenEscalada = image.getScaledInstance(vistaMascota.lblImagen.getWidth(), vistaMascota.lblImagen.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(imagenEscalada);
                    vistaMascota.lblImagen.setIcon(icon);
                    dirImagen = archivo;
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una Imagen");
        }
    }



    public void guardarImagen(File archivo) {
        try {
            String dest = "./src/vista/imagenes/" + archivo.getName();
            Path destino = Paths.get(dest);
            String origen = archivo.getPath();
            Path org = Paths.get(origen);

            Files.copy(org, destino, StandardCopyOption.REPLACE_EXISTING);
            rutaDestino = archivo.getName();
        } catch (IOException ex) {
            System.out.println("No se guardó la imagen" + ex.getMessage());
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
                int x = (vistaMascota.panelImagen.getWidth() - nuevoAncho) / 2;  // Centrar la imagen horizontalmente
                int y = (vistaMascota.panelImagen.getHeight() - nuevoAlto) / 2;    // Centrar la imagen verticalmente

                imagenLabel.setBounds(x, y, nuevoAncho, nuevoAlto);

                // Establecer un diseño nulo en el panelImagen
                vistaMascota.panelImagen.setLayout(null);

                // Limpiar el panel y agregar el JLabel con la imagen
                vistaMascota.panelImagen.removeAll();
                vistaMascota.panelImagen.add(imagenLabel);

                vistaMascota.panelImagen.revalidate();
                vistaMascota.panelImagen.repaint();



            } else if (seleccion == JFileChooser.CANCEL_OPTION) {

            System.out.println("Operación cancelada");
        }
    }
    public void cargarMascota() {
        String textoID = vistaMascota.txtBuscarMascota.getText();

        if (!textoID.isEmpty()) {
            try {
                int indice = modeloMascota.buscarMascota(textoID);

                if (indice != -1) {
                    // Limpiar el modelo de la tabla antes de agregar columnas y filas
                    modeloTabla.setRowCount(0);

                    // Verificar si las columnas ya han sido agregadas al modelo de la tabla
                    if (modeloTabla.getColumnCount() == 0) {
                        modeloTabla.addColumn("ID");
                        modeloTabla.addColumn("NOMBRE");
                        modeloTabla.addColumn("RAZA");
                        modeloTabla.addColumn("DUEÑO");
                        modeloTabla.addColumn("EDAD");
                        modeloTabla.addColumn("COLOR");
                        modeloTabla.addColumn("SEXO");
                        modeloTabla.addColumn("VACUNAS");
                        modeloTabla.addColumn("DESPARASITACIONES");
                        modeloTabla.addColumn("ESTERILIZACION");
                        modeloTabla.addColumn("OTRAS CIRUGIAS");
                    }

                    Mascota mascota = modeloMascota.mostrarMascotas().get(indice);

                    Object[] fila = {
                            mascota.getID(),
                            mascota.getNombreMascota(),
                            mascota.getRaza(),
                            mascota.getDuenio(),
                            mascota.getEdad(),
                            mascota.getColor(),
                            mascota.getSexo(),
                            mascota.isVacunas(),
                            mascota.isDesparasitaciones(),
                            mascota.isEsterilizacion(),
                            mascota.isOtrasCirugias()
                    };
                    modeloTabla.addRow(fila);
                    vistaMascota.tablaMascotas.setModel(modeloTabla);

                    // Cargar la imagen de la mascota en lblImagenMascota
                    String rutaImagen = modeloMascota.mostrarMascotas().get(indice).getRutaFotoCarnet();
                    System.out.println("Ruta de la imagen: " + rutaImagen); // Añade este mensaje para verificar la ruta en la consola
                    cargarImagenMascota(rutaImagen);
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

    // Método para cargar la imagen de la mascota en lblImagen
    private void cargarImagenMascota(String rutaImagen) {
        try {
            if (rutaImagen != null) {
                File file = new File(rutaImagen);

                // Verificar si el archivo existe
                if (file.exists()) {
                    Image image = ImageIO.read(file);
                    Image imagenEscalada = image.getScaledInstance(vistaMascota.lblImagen.getWidth(), vistaMascota.lblImagen.getHeight(), Image.SCALE_SMOOTH);
                    vistaMascota.lblImagen.setIcon(new ImageIcon(imagenEscalada));
                } else {
                    JOptionPane.showMessageDialog(null, "El archivo de imagen no existe: " + rutaImagen, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "La ruta de la imagen es nula", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar la imagen de la mascota", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modificarMascota() {
        String textoID = vistaMascota.txtIDMascota.getText();
        String nombreMascota = vistaMascota.txtNombreMascota.getText();
        String raza = vistaMascota.txtRaza.getText();
        String duenio = vistaMascota.txtDuenio.getText();
        int edad = (int) vistaMascota.spnEdad.getValue();
        String sexo = vistaMascota.cboSexo.getSelectedItem().toString();
        String color = vistaMascota.txtColor.getText();

        boolean vacunas = vistaMascota.chkVacunas.isSelected();
        boolean esterilizacion = vistaMascota.chkEsterilizacion.isSelected();
        boolean desparacitaciones = vistaMascota.chkDesparacitaciones.isSelected();
        boolean otrasCirugias = vistaMascota.chkCirugias.isSelected();

        if (!textoID.isEmpty() && !nombreMascota.isEmpty() && !raza.isEmpty() && !duenio.isEmpty() && !color.isEmpty()) {
            try {
                int indice = modeloMascota.buscarMascota(textoID);

                if (indice != -1) {
                    guardarImagen(dirImagen);
                    String nuevaRutaFotoCarnet = modeloMascota.modificarMascota(textoID, edad, nombreMascota, sexo, raza, color, duenio, dirImagen, vacunas, desparacitaciones, esterilizacion, otrasCirugias, indice);

                    // Guardas las mascotas actualizadas en el modelo
                    modeloMascota.guardarMascotas();

                    // Cargas la nueva imagen en la vista
                    cargarImagenMascota(nuevaRutaFotoCarnet);

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
        if (!modeloMascota.getListaMascotas().isEmpty()) {
            if (modeloTabla.getColumnCount() == 0) {
                modeloTabla.addColumn("ID");
                modeloTabla.addColumn("NOMBRE");
                modeloTabla.addColumn("RAZA");
                modeloTabla.addColumn("EDAD");
                modeloTabla.addColumn("DUEÑO");
                modeloTabla.addColumn("SEXO");
                modeloTabla.addColumn("COLOR");
                modeloTabla.addColumn("VACUNAS");
                modeloTabla.addColumn("DESPARASITACIONES");
                modeloTabla.addColumn("ESTERILIZACION");
                modeloTabla.addColumn("OTRAS CIRUGIAS");
            }

            modeloTabla.setRowCount(0);
            ArrayList<Mascota> listaMascotas;
            if(esDuenio()){
                listaMascotas = modeloMascota.buscarMascotasDuenio(usuario);
            }else{
                listaMascotas = modeloMascota.getListaMascotas();
            }
            for (Mascota p : listaMascotas) {
                Object[] fila = {
                        p.getID(),
                        p.getNombreMascota(),
                        p.getRaza(),
                        p.getDuenio(),
                        p.getEdad(),
                        p.getColor(),
                        p.getSexo(),
                        p.isVacunas(),
                        p.isDesparasitaciones(),
                        p.isEsterilizacion(),
                        p.isOtrasCirugias()
                };
                modeloTabla.addRow(fila);
            }
            vistaMascota.tablaMascotas.setModel(modeloTabla);
            vistaMascota.btnMostrarMascotas.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "No existen mascotas", "Error", JOptionPane.ERROR_MESSAGE);
            modeloTabla.setRowCount(0);
            vistaMascota.btnMostrarMascotas.setEnabled(false);
        }
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void mouseEntered(MouseEvent e){
        Color bg = new Color(4, 148, 156);
        Color fg = new Color(255,255,255);

        if (e.getSource() == vistaMascota.btnAgregarMascota) {
            vistaMascota.btnAgregarMascota.setBackground(bg);
            vistaMascota.btnAgregarMascota.setForeground(fg);
            vistaMascota.btnAgregarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vistaMascota.btnModificarMascota) {
            vistaMascota.btnModificarMascota.setBackground(bg);
            vistaMascota.btnModificarMascota.setForeground(fg);
            vistaMascota.btnModificarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaMascota.btnMostrarMascotas){
            vistaMascota.btnMostrarMascotas.setBackground(bg);
            vistaMascota.btnMostrarMascotas.setForeground(fg);
            vistaMascota.btnMostrarMascotas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaMascota.btnEliminarMascota){
            vistaMascota.btnEliminarMascota.setBackground(bg);
            vistaMascota.btnEliminarMascota.setForeground(fg);
            vistaMascota.btnEliminarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaMascota.btnBuscarMascota){
            vistaMascota.btnBuscarMascota.setBackground(bg);
            vistaMascota.btnBuscarMascota.setForeground(fg);
            vistaMascota.btnBuscarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaMascota.btnSubirFotoCarnet){
            vistaMascota.btnSubirFotoCarnet.setBackground(bg);
            vistaMascota.btnSubirFotoCarnet.setForeground(fg);
            vistaMascota.btnSubirFotoCarnet.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaMascota.btnRegresar){
            vistaMascota.btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

    }
    public void mouseExited(MouseEvent e) {

        Color bg2 = new Color(229, 236, 186);
        Color fg2 = new Color(0, 0, 0);

        if (e.getSource() == vistaMascota.btnAgregarMascota) {
            vistaMascota.btnAgregarMascota.setBackground(bg2);
            vistaMascota.btnAgregarMascota.setForeground(fg2);
            vistaMascota.btnAgregarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vistaMascota.btnModificarMascota) {
            vistaMascota.btnModificarMascota.setBackground(bg2);
            vistaMascota.btnModificarMascota.setForeground(fg2);
            vistaMascota.btnModificarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaMascota.btnMostrarMascotas){
            vistaMascota.btnMostrarMascotas.setBackground(bg2);
            vistaMascota.btnMostrarMascotas.setForeground(fg2);
            vistaMascota.btnMostrarMascotas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaMascota.btnEliminarMascota){
            vistaMascota.btnEliminarMascota.setBackground(bg2);
            vistaMascota.btnEliminarMascota.setForeground(fg2);
            vistaMascota.btnEliminarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaMascota.btnBuscarMascota){
            vistaMascota.btnBuscarMascota.setBackground(bg2);
            vistaMascota.btnBuscarMascota.setForeground(fg2);
            vistaMascota.btnBuscarMascota.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vistaMascota.btnSubirFotoCarnet){
            vistaMascota.btnSubirFotoCarnet.setBackground(bg2);
            vistaMascota.btnSubirFotoCarnet.setForeground(fg2);
            vistaMascota.btnSubirFotoCarnet.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource()== vistaMascota.btnRegresar){
            vistaMascota.btnRegresar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    //validaciones
    public void keyTyped(KeyEvent e){
        char c = e.getKeyChar();
        if(e.getSource()== vistaMascota.txtDuenio){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()== vistaMascota.txtNombreMascota || e.getSource()== vistaMascota.txtRaza || e.getSource()== vistaMascota.txtColor ){
            if(Character.isLetter(c) || (e.getKeyChar()==KeyEvent.VK_SPACE) ||  (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) ) {
                e.setKeyChar(Character.toUpperCase(c));

            }else{
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }
        if(e.getSource()== vistaMascota.txtBuscarMascota){
            if (Character.isLetter(c) || Character.isDigit(c)|| (e.getKeyChar()==KeyEvent.VK_SPACE) ||  (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)){
                e.setKeyChar(Character.toUpperCase(c));
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
        if(e.getSource()== vistaMascota.btnAgregarMascota)  {

            agregar();
            generarYMostrarID();
        }
        if(e.getSource()== vistaMascota.btnMostrarMascotas) {

            mostrarMascotas();
        }
        if(e.getSource()== vistaMascota.btnEliminarMascota){

            eliminarTabla();
        }
        if(e.getSource()== vistaMascota.btnBuscarMascota) {

            cargarMascota();
        }
        if(e.getSource()== vistaMascota.btnModificarMascota){

            modificarMascota();
        }
        if(e.getSource()== vistaMascota.btnSubirFotoCarnet) {
            cargarImagen();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
            vistaMascota.txtBuscarMascota.setText("");
            vistaMascota.txtBuscarMascota.setForeground(Color.BLACK);
            vistaMascota.btnBuscarMascota.setEnabled(true);

    }

    @Override
    public void focusLost(FocusEvent e) {
            vistaMascota.txtBuscarMascota.setForeground(Color.GRAY);
            vistaMascota.txtBuscarMascota.setText("Ingrese el ID,NOMBRE de la mascota o CEDULA del PROPIETARIO");
            vistaMascota.btnBuscarMascota.setEnabled(false);


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()== vistaMascota.btnRegresar){
            limpiar();
            vistaMascota.dispose();
        }
    }

}
