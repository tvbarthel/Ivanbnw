package fr.tvbarthel.ivanbnw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity declared as Launcher activity.
 * <p/>
 * Name as well as package name can't be change, never ever.
 * http://android-developers.blogspot.fr/2011/06/things-that-cannot-change.html
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
