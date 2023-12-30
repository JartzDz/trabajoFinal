package Controlador;

import Modelo.*;
import Vista.InterfazPersonalCGA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorCGA extends MouseAdapter implements ActionListener, KeyListener,FocusListener  {
    InterfazPersonalCGA vista;
    GestorEstablecimiento modeloEstablecimiento;
    GestorUsuario modeloUsuarios;
    String usuario="";
    DefaultTableModel modeloTablaEst;
    public ControladorCGA(InterfazPersonalCGA vista, GestorEstablecimiento modeloEstablecimiento, GestorUsuario modeloUsuarios) {
        this.vista = vista;
        this.modeloEstablecimiento = modeloEstablecimiento;
        this.modeloUsuarios = modeloUsuarios;
        modeloUsuarios.recuperarUsuarios();
        modeloEstablecimiento.recuperarEstablecimientos();
        vista.btnBuscarEst.addActionListener(this);
        vista.btnAgregarEst.addActionListener(this);
        vista.btnEliminarEst.addActionListener(this);
        vista.btnMostrarEst.addActionListener(this);
        vista.btnModificarEst.addActionListener(this);
        vista.btnBuscarEst.setFocusable(false);
        vista.btnBuscarEst.addActionListener(this);
        vista.btnAgregarEst.addMouseListener(this);
        vista.btnEliminarEst.addMouseListener(this);
        vista.btnMostrarEst.addMouseListener(this);
        vista.btnModificarEst.addMouseListener(this);

        vista.cboCIProp.addActionListener(this);
        vista.txtRUC.addKeyListener(this);
        vista.txtTelfEst.addKeyListener(this);
        vista.txtBuscarEst.addKeyListener(this);
        vista.txtDireccionEst.addKeyListener(this);
        vista.txtNombreEst.addKeyListener(this);
        vista.txtCorreoEst.addKeyListener(this);
        vista.txtBuscarEst.addFocusListener(this);
        vista.cboTipoEst.addActionListener(this);
        vista.cboSubTipoEst.addActionListener(this);
        vista.cboSubTipoEst.setEnabled(false);
        vista.btnRegresar.addMouseListener(this);
        modeloUsuarios.recuperarUsuarios();
        String[] columnasEst = {"RUC", "NOMBRE", "DIRECCION", "TELEFONO", "CORREO", "PROPIETARIO", "TIPO"};
        modeloTablaEst = new DefaultTableModel(null, columnasEst);
        activarBotonesEst();
        actualizarSubtipo();

    }

    public void mostrarInterfaz(){
        cargarCombo();
        vista.setTitle("CGA");
        vista.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vista.setLocationRelativeTo(null);
        vista.setResizable(true);
        vista.setVisible(true);
    }
    public boolean esPropietario(){
        int indice = modeloUsuarios.buscarUsuario(usuario);
        if(modeloUsuarios.getUsuarios().get(indice) instanceof PersonalCGA){
            return false;
        }
        return true;
    }
    public void cargarCombo() {
        if (!esPropietario()) {
            for (Persona p : modeloUsuarios.getUsuarios()) {
                if (p instanceof DuenioEstablecimiento) {
                    vista.cboCIProp.addItem(p.getCedula() + "-" + p.getNombre());
                }
            }

            ArrayList<String> cedulas = separarCedula();
            for (String cedula : cedulas) {
                System.out.println(cedula);
            }
        }

    }

    public ArrayList<String> separarCedula() {
        ArrayList<String> cedulas = new ArrayList<>();

        int itemCount = vista.cboCIProp.getItemCount();

        for (int i = 0; i < itemCount; i++) {
            Object item = vista.cboCIProp.getItemAt(i);
            String combo = item.toString();
            String[] partes = combo.split("-");

            if (partes.length > 0) {
                String cedula = partes[0].trim();
                cedulas.add(cedula);
            } else {
                System.out.println("Error al separar cédula y nombre para: " + combo);
            }
        }

        return cedulas;
    }
    public void limpiarEst(){
        vista.txtRUC.setText("");
        vista.txtNombreEst.setText("");
        vista.txtTelfEst.setText("");
        vista.txtDireccionEst.setText("");
        vista.txtCorreoEst.setText("");
    }
    public void activarBotonesEst(){
        if(modeloEstablecimiento.getEstablecimiento().isEmpty()){
            vista.txtBuscarEst.setEnabled(false);
            vista.btnMostrarEst.setEnabled(false);
            vista.btnBuscarEst.setEnabled(false);
        }else{
            vista.txtBuscarEst.setEnabled(true);
            vista.btnMostrarEst.setEnabled(true);
            vista.btnBuscarEst.setEnabled(true);
        }
    }

    public void agregarEst() {
        try {
            String RUC = vista.txtRUC.getText();
            String nombreEstablecimiento = vista.txtNombreEst.getText();
            String tel = vista.txtTelfEst.getText();
            String direccion = vista.txtDireccionEst.getText();
            String correo = vista.txtCorreoEst.getText();
            String CIPropietario = vista.cboCIProp.getSelectedItem().toString();
            String tipoEstablecimiento = String.valueOf(vista.cboSubTipoEst.getSelectedItem());

            if (!nombreEstablecimiento.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !RUC.isEmpty()) {

                System.out.println("Valor del RUC: " + RUC);
                if (validarRUC(RUC)) {
                    if (validarCorreo(correo)) {
                        if (!modeloEstablecimiento.existeEstablecimiento(RUC)) {
                            // Agregar el establecimiento
                            modeloEstablecimiento.agregarEstablecimiento(RUC, nombreEstablecimiento, direccion, tel, correo, CIPropietario, tipoEstablecimiento);
                            modeloEstablecimiento.guardarEstablecimientos();
                            JOptionPane.showMessageDialog(null, "Establecimiento creado con éxito. Las credenciales fueron enviadas al Propietario");
                            limpiarEst();
                        } else {
                            JOptionPane.showMessageDialog(null, "El establecimiento no cuenta con los permisos necesarios.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Correo Inválido", "Error", JOptionPane.ERROR_MESSAGE);
                        vista.txtCorreoEst.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "RUC Inválido", "Error", JOptionPane.ERROR_MESSAGE);
                    vista.txtRUC.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Error al agregar el establecimiento.");
            e.printStackTrace();
        }
    }
    private boolean validarCheckBoxes() {
            return vista.chckUsoSuelo.isSelected() && vista.chckCroquis.isSelected() && vista.chckCertificado.isSelected() &&
                    vista.chckInspeccion.isSelected() && vista.chckMedicos.isSelected() && vista.chckPago.isSelected() &&
                    vista.chckRegistro.isSelected() && vista.chckResponsable.isSelected() && vista.chckRuc.isSelected();
    }
    private void desmarcarCheckBoxes() {
        vista.chckUsoSuelo.setSelected(false);
        vista.chckCroquis.setSelected(false);
        vista.chckCertificado.setSelected(false);
        vista.chckInspeccion.setSelected(false);
        vista.chckMedicos.setSelected(false);
        vista.chckPago.setSelected(false);
        vista.chckRegistro.setSelected(false);
        vista.chckResponsable.setSelected(false);
        vista.chckRuc.setSelected(false);

    }
    public void eliminarTablaEst() {
        try {
            int fila = vista.tablaEstablecimientos.getSelectedRow();

            if (fila == -1) {
                // No hay fila seleccionada, muestra un mensaje de error
                throw new Exception("Seleccione la fila que desea eliminar.");
            }

            String valor = (String) vista.tablaEstablecimientos.getValueAt(fila, 0);
            modeloEstablecimiento.eliminarEstablecimiento(fila);
            modeloEstablecimiento.guardarEstablecimientos();
            mostrarEstablecimiento();


            JOptionPane.showMessageDialog(vista, "Registro eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            // Capturar la excepción y mostrar mensaje de error
            JOptionPane.showMessageDialog(vista, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    public void mostrarEstablecimiento() {
        if (!modeloEstablecimiento.getEstablecimiento().isEmpty()) {
            if (modeloTablaEst.getColumnCount() == 0) {
                modeloTablaEst.addColumn("RUC");
                modeloTablaEst.addColumn("NOMBRE");
                modeloTablaEst.addColumn("DIRECCION");
                modeloTablaEst.addColumn("TELEFONO");
                modeloTablaEst.addColumn("CORREO");
                modeloTablaEst.addColumn("PROPIETARIO");
                modeloTablaEst.addColumn("TIPO");
            }
            modeloTablaEst.setRowCount(0);
            ArrayList<Establecimiento> listaEstablecimiento;
            if(esPropietario()){
                listaEstablecimiento = modeloEstablecimiento.buscarEstablecimientosDuenio(usuario);
            }else{
                listaEstablecimiento = modeloEstablecimiento.getEstablecimiento();
            }
            for (Establecimiento p : listaEstablecimiento) {
                Object[] fila = {p.getRuc(), p.getNombreEst(), p.getDireccion(), p.getTelefono(), p.getCorreo(), p.getCIRepresentante(), p.getTipoEstablecimiento()};
                modeloTablaEst.addRow(fila);
            }
            vista.tablaEstablecimientos.setModel(modeloTablaEst);
            vista.btnMostrarEst.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "No existen establecimientos ingresados", "Error", JOptionPane.ERROR_MESSAGE);
            modeloTablaEst.setRowCount(0);
            vista.btnMostrarEst.setEnabled(false);
        }
    }

    public void cargarEst() {
        String textoID = vista.txtBuscarEst.getText();

        if (!textoID.isEmpty()) {
            try {
                int indice = modeloEstablecimiento.buscarEst(textoID);

                if (indice != -1) {
                    // Limpiar el modelo de la tabla antes de agregar columnas y filas
                    modeloTablaEst.setRowCount(0);

                    // Verificar si las columnas ya han sido agregadas al modelo de la tabla
                    if (modeloTablaEst.getColumnCount() == 0) {
                        modeloTablaEst.addColumn("RUC");
                        modeloTablaEst.addColumn("NOMBRE");
                        modeloTablaEst.addColumn("DIRECCION");
                        modeloTablaEst.addColumn("TELEFONO");
                        modeloTablaEst.addColumn("CORREO");
                        modeloTablaEst.addColumn("PROPIETARIO");
                        modeloTablaEst.addColumn("TIPO");
                    }

                    Object[] fila = {
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getRuc(),
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getNombreEst(),
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getDireccion(),
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getTelefono(),
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getCorreo(),
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getCIRepresentante(),
                            modeloEstablecimiento.mostrarEstablecimientos().get(indice).getTipoEstablecimiento(),
                    };

                    modeloTablaEst.addRow(fila);
                    vista.tablaEstablecimientos.setModel(modeloTablaEst);

                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el establecimiento", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un RUC válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese un RUC o NOMBRE para buscar establecimiento", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void modificarEstablecimiento() {
        String RUC = vista.txtRUC.getText();
        String nombreEstablecimiento = vista.txtNombreEst.getText();
        String tel = vista.txtTelfEst.getText();
        String direccion = vista.txtDireccionEst.getText();
        String correo = vista.txtCorreoEst.getText();
        String CIPropietario = vista.cboCIProp.getSelectedItem().toString();
        String tipoEstablecimiento = String.valueOf(vista.cboSubTipoEst.getSelectedIndex());

        if (!nombreEstablecimiento.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !CIPropietario.isEmpty() && !RUC.isEmpty()) {
            if (validarCorreo(correo)) {
                // Obtener el índice del usuario a modificar
                int indice = GestorEstablecimiento.buscarEstablecimiento(RUC, modeloEstablecimiento.getEstablecimiento());
                // Verificar si el índice es válido
                if (indice != -1) {
                    // Llama al método modificarUsuario en el modelo
                    modeloEstablecimiento.modificarEstablecimiento(RUC, nombreEstablecimiento, direccion, tel, correo, CIPropietario, tipoEstablecimiento, indice);
                    modeloEstablecimiento.guardarEstablecimientos();
                    JOptionPane.showMessageDialog(null, "Establecimiento modificado con éxito.");
                    limpiarEst();
                    mostrarEstablecimiento();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el establecimiento con ese RUC", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese correo válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean validarCorreo(String correo) {
        String patronCorreo = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(patronCorreo);
        Matcher matcher = pattern.matcher(correo);


        return matcher.matches();
    }

    private static final int NUMERO_PROVINCIAS = 24;
    private static final int[] COEFICIENTES = {4, 3, 2, 7, 6, 5, 4, 3, 2};
    private static final int CONSTANTE = 11;

    public static boolean validarRUC(String ruc) {
        // Verifica que los dos primeros dígitos correspondan a un valor entre 1 y NUMERO_PROVINCIAS
        int prov = Integer.parseInt(ruc.substring(0, 2));

        if (!(prov > 0 && prov <= NUMERO_PROVINCIAS)) {
            System.out.println("Error: RUC ingresado incorrecto");
            return false;
        }

        // Verifica que el último dígito del RUC sea válido
        int[] digitos = new int[10];
        int suma = 0;

        // Asignamos el string a un array
        for (int i = 0; i < digitos.length; i++) {
            digitos[i] = Integer.parseInt(String.valueOf(ruc.charAt(i)));
        }

        for (int i = 0; i < digitos.length - 1; i++) {
            digitos[i] = digitos[i] * COEFICIENTES[i];
            suma += digitos[i];
        }


        int aux = suma % CONSTANTE;
        int resp = CONSTANTE - aux;

        resp = (resp == 10) ? 0 : resp;

        if (resp == digitos[9]) {
            return true;
        } else {
            System.out.println("Error: RUC ingresado incorrecto");
            return false;
        }
    }

    private void actualizarSubtipo() {
        // Limpiar el modelo del JComboBox secundario
        DefaultComboBoxModel<String> modeloSubTipo = new DefaultComboBoxModel<>();
        vista.cboSubTipoEst.setModel(modeloSubTipo);

        // Obtener la selección del JComboBox principal (Tipo)
        String seleccionTipo = vista.cboTipoEst.getSelectedItem().toString();

        // Agregar elementos al JComboBox secundario (Subtipo) según la selección en el JComboBox principal
        if ("CENTRO DE ATENCIÓN MÉDICO VETERINARIA".equals(seleccionTipo)) {
            modeloSubTipo.addElement("MEDICINA VETERINARIA A DOMICILIO");
            modeloSubTipo.addElement("CONSULTORIOS VETERINARIOS");
            modeloSubTipo.addElement("CLINICAS VETERINARIAS");
            modeloSubTipo.addElement("HOSPITALES VETERINARIOS");
            modeloSubTipo.addElement("U. VETERINARIAS MOVILES");
            modeloSubTipo.addElement("SERVICIOS DE REHABILITACION");
            modeloSubTipo.addElement("CAMPAÑAS DE ESTERILIZACION");
            modeloSubTipo.addElement("CENTROS DE ESTERILIZACION");
        } else if ("CENTRO DE MANEJO".equals(seleccionTipo)) {
            modeloSubTipo.addElement("CENTRO DE CRIANZA");
            modeloSubTipo.addElement("TIENDAS DE MASCOTAS");
            modeloSubTipo.addElement("CENTROS DE ESTETICA ANIMAL");
            modeloSubTipo.addElement("HOTELES Y ALOJAMIENTO");
            modeloSubTipo.addElement("ALBERGUES");
            modeloSubTipo.addElement("CENTROS DE ADIESTRAMIENTO");
            modeloSubTipo.addElement("ESTABLECIMIENTOS PARA ESPECTACULOS");
            modeloSubTipo.addElement("CENTROS DE INVESTIGACION");
            modeloSubTipo.addElement("CENTROS DE CUARENTENA");
            modeloSubTipo.addElement("OTROS");
        }
        vista.cboSubTipoEst.setEnabled(true);
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Color bg2 = new Color(4, 148, 156);
        Color fg2 = new Color(255, 255, 255);
        Color bg3 = new Color(229, 236, 186);
        Color fg3 = new Color(0, 0, 0);

        if (e.getSource() == vista.btnAgregarEst) {
            vista.btnAgregarEst.setBackground(bg3);
            vista.btnAgregarEst.setForeground(fg3);
            vista.btnAgregarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnModificarEst) {
            vista.btnModificarEst.setBackground(bg3);
            vista.btnModificarEst.setForeground(fg3);
            vista.btnModificarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnMostrarEst) {
            vista.btnMostrarEst.setBackground(bg3);
            vista.btnMostrarEst.setForeground(fg3);
            vista.btnMostrarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnEliminarEst) {
            vista.btnEliminarEst.setBackground(bg3);
            vista.btnEliminarEst.setForeground(fg3);
            vista.btnEliminarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnBuscarEst) {
            vista.btnBuscarEst.setBackground(bg3);
            vista.btnBuscarEst.setForeground(fg3);
            vista.btnBuscarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        if (e.getSource() == vista.btnRegresar) {
            vista.btnRegresar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        if (e.getSource() == vista.btnRegresar) {
            vista.btnRegresar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getSource()==vista.btnRegresar){
            limpiarEst();
            System.exit(0);
        }
    }
    @Override
    public void mouseEntered(MouseEvent e){
        Color bg = new Color(4, 148, 156);
        Color fg = new Color(255,255,255);
        Color bg4 = new Color(162, 197, 121);
        Color fg5 = new Color(0,0,0);

        if (e.getSource() == vista.btnAgregarEst) {
            vista.btnAgregarEst.setBackground(bg);
            vista.btnAgregarEst.setForeground(fg);
            vista.btnAgregarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (e.getSource() == vista.btnModificarEst) {
            vista.btnModificarEst.setBackground(bg);
            vista.btnModificarEst.setForeground(fg);
            vista.btnModificarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnMostrarEst){
            vista.btnMostrarEst.setBackground(bg);
            vista.btnMostrarEst.setForeground(fg);
            vista.btnMostrarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnEliminarEst){
            vista.btnEliminarEst.setBackground(bg);
            vista.btnEliminarEst.setForeground(fg);
            vista.btnEliminarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if(e.getSource() == vista.btnBuscarEst){
            vista.btnBuscarEst.setBackground(bg);
            vista.btnBuscarEst.setForeground(fg);
            vista.btnBuscarEst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        if(e.getSource() == vista.btnRegresar){
            vista.btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));


        }

    }

    //validaciones
    public void keyTyped(KeyEvent e){
        char c = e.getKeyChar();
        if(e.getSource()==vista.txtRUC){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()== vista.txtTelfEst){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()==vista.txtNombreEst || e.getSource()==vista.txtDireccionEst){
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
    public void focusGained(FocusEvent e) {
        vista.txtBuscarEst.setText("");
        vista.txtBuscarEst.setForeground(Color.BLACK);
        vista.btnBuscarEst.setEnabled(true);
    }

    public void focusLost(FocusEvent e) {
        vista.txtBuscarEst.setForeground(Color.GRAY);
        vista.txtBuscarEst.setText("Ingrese el RUC del establecimiento");
        vista.btnBuscarEst.setEnabled(false);

    }
    public void generarDocumento() {
        try {
            boolean establecimientoAgregado = false;

            if (validarCheckBoxes()) {
                String ruc = vista.txtRUC.getText();

                if (!modeloEstablecimiento.existeEstablecimiento(ruc)) {
                    JOptionPane.showMessageDialog(null, "ESTABLECIMIENTO VÁLIDO, SE PROCEDERÁ A CREAR EL DOCUMENTO DE ACEPTACIÓN");
                    String nombreDocumento = "DocAprobado" + vista.cboCIProp.getSelectedItem().toString();
                    establecimientoAgregado = true;

                    String contenido = generarContenido(
                            vista.txtRUC.getText(),
                            vista.cboCIProp.getSelectedItem().toString(),
                            vista.txtDireccionEst.getText(),
                            vista.txtTelfEst.getText(),
                            vista.txtCorreoEst.getText(),
                            String.valueOf(vista.cboSubTipoEst.getSelectedItem())
                    );

                    guardarEnArchivo(nombreDocumento, contenido);

                    // Llamada a agregarEst solo si el establecimiento es válido
                    agregarEst();
                } else {
                    limpiarEst();
                    JOptionPane.showMessageDialog(null, "EL ESTABLECIMIENTO YA EXISTE, NO SE PUEDE CREAR EL DOCUMENTO");
                }
            } else {
                JOptionPane.showMessageDialog(null, "EL ESTABLECIMIENTO NO CUENTA CON LOS PERMISOS NECESARIOS, POR LO TANTO NO ES VÁLIDO Y SE PROCEDERÁ A REALIZAR EL DOCUMENTO DE NEGACIÓN");
                String nombreDocumento = "DocRechazado" + vista.cboCIProp.getSelectedItem().toString();
                String contenido = generarContenidoNegacionServicios(vista.txtNombreEst.getText());
                guardarEnArchivo(nombreDocumento, contenido);
                limpiarEst();
            }

            desmarcarCheckBoxes();
        } catch (Exception e) {
            System.err.println("Error al generar el documento.");
            e.printStackTrace();
        }
    }

    public String generarContenido(String rucEstablecimiento, String nombreRepresentanteLegal,
                                   String direccionEstablecimiento, String numerosTelefono, String correoElectronico,
                                   String tipoEstablecimiento) {
        StringBuilder contenido = new StringBuilder();

        contenido.append("RUC del Establecimiento: ").append(rucEstablecimiento).append("\n");
        contenido.append("Nombre del Representante Legal: ").append(nombreRepresentanteLegal).append("\n");
        contenido.append("Dirección del Establecimiento: ").append(direccionEstablecimiento).append("\n");
        contenido.append("Números de Teléfono: ").append(numerosTelefono).append("\n");
        contenido.append("Correo Electrónico: ").append(correoElectronico).append("\n");

        contenido.append("Tipo de Establecimiento: ").append(tipoEstablecimiento).append("\n\n");

        contenido.append("Estimado/a ").append(nombreRepresentanteLegal).append(",\n\n");
        contenido.append("Por la presente, autorizamos formalmente al establecimiento con RUC ")
                .append(rucEstablecimiento).append(", ubicado en ").append(direccionEstablecimiento)
                .append(", para brindar servicios en nuestro nombre.\n\n");
        contenido.append("\nEl propósito de esta autorización es permitir al establecimiento operar como un punto de prestación de servicios, manteniendo los estándares de calidad y servicio establecidos por nuestra organización.\n\n");
        contenido.append("Esta autorización tiene una vigencia a partir de la fecha indicada y permanecerá en efecto hasta que sea revocada por escrito. Durante este período, el establecimiento autorizado está sujeto a revisiones periódicas para garantizar el cumplimiento continuo de nuestros estándares de calidad.\n\n");
        contenido.append("Agradecemos su compromiso y cooperación para mantener la reputación y la calidad de los servicios proporcionados bajo el nombre de nuestra organización.\n\n");
        contenido.append("Por favor, no dude en ponerse en contacto con nosotros si tiene alguna pregunta o inquietud con respecto a esta autorización.\n\n");
        contenido.append("Atentamente, la empresa\n\n");

        return contenido.toString();
    }
    public String generarContenidoNegacionServicios(String nombreEstablecimiento) {
        StringBuilder contenido = new StringBuilder();

        contenido.append("Estimado/a ").append(nombreEstablecimiento).append(",\n\n");
        contenido.append("Lamentamos informarle que, después de una cuidadosa revisión, hemos decidido no aceptar su solicitud para brindar servicios. El motivo de esta decisión es el siguiente:\n\n");
        contenido.append("Falta de permisos").append("\n\n");
        contenido.append("Entendemos que esto puede causar inconvenientes y estamos dispuestos a discutir cualquier inquietud que pueda tener al respecto.\n\n");
        contenido.append("Lamentamos lo que esto pueda causar y apreciamos su comprensión.\n\n");
        contenido.append("Atentamente,\n\n");
        contenido.append("La Empresa");


        return contenido.toString();
    }
    public void guardarEnArchivo(String nombreArchivo, String contenido) {
        try {
            // Obtener la ruta del directorio actual del usuario
            String directorioActual = System.getProperty("user.dir");

            // Combinar la ruta del directorio actual con el nombre del archivo
            String rutaCompleta = directorioActual + File.separator + nombreArchivo;

            // Crear el directorio si no existe
            File directorioDocumento = new File(rutaCompleta).getParentFile();
            directorioDocumento.mkdirs();

            // Escribir en el archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaCompleta))) {
                writer.write(contenido);
                System.out.println("Documento guardado correctamente en: " + rutaCompleta);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar el documento.");
            e.printStackTrace();
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==vista.btnAgregarEst){
            generarDocumento();
        }
        if(e.getSource()==vista.btnMostrarEst) mostrarEstablecimiento();
        if(e.getSource()==vista.btnEliminarEst)eliminarTablaEst();
        if(e.getSource()==vista.btnBuscarEst)cargarEst();
        if(e.getSource()==vista.btnModificarEst) modificarEstablecimiento();
        if (e.getSource() == vista.cboTipoEst) {
            actualizarSubtipo();
        }
    }



}
