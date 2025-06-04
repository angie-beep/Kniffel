package org.example.gui;

import org.example.gameLogik.Dice;
import org.example.gameLogik.GameController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DicePanel extends JPanel {
    private final Dice[] dice;
    private final JLabel[] diceLabels;
    private final DiceIcon[] diceIcons;
    private final GameController controller;

    // Farben
    private static final Color HELD_BG = new Color(173, 216, 230); // Pastellblau
    private static final Color HELD_BORDER = new Color(70, 130, 180); // Dunkelblau
    private static final Color NORMAL_BG = Color.WHITE;
    private static final Color NORMAL_BORDER = Color.GRAY;
    private static final Color HOVER_BORDER = Color.ORANGE;

    // Größen
    private static final int SIZE = 80;
    private static final int BORDER_THICK = 3;

    public DicePanel(Dice[] dice, GameController controller) {
        this.dice = dice;
        this.controller = controller;
        this.diceLabels = new JLabel[5];
        this.diceIcons = new DiceIcon[6];

        setLayout(new GridLayout(1, 5, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(240, 240, 240));

        // Würfel-Icons initialisieren
        for (int i = 0; i < 6; i++) {
            diceIcons[i] = new DiceIcon(i + 1, SIZE);
        }

        // Würfel-Labels erstellen
        for (int i = 0; i < 5; i++) {
            diceLabels[i] = createDieLabel(i);
            add(diceLabels[i]);
        }
    }

    private JLabel createDieLabel(int index) {
        JLabel label = new JLabel(diceIcons[0], JLabel.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                // Hintergrund mit abgerundeten Ecken
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g2);
            }
        };

        label.setOpaque(true);
        label.setBackground(NORMAL_BG);
        label.setBorder(BorderFactory.createLineBorder(NORMAL_BORDER, 2));
        label.setPreferredSize(new Dimension(SIZE, SIZE));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller.getGameState() == GameController.GameState.ROLLING) {
                    controller.toggleDieHold(index);
                    updateDice();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!dice[index].isHeld()) {
                    label.setBorder(BorderFactory.createLineBorder(HOVER_BORDER, 2));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updateDice();
            }
        });

        return label;
    }

    public void updateDice() {
        for (int i = 0; i < 5; i++) {
            // Würfelwert aktualisieren
            int value = Math.max(0, Math.min(5, dice[i].getValue() - 1));
            diceLabels[i].setIcon(diceIcons[value]);

            // Visuelle Darstellung anpassen
            if (dice[i].isHeld()) {
                diceLabels[i].setBackground(HELD_BG);
                diceLabels[i].setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(HELD_BORDER, BORDER_THICK),
                        BorderFactory.createEmptyBorder(2, 2, 2, 2)
                ));
            } else {
                diceLabels[i].setBackground(NORMAL_BG);
                diceLabels[i].setBorder(BorderFactory.createLineBorder(NORMAL_BORDER, 2));
            }
        }
        repaint();
    }
}