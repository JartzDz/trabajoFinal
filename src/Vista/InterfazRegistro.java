package Vista;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;

public class InterfazRegistro extends JFrame{
    public JPanel contenedor;
    public JLabel btnRegresar;
    public JButton btnRegistrarme;
    public JTextField txtCedula;
    public JCheckBox chkMostrarContra;
    public JTextField txtNombres;
    public JTextField txtCorreo;
    public JTextField txtDireccion;
    public JTextField txtTelefono;
    public JPasswordField txtContra;
    public JPasswordField txtContraRep;

    public InterfazRegistro(){
        setContentPane(contenedor);
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
    }
}
