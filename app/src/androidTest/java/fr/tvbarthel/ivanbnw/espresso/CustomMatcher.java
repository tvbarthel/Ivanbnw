package fr.tvbarthel.ivanbnw.espresso;

import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Custom matcher for espresso.
 */
public class CustomMatcher {

    /**
     * Match a given alpha.
     *
     * @param alpha alpha to match.
     * @return matcher.
     */
    public static org.hamcrest.Matcher<View> hasAlpha(float alpha) {
        return new AlphaMatcher(alpha);
    }

    /**
     * Custom matcher used to match a given alpha.
     */
    private static final class AlphaMatcher extends TypeSafeMatcher<View> {

        private final float alpha;

        /**
         * Custom matcher used to match a given alpha.
         *
         * @param alpha alpha at which view must be.
         */
        public AlphaMatcher(float alpha) {
            this.alpha = alpha;
        }

        @Override
        public boolean matchesSafely(View view) {
            return view.getAlpha() == alpha;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("is alpha equals to : " + alpha);
        }
    }
}
