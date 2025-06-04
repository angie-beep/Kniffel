package org.example.gui;

import org.example.gameLogik.ScoreCard;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class ScoreCardPanel extends JPanel {
    private final JTable scoreTable;
    private final ScoreCardTableModel tableModel;

    public ScoreCardPanel(ScoreCard scoreCard) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tableModel = new ScoreCardTableModel(scoreCard);
        scoreTable = new JTable(tableModel);
        customizeTable();

        add(new JScrollPane(scoreTable), BorderLayout.CENTER);
    }

    private void customizeTable() {
        scoreTable.setRowHeight(30);
        scoreTable.setFont(new Font("Arial", Font.PLAIN, 14));
        scoreTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        scoreTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        scoreTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        scoreTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void updateScoreCard(ScoreCard scoreCard) {
        tableModel.setScoreCard(scoreCard);
        tableModel.fireTableDataChanged();
    }

    public ScoreCard.Category getSelectedCategory() {
        int row = scoreTable.getSelectedRow();
        return row >= 0 ? tableModel.getCategoryAt(row) : null;
    }

    private static class ScoreCardTableModel extends AbstractTableModel {
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
            return switch (category) {
                case EINSER -> "Einser";
                case ZWEIER -> "Zweier";
                case DREIER -> "Dreier";
                case VIERER -> "Vierer";
                case FUENFER -> "Fünfer";
                case SECHSER -> "Sechser";
                case DREIERPASCH -> "Dreierpasch";
                case VIERERPASCH -> "Viererpasch";
                case FULL_HOUSE -> "Full House";
                case KLEINE_STRASSE -> "Kleine Straße";
                case GROSSE_STRASSE -> "Große Straße";
                case KNIFFEL -> "Kniffel";
                case CHANCE -> "Chance";
            };
        }
    }
}