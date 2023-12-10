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
    private boolean voteStatus;
    private AcceptThread acceptThread;

    public Server() {
        try {
            serverSocket = new ServerSocket(150);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ideaDataBase = new DataBase();
        senderThreadVector = new Vector<>();
        listenThreadVector = new Vector<>();
        acceptThread = new AcceptThread(this);

        acceptThread.start();

    }
    public void setVoteStatus(boolean newStatus) {
        this.voteStatus = newStatus;
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
                if(!voteStatus){
                    endedAddingStartedVoting();
                }
            }
        },180 * 1000);

    }
    public void endedAddingStartedVoting(){
        senderThreadVector.forEach(ClientSenderThread::sendStartVote);
        System.out.println("Ended adding ideas");
        System.out.println("Started voting");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(voteStatus){
                    endedVotingResultsHandle();
                }
            }
        },90 * 1000);
        updateListener.update();
    }
    public void endedVotingResultsHandle(){
        System.out.println("Vote results request");
        //запит відправки результатів голосування
        senderThreadVector.forEach(ClientSenderThread::sendVoteResult);
        System.out.println("Vote results taken");

        //зворотня розсилка найкращих результатів
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                senderThreadVector.forEach(ClientSenderThread::sendBestIdeas);
            }
        },2 * 1000);
        System.out.println("Best results sent");
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

    public ClientSenderThread getThreadFromClient(Client client){
        for(var i: senderThreadVector){
            if(i.getClient().equals(client)){
                return i;
            }
        }
        return null;
    }
    public void deleteClientThreads(ClientSenderThread clientSenderThread, ClientListenThread listenThread){
        System.out.println("Deleting threads");
        senderThreadVector.remove(clientSenderThread);
        listenThreadVector.remove(listenThread);
    }
}
