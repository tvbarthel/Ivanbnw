package fr.tvbarthel.ivanbnw.fr;

import android.support.annotation.Nullable;

import fr.tvbarthel.ivanbnw.test.MainActivityTest;

/**
 * Ensure that main activity feature won't break in FR locale.
 */
public class FrMainActivityTest extends MainActivityTest {

    @Nullable
    @Override
    protected String overrideActivityLanguage() {
        return "fr";
    }
}
