package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Ship {

    private int x0, x1, y0, y1;
    private Image shipImg = null;                              //картинку найти

    public Ship (int x0, int y0, int x1, int y1){
        if (!((x0 == x1) || (y0==y1))){

        }
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
}
