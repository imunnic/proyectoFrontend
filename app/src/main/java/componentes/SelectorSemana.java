package componentes;

import javax.swing.*;

public class SelectorSemana extends  JPanel{
  private JButton btnAnterior;
  private JButton btnSiguiente;

  public SelectorSemana(){
    btnAnterior = new JButton("Semana Anterior");
    btnSiguiente = new JButton("Semana Siguiente");
    add(btnAnterior);
    add(btnSiguiente);
  }
}
