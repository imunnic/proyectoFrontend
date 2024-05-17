package componentes;

import entidades.Reserva;
import proyectofrontend.App;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TablaReservas extends JPanel {
  private final String[] DIASSSEMANA = {"", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
  private final String[] FRANJASHORARIAS =
      {"9:00-10:00", "10:00-11:00", "11:00-12:00", "12:00-13:00", "13:00-14:00", "14:00-15:00"};
  private List<String>[][] datos;
  private JTable tabla;
  public DefaultTableModel model;
  public TablaReservas() {
    setLayout(new BorderLayout());
    datos = new List[FRANJASHORARIAS.length][DIASSSEMANA.length];

    inicializarDatos();
    model = modeloDeTabla();
    tabla = new JTable(model);
    //render de celdas de horario
    tabla.getColumnModel().getColumn(0).setCellRenderer(celdasRender());

    //configuracion detalles de tabla
    configurarTabla();

    JScrollPane scrollPane = new JScrollPane(tabla);
    add(scrollPane, BorderLayout.CENTER);
  }

  public void pintarReservas() {
    limpiarCeldas();
    if (App.getReservasApi() == null) {

    } else {
      for (Reserva reserva : App.getReservasApi()) {
        int column = reserva.getFecha().getDayOfWeek().getValue();
        int row = reserva.getHora() - 9;
        String texto =
            "<html>Grupo " + reserva.getGrupo() + "<br>Asignatura " + reserva.getAsignatura() + "</html>";
        model.setValueAt(texto, row, column);
      }
    }
  }

  private void inicializarDatos() {
    for (int i = 0; i < FRANJASHORARIAS.length; i++) {
      for (int j = 0; j < DIASSSEMANA.length; j++) {
        if (j == 0) {
          datos[i][j] = Arrays.asList(FRANJASHORARIAS[i], "");
        } else {
          //datos[i][j] = Arrays.asList("", ""); // Otras columnas vacías
        }
      }
    }
  }

  private void configurarTabla() {
    int altoFila = 50;
    tabla.setRowHeight(altoFila);
    tabla.getTableHeader().setReorderingAllowed(false);

    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    tabla.setDefaultRenderer(Object.class, centerRenderer);
  }

  private DefaultTableCellRenderer celdasRender() {
    return new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
          boolean hasFocus, int row, int column) {
        JLabel label = new JLabel((String) ((List<?>) value).get(0));
        label.setHorizontalAlignment(JLabel.CENTER);
        if (isSelected) {
          label.setBackground(table.getSelectionBackground());
        } else {
          label.setBackground(table.getBackground());
        }
        return label;
      }
    };
  }

  private DefaultTableModel modeloDeTabla() {
    return new DefaultTableModel(datos, DIASSSEMANA) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
  }

  private void limpiarCeldas() {
    for (int i = 0; i < model.getRowCount(); i++) {
      for (int j = 1; j < model.getColumnCount(); j++) {
        model.setValueAt("", i, j);
      }
    }
  }


}
