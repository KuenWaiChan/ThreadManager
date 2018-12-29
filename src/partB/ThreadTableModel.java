package partB;

import javax.swing.*;

public class ThreadTableModel extends JTable {
    ThreadTableModel(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
