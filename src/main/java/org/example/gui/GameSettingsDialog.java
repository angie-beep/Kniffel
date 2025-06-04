package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class GameSettingsDialog extends JDialog {
    private boolean startConfirmed = false;

    public GameSettingsDialog(JFrame parent) {
        super(parent, "Spieleinstellungen", true);
        setupUI();
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void setupUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Kniffel", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JTextArea infoText = new JTextArea(
                "Regel:\n" +
                        "3 Würfe pro Runde\n" +
                        "Würfel durch klicken festhalten\n" +
                        "Nach 3 Würfen Kategorie Wählen\n" +
                        "Ziel: Höchstpunktzahl erreichen\n\n" +
                        "Viel Spaß!"
        );
        infoText.setEditable(false);
        infoText.setFont(new Font("Arial", Font.PLAIN, 14));
        infoText.setBackground(mainPanel.getBackground());

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(15));
        infoPanel.add(infoText);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton startButton = createStartButton();
        buttonPanel.add(startButton);

        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JButton createStartButton() {
        JButton button = new JButton("Spiel starten");
        button.setPreferredSize(new Dimension(150, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(e -> {
            startConfirmed = true;
            dispose();
        });
        return button;
    }

    public boolean isStartConfirmed() {
        return startConfirmed;
    }
}