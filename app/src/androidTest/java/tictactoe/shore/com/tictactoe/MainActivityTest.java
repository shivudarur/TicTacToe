package tictactoe.shore.com.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;

import java.lang.Class;

import tictactoe.shore.com.tictactoe.MainActivity;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;
    public MainActivityTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @java.lang.Override
    public void setUp() throws Exception {
        solo=new Solo(getInstrumentation(), getActivity());
    }

    @java.lang.Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testACtivityEvents() throws Exception {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
    }
}
