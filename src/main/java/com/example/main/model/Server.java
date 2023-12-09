package com.example.main.model;

import com.example.main.UpdateListener;

import java.net.ServerSocket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Server {
    private DataBase ideaDataBase;
    private ServerSocket serverSocket;
    private Vector<ClientSenderThread> senderThreadVector;
    private Vector<ClientListenThread> listenThreadVector;
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
        Timer timer = new Timer();
        acceptThread.start();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                endAccepting();
                updateListener.update();
                System.out.println("Hello");
            }
        }, 6 * 1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                senderThreadVector.forEach(ClientSenderThread::sendVoteResult);
            }
        },180 * 1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                senderThreadVector.forEach(ClientSenderThread::sendBestIdeas);
            }
        },180 * 1000);
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
        acceptThread.interrupt();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                senderThreadVector.forEach(ClientSenderThread::sendStartVote);
                System.out.println("Ended voting");
                updateListener.update();
            }
        },10 * 1000);
    }
    public void addCounter(){
        updateListener.update();
    }
    public void addIdea(Idea toAdd){
        updateListener.addIdea(toAdd);
    }

}
