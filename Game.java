package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.util.ArrayList;

public class Game extends JPanel {

    private int[][] gameField;
    private ArrayList<Ship> Ships;
    private MainWindow1 mv;
    private int numberOfShips[];

    private String IPAdressOfOpponent;
    private boolean IPdetected;
    private boolean sideIPdetected;
    private String sideIPAdress = null;

    private boolean battleCondition;
    private boolean localConnetion;


    public int countCellSize() {                                                                                         //установка размеров клеток и поля в целом работает в отношении с полем
        int cellSize;
        if (mv.getWidth() <= mv.getHeight()) {
            cellSize = (int) (mv.getWidth() * 2.2) / 13;
            cellSize = (mv.getWidth() - 2 * cellSize) / 11;
        } else {
            cellSize = (int) (mv.getHeight() * 2.2) / 13;
            cellSize = (mv.getHeight() - 2 * cellSize) / 11;
        }
        return cellSize;
    }

    public Game(MainWindow1 mv) {                                                                                       //установка для окна листенера для расстоновки кораблей (добавить кнопки через листенеры)
        battleCondition = false;
        localConnetion = false;
//        mv.addMouseListener(ListenerFactory.createMouseListener((ListenerFactory.MOUSE_LISTENER_WHILE_FIGHTNG), this)); //убрать и перенести в листенер

//        battleCondition = true;
        numberOfShips = new int[]{0, 4, 3, 2, 1};
        gameField = new int[10][10];
        IPdetected = false;
        Ships = new ArrayList<Ship>(10);
        this.mv = mv;

        mv.addMouseListener(ListenerFactory.createMouseListener((ListenerFactory.MOUSE_LISTENER_WHILE_PLACING), this));
        mv.addMouseListener(ListenerFactory.createMouseListener((ListenerFactory.MOUSE_LISTENER_FOR_BUTTONS), this));

    }

    public void createShip(int x0, int y0, int x1, int y1, boolean isHorizontal) {                                      //запихивание информации о корабле в корбаль, а также в поле и колличество кораблей
        if(battleCondition)
            return;
        int len = Math.max(Math.abs(x1 - x0) + 1, Math.abs(y1 - y0) + 1);
        if (len > 4) {                                                                                                  //считает палубы до 4 (выводить на экран как ошибку)
            System.out.println("na ekran");
            return;
        }
        if (!(numberOfShips[len] > 0))                                                                                  //кораблей такого вида очень много
            return;

        if (isHorizontal) {                                                                                             //(может можно убрать) разная отрисовка горизонтальных и вертикальных кораблей
            int ix1 = Math.max(x0, x1), ix0 = Math.min(x0, x1);
            int iy1 = Math.max(y0, y1), iy0 = Math.min(y0, y1);
            for (int i = ix0; i <= ix1; i++)                                                                            //проверка на возможность поставить
                if (gameField[iy0][i] == 1 || gameField[iy0][i] == 2) {
                    return;
                }

            for (int i = ix0 - 1; i <= ix1 + 1; i++)                                                                    //запрет на постановку кораблей около данного
                for (int j = iy0 - 1; j <= iy1 + 1; j++) {
                    try {
                        if (ix0 <= i && i <= ix1 && iy0 <= j && iy1 >= j)
                            gameField[j][i] = 1;
                        else
                            gameField[j][i] = 2;
                    } catch (IndexOutOfBoundsException e) {
                        ;
                    }
                }
        } else {
            int ix1 = Math.max(x0, x1), ix0 = Math.min(x0, x1);
            int iy1 = Math.max(y0, y1), iy0 = Math.min(y0, y1);

            for (int i = iy0; i <= iy1; i++)
                if (gameField[i][ix0] == 1 || gameField[i][ix0] == 2) {
                    return;
                }
            for (int i = iy0 - 1; i <= iy1 + 1; i++)
                for (int j = ix0 - 1; j <= ix1 + 1; j++) {
                    try {
                        if (iy0 <= i && iy1 >= i && ix0 <= j && ix1 >= j)
                            gameField[i][j] = 1;
                        else
                            gameField[i][j] = 2;
                    } catch (IndexOutOfBoundsException e) {
                        ;
                    }
                }
        }

        numberOfShips[len] -= 1;                                                                                        //фактическое создание корабля и отрисовка
        Ship s = new Ship(x0, y0, x1, y1, isHorizontal);
        Ships.add(s);
        repaint();

//        for (int i = 0; i < 10; i++) {                                                                                  //игровое поле
//            for (int j = 0; j < 10; j++)
//                System.out.print(gameField[i][j] + " ");
//            System.out.println();
//        }
//        System.out.println();
//        System.out.println();
//        System.out.println();

        if(!IPdetected)
            sendMessageToDetectOpponent();
    }

