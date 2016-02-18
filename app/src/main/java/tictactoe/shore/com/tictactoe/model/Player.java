package tictactoe.shore.com.tictactoe.model;

/**
 * Created by Shiva on 2/18/2016.
 */
public class Player {
    private String name;
    private int score;

    public Player() {
    }

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
