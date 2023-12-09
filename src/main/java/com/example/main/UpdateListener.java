package com.example.main;

import com.example.main.model.Idea;

public interface UpdateListener {
    default void addIdea(Idea idea){};
    default void update(){

    }
}
