package Vista;

import javax.swing.*;

public class InterfazLogin extends JFrame {
    public JButton btnIngresar;
    public JTextField txtUsuario;
    public JPasswordField txtContra;
    public JComboBox cboTipoUsuario;
    public JPanel contenedor;
    public JCheckBox chkMostrarContra;
    public JButton btnSalir;
    public JLabel btnCrearCuenta;

    public InterfazLogin() {

       setContentPane(contenedor);
    }
}
