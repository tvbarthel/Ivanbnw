package fr.tvbarthel.ivanbnw.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import fr.tvbarthel.ivanbnw.R;

/**
 * Used to display a player interface to the user.
 */
public class PlayerView extends FrameLayout {

    private int height;

    /**
     * Used to display a player interface to the user.
     *
     * @param context holding context.
     */
    public PlayerView(Context context) {
        this(context, null);
    }

    /**
     * Used to display a player interface to the user.
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public PlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Used to display a player interface to the user.
     *
     * @param context      holding context.
     * @param attrs        attr from xml.
     * @param defStyleAttr style from xml.
     */
    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
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
     * Used to initialize internal component.
     *
     * @param context holding context.
     */
    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.player_view, this);
        height = context.getResources().getDimensionPixelSize(R.dimen.player_view_height);
    }
}
