package com.example.os_9_server.model;

import java.net.Socket;

public class AcceptThread extends Thread {
    private Server server;

    private boolean running = true;

    public AcceptThread(Server server) {
        System.out.println("Accept thread started");
        this.server = server;
    }

    @Override
    public void run(){
        while(running){
            try{
                System.out.println("Started accepting");
                Socket socket = server.getServerSocket().accept();
                if(!running){
                    System.out.println("Ended accepting");
                    return;
                }
                System.out.println("Socket accepted");
                Client client = new Client(socket);


                // Важливо щоб спочатку був Output а потім Input
                ClientSenderThread senderThread = new ClientSenderThread(client, server);
                ClientListenThread listenThread = new ClientListenThread(client ,server);

                server.getListenThreadVector().add(listenThread);
                server.getSenderThreadVector().add(senderThread);

                listenThread.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("Ended accepting");
    }

    public void stopThread() {
        running = false;
    }
}
