package componentes;

import entidades.*;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import proyectofrontend.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FormularioReserva extends JPanel {
  private JDatePicker selectorFecha;
  private Profesor profesor;
  private JComboBox<String> selectorGrupo;
  private JComboBox<String> selectorFranajaHoraria;
  private JComboBox<String> selectorAsignatura;

  public JComboBox<String> getSelectorAsignatura() {
    return selectorAsignatura;
  }

  public JComboBox<String> getSelectorGrupo() {
    return selectorGrupo;
  }

  public void setProfesor(Profesor profesor) {
    this.profesor = profesor;
  }

  public FormularioReserva() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    JDateComponentFactory factory = new JDateComponentFactory();
    selectorFecha = factory.createJDatePicker();
    selectorGrupo = new JComboBox<>();
    selectorFranajaHoraria = new JComboBox<>();
    selectorAsignatura = new JComboBox<>();



    selectorAsignatura.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Asignatura asignaturaSeleccionada = App.getApiDAO().obtenerAsignaturaPorNombre(
            (selectorAsignatura.getSelectedItem()).toString());
        if (asignaturaSeleccionada != null) {
          actualizarGrupos(asignaturaSeleccionada);
        }
      }
    });
  }

  public void iniciarFormulario(Profesor profesor){
    setProfesor(profesor);
    JLabel fecha = new JLabel("Fecha");
    JLabel asignatura = new JLabel("Asignatura");
    JLabel grupo = new JLabel("Grupo");
    JLabel franja = new JLabel("Franja Horaria");
    add(fecha);
    add((Component) selectorFecha);
    add(asignatura);
    add(selectorAsignatura);
    iniciarSelectorAsignaturas();
    add(grupo);
    add(selectorGrupo);
    add(franja);
    add(selectorFranajaHoraria);
    llenarSelectorFranjaHoraria();
  }

  private void iniciarSelectorAsignaturas() {
    for (Integer asignatura : profesor.getAsignaturas()) {
      selectorAsignatura.addItem(App.getApiDAO().obtenerAsignaturaPorId(asignatura).getNombre());
    }
  }
  private void iniciarSelectorGrupo() {
    Asignatura asignatura = App.getApiDAO()
        .obtenerAsignaturaPorNombre(selectorAsignatura.getSelectedItem()
            .toString()
        );
    for (Integer grupo : asignatura.getGrupos()) {
      Grupo group = App.getApiDAO().obtenerGrupoPorId(grupo);
      selectorGrupo.addItem(group.getNombre());
    }
  }

  private void llenarSelectorFranjaHoraria() {
    // Horas de inicio y fin de la franja horaria
    int horaInicio = 9;
    int horaFin = 15;

    // Agregar franjas horarias al selector
    for (int i = horaInicio; i < horaFin; i++) {
      FranjaHoraria franja = new FranjaHoraria(i, i + 1);
      selectorFranajaHoraria.addItem(franja.toString());
    }
  }

  private void actualizarGrupos(Asignatura asignatura) {
    selectorGrupo.removeAllItems();
    for (Integer grupo : asignatura.getGrupos()) {
      Grupo group = App.getApiDAO().obtenerGrupoPorId(grupo);
      selectorGrupo.addItem(group.getNombre());
    }
  }

}
