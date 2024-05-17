package vistas;

import DAO.ApiDAO;
import com.esotericsoftware.tablelayout.swing.Table;
import componentes.*;
import entidades.Profesor;
import entidades.Reserva;
import es.lanyu.ui.swing.SimpleJTable;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import proyectofrontend.App;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class VistaReservas extends JFrame {
  private int ancho = 1000, alto = 1000;
  private int headerHeight = (int) (0.1 * alto);
  private int footerHeight = (int) (0.1 * alto);
  private List<Reserva> reservas;
  private TablaReservas tablaReservas;
  private SelectorSemana selectorSemana;

  public List<Reserva> getReservas() {
    return reservas;
  }

  public void setReservas(List<Reserva> reservas) {
    this.reservas = reservas;
  }

  public VistaReservas() {



    JPanel headerPanel = new Header("MiColegio");
    headerPanel.setPreferredSize(new Dimension(1, headerHeight));

    JPanel centralPanel = new JPanel();

    centralPanel.setLayout(new GridBagLayout());
    centralPanel.setMinimumSize(new Dimension(0, 800));
    GridBagConstraints configuracion = configurarGridBag();

    FormularioReserva formularioReserva = new FormularioReserva();
    centralPanel.add(formularioReserva, configuracion);

    tablaReservas = new TablaReservas();
    selectorSemana = new SelectorSemana(tablaReservas);


    configuracion.weightx = 0.8;
    configuracion.gridx = 1;
    JPanel calendario = new JPanel();
    calendario.setLayout(new BorderLayout());
    calendario.add(selectorSemana, BorderLayout.NORTH);
    calendario.add(tablaReservas, BorderLayout.CENTER);
    centralPanel.add(calendario, configuracion);


    JPanel footerPanel = new Footer("@imunnic");
    footerPanel.setPreferredSize(new Dimension(1, footerHeight));

    add(headerPanel, BorderLayout.NORTH);
    add(centralPanel, BorderLayout.CENTER);
    add(footerPanel, BorderLayout.SOUTH);

  }

  public void iniciarVistasReservas() {
    cargaProfesor();
    setSize(ancho, alto);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    selectorSemana.cargarReservas();
  }

  public void cargaProfesor() {
    App.setProfesor((App.getApiDAO().getProfesor()));
  }

  public GridBagConstraints configurarGridBag() {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.NORTH;
    gbc.weightx = 0.2;
    gbc.gridx = 0;
    gbc.gridy = 0;
    return gbc;
  }



}
