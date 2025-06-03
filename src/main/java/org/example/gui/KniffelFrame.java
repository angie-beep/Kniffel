package org.example.gui;

import org.example.gameLogik.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class KniffelFrame extends JFrame {
    private final GameController controller;
    private  DicePanel dicePanel;
    private ScoreCardPanel scoreCardPanel;
    private  JLabel statusLabel;

    public KniffelFrame(int gameMode, int playerCount) {
        this.controller = new GameController(gameMode, playerCount);

        setTitle("Kniffel - " + getGameModeName(gameMode));
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupUI();
        updateGameState();
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));


        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(statusLabel, BorderLayout.NORTH);


        dicePanel = new DicePanel(controller.getDices(), controller);
        add(dicePanel, BorderLayout.CENTER);


        scoreCardPanel = new ScoreCardPanel(controller.getCurrentPlayer().getScoreCard());
        add(scoreCardPanel, BorderLayout.EAST);


        add(new ControlPanel(), BorderLayout.SOUTH);
    }

    private void updateGameState() {
        SwingUtilities.invokeLater(() -> {
            Player current = controller.getCurrentPlayer();
            statusLabel.setText(current.getName() + " ist am Zug - Würfe übrig: " + (3 - controller.getRollCount()));

            dicePanel.updateDiceDisplay();
            scoreCardPanel.updateScoreCard(current.getScoreCard());

            if (controller.isGameOver()) {
                showGameResult();
            }
        });
    }

    private void showGameResult() {
        Player winner = controller.getWinner();
        JOptionPane.showMessageDialog(this,
                "Spiel beendet!\nGewinner: " + winner.getName() +
                        "\nPunkte: " + winner.getScoreCard().getTotalScore(),
                "Spielende", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getGameModeName(int gameMode) {
        return switch (gameMode) {
            case 0 -> "Einzelspieler";
            case 1 -> "Mehrspieler";
            case 2 -> "Gegen Computer";
            default -> "Unbekannter Modus";
        };
    }

    private class ControlPanel extends JPanel {
        private final JButton rollButton;
        private final JButton endTurnButton;

        public ControlPanel() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            rollButton = createButton("Würfeln", this::handleRoll);
            endTurnButton = createButton("Zug beenden", this::handleEndTurn);

            add(rollButton);
            add(endTurnButton);
        }

        private JButton createButton(String text, java.util.function.Consumer<ActionEvent> action) {
            JButton button = new JButton(text);
            button.setPreferredSize(new Dimension(150, 40));
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.addActionListener(action::accept);
            return button;
        }

        private void handleRoll(ActionEvent e) {
            controller.rollDice();
            updateGameState();
        }

        private void handleEndTurn(ActionEvent e) {
            ScoreCard.Category selected = scoreCardPanel.getSelectedCategory();
            if (selected != null) {
                controller.selectCategory(selected);
                updateGameState();
            } else {
                JOptionPane.showMessageDialog(KniffelFrame.this,
                        "Bitte eine Kategorie auswählen!",
                        "Fehler", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}