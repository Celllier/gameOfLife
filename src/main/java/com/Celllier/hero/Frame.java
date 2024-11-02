package com.Celllier.hero;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{
    private int width = 500;
    private int length = 500;

    public Frame(){
        super("Game of life");

        setContentPane(new GridPanel());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, length);
        setResizable(false);

    }
}
