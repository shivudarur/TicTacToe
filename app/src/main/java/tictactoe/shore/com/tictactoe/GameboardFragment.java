package tictactoe.shore.com.tictactoe;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import tictactoe.shore.com.tictactoe.model.Player;
import tictactoe.shore.com.tictactoe.sqlite.DatabaseHandler;
import tictactoe.shore.com.tictactoe.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameboardFragment.GameboardFragmentEventsListener} interface
 * to handle interaction events.
 * Use the {@link GameboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameboardFragment extends Fragment implements View.OnClickListener {

    private static final char NAUGHT_SYMBOL = 'X';
    private static final char CROSS_SYMBOL = 'O';
    private static final int TOTAL_NUMBER_OF_MOVES;
    private static final int NUMBER_OF_ROWS;
    private static final char NONE_WIN_SYMBOL = '\0';

    static {
        TOTAL_NUMBER_OF_MOVES = 9;
        NUMBER_OF_ROWS = getNumberOfRows();
    }

    private GameboardFragmentEventsListener mGameboardFragmentEventsListener;
    // Representing the game state:
    private boolean mNoughtsTurn = false; // Who's turn is it? false=X true=O
    private char mBoard[][] = new char[NUMBER_OF_ROWS][NUMBER_OF_ROWS]; // for now we will represent the mBoard as an array of characters
    private int mPlayer1Score = 0, mPlayer2Score = 0;
    private View mRootView;
    private LinearLayout mGameHolder, mGameboardHolder, mNavigationButtonsHolder;
    private TextView mTextViewPlayer1Score, mTextViewPlayer2Score;

    public GameboardFragment() {
        // Required empty public constructor
    }

    private static int getNumberOfRows() {
        return (int) Math.round(TOTAL_NUMBER_OF_MOVES / Math.sqrt(TOTAL_NUMBER_OF_MOVES));
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GameboardFragment.
     */
    public static GameboardFragment newInstance() {
        return new GameboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_gameboard, container, false);
        init();
        return mRootView;
    }

    private void init() {
        TicTacToeApp mTicTacToeApp = (TicTacToeApp) getActivity().getApplication();
        mRootView.findViewById(R.id.button_new_game).setOnClickListener(this);
        mRootView.findViewById(R.id.button_proceed_to_leader_board).setOnClickListener(this);
        mRootView.findViewById(R.id.button_new_game).setOnClickListener(this);
        ((TextView) mRootView.findViewById(R.id.textView_player1)).setText(mTicTacToeApp.getPlayer1Name());
        ((TextView) mRootView.findViewById(R.id.textView_player2)).setText(mTicTacToeApp.getPlayer2Name());
        mTextViewPlayer1Score = (TextView) mRootView.findViewById(R.id.textView_player1_score);
        mTextViewPlayer2Score = (TextView) mRootView.findViewById(R.id.textView_player2_score);
        mGameboardHolder = (LinearLayout) mRootView.findViewById(R.id.ll_gameboard_holder);
        mGameHolder = (LinearLayout) mRootView.findViewById(R.id.ll_game_holder);
        mNavigationButtonsHolder = (LinearLayout) mRootView.findViewById(R.id.ll_navigation_buttons_holder);
        setPlayersScores();
        setupOnClickListeners();
        resetButtons();
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof GameboardFragmentEventsListener)
            mGameboardFragmentEventsListener = (GameboardFragmentEventsListener) context;
        else {
            throw new RuntimeException(context.toString()
                    + " must implement GameboardFragmentEventsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mGameboardFragmentEventsListener = null;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_new_game:
                mNoughtsTurn = false;
                mBoard = new char[NUMBER_OF_ROWS][NUMBER_OF_ROWS];
                mGameboardFragmentEventsListener.onNewGameClick();
                break;
            case R.id.button_proceed_to_leader_board:
                if (mGameboardFragmentEventsListener != null)
                    mGameboardFragmentEventsListener.onProceedToLeaderboardClick();
                break;
        }

    }

    /**
     * Reset each button in the grid to be blank and enabled.
     */
    private void resetButtons() {
        int gameboardHolderChildCount = mGameboardHolder.getChildCount();
        int subViewGroupChildCount;
        for (int y = 0; y < gameboardHolderChildCount; y++) {
            if (mGameboardHolder.getChildAt(y) instanceof LinearLayout) {
                LinearLayout subViewGroup = (LinearLayout) mGameboardHolder.getChildAt(y);
                subViewGroupChildCount = subViewGroup.getChildCount();
                for (int x = 0; x < subViewGroupChildCount; x++) {
                    if (subViewGroup.getChildAt(x) instanceof Button) {
                        Button button = (Button) subViewGroup.getChildAt(x);
                        button.setText("");
                        button.setEnabled(true);
                    }
                }
            }
        }
        //TextView t = (TextView) mRootView.findViewById(R.id.titleText);
        //t.setText(R.string.title);
    }

    /**
     * Method that returns true when someone has won and false when nobody has.<br />
     * It also display the winner on screen.
     *
     * @return true if any player wins
     */
    private boolean checkWin() {

        char winner = NONE_WIN_SYMBOL;
        if (checkWinner(mBoard, NUMBER_OF_ROWS, NAUGHT_SYMBOL)) {
            winner = NAUGHT_SYMBOL;
        } else if (checkWinner(mBoard, NUMBER_OF_ROWS, CROSS_SYMBOL)) {
            winner = CROSS_SYMBOL;
        }

        if (winner == NONE_WIN_SYMBOL) {
            if (mPlayer1Score + mPlayer2Score >= TOTAL_NUMBER_OF_MOVES) { // Game is over
                Utils.showToast(getActivity(), R.string.no_player_won);
                mGameboardFragmentEventsListener.onNewGameClick();
                return false; // nobody won
            }
            return false; // nobody won
        } else {
            // display winner and store the winner details persistently.

            DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
            TicTacToeApp ticTacToeApp = (TicTacToeApp) getActivity().getApplication();
            String winnerName = (winner == NAUGHT_SYMBOL) ? ticTacToeApp.getPlayer1Name() : ticTacToeApp.getPlayer2Name();
            int winnerScore = (winner == NAUGHT_SYMBOL) ? mPlayer1Score : mPlayer2Score;
            Utils.showToast(getActivity(), String.format(getResources().getString(R.string.wins), winnerName));
            databaseHandler.addPlayer(new Player(winnerName, winnerScore));
            return true;
        }
    }

    /**
     * This is a generic algorithm for checking if a specific player has won on a tic tac toe mBoard of any size.
     *
     * @param mBoard the mBoard itself
     * @param size   the width and height of the mBoard
     * @param player the player, 'X' or 'O'
     * @return true if the specified player has won
     */
    private boolean checkWinner(char[][] mBoard, int size, char player) {
        // check each column
        for (int x = 0; x < size; x++) {
            int total = 0;
            for (int y = 0; y < size; y++) {
                if (mBoard[x][y] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // check each row
        for (int y = 0; y < size; y++) {
            int total = 0;
            for (int x = 0; x < size; x++) {
                if (mBoard[x][y] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // forward diag
        int total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == y && mBoard[x][y] == player) {
                    total++;
                }
            }
        }
        if (total >= size) {
            return true; // they win
        }

        // backward diag
        total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x + y == size - 1 && mBoard[x][y] == player) {
                    total++;
                }
            }
        }
        if (total >= size) {
            return true; // they win
        }

        return false; // nobody won
    }

    /**
     * Disables all the buttons in the grid.
     */
    private void disableButtons() {

        int gameboardHolderChildCount = mGameboardHolder.getChildCount();
        int subViewGroupChildCount;
        for (int y = 0; y < gameboardHolderChildCount; y++) {
            if (mGameboardHolder.getChildAt(y) instanceof LinearLayout) {
                LinearLayout subViewGroup = (LinearLayout) mGameboardHolder.getChildAt(y);
                subViewGroupChildCount = subViewGroup.getChildCount();
                for (int x = 0; x < subViewGroupChildCount; x++) {
                    if (subViewGroup.getChildAt(x) instanceof Button) {
                        Button button = (Button) subViewGroup.getChildAt(x);
                        button.setEnabled(false);
                    }
                }
            }
        }
    }

    /**
     * This will add the OnClickListener to each button inside out LinearLayout
     */
    private void setupOnClickListeners() {

        int gameboardHolderChildCount = mGameboardHolder.getChildCount();
        int subViewGroupChildCount;
        for (int y = 0; y < gameboardHolderChildCount; y++) {
            if (mGameboardHolder.getChildAt(y) instanceof LinearLayout) {
                LinearLayout subViewGroup = (LinearLayout) mGameboardHolder.getChildAt(y);
                subViewGroupChildCount = subViewGroup.getChildCount();
                for (int x = 0; x < subViewGroupChildCount; x++) {
                    View view = subViewGroup.getChildAt(x); // In our case this will be each button on the grid
                    view.setOnClickListener(new PlayOnClick(x, y));
                }
            }
        }
    }

    private void showGameboard(boolean showGameboard) {
        if (showGameboard) {
            mGameHolder.setVisibility(View.VISIBLE);
            mNavigationButtonsHolder.setVisibility(View.GONE);
        } else {
            mGameHolder.setVisibility(View.GONE);
            mNavigationButtonsHolder.setVisibility(View.VISIBLE);
        }
    }

    private void setPlayersScores() {
        mTextViewPlayer1Score.setText(String.valueOf(mPlayer1Score));
        mTextViewPlayer2Score.setText(String.valueOf(mPlayer2Score));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface GameboardFragmentEventsListener {
        void onProceedToLeaderboardClick();

        void onNewGameClick();
    }

    /**
     * Custom OnClickListener for Noughts and Crosses<br />
     * Each Button for Noughts and Crosses has a position we need to take into account
     *
     * @author Shiva
     */
    private class PlayOnClick implements View.OnClickListener {

        private int x = 0;
        private int y = 0;

        public PlayOnClick(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void onClick(View view) {
            if (view instanceof Button) {
                Button button = (Button) view;
                mBoard[x][y] = mNoughtsTurn ? CROSS_SYMBOL : NAUGHT_SYMBOL;
                button.setText(mNoughtsTurn ? String.valueOf(CROSS_SYMBOL) : String.valueOf(NAUGHT_SYMBOL));
                if (mNoughtsTurn) {
                    mPlayer1Score++; // Increase player1 score

                } else {
                    mPlayer2Score++; // Increase player2 score
                }
                setPlayersScores();
                button.setEnabled(false);
                mNoughtsTurn = !mNoughtsTurn;

                // check if anyone has won
                if (checkWin()) {
                    disableButtons();
                    showGameboard(false);
                }
            }
        }
    }
}
