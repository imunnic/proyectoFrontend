package DAO;

import entidades.Familia;

import java.util.List;

public class FamiliaDAO extends APIAbstractDAO<Familia> {
  public List<Familia> getFamilias(){
    return getEntidades(Familia.class, "familias");
  }
}
