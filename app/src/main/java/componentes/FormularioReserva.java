package componentes;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;

public class FormularioReserva extends JPanel {
  private JDatePicker selectorFecha;
  JComboBox<String> selectorGrupo;
  JComboBox<String> selectorFranajaHoraria;
  JComboBox<String> selectorAsignatura;

  public FormularioReserva(){
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    JDateComponentFactory factory = new JDateComponentFactory();
    selectorFecha = factory.createJDatePicker();
    selectorGrupo = new JComboBox<>();
    selectorFranajaHoraria = new JComboBox<>();
    selectorAsignatura = new JComboBox<>();
    JLabel fecha = new JLabel("Fecha");
    JLabel asignatura = new JLabel("Asignatura");
    JLabel grupo = new JLabel("Grupo");
    JLabel franja = new JLabel("Franja Horaria");
    add(fecha);
    add((Component) selectorFecha);
    add(grupo);
    add(selectorGrupo);
    add(asignatura);
    add(selectorAsignatura);
    add(franja);
    add(selectorFranajaHoraria);
  }
}
