package model;

import java.net.ServerSocket;
import java.util.Vector;

public class Server {
    private DataBase ideaDataBase;
    private ServerSocket serverSocket;
    private Vector<ClientSenderThread> senderThreadVector;
    private Vector<ClientListenThread> listenThreadVector;

    public Server() {
        try {
            serverSocket = new ServerSocket(100);
        }catch (Exception e){
            e.printStackTrace();
        }
        ideaDataBase = new DataBase();
        senderThreadVector = new Vector<>();
    }

    public DataBase getIdeaDataBase() {
        return ideaDataBase;
    }

    public Vector<ClientSenderThread> getSenderThreadVector() {
        return senderThreadVector;
    }

    public Vector<ClientListenThread> getListenThreadVector() {
        return listenThreadVector;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
