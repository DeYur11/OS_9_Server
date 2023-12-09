package com.example.main;

import com.example.main.model.Server;

public abstract class ServerContainer {
    private static Server server;

    public static Server getServer() {
        return server;
    }

    public static void setServer(Server server) {
        ServerContainer.server = server;
    }
}
