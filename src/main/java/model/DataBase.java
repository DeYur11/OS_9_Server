package model;

import java.util.Vector;

public class DataBase {
    private Vector<Idea> ideaVector = new Vector<>();

    public Vector<Idea> getIdeaVector() {
        return ideaVector;
    }

    public void addIdea(Idea newIdea){
        ideaVector.add(newIdea);
    }
}
