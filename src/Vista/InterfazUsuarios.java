package Vista;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

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
    public JLabel btnRegresar;

    public InterfazUsuarios(){
        setContentPane(contenedor);
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
    }
}
