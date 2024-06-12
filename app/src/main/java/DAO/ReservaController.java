package DAO;
import componentes.FormularioReserva;
import componentes.SelectorSemana;
import componentes.TablaReservas;
import entidades.Asignatura;
import entidades.Grupo;
import entidades.Lugar;
import entidades.Reserva;
import proyectofrontend.App;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservaController {
  private SelectorSemana selectorSemana;
  private TablaReservas tablaReservas;
  private FormularioReserva formularioReserva;
  private int grupoId;
  private int asignaturaId;
  private LocalDate fecha;
  private LocalDate inicioSemana;
  private int hora;

  public LocalDate getInicioSemana() {
    return inicioSemana;
  }

  public void setInicioSemana(LocalDate inicioSemana) {
    this.inicioSemana = inicioSemana;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public int getHora() {
    return hora;
  }

  public void setHora(int hora) {
    this.hora = hora;
  }

  public void setFormularioReserva(FormularioReserva formularioReserva) {
    this.formularioReserva = formularioReserva;
  }

  public void setSelectorSemana(SelectorSemana selectorSemana) {
    this.selectorSemana = selectorSemana;
  }

  public void setTablaReservas(TablaReservas tablaReservas) {
    this.tablaReservas = tablaReservas;
  }

  public void setFecha(int diaSemana) {
    DayOfWeek diaInicioSemana = selectorSemana.getInicioSemana().getDayOfWeek();
    int diferenciaDias = diaSemana - diaInicioSemana.getValue();
    LocalDate fechaCalculada = selectorSemana.getInicioSemana().plusDays(diferenciaDias);
    this.fecha = fechaCalculada;
  }

  public void setAsignaturaId(int asignaturaId) {
    this.asignaturaId = asignaturaId;
  }

  public int getAsignaturaId() {
    return asignaturaId;
  }

  public void setGrupoId(int grupoId) {
    this.grupoId = grupoId;
  }

  public int getGrupoId() {
    return grupoId;
  }

  public void obtenerIdGrupo() {
    String nombreGrupo = (String) formularioReserva.getSelectorGrupo().getSelectedItem();
    setGrupoId(App.getApiDAO().obtenerGrupoPorNombre(nombreGrupo).getId());
  }

  public void obtenerIdAsignatura() {
    String nombreAsignatura = (String) formularioReserva.getSelectorAsignatura().getSelectedItem();
    setAsignaturaId(App.getApiDAO().obtenerAsignaturaPorNombre(nombreAsignatura).getId());
  }

  public List<Reserva> reservasGrupo(int grupoId) {
    List<Reserva> reservasGrupo = new ArrayList<>();
    try {
      reservasGrupo = App.getApiDAO().obtenerReservasGrupo(grupoId,
          selectorSemana.getInicioSemana().minusDays(1), selectorSemana.getFinSemana() );
      repintar();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return reservasGrupo;
  }

  public void reservar(int franjaHoraria, int diaSemana){
    setFecha(diaSemana);
    setHora(franjaHoraria);
    obtenerIdAsignatura();
    obtenerIdGrupo();
    ReservaRequest request = new ReservaRequest(formularioReserva.getProfesorId(),getAsignaturaId()
        ,getGrupoId(),getFecha(),getHora());
    int lugarId = getLugar();
    if (lugarId > 0) {
    request.setLugar(App.getApiDAO().lugarHref(lugarId));
      try {
        App.getApiDAO().reserva(request);
        JOptionPane.showMessageDialog(null, "Reserva realizada");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      repintar();
    } else {
      JOptionPane.showMessageDialog(null,"No hay lugares disponibles");
    }
  }

  private int getLugar() {
    Asignatura asignatura = App.getApiDAO().obtenerAsignaturaPorId(getAsignaturaId());
    List<Integer> lugaresIds = asignatura.getLugares();
    List<Lugar> lugaresFiltradosYOrdenados = App.getLugares().stream()
        .filter(lugar -> lugaresIds.contains(lugar.getId()))
        .sorted((l1, l2) -> Integer.compare(l2.getCapacidad(), l1.getCapacidad()))
        .collect(Collectors.toList());
    for (Lugar lugar : lugaresFiltradosYOrdenados) {
      if (App.getApiDAO().isLugarDisponible(lugar.getId(), getFecha(), getHora())) {
        return lugar.getId();
      }
    }
    return 0;
  }

  public boolean esMiReserva(int franjaHoraria, int diaSemana) {
    return getReservaApp(franjaHoraria, diaSemana).getProfesor() == formularioReserva.getProfesorId();
  }
  public Reserva getReservaApp(int franjaHoraria, int diaSemana) {
    LocalDate fechaAux = selectorSemana.getInicioSemana().plusDays(diaSemana - 1);
    Reserva reservaAux = new Reserva();
    Reserva reserva;
    reservaAux.setFecha(fechaAux);
    reservaAux.setHora(franjaHoraria);
    reserva = App.getReservasApi().stream().filter(r -> {
      System.out.println(r);
      return r.equals(reservaAux);
    }).collect(Collectors.toList()).get(0);
    return reserva;
  }

  public void borrarReserva(int franjaHoraria, int diaSemana){
    LocalDate fecha = selectorSemana.getInicioSemana().plusDays(diaSemana - 1);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String fechaFormateda = fecha.format(formatter);
    App.getApiDAO().borrarReserva(fechaFormateda, franjaHoraria);
    repintar();
    JOptionPane.showMessageDialog(null, "Reserva borrada");
  }

  @Override
  public String toString() {
    return "Reserva{" +
        "fecha=" + fecha +
        ", hora=" + hora +
        '}';
  }

  public void repintar(){
    selectorSemana.cargarReservas();

    tablaReservas.pintarReservas();
  }

  public void repintar(int grupoId){
    selectorSemana.cargarReservas();
        quitarReservasExtra();
        App.getReservaController().reservasGrupo(grupoId).forEach(r -> {
          App.getReservasApi().add(r);
        });
    tablaReservas.pintarReservas();
  }

  public void quitarReservasExtra() {
    App.setReservasApi(App.getReservasApi().stream().filter(r -> {return r.getProfesor() == formularioReserva.getProfesorId();}).collect(
        Collectors.toList()));
  }
}
