package Vista;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;

public class InterfazMascotas extends JFrame {
    public JPanel JPanelFondo;
    public JButton btnAgregar;
    public JButton btnMostrarMascotas;
    public JButton btnEliminar;
    public JButton btnModificar;
    public JTextField txtID;
    public JTextField txtNombre;
    public JTextField txtRaza;
    public JTextField txtDuenio;
    public JTextField txtBuscar;
    public JButton btnBuscar;
    public JTable tablaMascotas;

    public JPanel panelImagen;
    public JComboBox cboSexo;
    public JCheckBox chkVacunas;
    public JButton btnSubirFotoCarnet;
    public JTextField txtColor;
    public JCheckBox chkDesparacitaciones;
    public JCheckBox chkEsterilizacion;
    public JCheckBox chkCirugias;
    public JSpinner spnEdad;
    public JLabel btnRegresar;
    public JLabel lblImagen;


    public InterfazMascotas() {
        setContentPane(JPanelFondo);
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

    }





}
