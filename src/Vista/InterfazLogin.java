package Vista;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.jtattoo.plaf.fast.FastLookAndFeel;

import javax.swing.*;

public class InterfazLogin extends JFrame {
    public JButton btnIngresar;
    public JTextField txtUsuario;
    public JPasswordField txtContra;
    public JPanel contenedor;
    public JCheckBox chkMostrarContra;
    public JButton btnSalir;
    public JLabel btnCrearCuenta;

    public InterfazLogin() {

       setContentPane(contenedor);
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
    }
}
