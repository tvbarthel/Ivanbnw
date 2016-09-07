package fr.tvbarthel.ivanbnw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.tvbarthel.ivanbnw.home.HomeView;

/**
 * Activity declared as Launcher activity.
 * <p/>
 * Name as well as package name can't be change, never ever.
 * http://android-developers.blogspot.fr/2011/06/things-that-cannot-change.html
 */
public class MainActivity extends AppCompatActivity {

    private HomeView homeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeView = ((HomeView) findViewById(R.id.main_activity_home_view));
    }

    @Override
    public void onBackPressed() {
        if (!homeView.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
