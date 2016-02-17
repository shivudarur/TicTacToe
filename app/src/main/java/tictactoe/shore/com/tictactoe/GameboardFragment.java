package tictactoe.shore.com.tictactoe;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameboardFragment.GameboardFragmentEventsListener} interface
 * to handle interaction events.
 * Use the {@link GameboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameboardFragment extends Fragment {

    private GameboardFragmentEventsListener mGameboardFragmentEventsListener;

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
        return inflater.inflate(R.layout.fragment_gameboard, container, false);
    }

    @Override
    public void onAttach(Context context) {
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
    }
}
