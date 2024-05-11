package componentes;

import javax.swing.*;
import java.awt.*;

public class Header extends JPanel {
  public Header(String text) {
    JLabel label = new JLabel(text, SwingConstants.CENTER);
    label.setForeground(Color.WHITE);
    label.setFont(new Font("Arial", Font.BOLD, 18));
    setBackground(new Color(30, 60, 90));
    add(label, BorderLayout.CENTER);
  }
}
