package Controlador;

import Modelo.*;
import Vista.InterfazEstablecimientos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorEstablecimientos extends MouseAdapter implements ActionListener,KeyListener, FocusListener {
    private GestorEstablecimiento modeloEstablecimiento;
    private GestorUsuario modeloPropietarios;
    private InterfazEstablecimientos vistaEstablecimiento;
    DefaultTableModel modeloTabla;
    private String usuario="";
    public ControladorEstablecimientos (GestorEstablecimiento modelo, InterfazEstablecimientos vista, GestorUsuario modeloPropietarios){
        modeloEstablecimiento=modelo;
        vistaEstablecimiento=vista;
        this.modeloPropietarios = modeloPropietarios;
        modeloEstablecimiento.recuperarEstablecimientos();
        vista.btnBuscar.addActionListener(this);
        vista.btnAgregar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnMostrar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.btnBuscar.setFocusable(false);
        vista.btnRegresar.addMouseListener(this);
        vista.cboCIProp.addActionListener(this);
        vista.cboTipoEst.addActionListener(this);
        vista.cboSubTipoEst.addActionListener(this);
        vista.cboSubTipoEst.setEnabled(false);
        vista.txtRUC.addKeyListener(this);
        vista.btnAgregar.setVisible(false);
        vista.txtTelefono.addKeyListener(this);
        vista.txtBuscar.addKeyListener(this);
        vista.txtDireccion.addKeyListener(this);
        vista.txtNombre.addKeyListener(this);
        vista.txtCorreo.addKeyListener(this);
        vista.txtBuscar.addFocusListener(this);
        modeloPropietarios.recuperarUsuarios();
        String[] columnas = {"RUC", "NOMBRE", "DIRECCION", "TELEFONO", "CORREO", "PROPIETARIO", "TIPO"};
        modeloTabla = new DefaultTableModel(null, columnas);
        activarBotones();
        actualizarSubtipo();
    }

    public boolean esPropietario() {
        int indice = modeloPropietarios.buscarUsuario(usuario);
        return modeloPropietarios.getUsuarios().get(indice) instanceof DuenioEstablecimiento;
    }

    public void cargarCombo() {
        if (esPropietario()) {
            vistaEstablecimiento.cboCIProp.addItem(usuario);
        } else {
            for (Persona p : modeloPropietarios.getUsuarios()) {
                if (p instanceof DuenioEstablecimiento) {
                    vistaEstablecimiento.cboCIProp.addItem(p.getCedula() + "-" + p.getNombre());
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

        int itemCount = vistaEstablecimiento.cboCIProp.getItemCount();

        for (int i = 0; i < itemCount; i++) {
            Object item = vistaEstablecimiento.cboCIProp.getItemAt(i);
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


    public void mostrarInterfazEstablecimiento() {
        vistaEstablecimiento.cboCIProp.removeAllItems();
        cargarCombo();
        vistaEstablecimiento.setUndecorated(true);
        vistaEstablecimiento.setTitle("ESTABLECIMIENTO");
        vistaEstablecimiento.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vistaEstablecimiento.setLocationRelativeTo(null);
        vistaEstablecimiento.setResizable(true);
        vistaEstablecimiento.setVisible(true);
    }
    public void limpiar(){
        vistaEstablecimiento.txtRUC.setText("");
        vistaEstablecimiento.txtNombre.setText("");
        vistaEstablecimiento.txtTelefono.setText("");
        vistaEstablecimiento.txtDireccion.setText("");
        vistaEstablecimiento.txtCorreo.setText("");
        DefaultTableModel modelo = (DefaultTableModel)  vistaEstablecimiento.tablaEstablecimientos.getModel();
        modeloTabla.setRowCount(0);
    }
    public void activarBotones(){
        if(modeloEstablecimiento.getEstablecimiento().isEmpty()){
            vistaEstablecimiento.txtBuscar.setEnabled(false);
            vistaEstablecimiento.btnMostrar.setEnabled(false);
            vistaEstablecimiento.btnBuscar.setEnabled(false);
        }else{
            vistaEstablecimiento.txtBuscar.setEnabled(true);
            vistaEstablecimiento.btnMostrar.setEnabled(true);
            vistaEstablecimiento.btnBuscar.setEnabled(true);
        }
    }

    public void agregar() {
        String RUC = vistaEstablecimiento.txtRUC.getText();
        String nombreEstablecimiento = vistaEstablecimiento.txtNombre.getText();
        String tel = vistaEstablecimiento.txtTelefono.getText();
        String direccion = vistaEstablecimiento.txtDireccion.getText();
        String correo = vistaEstablecimiento.txtCorreo.getText();
        String CIPropietario = getCedulaSeleccionada();

        String tipoEstablecimiento = String.valueOf(vistaEstablecimiento.cboSubTipoEst.getSelectedItem());

        if (!nombreEstablecimiento.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !RUC.isEmpty()) {
            // Validar RUC antes de agregar el establecimiento
            if (validarRUC(RUC)) {
                if (validarCorreo(correo)) {
                    if (esTelefonoValido(tel)) {
                        modeloEstablecimiento.agregarEstablecimiento(RUC, nombreEstablecimiento, direccion, tel, correo, CIPropietario, tipoEstablecimiento);
                        modeloEstablecimiento.guardarEstablecimientos();
                        // enviarCorreo(correo, ID, clave, nombreUsuario);
                        JOptionPane.showMessageDialog(null, "Establecimiento creado con éxito. Las credenciales fueron enviadas al Propietario");
                        mostrarEstablecimiento();
                        limpiar();
                    } else {
                        JOptionPane.showMessageDialog(null, "Número de teléfono debe tener entre 7 y 10 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                        vistaEstablecimiento.txtTelefono.setText("");
                        vistaEstablecimiento.txtTelefono.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Correo Inválido", "Error", JOptionPane.ERROR_MESSAGE);
                    vistaEstablecimiento.txtCorreo.setText("");
                    vistaEstablecimiento.txtCorreo.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "RUC Inválido", "Error", JOptionPane.ERROR_MESSAGE);
                vistaEstablecimiento.txtRUC.setText("");
                vistaEstablecimiento.txtRUC.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public String getCedulaSeleccionada() {
        int selectedIndex = vistaEstablecimiento.cboCIProp.getSelectedIndex();
        if (selectedIndex != -1) {

            Object selectedItem = vistaEstablecimiento.cboCIProp.getSelectedItem();

            if (selectedItem != null) {
                String combo = selectedItem.toString().trim();

                int separatorIndex = combo.indexOf("-");

                if (separatorIndex != -1) {

                    return combo.substring(0, separatorIndex).trim();
                } else {

                    return combo;
                }
            } else {
                System.out.println("Error: El item seleccionado es nulo.");
            }
        } else {
            System.out.println("Error: No hay item seleccionado en el JComboBox.");
        }

        return null;
    }

    public void eliminarTablaEst() {
        try {
            int fila = vistaEstablecimiento.tablaEstablecimientos.getSelectedRow();

            if (fila == -1) {
                // No hay fila seleccionada, muestra un mensaje de error
                throw new Exception("Seleccione la fila que desea eliminar.");
            }

            String valor = (String) vistaEstablecimiento.tablaEstablecimientos.getValueAt(fila, 0);
            modeloEstablecimiento.eliminarEstablecimiento(fila);
            modeloEstablecimiento.guardarEstablecimientos();
            mostrarEstablecimiento();


            JOptionPane.showMessageDialog(vistaEstablecimiento, "Registro eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            // Capturar la excepción y mostrar mensaje de error
            JOptionPane.showMessageDialog(vistaEstablecimiento, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    public void mostrarEstablecimiento() {

        if (!modeloEstablecimiento.getEstablecimiento().isEmpty()) {
            if (modeloTabla.getColumnCount() == 0) {
                modeloTabla.addColumn("RUC");
                modeloTabla.addColumn("NOMBRE");
                modeloTabla.addColumn("DIRECCION");
                modeloTabla.addColumn("TELEFONO");
                modeloTabla.addColumn("CORREO");
                modeloTabla.addColumn("PROPIETARIO");
                modeloTabla.addColumn("TIPO");
            }
            modeloTabla.setRowCount(0);
            ArrayList<Establecimiento> listaEstablecimiento;
            if(esPropietario()){
                listaEstablecimiento = modeloEstablecimiento.buscarEstablecimientosDuenio(usuario);
            }else{
                listaEstablecimiento = modeloEstablecimiento.getEstablecimiento();
            }
            for (Establecimiento p : listaEstablecimiento) {
                Object[] fila = {p.getRuc(), p.getNombreEst(), p.getDireccion(), p.getTelefono(), p.getCorreo(), p.getCIRepresentante(), p.getTipoEstablecimiento()};
                modeloTabla.addRow(fila);
            }
            vistaEstablecimiento.tablaEstablecimientos.setModel(modeloTabla);
            vistaEstablecimiento.btnMostrar.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "No existen establecimientos ingresados", "Error", JOptionPane.ERROR_MESSAGE);
            modeloTabla.setRowCount(0);
            vistaEstablecimiento.btnMostrar.setEnabled(false);
        }
    }

    public void cargarEstablecimiento() {
        String busqueda = vistaEstablecimiento.txtBuscar.getText();
        int indice = 0;
        if (esPropietario()) {
            indice = modeloEstablecimiento.buscarEstablecimiento(busqueda, modeloEstablecimiento.buscarEstablecimientosDuenio(usuario));
        } else {
            indice = modeloEstablecimiento.buscarEstablecimiento(busqueda, modeloEstablecimiento.getEstablecimiento());
        }
        if (indice != -1) {
            if (modeloTabla.getColumnCount() == 0) {
                modeloTabla.addColumn("RUC");
                modeloTabla.addColumn("NOMBRE");
                modeloTabla.addColumn("DIRECCION");
                modeloTabla.addColumn("TELEFONO");
                modeloTabla.addColumn("CORREO");
                modeloTabla.addColumn("PROPIETARIO");
                modeloTabla.addColumn("TIPO");
            }
            modeloTabla.setRowCount(0);
            Object[] fila = {
                    modeloEstablecimiento.buscarEstablecimientosDuenio(usuario).get(indice).getRuc(),
                    modeloEstablecimiento.buscarEstablecimientosDuenio(usuario).get(indice).getNombreEst(),
                    modeloEstablecimiento.buscarEstablecimientosDuenio(usuario).get(indice).getDireccion(),
                    modeloEstablecimiento.buscarEstablecimientosDuenio(usuario).get(indice).getTelefono(),
                    modeloEstablecimiento.buscarEstablecimientosDuenio(usuario).get(indice).getCorreo(),
                    modeloEstablecimiento.buscarEstablecimientosDuenio(usuario).get(indice).getCIRepresentante(),
                    modeloEstablecimiento.buscarEstablecimientosDuenio(usuario).get(indice).getTipoEstablecimiento()
            };
            modeloTabla.addRow(fila);
            vistaEstablecimiento.tablaEstablecimientos.setModel(modeloTabla);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el establecimiento con ese RUC", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarSubtipo() {
        // Limpiar el modelo del JComboBox secundario
        DefaultComboBoxModel<String> modeloSubTipo = new DefaultComboBoxModel<>();
        vistaEstablecimiento.cboSubTipoEst.setModel(modeloSubTipo);


        // Obtener la selección del JComboBox principal (Tipo)
        String seleccionTipo = vistaEstablecimiento.cboTipoEst.getSelectedItem().toString();


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
        vistaEstablecimiento.cboSubTipoEst.setEnabled(true);
    }


    public void modificarEstablecimiento() {
        String RUC = vistaEstablecimiento.txtRUC.getText();
        String nombreEstablecimiento = vistaEstablecimiento.txtNombre.getText();
        String tel = vistaEstablecimiento.txtTelefono.getText();
        String direccion = vistaEstablecimiento.txtDireccion.getText();
        String correo = vistaEstablecimiento.txtCorreo.getText();
        String CIPropietario = getCedulaSeleccionada();
        String tipoEstablecimiento = String.valueOf(vistaEstablecimiento.cboSubTipoEst.getSelectedItem());

        if (!nombreEstablecimiento.isEmpty() && !tel.isEmpty() && !direccion.isEmpty() && !correo.isEmpty() && !CIPropietario.isEmpty() && !RUC.isEmpty()) {
            if (validarCorreo(correo)) {
                if (esTelefonoValido(tel)) {
                    // Obtener el índice del establecimiento a modificar
                    int indice = modeloEstablecimiento.buscarEstablecimiento(RUC, modeloEstablecimiento.getEstablecimiento());
                    // Verificar si el índice es válido
                    if (indice != -1) {
                        // Llama al método modificarEstablecimiento en el modelo
                        modeloEstablecimiento.modificarEstablecimiento(RUC, nombreEstablecimiento, direccion, tel, correo, CIPropietario, tipoEstablecimiento, indice);
                        modeloEstablecimiento.guardarEstablecimientos();
                        JOptionPane.showMessageDialog(null, "Establecimiento modificado con éxito.");
                        limpiar();
                        mostrarEstablecimiento();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el establecimiento con ese RUC", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Número de teléfono debe tener entre 7 y 10 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                    vistaEstablecimiento.txtTelefono.setText("");
                    vistaEstablecimiento.txtTelefono.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese correo válido.", "Error", JOptionPane.ERROR_MESSAGE);
                vistaEstablecimiento.txtCorreo.setText("");
                vistaEstablecimiento.txtCorreo.requestFocus();
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
    private boolean esTelefonoValido(String telefono) {
        telefono = telefono.replaceAll("\\s", "");
        return telefono.length() >= 7 && telefono.length() <= 10 && telefono.matches("\\d+");
    }

    public boolean validarRUC(String ruc) {
        if (ruc.length() != 13) {
            return false;
        }

        int[] coeficientes = {4, 3, 2, 7, 6, 5, 4, 3, 2};
        int suma = 0;

        if (ruc.charAt(1) != '9') {
            return false;
        }

        for (int i = 0; i < 9; i++) {
            suma += Character.getNumericValue(ruc.charAt(i)) * coeficientes[i];
        }

        int residuo = suma % 11;
        int digitoVerificador = (residuo == 0) ? 0 : 11 - residuo;

        System.out.println(digitoVerificador == Character.getNumericValue(ruc.charAt(9)));

        return digitoVerificador == Character.getNumericValue(ruc.charAt(9));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==vistaEstablecimiento.btnRegresar) {
            limpiar();

            vistaEstablecimiento.dispose();
        }
    }
    public void mouseEntered(MouseEvent e) {
        vistaEstablecimiento.btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        vistaEstablecimiento.btnRegresar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vistaEstablecimiento.btnAgregar) agregar();
        if(e.getSource()==vistaEstablecimiento.btnMostrar) mostrarEstablecimiento();
        if(e.getSource()==vistaEstablecimiento.btnEliminar)eliminarTablaEst();
        if(e.getSource()==vistaEstablecimiento.btnBuscar)cargarEstablecimiento();
        if(e.getSource()==vistaEstablecimiento.btnModificar) modificarEstablecimiento();
        if (e.getSource() == vistaEstablecimiento.cboTipoEst) {
            actualizarSubtipo();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        vistaEstablecimiento.txtBuscar.setText("");
        vistaEstablecimiento.txtBuscar.setForeground(Color.BLACK);
        vistaEstablecimiento.btnBuscar.setEnabled(true);
    }

    @Override
    public void focusLost(FocusEvent e) {
        vistaEstablecimiento.txtBuscar.setForeground(Color.GRAY);
        vistaEstablecimiento.txtBuscar.setText("Ingrese el RUC o NOMBRE del establecimiento");
        vistaEstablecimiento.btnBuscar.setEnabled(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if(e.getSource()==vistaEstablecimiento.txtRUC){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()== vistaEstablecimiento.txtTelefono){
            if(!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE && c!=KeyEvent.VK_ENTER){
                e.consume();
                Toolkit.getDefaultToolkit().beep();}
        }
        if(e.getSource()==vistaEstablecimiento.txtNombre){
            if(Character.isLetter(c) || (e.getKeyChar()==KeyEvent.VK_SPACE) ||  (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) ) {
                e.setKeyChar(Character.toUpperCase(c));

            }else{
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }
        if (e.getSource() == vistaEstablecimiento.txtDireccion) {
            if (Character.isLetterOrDigit(c) || (e.getKeyChar()==KeyEvent.VK_SPACE) ||  (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                if (Character.isLowerCase(c)) {
                    e.setKeyChar(Character.toUpperCase(c));
                }
            } else {
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }
        if (e.getSource() == vistaEstablecimiento.txtBuscar) {
            if (Character.isLetterOrDigit(c) || (e.getKeyChar()==KeyEvent.VK_SPACE) ||  (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                if (Character.isLowerCase(c)) {
                    e.setKeyChar(Character.toUpperCase(c));
                }
            } else {
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
}
