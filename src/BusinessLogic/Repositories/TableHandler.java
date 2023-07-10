package BusinessLogic.Repositories;

import BusinessLogic.Interfaces.TableHandlerInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Map;

public class TableHandler implements TableHandlerInterface {

    private JTable table;
    private DefaultTableModel tableModel;

    public TableHandler(JTable table, DefaultTableModel tableModel) {
        this.table = table;
        this.tableModel = tableModel;
        setupTable();
    }

    public void setupTable() {
        this.tableModel.addColumn("Word");
        this.tableModel.addColumn("Occurrences");
        this.table.setModel(tableModel);
    }

    public void fillTable(Map<String, Integer> data) {
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            Object[] rowData = {entry.getKey(), entry.getValue()};
            tableModel.addRow(rowData);
        }
        tableModel.fireTableDataChanged();
    }

    public void clearTable() {
        this.tableModel.setRowCount(0);
        this.tableModel.fireTableDataChanged();
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }
}
