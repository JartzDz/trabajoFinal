package Vista;

import javax.swing.*;

public class InterfazUsuarios extends JFrame{
    public JPanel contenedor;
    public JTable tablaMascotas;
    public JTextField txtBuscar;
    public JButton btnBuscar;
    public JTextField txtID;
    public JTextField txtNombre;
    public JTextField txtRaza;
    public JTextField txtDuenio;
    public JTextField textField1;
    public JComboBox comboBox1;
    public JButton btnAgregar;
    public JButton btnMostrarMascotas;
    public JButton btnEliminar;
    public JButton btnModificar;

    public InterfazUsuarios(){
        setContentPane(contenedor);
    }
}
