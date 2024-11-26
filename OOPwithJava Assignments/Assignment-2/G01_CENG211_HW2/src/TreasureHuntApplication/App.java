package TreasureHuntApplication;
import TreasureHuntProcessing.GameArranger;
import FileIO.PrintToTxt;

public class App {
    public static void main(String[] args) throws Exception {
        
        // Create an instance of GameArranger and call its operate method
        GameArranger gameArranger = new GameArranger();

        // Call the operate method to start the game
        gameArranger.operate();

        // Print the scoreboard to a file
        PrintToTxt.printToFile("scoreboard.txt", gameArranger.getScoreboardString());
    }
}
