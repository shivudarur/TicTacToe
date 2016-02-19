package tictactoe.shore.com.tictactoe;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Shiva on 2/19/2016.
 * Unit tests, to test the units in Gameboard.
 */
public class GameBoardUnitTest {
    static int TOTAL_NUMBER_OF_MOVES = 9;

    private static int getNumberOfRows() {
        return (int) Math.round(TOTAL_NUMBER_OF_MOVES / Math.sqrt(TOTAL_NUMBER_OF_MOVES));
    }

    @Test
    public void areGivenTotalNumberOfRowsCorrect() throws Exception {
        int totalNumberOfRows = getNumberOfRows();
        assertEquals((totalNumberOfRows * totalNumberOfRows), TOTAL_NUMBER_OF_MOVES); // True, if it is a perfect square.
    }
}
