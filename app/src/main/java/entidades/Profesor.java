package entidades;

import java.util.List;

public class Profesor {
    private int id;
    private String nombre;
    private String apellido;
    private String avatar;
    private List<Integer> asignaturas;

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

    public String getApellido() {
      return apellido;
    }

    public void setApellido(String apellido) {
      this.apellido = apellido;
    }

    public String getAvatar() {
      return avatar;
    }

    public void setAvatar(String avatar) {
      this.avatar = avatar;
    }

    public List<Integer> getAsignaturas() {
      return asignaturas;
    }

    public void setAsignaturas(List<Integer> asignaturas) {
      this.asignaturas = asignaturas;
    }

    @Override
    public String toString() {
      return getNombre() + " " + getApellido(); // Esto es necesario para mostrar el nombre en el JComboBox
    }
  }


