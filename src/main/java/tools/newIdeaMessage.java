package tools;

import com.example.os_9_server.model.Idea;

import java.io.Serializable;

public class newIdeaMessage implements Serializable {
    private final Idea newIdea;

    public newIdeaMessage(Idea newIdea) {
        this.newIdea = newIdea;
    }

    public Idea getNewIdea() {
        return newIdea;
    }
}
