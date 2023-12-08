package model;

public class Idea {
    public static int ideaAmount;
    private int ideaID;
    private String ideaText;
    private Client ideaAuthor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Idea idea = (Idea) o;

        if (ideaID != idea.ideaID) return false;
        if (!ideaText.equals(idea.ideaText)) return false;
        return ideaAuthor.equals(idea.ideaAuthor);
    }
}
