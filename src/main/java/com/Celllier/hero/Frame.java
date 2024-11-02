package com.Celllier.hero;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{
    private int width = 800;
    private int length = 800;

    public Frame(){
        super("Game of life");
        var gridPanel = new GridPanel();

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
}
