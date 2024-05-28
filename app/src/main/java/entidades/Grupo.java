package entidades;

public class Grupo {
  private int id;
  private String nombre;
  private int cantidadAlumnos;

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

  public int getCantidadAlumnos() {
    return cantidadAlumnos;
  }

  public void setCantidadAlumnos(int cantidadAlumnos) {
    this.cantidadAlumnos = cantidadAlumnos;
  }

  @Override
  public String toString() {
    return "Grupo{" +
        "id=" + id +
        ", nombre='" + nombre + '\'' +
        ", cantidadAlumnos=" + cantidadAlumnos +
        '}';
  }
}
