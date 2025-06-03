package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class DiceIcon implements Icon {
    private final int value;
    private final int size;

    public DiceIcon(int value, int size) {
        this.value = value;
        this.size = size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();


        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x, y, size, size, 20, 20);


        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x, y, size, size, 20, 20);


        g2d.setColor(Color.BLACK);
        int dotSize = size / 5;

        switch (value) {
            case 1:
                drawDot(g2d, x + size/2, y + size/2, dotSize);
                break;
            case 2:
                drawDot(g2d, x + size/4, y + size/4, dotSize);
                drawDot(g2d, x + 3*size/4, y + 3*size/4, dotSize);
                break;
            case 3:
                drawDot(g2d, x + size/4, y + size/4, dotSize);
                drawDot(g2d, x + size/2, y + size/2, dotSize);
                drawDot(g2d, x + 3*size/4, y + 3*size/4, dotSize);
                break;
            case 4:
                drawDot(g2d, x + size/4, y + size/4, dotSize);
                drawDot(g2d, x + 3*size/4, y + size/4, dotSize);
                drawDot(g2d, x + size/4, y + 3*size/4, dotSize);
                drawDot(g2d, x + 3*size/4, y + 3*size/4, dotSize);
                break;
            case 5:
                drawDot(g2d, x + size/4, y + size/4, dotSize);
                drawDot(g2d, x + 3*size/4, y + size/4, dotSize);
                drawDot(g2d, x + size/2, y + size/2, dotSize);
                drawDot(g2d, x + size/4, y + 3*size/4, dotSize);
                drawDot(g2d, x + 3*size/4, y + 3*size/4, dotSize);
                break;
            case 6:
                drawDot(g2d, x + size/4, y + size/4, dotSize);
                drawDot(g2d, x + size/4, y + size/2, dotSize);
                drawDot(g2d, x + size/4, y + 3*size/4, dotSize);
                drawDot(g2d, x + 3*size/4, y + size/4, dotSize);
                drawDot(g2d, x + 3*size/4, y + size/2, dotSize);
                drawDot(g2d, x + 3*size/4, y + 3*size/4, dotSize);
                break;
        }

        g2d.dispose();
    }

    private void drawDot(Graphics2D g, int x, int y, int size) {
        g.fillOval(x - size/2, y - size/2, size, size);
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}