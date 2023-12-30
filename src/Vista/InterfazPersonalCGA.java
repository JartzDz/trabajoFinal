package Vista;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;

public class InterfazPersonalCGA extends JFrame{
    public JPanel contenedor;
    public JLabel btnRegresar;
    public JTabbedPane tabbedPane1;
    public JTextField txtRUC;
    public JTextField txtNombreEst;
    public JTextField txtDireccionEst;
    public JTextField txtTelfEst;
    public JTextField txtCorreoEst;
    public JComboBox cboTipoEst;
    public JComboBox cboCIProp;
    public JComboBox cboSubTipoEst;
    public JButton btnAgregarEst;
    public JButton btnMostrarEst;
    public JButton btnEliminarEst;
    public JButton btnModificarEst;
    public JTextField txtBuscarEst;
    public JButton btnBuscarEst;
    public JCheckBox APROBACIÓNDEUSODECheckBox;
    public JTable tablaEstablecimientos;

    public InterfazPersonalCGA(){
        setContentPane(contenedor);
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
    }
}
