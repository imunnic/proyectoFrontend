package vistas;

import componentes.Footer;
import componentes.Header;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;

public class VistaReservas extends JFrame {
  private int ancho = 700, alto = 700;
  private int headerHeight = (int) (0.1 * alto);
  private int footerHeight = (int) (0.1 * alto);
  private int profesor;

  private void setProfesor(int profesor) {
    this.profesor = profesor;
  }

  private int getProfesor() {
    return profesor;
  }

  public VistaReservas() {

    JPanel headerPanel = new Header("MiColegio");
    headerPanel.setPreferredSize(new Dimension(1, headerHeight));

    JPanel centralPanel = new JPanel();
    centralPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 0.2;
    gbc.gridx = 0;
    gbc.gridy = 0;
    /*centralPanel.add(componente, gbc);*/
    gbc.weightx = 0.8;
    gbc.gridx = 1;
    /*centralPanel.add(componente, gbc);*/

    JDateComponentFactory factory = new JDateComponentFactory();
    JDatePicker datePicker = factory.createJDatePicker();
    JLabel texto = new JLabel("Bienvenido a reservas");
    centralPanel.add(texto);
    centralPanel.add((Component) datePicker);

    JPanel footerPanel = new Footer("@imunnic");
    footerPanel.setPreferredSize(new Dimension(1, footerHeight));



    add(headerPanel, BorderLayout.NORTH);
    add(centralPanel, BorderLayout.CENTER);
    add(footerPanel, BorderLayout.SOUTH);

  }

  public void iniciarVistasReservas(int profesor){
    setProfesor(profesor);
    setSize(ancho,alto);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

}
