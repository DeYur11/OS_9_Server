package com.example.main.model;

import java.io.IOException;
import java.net.Socket;

public class AcceptThread extends Thread {

    private static final byte END_WAITING = 66;
    private static final byte CONNECT_REQUEST = 1;
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
                System.out.println("Started listening");
                Socket socket = server.getServerSocket().accept();

                int code = socket.getInputStream().read();
                if(code == END_WAITING){
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
                System.out.println("Ended thread accepting");
                return;
            }
        }
        System.out.println("Ended thread accepting");
    }

    public void stopThread() {
        running = false;
        try {
            Socket socket = new Socket("localhost", 150);
            socket.getOutputStream().write(END_WAITING); // Надсилаємо сигнал про закінчення прийому.
            socket.getOutputStream().flush();
            socket.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
