package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Game extends JPanel {

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
        mv.addMouseListener(ListenerFactory.ML.getMouseListener((ListenerFactory.MOUSE_LISTENER), this));

    }

    public void createShip(int x0, int y0, int x1, int y1) {                                        //запихивание информации о корабле в корбаль
        Ship s = new Ship(x0, y0, x1, y1);
        Ships.add(s);
        System.out.println(Ships.size() + " !");
        repaint();
    }

    public void paint(Graphics g) {                                                             //общая отрисовка
        super.paint(g);
        drawField(g);
        try {
            paintShips(g);
        }catch (Exception e){
            ;
        }
    }

    public void drawField(Graphics g) {
        int cellSize = countCellSize();
        System.out.println(cellSize);
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

    public void paintShips(Graphics g) {                                                     //отрисовка корабля (тупой ресайзабл) (горизонтальная)
        int cellSize = countCellSize();
//        Image shipImg = null;
//        try {
//            shipImg = ImageIO.read(new File("C:\\Users\\user\\IdeaProjects\\SeaBattle\\src\\com\\company\\ship.jpg")); //нормальный путь сделать
//        } catch (IOException e) {
//            System.out.println("JOKE");
//        }
//        g.drawImage(shipImg, cellSize, cellSize, cellSize*2,cellSize,null);
        for(int i = 0;i < Ships.size();i++){
            int lenx =  Ships.get(i).getX1() - Ships.get(i).getX0();
            int leny = Ships.get(i).getY1() - Ships.get(i).getY0();
            g.drawImage(Ships.get(i).getShipImg(), cellSize + Ships.get(i).getX0()*cellSize, cellSize + Ships.get(i).getY0()*cellSize,
                    lenx*cellSize, leny*cellSize,null);
        }
    }
}
