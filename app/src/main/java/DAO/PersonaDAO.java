package DAO;

import entidades.Persona;

import java.util.List;

public class PersonaDAO extends APIAbstractDAO<Persona> {
  public List<Persona> getPersonas() {
    return getEntidades(Persona.class, "personas");
  }
}
