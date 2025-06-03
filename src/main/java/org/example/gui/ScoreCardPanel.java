package org.example.gui;

import org.example.gameLogik.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class ScoreCardPanel extends JPanel {
    private JTable scoreTable;
    private ScoreCardTableModel tableModel;

    public ScoreCardPanel(ScoreCard scoreCard) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 600));

        tableModel = new ScoreCardTableModel(scoreCard);
        scoreTable = new JTable(tableModel);
        scoreTable.setRowHeight(30);
        scoreTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        scoreTable.getColumnModel().getColumn(1).setPreferredWidth(80);

        add(new JScrollPane(scoreTable), BorderLayout.CENTER);
    }

    public void updateScoreCard(ScoreCard scoreCard) {
        tableModel.setScoreCard(scoreCard);
        tableModel.fireTableDataChanged();
    }

    public ScoreCard.Category getSelectedCategory() {
        int row = scoreTable.getSelectedRow();
        if (row >= 0) {
            return tableModel.getCategoryAt(row);
        }
        return null;
    }

    class ScoreCardTableModel extends AbstractTableModel {
        private ScoreCard scoreCard;
        private final String[] columnNames = {"Kategorie", "Punkte"};

        public ScoreCardTableModel(ScoreCard scoreCard) {
            this.scoreCard = scoreCard;
        }

        public void setScoreCard(ScoreCard scoreCard) {
            this.scoreCard = scoreCard;
        }

        @Override
        public int getRowCount() {
            return ScoreCard.Category.values().length;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ScoreCard.Category category = ScoreCard.Category.values()[rowIndex];
            switch (columnIndex) {
                case 0: return categoryToString(category);
                case 1: return scoreCard.isCategoryUsed(category) ?
                        scoreCard.getScore(category) : "";
                default: return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public ScoreCard.Category getCategoryAt(int row) {
            return ScoreCard.Category.values()[row];
        }

        private String categoryToString(ScoreCard.Category category) {
            String name = category.name().toLowerCase();
            return name.substring(0, 1).toUpperCase() + name.substring(1).replace("_", " ");
        }
    }
}