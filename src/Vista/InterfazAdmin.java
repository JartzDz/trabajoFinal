package Vista;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.jtattoo.plaf.fast.FastLookAndFeel;

import javax.swing.*;

public class InterfazAdmin extends JFrame{
    public JPanel contenedor;
    public JButton btnUsuarios;
    public JButton btnPersonalCGA;
    public JButton btnEstablecimientos;
    public JButton btnMascotas;
    public JButton btnSalir;

    public InterfazAdmin(){
        setContentPane(contenedor);
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
    }
}
