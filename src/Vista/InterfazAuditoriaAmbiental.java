package Vista;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;

public class InterfazAuditoriaAmbiental extends JFrame{
    public JButton btnAgregar;
    public JTextField txtNumLicencia;
    public JTable table1;
    public JTextField txtPeriodo;

    public JTextField txtFechaAprobacionTDRS;
    public JTextField txtFechaAprobacionAA;
    public JTextField txtNombreConsultor;
    public JTextField txtNumPoliza;

    public JTextField txtFechaVenciPoliza;
    public JTextField txtMontoPoliza;
    private JPanel panel;
    public JLabel btnRegresar;
    public JTextField txtCorreo;
    public JCheckBox checkBox1;
    public JCheckBox checkBox2;
    private JPanel txtFechaEmisionlicencia;
    public JPanel txtFechaEmisiPoliza;
    private JPanel txtFechaAproTDRS;
    private JPanel txtFechavencimientoP;
    private JPanel txtFechaAA;
    public JTextField txtOficioTdr;
    public JTextField txtOficioAA;
    public JDateChooser fecha1;
    public JDateChooser fecha2;
    public JDateChooser fecha3;
    public JDateChooser fecha4,fecha5;



    public InterfazAuditoriaAmbiental(){
        fecha1 = new JDateChooser();
        fecha1.setDateFormatString("dd/MM/yyyy");
        txtFechaEmisionlicencia.add(fecha1);
        fecha2 = new JDateChooser();
        fecha2.setDateFormatString("dd/MM/yyyy");
        txtFechaAproTDRS.add(fecha2);
        fecha3 = new JDateChooser();
        fecha3.setDateFormatString("dd/MM/yyyy");
        txtFechaAA.add(fecha3);
        fecha4 = new JDateChooser();
        fecha4.setDateFormatString("dd/MM/yyyy");
        txtFechaEmisiPoliza.add(fecha4);
        fecha5 = new JDateChooser();
        fecha5.setDateFormatString("dd/MM/yyyy");
        txtFechavencimientoP.add(fecha5);
        setContentPane(panel);


        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
    }

}
