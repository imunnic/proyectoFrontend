package entidades;

public class FranjaHoraria {
  private int horaInicio;
  private int horaFin;

  public FranjaHoraria(int horaInicio, int horaFin) {
    this.horaInicio = horaInicio;
    this.horaFin = horaFin;
  }

  public int getHoraInicio() {
    return horaInicio;
  }

  public void setHoraInicio(int horaInicio) {
    this.horaInicio = horaInicio;
  }

  public int getHoraFin() {
    return horaFin;
  }

  public void setHoraFin(int horaFin) {
    this.horaFin = horaFin;
  }

  @Override
  public String toString() {
    return String.format("%02d:00-%02d:00", horaInicio, horaFin);
  }
}
