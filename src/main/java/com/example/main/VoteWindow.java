package com.example.main;

import com.example.main.model.Idea;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class VoteWindow implements Initializable, UpdateListener{
    private final ObservableList<Idea> totalIdeas = FXCollections.observableArrayList();
    @FXML
    private TableView<Idea> ideaTable;
    @FXML
    private TableColumn<Idea, Integer> ideaNumber;
    @FXML
    private TableColumn<Idea, String> ideaText;
    @FXML
    private TableColumn<Idea, Integer> ideaVotesAmount;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ideaVotesAmount.setCellValueFactory(new PropertyValueFactory<Idea, Integer>("ideaVotes"));
        ideaText.setCellValueFactory(new PropertyValueFactory<Idea, String>("ideaID"));
        ideaNumber.setCellValueFactory(new PropertyValueFactory<Idea, Integer>("ideaText"));

        totalIdeas.setAll(ServerContainer.getServer().getIdeaDataBase().getIdeaVector());
        ideaTable.setItems(totalIdeas);
        ServerContainer.getServer().setUpdateListener(this);
    }
    @Override
    public void update(){
        refreshTable(ServerContainer.getServer().getIdeaDataBase().getIdeaVector());
    }
    public void refreshTable(Vector<Idea> ideasVector){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                totalIdeas.setAll(ideasVector);
                ideaTable.refresh();
            }
        });
    }
}
