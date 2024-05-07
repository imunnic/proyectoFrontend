package proyectofrontend;

import java.awt.Font;
import java.util.Arrays;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.esotericsoftware.tablelayout.swing.Table;
import entidades.Persona;

public class FormularioPersona extends JPanel {
  private Persona persona = new Persona();
  private final JLabel ETIQUETA = new JLabel("Nombre:");
  private final JLabel ETIQUETA1 = new JLabel("Apellido 1:");
  private final JLabel ETIQUETA2 = new JLabel("Apellido 2:");
  private Table tabla = new Table();
  private JTextField nombreText = new JTextField();
  private JTextField apellido1Text = new JTextField();
  private JTextField apellido2Text = new JTextField();
  private final JLabel LABEL = new JLabel("Valor: ");
  private JLabel label2 = new JLabel("");
  private JLabel nombreUsuario = new JLabel();

  public void setUsuario(Persona persona) {
    this.persona = persona;
  }

  public Persona getUsuario() {
    return persona;
  }

  public FormularioPersona() {
    getUsuario().setNombre("Introduzca su nombre");
    nombreText.setEnabled(true);
    tabla.addCell(ETIQUETA);
    tabla.addCell(nombreText).width(150);
    tabla.row();
    tabla.addCell(ETIQUETA1);
    tabla.addCell(apellido1Text).width(150);
    tabla.row();
    tabla.addCell(ETIQUETA2);
    tabla.addCell(apellido2Text).width(150);
    tabla.row();
    tabla.addCell(LABEL);
    tabla.addCell(label2);
    tabla.row();
    
    nombreText.addCaretListener(e -> actualizarNombre());
    apellido1Text.addCaretListener(e -> actualizarApellido1());
    apellido2Text.addCaretListener(e -> actualizarApellido2());
    // usuarioText.setText(getUsuario().getNombre());
    this.add(tabla);
    Arrays.asList(this.getComponents()).forEach(c -> c.setFont(new Font("Arial", Font.ITALIC, 12)));

  }

  private void actualizarNombre() {
    persona.setNombre(nombreText.getText());
    actualizarLabel();
  }
  
  private void actualizarApellido1() {
    persona.setApellido1(apellido1Text.getText());
    actualizarLabel();
  }
  
  private void actualizarApellido2() {
    persona.setApellido2(apellido2Text.getText());
    actualizarLabel();
  }
  
  private void actualizarLabel(){
    label2.setText(persona.toString());
  }


}
