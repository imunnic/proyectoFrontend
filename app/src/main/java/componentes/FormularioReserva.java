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
import java.util.stream.Collectors;

public class FormularioReserva extends JPanel {
//  private JDatePicker selectorFecha;
  private Profesor profesor;
  private JComboBox<String> selectorGrupo;

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

  public int getProfesorId() {
    return profesor.getId();
  }

  public FormularioReserva() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//    JDateComponentFactory factory = new JDateComponentFactory();
//    selectorFecha = factory.createJDatePicker();
    selectorGrupo = new JComboBox<>();
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
//    add(fecha);
//    add((Component) selectorFecha);
    add(asignatura);
    add(selectorAsignatura);
    iniciarSelectorAsignaturas();
    add(grupo);
    add(selectorGrupo);

    selectorGrupo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Grupo grupoSeleccionado = App.getApiDAO().obtenerGrupoPorNombre(selectorGrupo.getSelectedItem().toString());
        if (grupoSeleccionado != null) {
          App.getReservaController().repintar(grupoSeleccionado.getId());
        }
      }
    });
  }

  private void iniciarSelectorAsignaturas() {
    for (Integer asignatura : profesor.getAsignaturas()) {
      selectorAsignatura.addItem(App.getApiDAO().obtenerAsignaturaPorId(asignatura).getNombre());
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
