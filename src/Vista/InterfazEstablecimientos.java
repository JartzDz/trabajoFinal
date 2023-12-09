package Vista;

import javax.swing.*;

public class InterfazEstablecimientos extends JFrame{
    public JTable tablaEstablecimientos;
    public JTextField txtBuscar;
    public JTextField txtDireccion;
    public JTextField txtTelefono;
    public JTextField txtRUC;
    public JButton btnBuscar;
    public JTextField txtNombre;
    public JTextField txtCorreo;
    public JComboBox cboTipoEst;
    public JTextField txtCIProp;
    public JButton btnAgregar;
    public JButton btnMostrar;
    public JButton btnEliminar;
    public JButton btnModificar;
    public JPanel contenedor;
    public JLabel btnRegresar;
    public JComboBox cboCIProp;

    public InterfazEstablecimientos() {
        setContentPane(contenedor);
    }

}