    public void shooting(int x, int y){
        if(!battleCondition)
            return;
//        for(int i = 0;i < Ships.size();i++) {

//            int x0 = Math.min(Ships.get(i).getX0(), Ships.get(i).getX1());
//            int x1 = Math.max(Ships.get(i).getX0(), Ships.get(i).getX1());
//            int y0 = Math.min(Ships.get(i).getY0(), Ships.get(i).getY1());
//            int y1 = Math.max(Ships.get(i).getY0(), Ships.get(i).getY1());
//
//            if (x0 <= x && x <= x1 && y0 <= y && y1 >= y) {
        boolean tempfl = false;
        for(int i = 0; i < 10;i++) {
            for (int j = 0; j < 10; j++)
                if (gameField[y][x] == 1) {
                    gameField[y][x] = -1;
                    System.out.println("HURT");
                    tempfl = true;
                    break;
                } else {
                    if(!(gameField[y][x] == -1)) {
                        System.out.println("NOT HURT");
                        gameField[y][x] = -2;
                    }
                    tempfl = true;
                    break;
                }
            if(tempfl)
                break;
        }
        System.out.println(x + " " +y);
//        for (int i = 0; i < 10; i++) {                                                                                  //игровое поле
//            for (int j = 0; j < 10; j++)
//                System.out.print(gameField[i][j] + " ");
//            System.out.println();
//        }
        System.out.println();
        System.out.println();
        System.out.println();
        repaint();
    }



    public int[] createButtonsForPlacingResetAndAccept() {
        int[] t = {0, 0, 0, 0, 0};
        int cellSize = countCellSize();
        int x1 = (int) (cellSize * 11 + cellSize * 1.66), y1 = (int) (cellSize * 11 + cellSize);
        int y2 = y1, x2 = (int) (cellSize * 11 + cellSize * 2.32);
        t[0] = x1;
        t[1] = y1;
        t[2] = x2;
        t[3] = y2;
        t[4] = (int) (cellSize * 0.5);
        return t;
    }
    public void sendMessageToDetectOpponent() {
        if(battleCondition)
            return;
//
//            byte data[] = s.getBytes();
        MyThread t = new MyThread(this);
        MyThread2 t2 = new MyThread2(this);
        if(!IPdetected && !sideIPdetected) {
            t.start();
            t2.start();
        }
    }


    public void paint(Graphics g) {                                                                                     //общая отрисовка
        System.out.println(IPdetected + " "+localConnetion);
        if(IPdetected && !localConnetion) {
            System.out.println("OPA");
            localConnetion = true;
            MyThread t = new MyThread(this);
            t.start();
        }
        System.out.println(getIPAdressOfOpponent() + " Это айпи оппонента");
        super.paint(g);
        drawField(g);
        int[] temp = createButtonsForPlacingResetAndAccept();
        paintButtons(g, temp[0], temp[1], temp[2], temp[3], temp[4]);
        paintShips(g);
        if(getBattleCondition()) {                                                                                    //УБРАТЬ КОммеНТарИИ
            System.out.println("Delete");
            ListenerFactory.deleteMouseListener(ListenerFactory.MOUSE_LISTENER_FOR_BUTTONS, this);
            ListenerFactory.deleteMouseListener(ListenerFactory.MOUSE_LISTENER_WHILE_PLACING, this);
        }
        if(battleCondition)
            paintHoles(g);


//            try{mv.removeMouseListener()}

    }

    public void drawField(Graphics g) {                                                                                 //отрисовка поля (пока линии, потом ещё кнопки с акшон листенерами)
        int cellSize = countCellSize();
        g.setColor(Color.BLACK);
        for (int i = 0; i < 2; i++)
            for (int j = 0; j <= 10; j++) {
                if (i == 0) {
                    g.drawLine(cellSize + j * cellSize, cellSize, cellSize + j * cellSize, cellSize * 11);
                } else
                    g.drawLine(cellSize, cellSize + j * cellSize, cellSize * 11, cellSize + j * cellSize);
            }
    }


