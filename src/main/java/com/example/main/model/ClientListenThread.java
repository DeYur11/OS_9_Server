package com.example.main.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

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
                System.out.println("Message accepted, message class: " + message.getClass());
                if(message instanceof Integer){
                    if((Integer)message == 66){
                        System.out.println("End of listening");
                        return;
                    } else if ((Integer)message == 55) {
                        server.getThreadFromClient(client).sendMessage(55);
                        server.deleteClientThreads(server.getThreadFromClient(client), this);
                        System.out.println("End of listening");
                        return;
                    }
                }
                if(message instanceof Idea){
                   server.getIdeaDataBase().addIdea((Idea)message);
                   Idea.ideaAmount++;
                   server.getSenderThreadVector().stream().forEach(thread -> thread.sendIdea(server.getIdeaDataBase().getIdeaVector().lastElement()));
                   server.addIdea((Idea)message);
                }
                else if(message instanceof  String){
                    System.out.println("Message");
                    System.out.println((String)message);
                }
                else if(message.getClass().equals(Vector.class)){
                    int random = (int) ((Math.random()*2000));
                    Thread.sleep(random);
                    Vector<Integer> selectedID = (Vector<Integer>) message;

                    for (int i = 0; i < selectedID.size(); i++) {
                        server.getIdeaDataBase().getIdeaByID(selectedID.get(i)).increaseVoteAmount();
                    }
                    server.addCounter();
                }

            }
            catch (Exception e){
                System.out.println("Ended work of ClientListenThread");
                return;
            }
        }
    }
}
