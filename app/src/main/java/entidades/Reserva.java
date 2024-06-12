package entidades;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Reserva {
  private Long id;
  private int profesor;
  private int asignatura;
  private int grupo;
  private int lugar;
  private LocalDate fecha;
  private int hora;

  @JsonProperty("identificacion")
  public Long getId() {
    return id;
  }
  @JsonProperty("identificacion")
  public void setId(Long id) {
    this.id = id;
  }

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

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public int getLugar() {
    return lugar;
  }

  public void setLugar(int lugar) {
    this.lugar = lugar;
  }

  public void setHora(int hora) {
    this.hora = hora;
  }

  public int getHora() {
    return hora;
  }

  public Long getIdentificacion() {
    return getId();
  }

  public Reserva() {
  }

  public Reserva(int profesor, int grupo, int asignatura, int lugar, LocalDate fecha, int hora) {
    setAsignatura(asignatura);
    setFecha(fecha);
    setGrupo(grupo);
    setLugar(lugar);
    setProfesor(profesor);
    setHora(hora);
  }

  @Override
  public String toString() {
    return "ID: " + getIdentificacion();
  }

  @Override
  public int hashCode() {
    return getFecha().hashCode() + getHora();
  }

  @Override
  public boolean equals(Object obj) {
    return hashCode() == obj.hashCode();
  }
}
