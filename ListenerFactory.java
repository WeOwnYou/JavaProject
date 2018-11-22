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
            System.out.println(x1 + " " + y1);
            mouseRel = true;
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }

        public static int[] start_len_tiltOfShip(int cellSize) {                                //расщет длинны(в клетках) и координат и наклона (не проебать проверку на выход за пределы, количество кораблей, )
//            System.out.println(mouseRel);

            if (mouseRel) {

                int res[] = new int[4];
                float x0 = (float) ListenerFactory.ML.x0/(float) cellSize;
                float y0 = (float) ListenerFactory.ML.y0/(float) cellSize;

//                res[1] = y0/cellSize;
                System.out.println(x0 + " " +y0);
                if (0 == x1 - res[0] && 0 == y1 - res[1]) {
                    res[2] = 0;
                    res[3] = 1;
                    return res;
                } else {
                    if (0 == x1 - res[0]) {                        //возврат длинны и наклона
                        res[2] = abs(y1 - res[0]);
                        res[3] = -1;
                        return res;
                    } else {
                        if (0 == y1 - res[1]) {
                            res[2] = abs(x1 - res[0]);
                            res[3] = 1;
                            return res;
                        }
                    }
                }
            }
            int[] t = {-1, -1, -1, -1};
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
