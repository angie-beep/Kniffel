package org.example.gui;

import org.example.gameLogik.Dice;
import org.example.gameLogik.GameController;
import org.example.gameLogik.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DicePanel extends JPanel {
    private final Dice[] dice;
    private final JLabel[] diceLabels;
    private final DiceIcon[] diceIcons;
    private final GameController controller;

    public DicePanel(Dice[] dice, GameController controller) {
        if (dice == null || dice.length != 5) {
            throw new IllegalArgumentException("Dice array must contain exactly 5 elements");
        }
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        this.dice = dice;
        this.controller = controller;
        this.diceLabels = new JLabel[5];
        this.diceIcons = new DiceIcon[6];

        initializeDiceIcons();
        setupPanel();
        updateDiceDisplay();
    }

    private void initializeDiceIcons() {
        for (int i = 0; i < 6; i++) {
            diceIcons[i] = new DiceIcon(i + 1, 80);
        }
    }

    private void setupPanel() {
        setLayout(new GridLayout(1, 5, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(240, 240, 240));

        for (int i = 0; i < 5; i++) {
            diceLabels[i] = createDiceLabel(i);
            add(diceLabels[i]);
        }
    }

    private JLabel createDiceLabel(int index) {
        JLabel label = new JLabel(diceIcons[0], JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setPreferredSize(new Dimension(80, 80));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.toggleDieHold(index);
                updateDiceDisplay();
            }
        });

        return label;
    }

    public void updateDiceDisplay() {
        Player currentPlayer = controller.getCurrentPlayer();

        for (int i = 0; i < 5; i++) {
            int value = dice[i].getValue() - 1;
            if (value < 0 || value >= 6) value = 0;

            diceLabels[i].setIcon(diceIcons[value]);

            if (currentPlayer.isDieHeld(i)) {
                diceLabels[i].setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            } else {
                diceLabels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        }
    }
}