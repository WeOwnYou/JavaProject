package com.company;

import java.io.*;
import java.net.*;

public class Server {

    private Game game;
    private int port;
    private int portForTest = 2001;
    private boolean running;

    public Server(Game game) {
        port = 2000;
        running = true;
        this.game = game;
    }

    public void serverToDetect() {
        System.out.println("Started1");
        if(game.getIPDetected()) {
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

                    if(!game.getIPDetected()) {
                        game.setIPDetected(true);
                        game.setIPAdressOfOpponent(packet.getAddress().getHostAddress());
                    }else if(!game.getSideIPdetected()){
                        if(game.getSideIPAdress().equals(packet.getAddress().getHostAddress()))
                            continue;
                        System.out.println(packet.getAddress().getHostAddress()+" NAdo " + game.getIPAdressOfOpponent());
                        game.setSideIPdetected(true);
                        game.setSideIPAdress(packet.getAddress().getHostAddress());
                    }else {
                        running = false;
                        return;
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
        System.out.println("Started2");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            System.out.println("Got a client ");

            InputStream sin = socket.getInputStream();
//            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
//            DataOutputStream out = new DataOutputStream(sout);

            String line = null;
            while (running){
                System.out.println("OPA");
                line = in.readUTF();
                System.out.println(line);
            }
        }catch (Exception e){e.printStackTrace();}
    }
}
