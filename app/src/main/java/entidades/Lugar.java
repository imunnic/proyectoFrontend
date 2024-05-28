package entidades;

public class Lugar {
  private int id;
  private String nombre;
  private int capacidad;
  private String tipo;

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

  public int getCapacidad() {
    return capacidad;
  }

  public void setCapacidad(int capacidad) {
    this.capacidad = capacidad;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  @Override
  public String toString() {
    return "Lugar{" +
        "id=" + id +
        ", nombre='" + nombre + '\'' +
        ", capacidad=" + capacidad +
        ", tipo='" + tipo + '\'' +
        '}';
  }
}
