package com.example.main;

import com.example.main.model.Idea;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.example.main.model.Server;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPanel implements Initializable, UpdateListener {
    Server server = ServerContainer.getServer();

    @FXML
    private TableView<Idea> ideaTable;

    @FXML
    void onEnd(ActionEvent event) {
        System.out.println("Hello from Scene 2");
        System.out.println(server.getListenThreadVector());
        onAddIdea(new Idea("Hello"));
    }

    @FXML
    ObservableList<Idea> ideas = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Idea, Integer> idColumn;
    @FXML
    private TableColumn<Idea, String> textColumn;

    @FXML
    public void onAddIdea(Idea idea){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ideas.add(idea);
                ideaTable.refresh();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Idea, Integer>("ideaID"));
        textColumn.setCellValueFactory(new PropertyValueFactory<Idea, String>("ideaText"));
        ideaTable.setItems(ideas);
        ServerContainer.getServer().setUpdateListener(this);
    }

    @Override
    public void addIdea(Idea idea){
        onAddIdea(idea);
    }
}
