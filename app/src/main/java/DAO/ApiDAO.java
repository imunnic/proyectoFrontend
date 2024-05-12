package DAO;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entidades.Profesor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiDAO<T> {
  private ObjectMapper mapper;
  private String token;
  private String username;

  private String getApiUrl() {
    return "https://micolegio-c6e07df12596.herokuapp.com/api";
  }

  public String getToken() {
    return token;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }

  private ObjectMapper getMapper() {
    return mapper;
  }

  public ApiDAO(){
    token = "";
    mapper = new ObjectMapper();
    getMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    /*getMapper().addMixIn(Persona.class, MixIns.Personas.class);*/
  }

  public boolean login(String usuario, String password){
    try {
      URL url = new URL(getApiUrl() + "/auth/login");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      con.setConnectTimeout(5000);
      con.setReadTimeout(5000);
      con.setDoOutput(true);
      con.setDoInput(true);
      con.setRequestProperty("Content-Type", "application/json");
      con.setRequestProperty("Accept", "application/json");

      // Crear el cuerpo de la solicitud en formato JSON
      String credenciales = "{\"username\": \"" + usuario + "\", \"password\": \"" + password + "\"}";

      // Escribir el cuerpo de la solicitud en la conexión
      OutputStream os = con.getOutputStream();
      byte[] input = credenciales.toString().getBytes("utf-8");
      os.write(input, 0, input.length);

      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      JsonNode jsonNode = mapper.readTree(response.toString());
      token = jsonNode.get("token").asText();
      setUsername(jsonNode.get("username").asText());

      con.disconnect();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return (!token.equals(""));
  }

  public Profesor getProfesor(){
    Profesor profesor = new Profesor();
    try {
      URL url = new URL(getApiUrl() + "/usuarios/search/findByUsername?username=" + getUsername());
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setConnectTimeout(5000);
      con.setReadTimeout(5000);
      con.setRequestProperty("Authorization", "Bearer " + token);

      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

      JsonNode nodeElementos = getMapper().readTree(in);
      profesor.setId(nodeElementos.findValue("profesor").asInt());
      profesor.setNombre(nodeElementos.findValue("nombre").asText());
      profesor.setApellido(nodeElementos.findValue("apellido").asText());
      profesor.setRol(nodeElementos.findValue("rol").asText());
      profesor.setUsername(nodeElementos.findValue("username").asText());
      profesor.setLink(nodeElementos.get("_links").get("self").get("href").asText());

      con.disconnect();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return profesor;
  }

  public List<T> getEntidades(Class<T> tipo, String path) {
    List<T> entidades = new ArrayList<T>();
    try {
      URL url = new URL(getApiUrl() + path);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setConnectTimeout(5000);
      con.setReadTimeout(5000);
      con.setRequestProperty("Authorization", "Bearer " + token);

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
