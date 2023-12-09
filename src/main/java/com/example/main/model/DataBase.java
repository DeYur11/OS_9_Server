package com.example.main.model;

import java.util.Collections;
import java.util.Vector;

public class DataBase {
    private Vector<Idea> ideaVector = new Vector<>();

    public Vector<Idea> getIdeaVector() {
        return ideaVector;
    }
    public  Idea getIdeaByID(int ideaID){
        Idea searchedIdea = null;

        for (int i = 0; i < ideaVector.size(); i++) {

            if (ideaVector.get(i).getIdeaID() == ideaID){
                searchedIdea = ideaVector.get(i);
            }
        }
        return searchedIdea;
    }
    public void addIdea(Idea newIdea){
        ideaVector.add(newIdea);
    }
    public Vector<Idea> getBestIdeas(){
        Vector<Idea> ideaWinnersList = new Vector<>();
        Vector<Idea> copiedIdeaVector = new Vector<>(ideaVector);

        Collections.sort(copiedIdeaVector, (idea1, idea2) -> Integer.compare(idea2.getIdeaVotes(), idea1.getIdeaVotes()));

        for (int i = 0; i < Math.min(3, copiedIdeaVector.size()); i++) {
            ideaWinnersList.add(copiedIdeaVector.get(i));
        }
        return ideaWinnersList;
    }
}
