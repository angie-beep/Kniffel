package org.example.gui;

import org.example.gameLogik.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class KniffelFrame extends JFrame {
    private final GameController controller;
    private DicePanel dicePanel;
    private ScoreCardPanel scoreCardPanel;
    private JLabel statusLabel;

    public KniffelFrame(String playerName) {
        this.controller = new GameController(playerName);

        setTitle("Kniffel - Einzelspieler");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupUI();
        updateGameState();
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // Status Panel
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(statusLabel, BorderLayout.NORTH);

        // Dice Panel
        dicePanel = new DicePanel(controller.getDices(), controller);
        add(dicePanel, BorderLayout.CENTER);

        // Score Card Panel
        scoreCardPanel = new ScoreCardPanel(controller.getPlayer().getScoreCard());
        add(scoreCardPanel, BorderLayout.EAST);

        // Control Panel
        add(new ControlPanel(), BorderLayout.SOUTH);
    }

    private void updateGameState() {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Würfe übrig: " + (3 - controller.getRollCount()));
            dicePanel.updateDice();
            scoreCardPanel.updateScoreCard(controller.getPlayer().getScoreCard());

            if (controller.isGameOver()) {
                showGameResult();
            }
        });
    }

    private void showGameResult() {
        int totalScore = controller.getPlayer().getScoreCard().getTotalScore();
        String message = String.format(
                "<html><div style='text-align:center;'>" +
                        "<h2>Spiel beendet!</h2>" +
                        "<p>Ihre Gesamtpunktzahl: <b>%d</b></p>" +
                        "<p>%s</p></div></html>",
                totalScore,
                getResultMessage(totalScore)
        );

        JOptionPane.showMessageDialog(
                this,
                message,
                "Spielende",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private String getResultMessage(int score) {
        if (score > 250) return "Hervorragende Leistung!";
        if (score > 200) return "Gut gespielt!";
        if (score > 150) return "Solide Leistung";
        return "Üben Sie weiter!";
    }

    private class ControlPanel extends JPanel {
        private final JButton rollButton;
        private final JButton endTurnButton;

        public ControlPanel() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
            setBackground(new Color(220, 220, 220));

            rollButton = createButton("Würfeln", this::handleRoll);
            endTurnButton = createButton("Zug beenden", this::handleEndTurn);

            add(rollButton);
            add(endTurnButton);
        }

        private JButton createButton(String text, java.util.function.Consumer<ActionEvent> action) {
            JButton button = new JButton(text);
            button.setPreferredSize(new Dimension(150, 45));
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.addActionListener(action::accept);
            return button;
        }

        private void handleRoll(ActionEvent e) {
            if (controller.getGameState() == GameController.GameState.ROLLING) {
                controller.rollDice();
                updateGameState();
            }
        }

        private void handleEndTurn(ActionEvent e) {
            if (controller.getGameState() == GameController.GameState.SELECTING_CATEGORY) {
                ScoreCard.Category selected = scoreCardPanel.getSelectedCategory();
                if (selected != null) {
                    controller.selectCategory(selected);
                    updateGameState();
                } else {
                    JOptionPane.showMessageDialog(
                            KniffelFrame.this,
                            "Bitte wählen Sie eine Kategorie aus",
                            "Fehler",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        }
    }
}