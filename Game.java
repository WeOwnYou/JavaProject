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

    public void createShip(int x0, int y0, int x1, int y1, boolean isHorizpntal) {                                        //запихивание информации о корабле в корбаль
        boolean isAbleToPlace = true;                                                                                                                    //в теории можно убрать один из циклов
        if(isHorizpntal) {
            System.out.println(x0 + " " + y0 + " " + x1 + " " + y1);
            for (int i = x0 - 1; i <= x1 + 1; i++) {
                for (int j = y0 - 1; j <= y1 + 1; j++) {
                    if(i>=x0 && i<=x1 && j>=y0 && j<=y1)
                        if(gameField[j][i] == 1) {
                            isAbleToPlace = false;
                            break;
                        }
                    try {
                        gameField[j][i] = 1;
                    } catch (IndexOutOfBoundsException e) {
                        ;
                    }
                }
                if(!isAbleToPlace)
                    break;
            }
        }
        else {
            for (int i = y0 - 1; i <= y1 + 1; i++) {
                for (int j = x0 - 1; j <= x1 + 1; j++) {
                    if(j>=x0 && j<=x1 && i>=y0 && i<=y1 )
                        if(gameField[i][j] == 1) {
                            isAbleToPlace = false;
                            break;
                        }
                    try {
                        gameField[i][j] = 1;
                    } catch (IndexOutOfBoundsException e) {
                        ;;
                    }
                }
                if(!isAbleToPlace)
                    break;
            }
        }
        if(isAbleToPlace) {
            Ship s = new Ship(x0, y0, x1, y1, isHorizpntal);
            Ships.add(s);
            repaint();
        }
        else
            System.out.println("Dodik");                                                                        //na ekran
        if(isAbleToPlace) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++)
                    System.out.print(gameField[i][j] + " ");
                System.out.println();
            }
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }

    public void paint(Graphics g) {                                                             //общая отрисовка
        super.paint(g);
        drawField(g);
        paintShips(g);

        try {
            paintShips(g);
        }catch (Exception e){
            ;
        }
    }

    public void drawField(Graphics g) { int cellSize = countCellSize();
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
        for(int i = 0;i < Ships.size();i++){
            int lenx =  Ships.get(i).getX1() - Ships.get(i).getX0()+1;
            int leny = Ships.get(i).getY1() - Ships.get(i).getY0()+1;
            paintShip(g, Ships.get(i).getShipImg(), cellSize + Ships.get(i).getX0()*cellSize,  cellSize + Ships.get(i).getY0()*cellSize,
                    lenx*cellSize, leny*cellSize, Ships.get(i).getIsHorizontal());
//
//            g.drawImage(Ships.get(i).getShipImg(), cellSize + Ships.get(i).getX0()*cellSize,  cellSize + Ships.get(i).getY0()*cellSize,
//                    lenx*cellSize, leny*cellSize,null);
        }
    }
    public void paintShip(Graphics g, Image img, int x, int y, int width, int height, boolean isHorizontal){
        Graphics2D g2d=(Graphics2D)g;
//        System.out.println(isHorizontal);
        if(!isHorizontal)
            g2d.rotate(Math.PI/2);
        g2d.drawImage(img, x, y, width, height, null);
    }
}
