package entidades;

import java.util.List;

public class Asignatura {
  private int id;
  private String nombre;
  private List<Integer> lugares;
  private List<Integer> grupos;

  // Getters y Setters

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<Integer> getLugares() {
    return lugares;
  }

  public void setLugares(List<Integer> lugares) {
    this.lugares = lugares;
  }

  public List<Integer> getGrupos() {
    return grupos;
  }

  public void setGrupos(List<Integer> grupos) {
    this.grupos = grupos;
  }

  @Override
  public String toString() {
    return nombre; // Esto es necesario para mostrar el nombre en el JComboBox
  }
}
