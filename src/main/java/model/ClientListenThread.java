package model;

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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true){
            try{
                Object message = ideaInputStream.readObject();
                if(message instanceof Idea){
                    server.getIdeaDataBase().addIdea((Idea)message);
                    server.getSenderThreadVector().stream().forEach(thread -> thread.sendMessage());
                }else if(message instanceof  String){
                    System.out.println(message);;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }



}
