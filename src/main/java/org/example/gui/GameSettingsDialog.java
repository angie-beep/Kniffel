package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSettingsDialog extends JDialog {
    private int selectedGameMode = 0; // 0=Einzelspieler, 1=Mehrspieler, 2=Gegen Computer
    private int playerCount = 1;
    private boolean startConfirmed = false;

    public GameSettingsDialog(JFrame parent) {
        super(parent, "Spieleinstellungen", true);
        setupUI();
        setSize(350, 250);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void setupUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


        JPanel modePanel = new JPanel();
        modePanel.setBorder(BorderFactory.createTitledBorder("Spielmodus wählen"));
        modePanel.setLayout(new BoxLayout(modePanel, BoxLayout.Y_AXIS));

        ButtonGroup modeGroup = new ButtonGroup();
        JRadioButton singlePlayerBtn = createModeRadioButton("Einzelspieler", true);
        JRadioButton multiPlayerBtn = createModeRadioButton("Mehrspieler", false);
        JRadioButton vsComputerBtn = createModeRadioButton("Gegen Computer", false);

        modeGroup.add(singlePlayerBtn);
        modeGroup.add(multiPlayerBtn);
        modeGroup.add(vsComputerBtn);

        modePanel.add(singlePlayerBtn);
        modePanel.add(multiPlayerBtn);
        modePanel.add(vsComputerBtn);


        JPanel playerPanel = new JPanel();
        playerPanel.setBorder(BorderFactory.createTitledBorder("Anzahl Spieler"));
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JSpinner playerSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 4, 1));
        playerSpinner.setPreferredSize(new Dimension(60, 30));
        playerPanel.add(playerSpinner);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton startButton = new JButton("Spiel starten");
        startButton.setPreferredSize(new Dimension(120, 35));
        startButton.addActionListener(e -> {
            selectedGameMode = singlePlayerBtn.isSelected() ? 0 :
                    multiPlayerBtn.isSelected() ? 1 : 2;
            playerCount = (Integer) playerSpinner.getValue();
            startConfirmed = true;
            dispose();
        });

        buttonPanel.add(startButton);


        mainPanel.add(modePanel, BorderLayout.NORTH);
        mainPanel.add(playerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);


        singlePlayerBtn.addActionListener(e -> playerSpinner.setValue(1));
        multiPlayerBtn.addActionListener(e -> playerSpinner.setValue(2));
        vsComputerBtn.addActionListener(e -> playerSpinner.setValue(2));
    }

    private JRadioButton createModeRadioButton(String text, boolean selected) {
        JRadioButton btn = new JRadioButton(text, selected);
        btn.setMargin(new Insets(5, 5, 5, 5));
        return btn;
    }

    public boolean isStartConfirmed() {
        return startConfirmed;
    }

    public int getGameMode() {
        return selectedGameMode;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}