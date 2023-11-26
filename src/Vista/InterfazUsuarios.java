package Vista;

import javax.swing.*;

public class InterfazUsuarios extends JFrame{
    public JPanel contenedor;
    public JTable tablaUsuarios;
    public JTextField txtBuscar;
    public JButton btnBuscar;
    public JTextField txtID;
    public JTextField txtNombre;
    public JTextField txtDireccion;
    public JTextField txtTelefono;
    public JTextField txtCorreo;
    public JComboBox cbTipoUsuario;
    public JButton btnAgregar;
    public JButton btnMostrarUsuarios;
    public JButton btnEliminar;
    public JButton btnModificar;
    public JTextField txtClave;

    public InterfazUsuarios(){
        setContentPane(contenedor);
    }
}
