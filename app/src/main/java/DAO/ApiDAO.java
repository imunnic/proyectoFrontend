package DAO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entidades.*;
import proyectofrontend.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ApiDAO {
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

  public ApiDAO() {
    token = "";
    mapper = new ObjectMapper();
    getMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    getMapper().registerModule(new JavaTimeModule());
    /*getMapper().addMixIn(Persona.class, MixIns.Personas.class);*/
  }

  public boolean login(String usuario, String password) {
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
      String credenciales =
          "{\"username\": \"" + usuario + "\", \"password\": \"" + password + "\"}";

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

  public Usuario getUsuario() {
    Usuario usuario = new Usuario();
    try {
      URL url = new URL(getApiUrl() + "/usuarios/search/findByUsername?username=" + getUsername());
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setConnectTimeout(5000);
      con.setReadTimeout(5000);
      con.setRequestProperty("Authorization", "Bearer " + token);

      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

      JsonNode nodeElementos = getMapper().readTree(in);
      usuario.setId(nodeElementos.findValue("profesor").asInt());
      usuario.setNombre(nodeElementos.findValue("nombre").asText());
      usuario.setApellido(nodeElementos.findValue("apellido").asText());
      usuario.setRol(nodeElementos.findValue("rol").asText());
      usuario.setUsername(nodeElementos.findValue("username").asText());
      usuario.setLink(nodeElementos.get("_links").get("self").get("href").asText());

      con.disconnect();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return usuario;
  }

//  public List<T> getEntidades(Class<T> tipo, String path) {
//    List<T> entidades = new ArrayList<T>();
//    try {
//      URL url = new URL(getApiUrl() + path);
//      HttpURLConnection con = (HttpURLConnection) url.openConnection();
//      con.setRequestMethod("GET");
//      con.setConnectTimeout(5000);
//      con.setReadTimeout(5000);
//      con.setRequestProperty("Authorization", "Bearer " + token);
//
//      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//      JsonNode nodeElementos = getMapper().readTree(in).findValue(path);
//      for (JsonNode nodo : nodeElementos) {
//        T entidad = getMapper().readValue(nodo.traverse(), tipo);
//        entidades.add(entidad);
//      }
//    } catch (IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//    return entidades;
//  }


  public List<Reserva> obtenerReservas(int profesor, LocalDate inicio, LocalDate fin)
      throws IOException, InterruptedException, URISyntaxException {
    List<Reserva> reservas = new ArrayList<>();

    // Construir la URL con los parámetros
    String url = String.format("%s/reservas/search/reservas-profesor-fecha?profesorId=%d&fechaInicio=%s&fechaFin=%s", getApiUrl(), profesor,
        inicio.toString(), fin.toString());

    URL enlace = new URL(url);
    try {
      HttpURLConnection con = (HttpURLConnection) enlace.openConnection();
      con.setRequestMethod("GET");
      con.setConnectTimeout(5000);
      con.setReadTimeout(5000);
      con.setRequestProperty("Authorization", "Bearer " + token);

      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      JsonNode nodeElementos = getMapper().readTree(in).findValue("reservas");
      if (nodeElementos == null) {
        reservas = null;
      } else {
        for (JsonNode nodo : nodeElementos) {
          Reserva reserva = getMapper().readValue(nodo.traverse(), Reserva.class);
          reservas.add(reserva);
        }
      }
      } catch(IOException e){
        e.printStackTrace();
      }
    return reservas;
  }

  public List<Asignatura> getAsignaturas() {
    //TODO poner un return
    try {
      return mapper.readValue(new File("src/main/resources/asignaturas.json"), new TypeReference<List<Asignatura>>() {});
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<Profesor> getProfesores() {
    //TODO poner un return
    try {
      return mapper.readValue(new File("src/main/resources/profesores.json"), new TypeReference<List<Profesor>>() {});
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<Grupo> getGrupos() {
    //TODO poner un return
    try {
      return mapper.readValue(new File("src/main/resources/grupos.json"), new TypeReference<List<Grupo>>() {});
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }


  public Asignatura obtenerAsignaturaPorId(int id) {
    //TODO poner un return
    for (Asignatura asignatura : App.getAsignaturas()) {
      if (asignatura.getId() == id) {
        return asignatura;
      }
    }
    return null;
  }

  public Grupo obtenerGrupoPorId(int id) {
    //TODO poner un return
    for (Grupo grupo : App.getGrupos()) {
      if (grupo.getId() == id) {
        return grupo;
      }
    }
    return null;
  }

  public Lugar obtenerLugarPorId(int id) {
    //TODO poner un return
    for (Lugar lugar : App.getLugares()) {
      if (lugar.getId() == id) {
        return lugar;
      }
    }
    return null;
  }

  public Asignatura obtenerAsignaturaPorNombre(String nombre) {
    //TODO poner un return
    for (Asignatura asignatura : App.getAsignaturas()) {
      if (asignatura.getNombre().equals(nombre)) {
        return asignatura;
      }
    }
    return null;
  }

  public Profesor obtenerProfesorPorId(int id) {
    //TODO poner un return
    for (Profesor profesor : App.getProfesores()) {
      if (profesor.getId() == id) {
        return profesor;
      }
    }
    return null;
  }


}
