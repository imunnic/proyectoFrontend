package componentes;

import DAO.ReservaController;
import entidades.Reserva;
import proyectofrontend.App;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    tabla.getColumnModel().getColumn(0).setCellRenderer(celdasRender());

    configurarTabla();

    agregarMouseListener();

    JScrollPane scrollPane = new JScrollPane(tabla);
    add(scrollPane, BorderLayout.CENTER);
  }

  public void pintarReservas() {
    limpiarCeldas();
    if (App.getReservasApi() == null) {
      //trhow exception
    } else {
      for (Reserva reserva : App.getReservasApi()) {
        int column = reserva.getFecha().getDayOfWeek().getValue();
        int row = reserva.getHora() - 9;
        String texto =
            "<html> " + App.getApiDAO().obtenerGrupoPorId(reserva.getGrupo()).getNombre() +
            "<br>" + App.getApiDAO().obtenerAsignaturaPorId(reserva.getAsignatura()).getNombre() +
            "</html>";
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
          //datos[i][j] = Arrays.asList("", ""); // Otras columnas vacías si hiciera falta
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

  private void agregarMouseListener() {
    tabla.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int row = tabla.rowAtPoint(e.getPoint());
        int column = tabla.columnAtPoint(e.getPoint());
        if (row >= 0 && column > 0) {
          Object value = model.getValueAt(row, column);
          if (value == null || value.toString().trim().isEmpty()) {
            try {
              App.getReservaController().reservar(row + 9, column);
            } catch (Exception ex) {
              JOptionPane.showMessageDialog(null,"Esta franja horaria ya está ocupada para el grupo o lugar");
            }
          } else {
            int response = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea borrar la reserva?");
            if(response == JOptionPane.YES_OPTION){
              App.getReservaController().borrarReserva(row + 9, column);
            } else if (response == JOptionPane.NO_OPTION) {
              
            } else if (response == JOptionPane.CLOSED_OPTION) {
              
            }
          }
        }
      }
    });
  }


}
