package com.example.main.model;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientListenThread extends Thread {
    private Client client;
    private ObjectInputStream ideaInputStream;
    private Server server;

    public ClientListenThread(Client client, Server server) {
        this.client = client;
        this.server = server;
        try {
            ideaInputStream = new ObjectInputStream(client.getClientSocket().getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Listener thread created");

    }

    @Override
    public void run(){
        while(true){
            try{
                Object message = ideaInputStream.readObject();
                System.out.println("Accec");
                if(true){
                    return;
                }
                if(false){
                   // server.getIdeaDataBase().addIdea((Idea)message);
                    server.getSenderThreadVector().stream().forEach(thread -> thread.sendMessage());
                }else if(message instanceof  String){
                    System.out.println("Message");
                    System.out.println((String)message);;
                }
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }

        }
    }



}
