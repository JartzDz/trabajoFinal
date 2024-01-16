package Vista;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;

public class InterfazInformeAmbiental extends JFrame {
    private JPanel panel1;
    public JButton CREARALERTAButton;
    public JTextField CodigoProyecto;
    public JTextField nResolucion;
    public JTextField PeriodoEvaluado;
    public JDateChooser fecha;
    private JPanel panelFechaAprobacion;
    public JCheckBox checkBox1;
    public JLabel btnRegresarInforme;

    public InterfazInformeAmbiental(){
        fecha = new JDateChooser();
        fecha.setDateFormatString("dd/MM/yyyy");
        panelFechaAprobacion.add(fecha);
        setContentPane(panel1);
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
    }



}
