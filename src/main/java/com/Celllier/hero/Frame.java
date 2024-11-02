package com.Celllier.hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame{
    private int width = 800;
    private int length = 800;
    private JButton startButton;
    private GridPanel gridPanel;

    public Frame(){
        super("Game of life");
        gridPanel = new GridPanel();
        gridPanel.setLayout(null);
        startButton = createButton();
        gridPanel.add(startButton);

        gridPanel.setGridListener(new GridPanel.GridListener() {
            @Override
            public void gridReady() {
                gridPanel.addState(1, Color.CYAN);
                gridPanel.addState(0, Color.ORANGE);
            }

            @Override
            public void click(int x, int y, int button) {
                int state = gridPanel.getCell(x,y);
                if (state == 0)gridPanel.setCell(1, x, y);
                if (state == 1)gridPanel.setCell(0, x, y);
                gridPanel.update();
            }
        });


        setContentPane(gridPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, length);
        setResizable(false);

    }

    private JButton createButton(){
        JButton button = new JButton("start");
        button.setFocusable(false);
        button.setBounds(width/2 - 40, length- 70, 100, 20);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("button clicked");
                run();
            }
        });


        return button;
    }

    private void run(){
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Updating grid in timer");
                gridPanel.iterate();
                gridPanel.update();
            }
        });
        timer.start();

    }


}
