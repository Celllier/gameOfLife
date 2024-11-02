package com.Celllier.hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GridPanel extends JPanel {

    public interface GridListener{
        void gridReady();
        void click(int x, int y, int button);
    }

    private GridListener gridListener;

    private static int CELL_SIZE = 30;

    private int gridWidth;
    private int gridHeight;
    private int leftMargin;
    private int topMargin;
    private Map<Integer, BufferedImage> statesMap = new HashMap<>();
    private Integer[][] states;

    public GridPanel(){
        setBackground(Color.DARK_GRAY.darker());
        addState(0, Color.ORANGE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int gridx = (e.getX() - leftMargin) / CELL_SIZE;
                int gridy = (e.getY() - topMargin) / CELL_SIZE;
                System.out.println("X:" + gridx +" Y:" + gridy);

                if(gridListener != null){
                    gridListener.click(gridx, gridy, e.getButton());
                }
                super.mouseClicked(e);
            }
        });
    }

    void setGridListener(GridListener gridListener){
        this.gridListener = gridListener;
    }

    public void update(){
        repaint();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();
        gridWidth = (width/CELL_SIZE) -1;
        gridHeight = (height/CELL_SIZE) -1;

        initCells(gridWidth, gridHeight);

        int xSpare = width - (gridWidth * CELL_SIZE);
        int ySpare = height - (gridHeight * CELL_SIZE);
        leftMargin = xSpare/2;
        topMargin = ySpare/2;

        g2d.setColor(Color.white);
        g2d.fillRect(leftMargin,topMargin,width - xSpare + 1 , height - ySpare + 1);

        g2d.setColor(Color.BLACK);
        for (int i =0; i < gridWidth; i++){
            for (int j =0; j< gridHeight; j++){
                int x = 1 + leftMargin + i*CELL_SIZE;
                int y = 1+ topMargin + j*CELL_SIZE;

                Integer state = states[j][i];
                BufferedImage bi = statesMap.get(state);

                g2d.drawImage(bi, x, y, null);
            }
        }
    }

    private void initCells(int gridWidth, int gridHeight){
        if (states != null){
            return;
        }
        states = new Integer[gridHeight][gridWidth];
        Arrays.stream(states).forEach(a -> Arrays.fill(a, 0));

        if (gridListener != null){
            gridListener.gridReady();
        }
    }

    public void addState(Integer state, Color background){
        BufferedImage bi = new BufferedImage(CELL_SIZE-1, CELL_SIZE-1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(background);
        g.fillRect(0,0, CELL_SIZE-1, CELL_SIZE-1);
        g.dispose();

        statesMap.put(state, bi);
    }

    public void setCell(int state, int x, int y){
        if (x < 0 || y <0 || x>= gridWidth || y>= gridHeight) return;
        states[y][x] = state;
    }

    public int getCell(int x, int y){
        if (x < 0 || y <0 || x>= gridWidth || y>= gridHeight) return -1;
        return states[y][x];
    }

    public int getAdjacent(int x, int y){
        int living =0;
        for(int i = x-1; i<= x+1; i++){
            for (int j = y-1; j<= y+1; j++){
                if(x ==i && y == j) continue;
                int state = getCell(i, j);
                if(state == 1) living++;
            }
        }
        return living;
    }


    public void iterate(){
        int [][] nextStates = new int[gridHeight][gridWidth];

        for (int i =0; i < gridWidth; i++){
            for (int j = 0; j< gridHeight; j++){
                int state = getCell(i, j);
                int adjLiving = getAdjacent(i, j);

                if(state == 0) {
                    if (adjLiving == 3) nextStates[j][i] = 1;
                    else nextStates[j][i] = 0;
                }
                else{  // is living
                            if(adjLiving < 2 || adjLiving > 3) nextStates[j][i] =0;
                            else nextStates[j][i]=1;
                        }
                    }
                }
        
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                states[j][i] = nextStates[j][i];
            }
        }
    }
}
