package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static java.lang.Math.abs;

public class ListenerFactory {

    public static final int MOUSE_LISTENER = 1;

    public static class ML implements MouseListener {

        private static boolean mouseRel = false;
        private static int x0, y0, x1, y1;
        private Game game;
//        private static int cellSize;


        public ML(Game game) {
            this.game = game;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            x0 = mouseEvent.getX();
            y0 = mouseEvent.getY();
            mouseRel = false;
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            x1 = mouseEvent.getX();
            y1 = mouseEvent.getY();
            System.out.println(x1 + " " +y1);
            mouseRel = true;
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }

        public static int[] start_len_tiltOfShip(int cellSize) {                                //расщет длинны(в клетках) и координат и наклона (не проебать проверку на выход за пределы, количество кораблей, )
            if (mouseRel) {
                boolean isStartFinded = false, isEndFinded = false;             //x0 и y0 > cellSize
                int res[] = new int[4];
//            System.out.println(2*cellSize+" "+2*cellSize);
//            System.out.println(x0+" " +y0);
                for (int i = 9; i >= 0; i--) {                                  //поиск клеток начала и конца
                    for (int j = 9; j >= 0; j--) {
                        if (cellSize + i * cellSize <= x0 && cellSize + j * cellSize <= y0 && !isStartFinded && x0<=cellSize+(i+1)*cellSize && y0<= cellSize + (j+1)*cellSize) {
//                            System.out.println(cellSize + " ! ");
//                            System.out.println(cellSize + i*cellSize + " " + x0);
//                            System.out.println(cellSize + j*cellSize + " " + y0);
                            res[0] = i;
                            res[1] = j;
                            isStartFinded = true;
                        }
                        if (cellSize + i * cellSize <= x1 && cellSize + j * cellSize <= y1  && !isEndFinded) {
                            x1 = i;
                            y1 = j;
                            isEndFinded = true;
                        }
//                    else {
//                        System.out.println("Я просрал проверку");
//                    }
                        if (isEndFinded && isStartFinded)
                            break;
                    }
                    if (isEndFinded && isStartFinded)
                        break;
                }
//            System.out.println("start: " + res[0] + " " + res[1]);
//            System.out.println("end: " + x1 + " " + y1);
                if(0 == x1 - res[0] && 0 == y1-res[1]){
                    res[2] = 0;
                    res[3] = 1;
                    return res;
                }else{
                    if (0 == x1 - res[0]) {                        //возврат длинны и наклона
                        res[2] = abs(y1 - res[0]);
                        res[3] = -1;
                        return res;
                    }else{
                        if(0 == y1-res[1]) {
                            res[2] = abs(x1 - res[0]);
                            res[3] = 1;
                            return res;
                        }
                    }
                }
            }
            int []t = {-1,-1,-1,-1};
            return t;
        }


        public static MouseListener getMouseListener(int type, Game game) {
            MouseListener result = null;
            switch (type) {
                case MOUSE_LISTENER:
                    result = new ML(game);
            }
            return result;
        }
    }
}
