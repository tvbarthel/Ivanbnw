package fr.tvbarthel.ivanbnw.core;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.test.ActivityInstrumentationTestCase2;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Instrumentation test case which can be localized.
 */
public class LocalizedActivityInstrumentationTestCase2<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    /**
     * Language of the activity used by the given test case.
     */
    protected String instrumentationLanguageCode;


    /**
     * Instrumentation test case which can be localized.
     *
     * @param activityClass activity to launch for the test.
     */
    public LocalizedActivityInstrumentationTestCase2(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Context app = getInstrumentation().getTargetContext().getApplicationContext();
        Configuration configuration = app.getResources().getConfiguration();
        DisplayMetrics displayMetrics = app.getResources().getDisplayMetrics();
        instrumentationLanguageCode = overrideActivityLanguage();
        if (instrumentationLanguageCode != null) {
            configuration.locale = new Locale(instrumentationLanguageCode);
            app.getResources().updateConfiguration(configuration, displayMetrics);
        } else {
            instrumentationLanguageCode = configuration.locale.getLanguage();
        }
    }

    /**
     * Allow to override the language of the activity used by the given test case.
     *
     * @return language code to used for the whole test case.
     */
    @Nullable
    protected String overrideActivityLanguage() {
        return null;
    }
}
