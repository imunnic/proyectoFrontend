package vistas;

import componentes.*;
import entidades.Profesor;
import entidades.Reserva;
import proyectofrontend.App;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VistaReservas extends JFrame {
  private int ancho = 1000, alto = 1000;
  private int headerHeight = (int) (0.1 * alto);
  private int footerHeight = (int) (0.1 * alto);
  private List<Reserva> reservas;
  private TablaReservas tablaReservas;
  private SelectorSemana selectorSemana;
  private FormularioReserva formularioReserva;

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

    formularioReserva = new FormularioReserva();
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

    App.getReservaController().setFormularioReserva(this.formularioReserva);
    App.getReservaController().setTablaReservas(this.tablaReservas);
    App.getReservaController().setSelectorSemana(this.selectorSemana);
  }

  public void iniciarVistasReservas() {
    cargaUsuario();
    setSize(ancho, alto);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    selectorSemana.cargarReservas();
    System.out.println(App.getReservasApi());
    App.getReservaController().setFormularioReserva(formularioReserva);
    App.getReservaController().setTablaReservas(tablaReservas);
    App.getReservaController().setSelectorSemana(selectorSemana);
  }

  public void cargaUsuario() {
    App.setUsuario((App.getApiDAO().getUsuario()));
    Profesor profesor = App.getApiDAO().obtenerProfesorPorId(App.getUsuario().getId());
    formularioReserva.iniciarFormulario(profesor);
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
