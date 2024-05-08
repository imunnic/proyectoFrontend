package vistas;

import componentes.Footer;
import componentes.Header;

import javax.swing.*;
import java.awt.*;

public class VistaReservas extends JFrame {
  private int ancho = 700, alto = 700;
  private int headerHeight = (int) (0.1 * alto);
  private int footerHeight = (int) (0.1 * alto);
  private int profesor;

  private void setProfesor(int profesor) {
    this.profesor = profesor;
  }

  private int getProfesor() {
    return profesor;
  }

  public VistaReservas() {

    JPanel headerPanel = new Header("MiColegio");
    headerPanel.setPreferredSize(new Dimension(1, headerHeight));

    JPanel footerPanel = new Footer("@imunnic");
    footerPanel.setPreferredSize(new Dimension(1, footerHeight));

  }

  public void iniciarVistasReservas(int profesor){
    setProfesor(profesor);
    setSize(ancho,alto);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

}
