package componentes;

import proyectofrontend.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class SelectorSemana extends JPanel {
  private JButton btnAnterior;
  private JButton btnSiguiente;
  private LocalDate inicioSemana;
  private LocalDate finSemana;
  private JLabel semanaActual;
  private DateTimeFormatter formatter;
  private TablaReservas tabla;

  public LocalDate getFinSemana() {
    return finSemana;
  }

  public void setFinSemana(LocalDate finSemana) {
    this.finSemana = finSemana;
  }

  public LocalDate getInicioSemana() {
    return inicioSemana;
  }

  public void setInicioSemana(LocalDate inicioSemana) {
    this.inicioSemana = inicioSemana;
  }

  public JLabel getSemanaActual() {
    return semanaActual;
  }

  public SelectorSemana(TablaReservas tabla) {
    this.tabla = tabla;
    formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    btnAnterior = new JButton("Semana Anterior");
    btnSiguiente = new JButton("Semana Siguiente");
    inicioSemana = App.getHoy().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    finSemana = App.getHoy().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    semanaActual = new JLabel();
    actualizarTextoSemana();
    btnAnterior.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        semanaAnterior();
      }
    });
    btnSiguiente.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        semanaSiguiente();
      }
    });
    add(btnAnterior);
    add(semanaActual);
    add(btnSiguiente);

  }

  private void semanaSiguiente() {
    setFinSemana(getFinSemana().plusWeeks(1));
    setInicioSemana(getInicioSemana().plusWeeks(1));
    actualizarTextoSemana();
    cargarReservas();
  }

  private void semanaAnterior() {
    setInicioSemana(getInicioSemana().minusWeeks(1));
    setFinSemana(getFinSemana().minusWeeks(1));
    actualizarTextoSemana();
    cargarReservas();
  }

  private void actualizarTextoSemana() {
    getSemanaActual().setText(inicioSemana.format(formatter) + " : " + finSemana.format(formatter));
  }

  public void cargarReservas(){
    try {
      App.setReservasApi(App.getApiDAO()
          .obtenerReservas(App.getUsuario().getId(), getInicioSemana(),
              getFinSemana()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
    tabla.pintarReservas();
  }
}
