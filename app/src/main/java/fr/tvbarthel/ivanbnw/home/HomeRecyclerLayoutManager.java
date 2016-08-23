package fr.tvbarthel.ivanbnw.home;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import fr.tvbarthel.ivanbnw.R;

/**
 * {@link android.support.v7.widget.RecyclerView.LayoutManager} used to render
 * the {@link HomeData} in a grid form.
 */
public class HomeRecyclerLayoutManager extends GridLayoutManager {

    /**
     * {@link android.support.v7.widget.RecyclerView.LayoutManager} used to render
     * the {@link HomeData} in a grid form.
     *
     * @param context holding context.
     */
    public HomeRecyclerLayoutManager(Context context) {
        super(context, context.getResources().getInteger(R.integer.home_recycler_span_count));
        setSpanSizeLookup(new InternalSpanSizeLookup(getSpanCount()));
    }

    /**
     * Internal span size lookup.
     */
    private static final class InternalSpanSizeLookup extends SpanSizeLookup {

        private final int spanCount;

        public InternalSpanSizeLookup(int spanCount) {
            this.spanCount = spanCount;
        }

        @Override
        public int getSpanSize(int position) {
            if (position == 0) {
                return spanCount; // artist view header take full width.
            } else {
                return 1; // a track take only one span.
            }
        }
    }
}
