package com.company;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client extends Thread{ //мб для других стей?

    private String s;
    private String addressOfNetwork;
    private String address1;
    private String address2;
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
//                Thread.sleep(3000);
                if(!game.getIPDetected() && !game.getIPDetected()) {
                    return;
                }
                System.out.println(game.getIPAdressOfOpponent() + " OPA " +game.getSideIPAdress());
            }catch(Exception e){;}
        }
        System.out.println("End client");
    }

    public void mainClient(){
        System.out.println("Started");
        address1 = game.getIPAdressOfOpponent();
        address2 = game.getSideIPAdress();
        try{
            InetAddress ipAddress = InetAddress.getByName(address1);
            Socket socket = new Socket(ipAddress, port);
            OutputStream sout = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(sout);

            InetAddress ipAddress2 = InetAddress.getByName(address2);
            Socket socket2 = new Socket(ipAddress2, port);
            OutputStream sout2 = socket2.getOutputStream();
            DataOutputStream out2 = new DataOutputStream(sout2);

            while (running){
                out.writeUTF(game.getGameField());
                out2.writeUTF(game.getGameField());
                System.out.println("VYVOD");
                Thread.sleep(10000);
            }


        } catch (Exception e) {
            System.out.println(";");
        }
    }
}
