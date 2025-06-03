package org.example;

import org.example.gui.GameSettingsDialog;
import org.example.gui.KniffelFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        GameSettingsDialog settings = new GameSettingsDialog(null);
        settings.setVisible(true);

        if (settings.isStartConfirmed()) {
            KniffelFrame frame = new KniffelFrame(
                    settings.getGameMode(),
                    settings.getPlayerCount()
            );
            frame.setVisible(true);
        }
    }
}