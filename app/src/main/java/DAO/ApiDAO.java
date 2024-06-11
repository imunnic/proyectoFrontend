package DAO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entidades.*;
import proyectofrontend.App;

import javax.swing.plaf.FontUIResource;
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
import java.time.format.DateTimeFormatter;
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

  public void reserva(ReservaRequest reservaRequest) {
    try {
      String body = reservaRequest.toString();
      HttpClient cliente = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
                                       .uri(URI.create(getApiUrl()+"/reservas"))
                                       .POST(HttpRequest.BodyPublishers.ofString(body))
                                       .header("Authorization", "Bearer " + token)
                                       .build();
      HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());

      // Verificar el código de estado de la respuesta
      int statusCode = response.statusCode();
      if (statusCode != 201) {
        throw new RuntimeException("Error al hacer la reserva. Código de estado: " + statusCode);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }


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
      e.printStackTrace();
    }
    return usuario;
  }

  public List<Reserva> obtenerReservas(int profesor, LocalDate inicio, LocalDate fin)
      throws IOException, InterruptedException, URISyntaxException {
    List<Reserva> reservas = new ArrayList<>();
    String url = String.format(
        "%s/reservas/search/reservas-profesor-fecha?profesorId=%d&fechaInicio=" + "%s&fechaFin=%s",
        getApiUrl(), profesor, inicio.toString(), fin.toString());

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
        reservas = new ArrayList<>();
      } else {
        for (JsonNode nodo : nodeElementos) {
          Reserva reserva = getMapper().readValue(nodo.traverse(), Reserva.class);
          reservas.add(reserva);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return reservas;
  }

  public List<Reserva> obtenerReservasGrupo(int grupoId, LocalDate inicio, LocalDate fin)
      throws IOException, InterruptedException, URISyntaxException {
    List<Reserva> reservas = new ArrayList<>();

    String url = String.format(
        "%s/reservas/search/reservas-grupo-fecha?grupoId=%d&fechaInicio=" + "%s&fechaFin=%s",
        getApiUrl(), grupoId, inicio.toString(), fin.toString());

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
        reservas = new ArrayList<>();
      } else {
        for (JsonNode nodo : nodeElementos) {
          Reserva reserva = getMapper().readValue(nodo.traverse(), Reserva.class);
          reservas.add(reserva);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return reservas;
  }

  public List<Asignatura> getAsignaturas() {
    List<Asignatura> asignaturas = new ArrayList<>();
    try {
      asignaturas = mapper.readValue(new File("src/main/resources/asignaturas.json"),
          new TypeReference<List<Asignatura>>() {
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
    return asignaturas;
  }

  public List<Profesor> getProfesores() {
    List<Profesor> profesores = new ArrayList<>();
    try {
      profesores = mapper.readValue(new File("src/main/resources/profesores.json"),
          new TypeReference<List<Profesor>>() {
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
    return profesores;
  }

  public List<Grupo> getGrupos() {
    List<Grupo> grupos = new ArrayList<>();
    try {
      grupos = mapper.readValue(new File("src/main/resources/grupos.json"),
          new TypeReference<List<Grupo>>() {
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
    return grupos;
  }

  public List<Lugar> getLugares(){
    List<Lugar> lugares = new ArrayList<>();
    try {
      return mapper.readValue(new File("src/main/resources/lugares.json"),
          new TypeReference<List<Lugar>>() {
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Asignatura obtenerAsignaturaPorId(int id) {
    Asignatura resultado = null;
    for (Asignatura asignatura : App.getAsignaturas()) {
      if (asignatura.getId() == id) {
        resultado = asignatura;
        break;
      }
    }
    return resultado;
  }

  public Grupo obtenerGrupoPorId(int id) {
    Grupo resultado = null;
    for (Grupo grupo : App.getGrupos()) {
      if (grupo.getId() == id) {
        resultado = grupo;
        break;
      }
    }
    return resultado;
  }

  public Grupo obtenerGrupoPorNombre(String nombre) {
    Grupo resultado = null;
    for (Grupo grupo : App.getGrupos()) {
      if (grupo.getNombre().equals(nombre)) {
        resultado = grupo;
        break;
      }
    }
    return resultado;
  }

  public Lugar obtenerLugarPorId(int id) {
    Lugar resultado = null;
    for (Lugar lugar : App.getLugares()) {
      if (lugar.getId() == id) {
        resultado = lugar;
        break;
      }
    }
    return resultado;
  }

  public Asignatura obtenerAsignaturaPorNombre(String nombre) {
    Asignatura resultado = null;
    for (Asignatura asignatura : App.getAsignaturas()) {
      if (asignatura.getNombre().equals(nombre)) {
        resultado = asignatura;
        break;
      }
    }
    return resultado;
  }

  public Profesor obtenerProfesorPorId(int id) {
    Profesor resultado = null;
    for (Profesor profesor : App.getProfesores()) {
      if (profesor.getId() == id) {
        resultado = profesor;
        break;
      }
    }
    return resultado;
  }

  public String lugarHref(int id){
    return getApiUrl() + "/lugares/" + id;
  }

  private int buscarReserva(String fecha, int hora) {
    String busqueda = "?fecha=" + fecha + "&hora=" + hora;
    HttpClient cliente = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(getApiUrl() + "/reservas/search/findByFechaAndHora" + busqueda))
        .GET()
        .header("Authorization", "Bearer " + token)
        .build();
    try {
      HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(response.body());
        JsonNode reservas = jsonResponse.path("_embedded").path("reservas");
        if (reservas.isArray() && reservas.size() > 0) {
          JsonNode reserva = reservas.get(0);
          return reserva.path("identificacion").asInt();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1; // Indicador de que no se encontró reserva
  }

  public void borrarReserva(String fecha, int hora) {
    int idReserva = buscarReserva(fecha, hora);
    if (idReserva != -1) {
      HttpClient cliente = HttpClient.newHttpClient();
      HttpRequest requestBorrado = HttpRequest.newBuilder()
          .DELETE()
          .header("Authorization", "Bearer " + token)
          .uri(URI.create(getApiUrl() + "/reservas/" + idReserva))
          .build();
      try {
        cliente.send(requestBorrado, HttpResponse.BodyHandlers.ofString());
      } catch (IOException | InterruptedException e) {
        System.out.println("Error en borrado");
        throw new RuntimeException(e);
      }
    } else {
      System.out.println("No se encontró reserva para la fecha y hora especificadas.");
    }
  }


  public boolean isLugarDisponible(int id, LocalDate fecha, int hora) {
    boolean retorno = false;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String consulta = "/reservas/search/lugar-disponible?lugar=" +
        id +"&fecha=" +fecha.format(formatter) + "&hora=" + hora;
    HttpClient cliente = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest
        .newBuilder()
        .GET()
        .header("Authorization", "Bearer " + token)
        .uri(URI.create(getApiUrl()+consulta)).build();
    try {
      HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
      String responseBody = response.body();
      retorno = Boolean.parseBoolean(responseBody);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return retorno;
  }
}
