package com.example.main.model;

import java.net.ServerSocket;
import java.util.Vector;

public class Server {
    private DataBase ideaDataBase;
    private ServerSocket serverSocket;
    private Vector<ClientSenderThread> senderThreadVector;
    private Vector<ClientListenThread> listenThreadVector;

    private AcceptThread acceptThread;

    public Server() {
        try {
            serverSocket = new ServerSocket(150);
        }catch (Exception e){
            e.printStackTrace();
        }
        ideaDataBase = new DataBase();
        senderThreadVector = new Vector<>();
        listenThreadVector = new Vector<>();
        acceptThread = new AcceptThread(this);
        acceptThread.start();
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

    public void endAccepting(){
        System.out.println(senderThreadVector);
        System.out.println(listenThreadVector);
        senderThreadVector.forEach(ClientSenderThread::sendEndAccept);
        acceptThread.stopThread();
        acceptThread.interrupt();
    }

}
