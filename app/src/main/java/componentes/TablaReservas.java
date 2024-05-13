package componentes;

import es.lanyu.ui.swing.SimpleJTable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

public class TablaReservas extends JPanel {
  public TablaReservas() {
  setLayout(new BorderLayout());

  // Crear datos de ejemplo para la tabla
  String[] diasSemana = {"","Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
  String[] franjasHorarias = {"9:00 ", "10:00", "11:00", "12:00", "13:00", "14:00"};
  String[][] datos = new String[franjasHorarias.length][diasSemana.length];

  // Crear la tabla con los datos
  DefaultTableModel model = new DefaultTableModel(datos, diasSemana);
  JTable tabla = new JTable(model);

  // Centrar el contenido de las celdas
  DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
  centerRenderer.setHorizontalAlignment(JLabel.CENTER);
  tabla.setDefaultRenderer(Object.class, centerRenderer);

  // Llenar la tabla con las franjas horarias
  for (int i = 0; i < franjasHorarias.length; i++) {
    tabla.setValueAt(franjasHorarias[i], i, 0);
  }
  // Personalizar el alto de las filas
  int altoFila = 50; // Puedes ajustar este valor según tus preferencias
  for (int i = 0; i < tabla.getRowCount(); i++) {
    tabla.setRowHeight(i, altoFila);
  }

  Dimension tablePreferredSize = tabla.getPreferredSize();
  tabla.setPreferredScrollableViewportSize(new Dimension(tablePreferredSize.width, tablePreferredSize.height));


  // Agregar la tabla a un JScrollPane para poder desplazarla si es necesario
  JScrollPane scrollPane = new JScrollPane(tabla);
  add(scrollPane, BorderLayout.CENTER);

  }

}

