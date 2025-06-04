package org.example;

import org.example.gui.GameSettingsDialog;
import org.example.gui.KniffelFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            // Startdialog anzeigen
            GameSettingsDialog settings = new GameSettingsDialog(null);
            settings.setVisible(true);

            if (settings.isStartConfirmed()) {
                // Namen abfragen
                String playerName = JOptionPane.showInputDialog(
                        null,
                        "Bitte geben Sie Ihren Namen ein:",
                        "Spielstart",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "Spielende Person"
                ).toString();

                // Hauptfenster starten
                new KniffelFrame(playerName != null ? playerName : "Spielende Person").setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}