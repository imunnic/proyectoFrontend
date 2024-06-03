package DAO;
import componentes.FormularioReserva;
import componentes.SelectorSemana;
import componentes.TablaReservas;
import proyectofrontend.App;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class ReservaController {
  private SelectorSemana selectorSemana;
  private TablaReservas tablaReservas;
  private FormularioReserva formularioReserva;
  private int grupoId;
  private int asignaturaId;
  private LocalDate fecha;
  private int hora;

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

  public void obtenerIdGrupo(FormularioReserva formulario) {
    // Obtener el nombre del grupo seleccionado en el formulario
    String nombreGrupo = (String) formularioReserva.getSelectorGrupo().getSelectedItem();

    // Utilizar la API DAO para obtener el ID del grupo
    setGrupoId(App.getApiDAO().obtenerGrupoPorNombre(nombreGrupo).getId());
  }

  public void obtenerIdAsignatura(FormularioReserva formulario) {
    // Obtener el nombre de la asignatura seleccionada en el formulario
    String nombreAsignatura = (String) formulario.getSelectorAsignatura().getSelectedItem();

    // Utilizar la API DAO para obtener el ID de la asignatura
    setAsignaturaId(App.getApiDAO().obtenerAsignaturaPorNombre(nombreAsignatura).getId());
  }

  @Override
  public String toString() {
    return "Reserva{" +
        "fecha=" + fecha +
        ", hora=" + hora +
        '}';
  }
}
