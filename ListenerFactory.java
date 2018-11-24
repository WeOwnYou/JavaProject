package com.company;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ListenerFactory {

    public static final int MOUSE_LISTENER = 1;

    public static class ML implements MouseListener {

        private  static int x0, y0, x1, y1;
        private boolean isHorizontal;
        private Game game;


        public ML(Game game) {
            this.game = game;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            x0 = mouseEvent.getX() - 10;     //эксепты для неправльного ввода
            y0 = mouseEvent.getY() - 30;
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            x1 = mouseEvent.getX() - 10;
            y1 = mouseEvent.getY() - 30;
//            System.out.println(x1 + " " + y1);

            int cellSize = this.game.countCellSize();
            int x0 = (int) ((float) ListenerFactory.ML.x0 / (float) cellSize) - 1;
            int y0 = (int) ((float) ListenerFactory.ML.y0 / (float) cellSize) - 1;
            int x1 = (int) ((float) ListenerFactory.ML.x1 / (float) cellSize) - 1;
            int y1 = (int) ((float) ListenerFactory.ML.y1 / (float) cellSize) - 1;
            if (x1 - x0 == 0) {
                isHorizontal = false;
                this.game.createShip(x0, y0, x1, y1, isHorizontal);
            } else {
                if (y1 - y0 == 0) {
                    isHorizontal = true;
                    this.game.createShip(x0, y0, x1, y1, isHorizontal);
                }
//            System.out.println(x0 + " " + y0 + " " + x1 + " " + y1);

            }
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
