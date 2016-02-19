package tictactoe.shore.com.tictactoe;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements GameboardFragment.GameboardFragmentEventsListener, PlayersLoginFragment.PlayersLoginFragmentEventsListener, LeaderboardFragment.LeaderboardFragmentEventsListener, AboutFragment.AboutFragmentEventsListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            replaceFragment(PlayersLoginFragment.newInstance());
        }
    }

    private void replaceFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            replaceFragment(AboutFragment.newInstance());
        }
        return super.onOptionsItemSelected(item);
    }

    private void setActionBarTitle(int resourceId) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(resourceId);
    }

    @Override
    public void onProceedToLeaderboardClick() {
        replaceFragment(LeaderboardFragment.newInstance());
        setActionBarTitle(R.string.leader_board_fragment);
    }

    @Override
    public void onStartGameClick() {
        replaceFragment(GameboardFragment.newInstance());
        setActionBarTitle(R.string.gameboard_fragment);
    }

    @Override
    public void onNewGameClick() {
        replaceFragment(PlayersLoginFragment.newInstance());
        setActionBarTitle(R.string.players_login_fragment);
    }
}
