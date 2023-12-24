package Vista;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;

public class InterfazMascotas extends JFrame {
    public JPanel JPanelFondo;
    public JButton btnAgregarMascota;
    public JButton btnMostrarMascotas;
    public JButton btnEliminarMascota;
    public JButton btnModificarMascota;
    public JTextField txtIDMascota;
    public JTextField txtNombreMascota;
    public JTextField txtRaza;
    public JTextField txtDuenio;
    public JTextField txtBuscarMascota;
    public JButton btnBuscarMascota;
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
