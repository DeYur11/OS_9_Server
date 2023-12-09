package com.example.os_9_server.model;

import com.example.os_9_server.model.messages.EndAcceptingMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ClientSenderThread {
    private Client client;
    private Server server;
    private ObjectOutputStream out;

    public ClientSenderThread(Client client, Server server) {
        this.client = client;
        this.server = server;
        try {
            this.out = new ObjectOutputStream(client.getClientSocket().getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Sending started");
    }

    public void sendMessage(){
    }
    public void sendEndAccept(){
        try {
            EndAcceptingMessage end = new EndAcceptingMessage();
            out.writeObject(end);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
