package vistas;

import DAO.ApiDAO;
import componentes.Footer;
import componentes.Header;
import entidades.Profesor;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import proyectofrontend.App;

import javax.swing.*;
import java.awt.*;

public class VistaReservas extends JFrame {
  private int ancho = 700, alto = 700;
  private int headerHeight = (int) (0.1 * alto);
  private int footerHeight = (int) (0.1 * alto);
  private Profesor profesor;
  private JLabel texto;

  private void setProfesor(Profesor profesor) {
    this.profesor = profesor;
  }

  private Profesor getProfesor() {
    return profesor;
  }

  public VistaReservas() {

    JPanel headerPanel = new Header("MiColegio");
    headerPanel.setPreferredSize(new Dimension(1, headerHeight));

    JPanel centralPanel = new JPanel();
    centralPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.VERTICAL;
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.NORTH;
    gbc.weightx = 0.2;
    gbc.gridx = 0;
    gbc.gridy = 0;
    /*centralPanel.add(componente, gbc);*/
    texto = new JLabel("Bienvenido a reservas: " + getProfesor());
    centralPanel.add(texto, gbc);

    gbc.weightx = 0.8;
    gbc.gridx = 1;
    /*centralPanel.add(componente, gbc);*/
    JDateComponentFactory factory = new JDateComponentFactory();
    JDatePicker datePicker = factory.createJDatePicker();
    centralPanel.add((Component) datePicker, gbc);

    JPanel footerPanel = new Footer("@imunnic");
    footerPanel.setPreferredSize(new Dimension(1, footerHeight));



    add(headerPanel, BorderLayout.NORTH);
    add(centralPanel, BorderLayout.CENTER);
    add(footerPanel, BorderLayout.SOUTH);

  }

  public void iniciarVistasReservas(){
    cargaProfesor();
    setSize(ancho,alto);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void cargaProfesor(){
    setProfesor(App.getApiDAO().getProfesor());
    texto.setText(getProfesor().toString());
  }

}
