package tictactoe.shore.com.tictactoe;

import android.app.Application;

public class TicTacToeApp extends Application {

    private String player1Name;
    private String player2Name;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }
}
