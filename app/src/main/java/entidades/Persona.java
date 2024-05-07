package entidades;

public class Persona {
  private String nombre;
  private String apellido1;
  private String apellido2;
  private Familia familia;
  
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido1() {
    return apellido1;
  }

  public void setApellido1(String apellido1) {
    this.apellido1 = apellido1;
  }

  public String getApellido2() {
    return apellido2;
  }

  public void setApellido2(String apellido2) {
    this.apellido2 = apellido2;
  }

  public Familia getFamilia() {
    return familia;
  }

  public void setFamilia(Familia familia) {
    this.familia = familia;
  }

  public Persona() {
    
  }

  public Persona(String nombre, String apellido1, String apellido2) {
    this.nombre = nombre;
    this.apellido1 = apellido1;
    this.apellido2 = apellido2;
  }
  
  @Override
  public String toString() {
    String nombre = (getNombre() == null)? "":getNombre();
    String apellido1 = (getApellido1() == null)? "":getApellido1();
    String apellido2 = (getApellido2() == null)? "":getApellido2();
    return nombre + " " + apellido1+ " " + apellido2;
  }

}
