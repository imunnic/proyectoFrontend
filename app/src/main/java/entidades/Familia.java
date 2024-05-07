package entidades;

import java.util.List;

public class Familia {
  private Long id;
  private List<Persona> familiares;

  public void setFamiliares(List<Persona> familiares) {
    this.familiares = familiares;
  }

  public List<Persona> getFamiliares() {
    return familiares;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public Familia() {}

  public void addFamiliar(Persona persona) {
    getFamiliares().add(persona);
  }

}
