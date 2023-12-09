package com.example.os_9_server.model;

import java.util.Vector;

public class UpdateMessage {
    private Vector<Idea> ideaVector;
    private int ideaAmount;


    public UpdateMessage(Vector<Idea> ideaVector, int ideaAmount) {
        this.ideaVector = ideaVector;
        this.ideaAmount = ideaAmount;
    }

    public Vector<Idea> getIdeaVector() {
        return ideaVector;
    }

    public int getIdeaAmount() {
        return ideaAmount;
    }
}
