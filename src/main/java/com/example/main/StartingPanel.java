package com.example.main;

import javafx.animation.FadeTransition;
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

public class StartingPanel implements Initializable {

    Server server = ServerContainer.getServer();
    @FXML
    private StackPane root;

    @FXML
    void onEndClicked(ActionEvent event) {
        makeFadout();
    }
    private void makeFadout(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(1));
        fadeTransition.setNode(root);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadNextScene();
            }
        });
        fadeTransition.play();
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
        ServerContainer.getServer().endAccepting();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServerContainer.setServer(new Server());
        this.server = ServerContainer.getServer();
    }
}
