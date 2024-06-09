package DAO;
import componentes.FormularioReserva;
import componentes.SelectorSemana;
import componentes.TablaReservas;
import entidades.Asignatura;
import entidades.Lugar;
import proyectofrontend.App;

import javax.swing.*;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
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

  public void reservar(int franjaHoraria, int diaSemana){
    setFecha(diaSemana);
    setHora(franjaHoraria);
    obtenerIdAsignatura();
    obtenerIdGrupo();
    ReservaRequest request = new ReservaRequest(formularioReserva.getProfesorId(),getAsignaturaId()
        ,getGrupoId(),getFecha(),getHora());
    int lugarId = getLugar();
    if (lugarId > 0) {
    request.setLugar(lugarId);
    System.out.println(request);
    App.getApiDAO().reserva(request);
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
      if (App.getApiDAO().isLugarDisponible(lugar.getId())) {
        return lugar.getId();
      }
    }
    return 0;
  }

  public void borrarReserva(int franjaHoraria, int diaSemana){
//TODO implementar borrado
    JOptionPane.showMessageDialog(null, "Reserva borrada");
  }

  @Override
  public String toString() {
    return "Reserva{" +
        "fecha=" + fecha +
        ", hora=" + hora +
        '}';
  }
}
