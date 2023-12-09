package com.example.os_9_server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import com.example.os_9_server.model.Server;

public class MainPanel {
    Server server = ServerContainer.getServer();

    @FXML
    private TableView<?> ideaTable;

    @FXML
    void onEnd(ActionEvent event) {
        System.out.println("Hello from Scene 2");
        System.out.println(server.getListenThreadVector());
    }

}
