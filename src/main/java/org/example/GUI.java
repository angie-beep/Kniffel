package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {

    public GUI (){
        frame = new JFrame();

        JButton button = new JButton();
        button.addActionListener(this);
        button.setText("Click me!");

        label = new JLabel("Number of clicks: 0");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(label);
        panel.add(button);


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Kniffel :3");
        frame.pack();
        frame.setVisible(true);




    }
    public static void main(String[] args) {
        new GUI();
    }

    JLabel label;
    JFrame frame;
    JPanel panel;

    int count = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        label.setText("Number of clicks: " + count);
    }
}
