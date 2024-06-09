package DAO;

import java.time.LocalDate;

public class ReservaRequest {
  private int profesor;
  private int asignatura;
  private int grupo;
  private int lugar;
  private LocalDate fecha;
  private int hora;

  // Constructor
  public ReservaRequest(int profesor, int asignatura, int grupo, LocalDate fecha, int hora) {
    this.profesor = profesor;
    this.asignatura = asignatura;
    this.grupo = grupo;
    this.lugar = lugar;
    this.fecha = fecha;
    this.hora = hora;
  }

  // Getters y Setters
  public int getProfesor() {
    return profesor;
  }

  public void setProfesor(int profesor) {
    this.profesor = profesor;
  }

  public int getAsignatura() {
    return asignatura;
  }

  public void setAsignatura(int asignatura) {
    this.asignatura = asignatura;
  }

  public int getGrupo() {
    return grupo;
  }

  public void setGrupo(int grupo) {
    this.grupo = grupo;
  }

  public int getLugar() {
    return lugar;
  }

  public void setLugar(int lugar) {
    this.lugar = lugar;
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

  @Override
  public String toString() {
    return "{" +
        "\"profesor\"=" + profesor +
        ", \"asignatura\"=" + asignatura +
        ", \"grupo\"=" + grupo +
        ", \"lugar\"=" + lugar +
        ", \"fecha\"=" + "\"" + fecha + "\"" +
        ", \"hora\"=" + hora +
        '}';
  }
}
