package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public  class Ship {

    private int x0, x1, y0, y1, len;
    private Image shipImg = null;                              //картинку найти
    private boolean isHorizontal = true;

    public  Ship (int x0, int y0, int x1, int y1, boolean isHorizontal){               //isHorizontal
        this.isHorizontal = isHorizontal;
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        try {
            shipImg = ImageIO.read(new File("C:\\Users\\user\\IdeaProjects\\SeaBattle\\src\\com\\company\\ship.jpg")); //нормальный путь сделать
        } catch (IOException e) {
            System.out.println("JOKE");
        }
    }

    public Image getShipImg() {
        return shipImg;
    }

    public int getY0() {
        return y0;
    }

    public int getX0() {
        return x0;
    }

    public int getX1() {return x1;}

    public int getY1() {
        return y1;
    }

    public boolean getIsHorizontal() {return isHorizontal;}
//    public int get
}
