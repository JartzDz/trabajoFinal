package Vista;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;

public class InterfazMonitoreo  extends JFrame{
    public JButton generarAlertaButton;
    public JComboBox comboBox1;
    public JTextField CodigoProyecto;
    public JTextField Nresolucion;
    public JPanel panel;
    public JPanel fechaAprobacion;
    public  JDateChooser fecha;
    public JCheckBox checkBox1;
    public InterfazMonitoreo(){
        fecha = new JDateChooser();
        fecha.setDateFormatString("dd/MM/yyyy");
        fechaAprobacion.add(fecha);

        setContentPane(panel);
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
    }
}
