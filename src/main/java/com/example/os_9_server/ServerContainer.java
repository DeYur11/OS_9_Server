package com.example.os_9_server;

import model.Server;

public abstract class ServerContainer {
    private static Server server;

    public static Server getServer() {
        return server;
    }

    public static void setServer(Server server) {
        ServerContainer.server = server;
    }
}
