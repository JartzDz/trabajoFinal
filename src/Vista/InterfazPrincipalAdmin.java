package Vista;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class InterfazPrincipalAdmin  extends JFrame{
    public JPanel panelPrincipal;
    public JLabel btnGestionUsuarios;
    public JLabel btnGestionCGA;
    public JLabel btnGestionMascotas;
    public JLabel btnGestionEst;
    public JLabel btnSalir;
    public JPanel panelInterfazAdmin;
    public JPanel panelUsuarios;
    public JPanel contenedor;
    public JLabel btnRegresar;
    public JTable tablaUsuarios;
    public JTextField txtBuscar;
    public JButton btnBuscar;
    public JTextField txtID;
    public JTextField txtNombre;
    public JTextField txtDireccion;
    public JTextField txtTelefono;
    public JTextField txtCorreo;
    public JComboBox cbTipoUsuario;
    public JTextField txtClave;
    public JButton btnAgregar;
    public JButton btnMostrarUsuarios;
    public JButton btnEliminar;
    public JButton btnModificar;
    public JPanel panelMascotas;
    public JPanel panelCGA;
    public JPanel panelEstablecimientos;
    public JPanel panelBienvenida;
    public JPanel JPanelFondo;
    public JTable tablaMascotas;
    public JTextField txtRaza;
    public JTextField txtDuenio;
    public JComboBox cboSexo;
    public JTextField txtColor;
    public JCheckBox chkVacunas;
    public JCheckBox chkDesparacitaciones;
    public JCheckBox chkEsterilizacion;
    public JCheckBox chkCirugias;
    public JSpinner spnEdad;
    public JButton btnMostrarMascotas;
    public JPanel panelImagen;
    public JLabel lblImagen;
    public JButton btnSubirFotoCarnet;
    public JTable tablaEstablecimientos;
    public JTextField txtRUC;
    public JComboBox cboTipoEst;
    public JComboBox cboCIProp;
    public JButton btnMostrarEst;
    public JTextField txtNombreEst;
    public JTextField txtDireccionEst;
    public JTextField txtTelfEst;
    public JTextField txtCorreoEst;
    public JButton btnEliminarEst;
    public JButton btnModificarEst;
    public JButton btnAgregarEst;
    public JButton btnBuscarEst;
    public JTextField txtBuscarEst;
    public JTextField txtIDMascota;
    public JTextField txtNombreMascota;
    public JButton btnAgregarMascota;
    public JButton btnModificarMascota;
    public JButton btnEliminarMascota;
    public JButton btnBuscarMascota;
    public JTextField txtBuscarMascota;
    public JLabel btnRegresar2;
    public JLabel btnRegresar3;
    public JLabel btnRegresar4;
    public JComboBox cboSubTipoEst;


    public InterfazPrincipalAdmin() {
        setContentPane(panelInterfazAdmin);

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

    }

}