    public void paintShips(Graphics g) {                                                                                   //отрисовка кораблей

        int cellSize = countCellSize();
        for (int i = 0; i < Ships.size(); i++) {
            int lenx = Ships.get(i).getX1() - Ships.get(i).getX0() + 1;
            int leny = Ships.get(i).getY1() - Ships.get(i).getY0() + 1;
            paintShip(g, Ships.get(i).getShipImg(), cellSize + Ships.get(i).getX0() * cellSize, cellSize + Ships.get(i).getY0() * cellSize,
                    lenx * cellSize, leny * cellSize, Ships.get(i).getIsHorizontal());
        }
//        System.out.println(IPAdressOfOpponent);
    }

    public void paintHoles(Graphics g) {
        int cellSize = countCellSize();
        Image hole = null;
        Image blow = null;
        try {
            hole = ImageIO.read(new File("C:\\Users\\User\\IdeaProjects\\SeaBattle\\src\\com\\company\\hole.png"));
            blow = ImageIO.read(new File("C:\\Users\\User\\IdeaProjects\\SeaBattle\\src\\com\\company\\blow.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++)
                if(gameField[i][j] == -2)
                    g.drawImage(hole, j*cellSize + cellSize, i*cellSize + cellSize, cellSize, cellSize, null);
                else if(gameField[i][j] == -1)
                    g.drawImage(blow, j*cellSize + cellSize, i*cellSize + cellSize, cellSize, cellSize, null);
    }

    public void paintShip(Graphics g, Image img, int x, int y, int width, int height, boolean isHorizontal) {           //Отрисовка и возможный переворто 1го корабля
        Graphics2D g2d = (Graphics2D) g;
        int cellSize = countCellSize();

        if (width <= 0) {                                                                                                  //Костыль для рисования в другую сторону
            width -= 2 * cellSize;
            x += cellSize;
        }
        if (height <= 0) {
            height -= 2 * cellSize;
            y += cellSize;
        }

        if (!isHorizontal) {
            double rotationRequired = Math.PI / 2;
            double locationX = img.getWidth(null) / 4.;
            double locationY = img.getHeight(null) / 4.;
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            g2d.drawImage(op.filter((BufferedImage) img, null), x, y, width, height, null);
        } else {
            g2d.drawImage(img, x, y, width, height, null);
        }
    }


    public void paintButtons(Graphics g, int x1, int y1, int x2, int y2, int width) {
        if(battleCondition)
            return;

        try {
            Image acceptpImg = ImageIO.read(new File("C:\\Users\\User\\IdeaProjects\\SesBattle\\src\\com\\company\\acceptPlacingButton.png"));
            Image resetImg = ImageIO.read(new File("C:\\Users\\User\\IdeaProjects\\SesBattle\\src\\com\\company\\resetForPlacingButton.png"));
            g.drawImage(acceptpImg, x1, y1, width, width, null);
            g.drawImage(resetImg, x2, y2, width, width, null);
        } catch (IOException e) {
            System.out.println("!!!!");
        }
    }















    public boolean getBattleCondition() {return battleCondition;}
    public void setBattleCondition(boolean battleCondition) {this.battleCondition = battleCondition;}
    public void setIPDetected(boolean IPdetected){this.IPdetected = IPdetected;}
    public boolean getIPDetected(){return IPdetected;}
    public void setIPAdressOfOpponent(String IPAdressOfOpponent){this.IPAdressOfOpponent = IPAdressOfOpponent;}
    public void resetGameField(){gameField = new int[10][10];}
    public void resetNumberOfShips(){numberOfShips = new int[]{0, 4, 3, 2, 1};}
    public void resetShips(){Ships = new ArrayList<Ship>(10);}
    public int[] getNumberOfShips(){return numberOfShips;}
    public String getIPAdressOfOpponent(){return IPAdressOfOpponent;}
    public MainWindow1 getMv(){return mv;}
    public String getGameField() {
        String s = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j != 9)
                    s += gameField[i][j] + " ";
                else
                    s += gameField[i][j];
            }
            if (i != 9)
                s += "\n";
        }
        return s;
    }

    public boolean getSideIPdetected() {
        return sideIPdetected;
    }

    public void setSideIPdetected(boolean sideIPdetected) {
        this.sideIPdetected = sideIPdetected;
    }

    public String getSideIPAdress() {
        return sideIPAdress;
    }
    public void setSideIPAdress(String setIPAddress) {
        this.sideIPAdress = setIPAddress;
    }
}
