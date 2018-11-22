package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Game extends JPanel implements Runnable {

    public Image img = null;
    private int gameField[][];
    private ArrayList<Ship> Ships;
    private boolean running;
    private MainWindow1 mv;
    private int numberOfShips[] = {0, 4 , 3, 2, 1};

    public int countCellSize() {                                                        //установка размеров клеток
        int cellSize = 0;
        if (mv.getWidth() <= mv.getHeight()) {
            cellSize = mv.getWidth() / 13;
            cellSize = (mv.getWidth() - 2 * cellSize) / 11;
        } else {
            cellSize = mv.getHeight() / 13;
            cellSize = (mv.getHeight() - 2 * cellSize) / 11;
        }
        return cellSize;
    }

    public Game(MainWindow1 mv) {

        gameField = new int[10][10];
        Ships = new ArrayList<Ship>(10);
        running = true;
        this.mv = mv;
        mv.addMouseListener(ListenerFactory.ML.getMouseListener((ListenerFactory.MOUSE_LISTENER),this));
        Thread t = new Thread(this);
        t.start();                                  // при отпускании возвращает клетки начала длинну и наклон (-1 - горизонталь, 1 - вертикаль)
//        run();                                    //без рана поле отрисовывается !!!!!!!!!!!!!!!
                                                     //выбрасывание экспшенов при клике не туда стедлать трай ктечем

    }
    public void run() {

//        createShip(0,0,1,1);
        while (running){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int t[] = ListenerFactory.ML.start_len_tiltOfShip(countCellSize());
            System.out.println(t[0] + " " + t[1] + " " + t[2] + " "+t[3]);
            if(numberOfShips[t[2]] > 0){
//                createShip(cellat[0]);
            }
        }
    }

    public void createShip(int x0, int y0, int x1, int y1) {                                        //запихивание информации о корабле в корбаль
        Ship s = new Ship(x0, y0, x1, y1);
        Ships.add(s);
        System.out.println(Ships.size());
//        repaint();
    }

    public void paint(Graphics g) {                                                             //общая отрисовка
        super.paint(g);
        drawField(g);
        try {
            paintShip(g);
        }catch (Exception e){
            ;
        }
    }

    public void drawField(Graphics g) {
        int cellSize = countCellSize();
        g.setColor(Color.BLACK);
        for (int i = 0; i < 2; i++)
            for (int j = 0; j <= 10; j++) {
                if (i == 0) {
                    g.drawLine(cellSize + j * cellSize, cellSize, cellSize + j * cellSize, cellSize * 11);
                }
                else
                    g.drawLine(cellSize, cellSize + j * cellSize, cellSize * 11, cellSize + j * cellSize);
            }
    }

    public void paintShip(Graphics g) {                                                     //отрисовка корабля (тупой ресайзабл) (горизонтальная)
        int cellSize = countCellSize();
        g.drawImage(Ships.get(0).getShipImg(), cellSize, cellSize, cellSize*2, cellSize,null);
    }
}
