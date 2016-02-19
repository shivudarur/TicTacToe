package tictactoe.shore.com.tictactoe;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A fragment to display information about the tictactoe game
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment implements View.OnClickListener {
    private AboutFragmentEventsListener mAboutFragmentEventsListener;

    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AboutFragment.
     */
    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_about, container,
                false);
        WebView aboutWebView;
        aboutWebView = (WebView) rootView.findViewById(R.id.webview_about);
        aboutWebView
                .loadUrl("file:///android_asset/tic_tac_toe_wikipedia.html");
        initViews(rootView);
        return rootView;

    }

    private void initViews(View rootView) {
        rootView.findViewById(R.id.button_new_game).setOnClickListener(
                AboutFragment.this);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof AboutFragmentEventsListener) {
            mAboutFragmentEventsListener = (AboutFragmentEventsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AboutFragmentEventsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAboutFragmentEventsListener = null;
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
                mAboutFragmentEventsListener.onNewGameClick();
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
    public interface AboutFragmentEventsListener {
        void onNewGameClick();
    }
}
