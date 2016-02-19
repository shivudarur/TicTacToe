package tictactoe.shore.com.tictactoe;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tictactoe.shore.com.tictactoe.adapters.PlayerListAdapter;
import tictactoe.shore.com.tictactoe.model.Player;
import tictactoe.shore.com.tictactoe.sqlite.DatabaseHandler;
import tictactoe.shore.com.tictactoe.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LeaderboardFragment.LeaderboardFragmentEventsListener} interface
 * to handle interaction events.
 * Use the {@link LeaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardFragment extends Fragment implements View.OnClickListener {

    private LeaderboardFragmentEventsListener mLeaderboardFragmentEventsListener;
    private View mRootView;
    private RecyclerView mRecyclerView;
    private DatabaseHandler mDatabaseHandler;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LeaderboardFragment.
     */
    public static LeaderboardFragment newInstance() {
        return new LeaderboardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_leader_board, container, false);
        init();
        bindData();
        return mRootView;
    }

    private void init() {
        mRootView.findViewById(R.id.button_new_game).setOnClickListener(this);
        // Initialize recycler view
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view_players);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void bindData() {
        List<Player> mPlayers = mDatabaseHandler.getAllPlayers();
        if (mPlayers != null && !mPlayers.isEmpty()) {
            PlayerListAdapter mPlayerListAdapter = new PlayerListAdapter(mPlayers, getActivity());
            mRecyclerView.setAdapter(mPlayerListAdapter);
        } else {
            Utils.showToast(getActivity(), R.string.no_players);
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mDatabaseHandler = new DatabaseHandler(getActivity());
        if (context instanceof LeaderboardFragmentEventsListener) {
            mLeaderboardFragmentEventsListener = (LeaderboardFragmentEventsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LeaderboardFragmentEventsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLeaderboardFragmentEventsListener = null;
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
                if (mLeaderboardFragmentEventsListener != null) {
                    mLeaderboardFragmentEventsListener.onNewGameClick();
                }
                break;
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
    public interface LeaderboardFragmentEventsListener {
        void onNewGameClick();
    }
}
