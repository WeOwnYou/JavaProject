package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static java.lang.Math.abs;
import static java.lang.Math.floor;

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
            x0 = mouseEvent.getX() - 10;     //фикшу курсов
            y0 = mouseEvent.getY() - 30;
            mouseRel = false;
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            x1 = mouseEvent.getX() - 10;
            y1 = mouseEvent.getY() - 30;
            System.out.println(x1 + " " + y1);
            mouseRel = true;

            int cellSize = this.game.countCellSize();
            int x0 = (int)((float) ListenerFactory.ML.x0/(float) cellSize) - 1;
            int y0 = (int)((float) ListenerFactory.ML.y0/(float) cellSize) - 1;
            int x1 = (int)((float) ListenerFactory.ML.x1/(float) cellSize) - 1;
            int y1 = (int)((float) ListenerFactory.ML.y1/(float) cellSize) - 1;
            this.game.createShip(x0,y0,x1,y1);
            System.out.println(x0 + " " + y0 + " " + x1 + " " + y1);

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

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
