package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game extends JPanel {

    private int gameField[][];
    private ArrayList<Ship> Ships;
    private MainWindow1 mv;
    private int numberOfShips[] = {0, 4 , 3, 2, 1};

    public int countCellSize() {                                                        //установка размеров клеток
        int cellSize;
        if (mv.getWidth() <= mv.getHeight()) {
            cellSize = (int)(mv.getWidth())/ 13;                        //сделать зависимость от процентов
            cellSize = (mv.getWidth() - 2 * cellSize) / 11;
        } else {
            cellSize = (int)(mv.getHeight()) / 13;
            cellSize = (mv.getHeight() - 2 * cellSize) / 11;
        }
        return cellSize;
    }

    public Game(MainWindow1 mv) {

        gameField = new int[10][10];
        Ships = new ArrayList<Ship>(10);
        this.mv = mv;
        mv.addMouseListener(ListenerFactory.ML.getMouseListener((ListenerFactory.MOUSE_LISTENER), this));

    }

    public void createShip(int x0, int y0, int x1, int y1, boolean isHorizpntal) {                                        //запихивание информации о корабле в корбаль
        boolean isAbleToPlace = true;
        boolean isAbleOfSq = true;
        int len = Math.max(Math.abs(x1-x0)+1,Math.abs(y1-y0)+1);
        if(len > 4){
            System.out.println("na ekran");
            return;
        }
        if(numberOfShips[len] > 0 ) {
            isAbleOfSq = true;
        }
        else{
            isAbleOfSq = false;
        }
        if(isHorizpntal) {
            for (int i = x0; i <= x1; i++)
                if (gameField[y0][i] == 1) {
                    isAbleToPlace = false;
                    break;
                }
            for (int i = x0 - 1; i <= x1 + 1 && isAbleOfSq && isAbleToPlace; i++)
                for (int j = y0 - 1; j <= y1 + 1; j++) {
                    try {
                        gameField[j][i] = 1;
                    } catch (IndexOutOfBoundsException e) {
                        ;
                    }
                }
        }else{
            for (int i = y0; i <= y1; i++)
                if (gameField[i][x0] == 1) {
                    isAbleToPlace = false;
                    break;
                }
            for (int i = y0 - 1; i <= y1 + 1 && isAbleOfSq && isAbleToPlace; i++)
                for (int j = x0 - 1; j <= x1 + 1; j++) {
                    try {
                        gameField[i][j] = 1;
                    } catch (IndexOutOfBoundsException e) {
                        ;
                    }
                }
        }

        if(isAbleOfSq && isAbleToPlace) {
            numberOfShips[len] -= 1;
            Ship s = new Ship(x0, y0, x1, y1, isHorizpntal);
            Ships.add(s);
            repaint();
        }
        if(isAbleToPlace ) {
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
    public void paintShip(Graphics g, Image img, int x, int y, int width, int height, boolean isHorizontal) {
        Graphics2D g2d = (Graphics2D) g;
        if (!isHorizontal) {
            double rotationRequired = Math.PI / 2;
            double locationX = img.getWidth(null)/4.;
            double locationY = img.getHeight(null)/4.;
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            g2d.drawImage(op.filter((BufferedImage) img, null), x, y, width, height, null);
        } else {
            g2d.drawImage(img, x, y, width, height, null);
        }
    }
}
