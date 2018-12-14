package com.company;

public class MyThread2 extends Thread {
    private Game game;

    public MyThread2(Game game) {
        this.game = game;
    }

    public void run() {
        Server server = new Server(game);
        server.serverToDetect();
//            }
////                else{
////                    client.mainClient();
////                    server.mainServer();
////                }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    }
}
