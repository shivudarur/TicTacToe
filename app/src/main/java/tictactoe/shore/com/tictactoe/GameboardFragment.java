package tictactoe.shore.com.tictactoe;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameboardFragment.GameboardFragmentEventsListener} interface
 * to handle interaction events.
 * Use the {@link GameboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameboardFragment extends Fragment implements View.OnClickListener {

    private GameboardFragmentEventsListener mGameboardFragmentEventsListener;
    // Representing the game state:
    private boolean mNoughtsTurn = false; // Who's turn is it? false=X true=O
    private char mBoard[][] = new char[3][3]; // for now we will represent the mBoard as an array of characters
    private int mPlayer1Score = 0, mPlayer2Score = 0;
    private View mRootView;
    private TextView mTextViewPlayer1Score, mTextViewPlayer2Score;

    public GameboardFragment() {
        // Required empty public constructor
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
        ((TextView) mRootView.findViewById(R.id.textView_player1)).setText(mTicTacToeApp.getPlayer1Name());
        ((TextView) mRootView.findViewById(R.id.textView_player2)).setText(mTicTacToeApp.getPlayer2Name());
        mTextViewPlayer1Score = (TextView) mRootView.findViewById(R.id.textView_player1_score);
        mTextViewPlayer2Score = (TextView) mRootView.findViewById(R.id.textView_player2_score);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setupOnClickListeners();
        resetButtons();
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
        switch (v.getId())
        {
            case R.id.button_new_game:
                mNoughtsTurn = false;
                mBoard = new char[3][3];
                resetButtons();
                break;
            case R.id.button_proceed_to_leader_board:
                if (mGameboardFragmentEventsListener!=null)
                mGameboardFragmentEventsListener.onProceedToLeadermBoardClick();
                break;
        }

    }

    /**
     * Reset each button in the grid to be blank and enabled.
     */
    private void resetButtons() {
        LinearLayout gameboardHolder = (LinearLayout) mRootView.findViewById(R.id.ll_gameboard_holder);
        int gameboardHolderChildCount=gameboardHolder.getChildCount();
        int subViewGroupChildCount;
        for (int y = 0; y < gameboardHolderChildCount; y++) {
            if (gameboardHolder.getChildAt(y) instanceof LinearLayout) {
                LinearLayout subViewGroup = (LinearLayout) gameboardHolder.getChildAt(y);
                subViewGroupChildCount=subViewGroup.getChildCount();
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
     * @return true if any player won
     */
    private boolean checkWin() {

        char winner = '\0';
        if (checkWinner(mBoard, 3, 'X')) {
            winner = 'X';
        } else if (checkWinner(mBoard, 3, 'O')) {
            winner = 'O';
        }

        if (winner == '\0') {
            return false; // nobody won
        } else {
            // display winner
            // TextView T = (TextView) mRootView.findViewById(R.id.titleText);
            //T.setText(winner + " wins");
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
        LinearLayout gameboardHolder = (LinearLayout) mRootView.findViewById(R.id.ll_gameboard_holder);
        int gameboardHolderChildCount=gameboardHolder.getChildCount();
        int subViewGroupChildCount;
        for (int y = 0; y < gameboardHolderChildCount; y++) {
            if (gameboardHolder.getChildAt(y) instanceof LinearLayout) {
                LinearLayout subViewGroup = (LinearLayout) gameboardHolder.getChildAt(y);
                subViewGroupChildCount=subViewGroup.getChildCount();
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
        LinearLayout gameboardHolder = (LinearLayout) mRootView.findViewById(R.id.ll_gameboard_holder);
        int gameboardHolderChildCount=gameboardHolder.getChildCount();
        int subViewGroupChildCount;
        for (int y = 0; y < gameboardHolderChildCount; y++) {
            if (gameboardHolder.getChildAt(y) instanceof LinearLayout) {
                LinearLayout subViewGroup = (LinearLayout) gameboardHolder.getChildAt(y);
                subViewGroupChildCount=subViewGroup.getChildCount();
                for (int x = 0; x <subViewGroupChildCount ; x++) {
                    View view = subViewGroup.getChildAt(x); // In our case this will be each button on the grid
                    view.setOnClickListener(new PlayOnClick(x, y));
                }
            }
        }
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
        void onProceedToLeadermBoardClick();
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
                mBoard[x][y] = mNoughtsTurn ? 'O' : 'X';
                button.setText(mNoughtsTurn ? "O" : "X");
                if (mNoughtsTurn) {
                    mPlayer1Score++; // Increase player1 score
                    mTextViewPlayer1Score.setText(String.valueOf(mPlayer1Score));
                } else {
                    mPlayer2Score++; // Increase player2 score
                    mTextViewPlayer2Score.setText(String.valueOf(mPlayer2Score));
                }
                button.setEnabled(false);
                mNoughtsTurn = !mNoughtsTurn;

                // check if anyone has won
                if (checkWin()) {
                    disableButtons();
                }
            }
        }
    }
}
