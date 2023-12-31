package com.example.main;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.example.main.model.Server;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StartingPanel implements Initializable, UpdateListener {

    Server server = ServerContainer.getServer();
    @FXML
    private StackPane root;

    @FXML
    void onEndClicked(ActionEvent event) {
        server.endAccepting();
        loadNextScene();
    }

    private void loadNextScene() {
        Parent mainWindow;
        try {
            mainWindow = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainPanel.fxml")));
            Scene mainWindowsScene = new Scene(mainWindow);
            Stage curStage = (Stage) root.getScene().getWindow();
            curStage.setScene(mainWindowsScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(server.getListenThreadVector());
    }
    @Override
    public void update(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loadNextScene();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServerContainer.setServer(new Server());
        this.server = ServerContainer.getServer();
        ServerContainer.getServer().setUpdateListener(this);
    }
}
