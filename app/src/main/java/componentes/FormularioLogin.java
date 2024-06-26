package componentes;

import DAO.ApiDAO;
import proyectofrontend.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioLogin extends JPanel {
  private final JLabel USUARIO = new JLabel("Usuario:");
  private JTextField usuarioCampo;
  private final JLabel PASSWORD = new JLabel("Contraseña:");
  private JPasswordField passwordCampo;
  private JButton botonLogin;

  public JButton getBotonLogin() {
    return botonLogin;
  }

  public FormularioLogin() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    setBackground(Color.WHITE);

    JPanel formPanel = crearFormularioPanel();

    JPanel botonesPanel = crearBotonesPanel();

    add(formPanel);
    add(botonesPanel);
  }

  private JPanel crearFormularioPanel() {
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new GridLayout(0, 2, 5, 5));
    formPanel.setMaximumSize(new Dimension(200, 200));
    formPanel.setBackground(Color.WHITE);
    agregarComponentesFormularioPanel(formPanel);
    return formPanel;
  }

  private void agregarComponentesFormularioPanel(JPanel formPanel) {
    formPanel.add(USUARIO);
    usuarioCampo = new JTextField("alejandro"); //TODO quitar harcode para login rapido
    formPanel.add(usuarioCampo);
    formPanel.add(PASSWORD);
    passwordCampo = new JPasswordField("alejandro");//TODO quitar harcode para login rápido
    formPanel.add(passwordCampo);
  }

  private JPanel crearBotonesPanel() {
    JPanel botonesPanel = new JPanel();
    botonesPanel.setBackground(Color.WHITE);
    crearContenidoBotonesPanel(botonesPanel);
    return botonesPanel;
  }

  private void crearContenidoBotonesPanel(JPanel botonesPanel) {
    botonLogin = new BotonRedondeado("Login");
    botonLogin.setBackground(Color.WHITE);
    botonLogin.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          App.getApiDAO().login(getUsuario(), getPassword());
          App.loggear(App.getApiDAO().getToken());

        } catch (Exception exception) {
          System.out.println(exception.toString());
          JOptionPane.showMessageDialog(null, "Error en el login", "Alerta",
              JOptionPane.WARNING_MESSAGE);
        }
      }
    });
    botonesPanel.add(botonLogin);
  }

  public String getUsuario() {
    return usuarioCampo.getText();
  }

  public String getPassword() {
    return passwordCampo.getText();
  }
}
