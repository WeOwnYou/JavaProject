package com.company;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client extends Thread{                                                                                     //мб для других стей?

    private String s;
    private String addressOfNetwork;
    private String address;
    private boolean running;
    private Game game;
    private int port = 2000;

    public Client(Game game) {
        this.game = game;
        this.s = "1";
        this.running = true;
        this.addressOfNetwork = "10.1.1.255";
        try {
            addressOfNetwork();
        } catch (Exception e) {System.out.println("Так не должно быть");}
    }

        public void addressOfNetwork() throws Exception{
            String adr = InetAddress.getByName(addressOfNetwork).getLocalHost().getHostAddress();
            StringBuilder sb = new StringBuilder();
            Scanner scaner = new Scanner(adr);
            scaner.useDelimiter("\\.");
            sb.append(scaner.next()+".");
            sb.append(scaner.next()+".");
            sb.append(scaner.next()+".");
            sb.append(255);
            this.addressOfNetwork = sb.toString();
        }

        public void sendPacketToDetect() throws Exception{
            System.out.println("Started2");
            if(game.getIPDetected())
                return;

            MulticastSocket ms = new MulticastSocket(1024);


            byte[] buffer;
            buffer = s.getBytes();

            InetAddress ipAddress = InetAddress.getByName(addressOfNetwork);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ipAddress, 9999);

            while(running) {
                try {
                    ms.send(packet);
                    System.out.println("Packet Send");
                    Thread.sleep(3000);
                    if(!game.getIPDetected() && !game.getIPDetected()) {
                        return;
                    }
                    System.out.println(game.getIPDetected() + " OPA " +game.getSideIPdetected());
                }catch(Exception e){;}
        }
        System.out.println("End client");
    }

    public void mainClient(){
        System.out.println("Started");
        address = game.getIPAdressOfOpponent();
        try{
            InetAddress ipAddress = InetAddress.getByName(address);
            Socket socket = new Socket(ipAddress, port);
//            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

//            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            while (running){
                out.writeUTF(game.getGameField());
                System.out.println("VYVOD");
                Thread.sleep(1000);
            }


        } catch (Exception e) {
            System.out.println(";");
        }
    }

}
