package com.company;

import java.io.*;
import java.net.*;

public class Server {

    private Game game;
    private int port;
    private int portForTest = 2001;
    private boolean running;

    public Server(Game game) {
        port = 9000;
        running = true;
        this.game = game;
    }

    public void serverToDetect() {
        System.out.println("Started1");
        if(game.isIPDetected()) {
            System.out.println("ТипаНашел");
            return;
        }
        while (running){
            try {
                DatagramSocket ds = new DatagramSocket(9999);
                try {
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    ds.receive(packet);

                    System.out.println(packet.getAddress().getHostAddress()+ "Это айпи1");                                               // + "  OPA \n " + new String(packet.getData(), 0, packet.getLength())

                    if(!game.isIPDetected()) {
                        game.setIPDetected(true);
                        game.setIPAdressOfOpponent(packet.getAddress().getHostAddress());
                    }else {
                        System.out.println(packet.getAddress().getHostAddress() + " &!");
                        if (!game.isSideIPdetected()) {
                            System.out.println(packet.getAddress().getHostAddress());
                            if (game.getIPAdressOfOpponent().equals(packet.getAddress().getHostAddress()))
                                continue;
                            System.out.println(packet.getAddress().getHostAddress() + " NAdo " + game.getIPAdressOfOpponent());
                            game.setSideIPdetected(true);
                            game.setSideIPAdress(packet.getAddress().getHostAddress());
                        } else {
                            running = false;
                            return;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Nado");
                }
            } catch (Exception e) {
//            System.out.println("Already exists");
            }
        }
        System.out.println("end server");
    }

    public  void mainServer(){
        System.out.println("StartedX");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            game.setLocalConnection(true);
            InputStream sin = socket.getInputStream();
            DataInputStream in = new DataInputStream(sin);
            System.out.println("Got a client1 ");

            String line = null;
            while (running){
                line = in.readUTF();
                try{
                    line.split(".");
                    game.setSideIPAdress(line);
                    game.setSideIPdetected(true);
                }catch (Exception e){;}
                System.out.println(line);
            }
        }catch (Exception e){e.printStackTrace();}
    }
}
