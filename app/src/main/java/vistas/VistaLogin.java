package vistas;

import componentes.Footer;
import componentes.FormularioLogin;
import componentes.Header;

import javax.swing.*;
import java.awt.*;

public class VistaLogin extends JFrame {
  private int ancho = 450, alto = 450;

  private FormularioLogin login;

  public VistaLogin() {
    int headerHeight = (int) (0.1 * alto);
    int footerHeight = (int) (0.1 * alto);

    JPanel headerPanel = new Header("MiColegio");
    headerPanel.setPreferredSize(new Dimension(1, headerHeight));

    JPanel footerPanel = new Footer("@imunnic");
    footerPanel.setPreferredSize(new Dimension(1, footerHeight));

    login = new FormularioLogin();

    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(Color.WHITE);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 1.0;
    centerPanel.add(login, gbc);

    setLayout(new BorderLayout());
    add(headerPanel, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
    add(footerPanel, BorderLayout.SOUTH);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(ancho, alto);
    setVisible(true);
  }

  public void autoclick() {
    login.getBotonLogin().doClick();
  }


}
