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
     * Match the first displayed view.
     *
     * @return matcher.
     */
    public static org.hamcrest.Matcher<View> firstView() {
        return new FirstViewMatcher();
    }

    /**
     * Matcher used to match the first displayed view.
     */
    private static class FirstViewMatcher extends TypeSafeMatcher<View> {

        public boolean matchedBefore = false;

        public FirstViewMatcher() {
            matchedBefore = false;
        }

        @Override
        public boolean matchesSafely(View view) {
            if (matchedBefore) {
                return false;
            } else {
                matchedBefore = true;
                return true;
            }
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(" is the first view that comes along ");
        }
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
