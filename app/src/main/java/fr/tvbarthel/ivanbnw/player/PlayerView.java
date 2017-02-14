package fr.tvbarthel.ivanbnw.player;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import fr.tvbarthel.ivanbnw.R;

/**
 * Used to display a player interface to the user.
 */
public class PlayerView extends FrameLayout {

    private int height;
    private TextView trackTitle;
    private SeekBar seekbar;
    private TextView currentTime;
    private TextView trackDuration;

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
        trackTitle = ((TextView) findViewById(R.id.player_view_track_title));
        seekbar = ((SeekBar) findViewById(R.id.player_view_seekbar));
        currentTime = ((TextView) findViewById(R.id.player_view_current_play_time));
        trackDuration = ((TextView) findViewById(R.id.player_view_track_duration));

        Resources resources = context.getResources();
        height = resources.getDimensionPixelSize(R.dimen.player_view_height);

        seekbar.getThumb().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        seekbar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    }
}
