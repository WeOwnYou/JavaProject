package com.company;

public class MyThread extends Thread {

    private Game game;

    public MyThread(Game game) {
        this.game = game;
    }

    public void run() {
        Server server = new Server(game);
        Client client = new Client(game);
        try {
                if(!game.getIPDetected()) {
                    client.sendPacketToDetect();
                    server.serverToDetect();
                }
//                else{
//                    client.mainClient();
//                    server.mainServer();
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
