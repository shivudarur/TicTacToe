package tictactoe.shore.com.tictactoe;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import tictactoe.shore.com.tictactoe.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayersLoginFragment.PlayersLoginFragmentEventsListener} interface
 * to handle interaction events.
 * Use the {@link PlayersLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayersLoginFragment extends Fragment implements View.OnClickListener {

    private PlayersLoginFragmentEventsListener mPlayersLoginFragmentEventsListener;
    private EditText mEditTextPlayer1, mEditTextPlayer2;

    public PlayersLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayersLoginFragment.
     */
    public static PlayersLoginFragment newInstance() {
        return new PlayersLoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_players_login,
                container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        mEditTextPlayer1 = (EditText) rootView
                .findViewById(R.id.editText_player1);
        mEditTextPlayer2 = (EditText) rootView
                .findViewById(R.id.editText_player2);
        rootView.findViewById(R.id.button_start_game).setOnClickListener(
                PlayersLoginFragment.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start_game:
                String player1Name = mEditTextPlayer1.getText().toString();
                String player2Name = mEditTextPlayer2.getText().toString();
                if (!player1Name.isEmpty() && !player2Name.isEmpty()) {
                    TicTacToeApp ticTacToeApp = (TicTacToeApp) getActivity().getApplication();
                    ticTacToeApp.setPlayer1Name(player1Name);
                    ticTacToeApp.setPlayer2Name(player2Name);
                    mPlayersLoginFragmentEventsListener.onStartGameClick();
                } else {
                    Utils.showToast(getActivity(), R.string.error_player_name_empty);
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlayersLoginFragmentEventsListener) {
            mPlayersLoginFragmentEventsListener = (PlayersLoginFragmentEventsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PlayersLoginFragmentEventsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPlayersLoginFragmentEventsListener = null;
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
    public interface PlayersLoginFragmentEventsListener {
        void onStartGameClick();
    }
}
