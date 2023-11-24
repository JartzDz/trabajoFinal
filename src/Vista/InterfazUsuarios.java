package Vista;

import javax.swing.*;
import java.awt.*;

public class InterfazUsuarios extends JFrame{
    private JPanel panel1;
    private JPanel JPanelFondo;
    private JTable tablaMascotas;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTextField txtID;
    private JTextField txtNombre;
    private JTextField txtRaza;
    private JTextField txtDuenio;
    private JButton btnAgregar;
    private JButton btnMostrarMascotas;
    private JButton btnEliminar;
    private JButton btnModificar;
    private JTextField textField1;
    private JComboBox comboBox1;

    public InterfazUsuarios(){
        setContentPane(panel1);
    }
}
