package com.company;

public class MyThread extends Thread {

    private Game game;

    public MyThread(Game game) {
        this.game = game;
    }

    public void run() {
        Client client = new Client(game);
        try {
            client.sendPacketToDetect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
