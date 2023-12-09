package com.example.main.model;

import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class AcceptThread extends Thread {
    private Server server;

    private boolean running = true;

    public AcceptThread(Server server) {
        System.out.println("Accept thread started");
        this.server = server;
    }

    @Override
    public void run() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("Started accepting");
                    Socket socket = server.getServerSocket().accept();
                    if (!running) {
                        System.out.println("Ended accepting");
                        return;
                    }
                    System.out.println("Socket accepted");
                    Client client = new Client(socket);

                    // Важливо щоб спочатку був Output а потім Input
                    ClientSenderThread senderThread = new ClientSenderThread(client, server);
                    ClientListenThread listenThread = new ClientListenThread(client, server);

                    server.getListenThreadVector().add(listenThread);
                    server.getSenderThreadVector().add(senderThread);

                    listenThread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 60 * 1000);

        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.stopThread();
        System.out.println("Ended thread accepting");
    }

    public void stopThread() {
        running = false;
    }
}
