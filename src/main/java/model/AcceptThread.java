package model;

import java.net.Socket;

public class AcceptThread extends Thread {
    private Server server;

    public AcceptThread(Server server) {
        this.server = server;
    }

    @Override
    public void run(){
        while(true){
            try{
                Socket socket = server.getServerSocket().accept();
                server.getListenThreadVector().add(new ClientListenThread(new Client(socket), server));

            }catch (Exception e){
                e.printStackTrace();
            }
        }



    }

}
