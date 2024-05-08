package componentes;

import javax.swing.*;
import java.awt.*;

public class Footer extends JPanel {
  public Footer(String text) {
    JLabel label = new JLabel(text, SwingConstants.CENTER);
    label.setForeground(Color.WHITE);
    label.setFont(new Font("Arial", Font.ITALIC, 12));
    setBackground(new Color(30, 60, 90));
    add(label);
  }
}
