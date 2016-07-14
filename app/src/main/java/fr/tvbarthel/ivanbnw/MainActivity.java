package fr.tvbarthel.ivanbnw;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import fr.tvbarthel.ivanbnw.core.IvanbApplication;

/**
 * Activity declared as Launcher activity.
 * <p/>
 * Name as well as package name can't be change, never ever.
 * http://android-developers.blogspot.fr/2011/06/things-that-cannot-change.html
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Dummy injection used as injection example.
     * TODO to remove.
     */
    @Inject
    protected Context applicationContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((IvanbApplication) getApplication()).component().inject(this);

        // Dummy injection used as injection example.
        //TODO to remove.
        Log.d(TAG, "application context : " + applicationContext);
    }
}
