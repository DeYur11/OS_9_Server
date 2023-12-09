package com.example.main.model;

import tools.messages.EndAcceptingMessage;
import tools.messages.StartVoteMessage;
import tools.messages.VoteResultMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

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

    public void sendIdea(Idea idea){
        try{
            System.out.println("Sending idea: "+idea.getIdeaText());
            out.writeObject(idea);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sendEndAccept(){
        try {
            EndAcceptingMessage end = new EndAcceptingMessage();
            out.writeObject(end);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendStartVote(){
        try {
            StartVoteMessage end = new StartVoteMessage();
            out.writeObject(end);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendVoteResult(){
        try {
            VoteResultMessage end = new VoteResultMessage();
            out.writeObject(end);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
   public void sendBestIdeas(){
        try {
            Vector<Idea> bestIdea = server.getIdeaDataBase().getBestIdeas();

            out.writeObject(bestIdea);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
