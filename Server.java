package com.company;

import java.io.*;
import java.net.*;

public class Server {
    public Server() {
        try {
            System.out.println("Start server");
            DatagramSocket ds = new DatagramSocket(9999);
//            while (true) {
            try {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                ds.receive(packet);
                System.out.println(packet.getAddress().getHostAddress() + " " + new String(packet.getData(), 0, packet.getLength()));
                System.out.println("end server");
            } catch (Exception e) {
                System.out.println("prikol");
            }
//        }
        }catch (Exception e){System.out.println("Already exists");}
    }
}