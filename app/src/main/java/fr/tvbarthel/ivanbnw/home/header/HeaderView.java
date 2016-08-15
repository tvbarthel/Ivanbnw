package fr.tvbarthel.ivanbnw.home.header;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Encapsulate common behavior linked to a header.
 */
class HeaderView extends FrameLayout {

    private static final float SCREEN_HEIGHT_RATIO = 0.8f;

    private int height;

    /**
     * Encapsulate common behavior linked to a header.
     *
     * @param context holding context.
     */
    public HeaderView(Context context) {
        this(context, null);
    }

    /**
     * Encapsulate common behavior linked to a header.
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public HeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Encapsulate common behavior linked to a header.
     *
     * @param context      holding context.
     * @param attrs        attr from xml.
     * @param defStyleAttr style from xml.
     */
    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        );
    }

    /**
     * Initialize internal components.
     *
     * @param context holding context.
     */
    protected void initialize(Context context) {
        WindowManager windowManager
                = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        height = (int) (displayMetrics.heightPixels * SCREEN_HEIGHT_RATIO);
    }
}
