package Vista;

import javax.swing.*;

public class InterfazRegistro extends JFrame{
    public JPanel contenedor;
    public JLabel btnRegresar;
    public JButton btnRegistrarme;
    public JTextField txtCedula;
    public JCheckBox chkMostrarContra;
    public JTextField txtNombres;
    public JTextField txtCorreo;
    public JTextField txtDireccion;
    public JTextField txtTelefono;
    public JPasswordField txtContra;
    public JPasswordField txtContraRep;

    public InterfazRegistro(){
        setContentPane(contenedor);
    }
}
