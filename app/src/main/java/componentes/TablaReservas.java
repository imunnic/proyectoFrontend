package componentes;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TablaReservas extends JPanel {
  public TablaReservas() {
    setLayout(new BorderLayout());

    // Crear datos de ejemplo para la tabla
    String[] diasSemana = {"", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
    String[] franjasHorarias = {"9:00-10:00", "10:00-11:00", "11:00-12:00", "12:00-13:00", "13:00-14:00", "14:00-15:00"};
    List<String>[][] datos = new List[franjasHorarias.length][diasSemana.length];
    String[] opcionAsignaturas = {"Matemáticas", "Física", "Química"};
    String[] opcionesGrupo = {"Grupo 1", "Grupo 2", "Grupo 3"};
    JCeldaSelectores celdas = new JCeldaSelectores(opcionAsignaturas, opcionesGrupo);

    // Inicializar los datos
    for (int i = 0; i < franjasHorarias.length; i++) {
      for (int j = 0; j < diasSemana.length; j++) {
        if (j == 0) {
          datos[i][j] = Arrays.asList(franjasHorarias[i], ""); // La primera columna con franjas horarias
        } else {
          datos[i][j] = Arrays.asList("", ""); // Otras columnas vacías
        }
      }
    }

    // Crear la tabla con los datos
    DefaultTableModel model = new DefaultTableModel(datos, diasSemana) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column != 0; // Permitir la edición de todas las celdas excepto la primera columna
      }

      @Override
      public Class<?> getColumnClass(int columnIndex) {
        return List.class;
      }
    };
    JTable tabla = new JTable(model);

    // Configurar el editor y el renderer
    tabla.setDefaultEditor(List.class, celdas);
    tabla.setDefaultRenderer(List.class, celdas);

    // Centrar el contenido de las celdas
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    tabla.setDefaultRenderer(Object.class, centerRenderer);

    // Configurar el renderer para la primera columna para mostrar solo las horas
    tabla.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel((String) ((List<?>) value).get(0));
        label.setHorizontalAlignment(JLabel.CENTER);
        if (isSelected) {
          label.setBackground(table.getSelectionBackground());
        } else {
          label.setBackground(table.getBackground());
        }
        return label;
      }
    });

    // Personalizar el alto de las filas
    int altoFila = 50; // Puedes ajustar este valor según tus preferencias
    tabla.setRowHeight(altoFila);

    // Evitar que el usuario reordene las columnas
    tabla.getTableHeader().setReorderingAllowed(false);

    // Agregar la tabla a un JScrollPane para poder desplazarla si es necesario
    JScrollPane scrollPane = new JScrollPane(tabla);
    add(scrollPane, BorderLayout.CENTER);
  }
}
