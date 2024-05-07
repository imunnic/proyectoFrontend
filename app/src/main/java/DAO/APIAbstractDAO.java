package DAO;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entidades.MixIns;
import entidades.Persona;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class APIAbstractDAO<T> {
  private ObjectMapper mapper;

  private String getApiUrl() {
    return "https://micolegio-c6e07df12596.herokuapp.com/api";
  }


  private ObjectMapper getMapper() {
    return mapper;
  }

  public APIAbstractDAO(){
    super();
    mapper = new ObjectMapper();
    getMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    getMapper().addMixIn(Persona.class, MixIns.Personas.class);
  }
  public List<T> getEntidades(Class<T> tipo, String path) {
    List<T> entidades = new ArrayList<T>();
    try {
      URL url = new URL(getApiUrl() + path);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setConnectTimeout(5000);
      con.setReadTimeout(5000);

      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

      JsonNode nodeElementos = getMapper().readTree(in).findValue(path);
      for (JsonNode nodo : nodeElementos) {
        T entidad = getMapper().readValue(nodo.traverse(), tipo);
        entidades.add(entidad);
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return entidades;
  }
}
