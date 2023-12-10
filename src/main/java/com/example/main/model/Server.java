package com.example.main.model;

import com.example.main.UpdateListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Server {
    private DataBase ideaDataBase;
    private static final byte END_WAITING = 66;
    private ServerSocket serverSocket;
    private Vector<ClientSenderThread> senderThreadVector;
    private Vector<ClientListenThread> listenThreadVector;
    private Timer timer = new Timer();
    private UpdateListener updateListener;

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

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                endAccepting();
                updateListener.update();
            }
        }, 6 * 1000);


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


    public UpdateListener getUpdateListener() {
        return updateListener;
    }

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public void endAccepting(){
        System.out.println(senderThreadVector);
        System.out.println(listenThreadVector);
        senderThreadVector.forEach(ClientSenderThread::sendEndAccept);
        acceptThread.stopThread();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                endedAddingStartedVoting();
            }
        },10 * 1000);

    }
    private void endedAddingStartedVoting(){
        senderThreadVector.forEach(ClientSenderThread::sendStartVote);
        System.out.println("Ended adding ideas");
        System.out.println("Started voting");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                senderThreadVector.forEach(ClientSenderThread::sendVoteResult);
            }
        },25 * 1000);
        updateListener.update();
    }

    public void addCounter(){
        updateListener.update();
    }
    public void addIdea(Idea toAdd){
        updateListener.addIdea(toAdd);
    }

    public void powerOff(){
        try {
            timer.cancel();
            senderThreadVector.forEach(senderThreadVector-> senderThreadVector.sendMessage((int) END_WAITING));
            System.out.println("Sent message to close");
            for(var i: listenThreadVector){
                i.join();
            }
            System.out.println("Closing socket ...");
            serverSocket.close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
