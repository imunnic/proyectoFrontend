package componentes;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

class JCeldaSelectores extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
  private JPanel panel;
  private JComboBox<String> comboBox1;
  private JComboBox<String> comboBox2;

  public JCeldaSelectores(String[] options1, String[] options2) {
    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    comboBox1 = new JComboBox<>(options1);
    comboBox2 = new JComboBox<>(options2);

    setComboBoxHeight(comboBox1, 20);
    setComboBoxHeight(comboBox2, 20);

    panel.add(comboBox1);
    panel.add(comboBox2);
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
      int row, int column) {
    if (value instanceof List) {
      List<String> values = (List<String>) value;
      comboBox1.setSelectedItem(values.get(0));
      comboBox2.setSelectedItem(values.get(1));
    }
    return panel;
  }

  @Override
  public Object getCellEditorValue() {
    List<String> values = new ArrayList<>();
    values.add((String) comboBox1.getSelectedItem());
    values.add((String) comboBox2.getSelectedItem());
    return values;
  }

  @Override
  public boolean isCellEditable(EventObject e) {
    return true;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
    if (value instanceof List) {
      List<String> values = (List<String>) value;
      comboBox1.setSelectedItem(values.get(0));
      comboBox2.setSelectedItem(values.get(1));
    }
    if (isSelected) {
      panel.setBackground(table.getSelectionBackground());
    } else {
      panel.setBackground(table.getBackground());
    }
    return panel;
  }

  private void setComboBoxHeight(JComboBox<String> comboBox, int height) {
    Dimension preferredSize = comboBox.getPreferredSize();
    preferredSize.height = height;
    comboBox.setMaximumSize(preferredSize);
  }
}
