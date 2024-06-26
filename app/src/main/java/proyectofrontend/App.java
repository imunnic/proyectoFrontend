/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package proyectofrontend;

import DAO.ApiDAO;
import DAO.ReservaController;
import componentes.LoggedListener;
import entidades.*;
import vistas.VistaLogin;
import vistas.VistaReservas;


import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {
  private static String token;
  private static ApiDAO apiDAO = new ApiDAO();
  private static boolean logged;
  private static final LocalDate hoy = LocalDate.now();
  private static List<LoggedListener> loggedListeners = new ArrayList<>();
  private static List<Reserva> reservasApi = new ArrayList<>();
  private static Usuario usuario;
  private static List<Profesor> profesores;
  private static List<Asignatura> asignaturas;
  private static List<Grupo> grupos;
  private static List<Lugar> lugares;
  private static ReservaController reservaController = new ReservaController();

  public static Usuario getUsuario() {
    return usuario;
  }

  public static void setUsuario(Usuario usuario) {
    App.usuario = usuario;
  }

  public static List<Reserva> getReservasApi() {
    return reservasApi;
  }

  public static void setReservasApi(List<Reserva> reservasApi) {
    App.reservasApi = reservasApi;
  }

  public static LocalDate getHoy() {
    return hoy;
  }

  public static ApiDAO getApiDAO() {
    return apiDAO;
  }

  public static List<Asignatura> getAsignaturas() {
    return asignaturas;
  }

  public static List<Grupo> getGrupos() {
    return grupos;
  }

  public static List<Lugar> getLugares() {
    return lugares;
  }

  public static List<Profesor> getProfesores() {
    return profesores;
  }

  public static ReservaController getReservaController() {
    return reservaController;
  }

  public static void main(String[] args) {
    App.asignaturas = getApiDAO().getAsignaturas();
    App.profesores = getApiDAO().getProfesores();
    App.grupos = getApiDAO().getGrupos();
    App.lugares = getApiDAO().getLugares();
    VistaLogin login = new VistaLogin();
    VistaReservas reservas = new VistaReservas();

    addLoggedListener(new LoggedListener() {
      @Override
      public void onLoggedChanged(boolean logged) {
        if (logged) {
          setUsuario(App.getApiDAO().getUsuario());
          if (usuario.getRol().equals("PROFESOR")){
          login.setVisible(false);
          reservas.iniciarVistasReservas();
          } else {
            login.setVisible(false);
            //TODO continuar con vista gestor
            JOptionPane.showMessageDialog(null,"gestor");
          }
        } else {
          reservas.setVisible(false);
          login.setVisible(true);
        }
      }
    });

    login.autoclick();

  }

  public static void loggear(String token) {
    App.token = token;
    logged = true;
    notifyLoggedListeners(logged);

  }

  public static void addLoggedListener(LoggedListener listener) {
    loggedListeners.add(listener);
  }

  private static void notifyLoggedListeners(boolean logged) {
    for (LoggedListener listener : loggedListeners) {
      listener.onLoggedChanged(logged);
    }
  }

}
