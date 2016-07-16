package fr.tvbarthel.ivanbnw.en;

import android.support.annotation.Nullable;

import fr.tvbarthel.ivanbnw.test.MainActivityTest;

/**
 * Ensure that main activity feature won't break in EN locale.
 */
public class EnMainActivityTest extends MainActivityTest {

    @Nullable
    @Override
    protected String overrideActivityLanguage() {
        return "en";
    }
}
