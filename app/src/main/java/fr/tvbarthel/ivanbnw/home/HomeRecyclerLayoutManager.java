package fr.tvbarthel.ivanbnw.home;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * {@link android.support.v7.widget.RecyclerView.LayoutManager} used to render
 * the {@link HomeData} in a grid form.
 */
public class HomeRecyclerLayoutManager extends GridLayoutManager {

    private static final int SPAN_COUNT = 2;

    /**
     * {@link android.support.v7.widget.RecyclerView.LayoutManager} used to render
     * the {@link HomeData} in a grid form.
     *
     * @param context holding context.
     */
    public HomeRecyclerLayoutManager(Context context) {
        super(context, SPAN_COUNT);
        setSpanSizeLookup(new InternalSpanSizeLookup());
    }

    /**
     * Internal span size lookup.
     */
    private static final class InternalSpanSizeLookup extends SpanSizeLookup {

        @Override
        public int getSpanSize(int position) {
            if (position == 0) {
                return SPAN_COUNT; // artist view header take full width.
            } else {
                return 1; // a track take only one span.
            }
        }
    }
}
