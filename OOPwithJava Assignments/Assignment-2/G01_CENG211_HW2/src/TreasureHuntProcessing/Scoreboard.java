package TreasureHuntProcessing;

import java.util.ArrayList;

public class Scoreboard {
    private ArrayList<Score> scores;

    // Default constructor initializing the scoreboard with default values
    public Scoreboard() {
        this.scores = new ArrayList<Score>();
    }

    // Copy constructor to create a new Scoreboard object from an existing one
    public Scoreboard(Scoreboard original) {

        // Create a new ArrayList of Score objects and copy each Score object from the original Scoreboard
        this.scores = new ArrayList<Score>();
        for(int i = 0; i < original.scores.size(); i++) {
            this.scores.add(new Score(original.scores.get(i)));
        }
    }

    // Method to add a new score to the scoreboard
    public void addScore(Score score) {
        this.scores.add(new Score(score));
    }


    // Overridden toString method to provide a string representation of the Scoreboard object
    @Override
    public String toString() {
        String result = "";
        for (Score score : scores) {
            result += score.toString() + "\n";
        }
        return result;
    }
}
