package fr.tvbarthel.ivanbnw.home.track;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.cheerleader.library.helpers.SoundCloudArtworkHelper;
import fr.tvbarthel.ivanbnw.R;
import fr.tvbarthel.ivanbnw.utils.BackgroundUtils;

/**
 * View used to display a {@link SoundCloudTrack} in the
 * {@link fr.tvbarthel.ivanbnw.home.HomeView}
 */
class TrackView extends FrameLayout {

    private static final float HEIGHT_WIDTH_RATIO = 1.2f;
    private static final float BUTTON_BAR_HEIGHT_RATIO = 1f / 5f;
    private static final float FONT_SIZE_HEIGHT_RATIO = 1 / 2.5f;

    private TextView trackTitle;
    private SoundCloudTrack track;
    private Listener listener;
    private ImageView cover;
    private TextView addButton;
    private TextView playButton;
    private View playingIndicator;
    private View trackTitleShadow;
    private View buttonsShadow;
    private int shadowHeight;

    /**
     * View used to display a {@link SoundCloudTrack} in the
     * {@link fr.tvbarthel.ivanbnw.home.HomeView}
     *
     * @param context holding context.
     */
    public TrackView(Context context) {
        this(context, null);
    }

    /**
     * View used to display a {@link SoundCloudTrack} in the
     * {@link fr.tvbarthel.ivanbnw.home.HomeView}
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public TrackView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * View used to display a {@link SoundCloudTrack} in the
     * {@link fr.tvbarthel.ivanbnw.home.HomeView}
     *
     * @param context      holding context.
     * @param attrs        attr from xml.
     * @param defStyleAttr style from xml.
     */
    public TrackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.getSize(widthMeasureSpec);

        int sizeSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);

        cover.measure(sizeSpec, sizeSpec);

        int half = (int) (size / 2f);
        int buttonHeight = (int) (size * BUTTON_BAR_HEIGHT_RATIO);
        addButton.measure(
                MeasureSpec.makeMeasureSpec(half, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(buttonHeight, MeasureSpec.EXACTLY)
        );
        addButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, addButton.getMeasuredHeight() * FONT_SIZE_HEIGHT_RATIO);

        playButton.measure(
                MeasureSpec.makeMeasureSpec(size - half, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(buttonHeight, MeasureSpec.EXACTLY)
        );
        playButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, playButton.getMeasuredHeight() * FONT_SIZE_HEIGHT_RATIO);

        trackTitle.measure(
                sizeSpec,
                MeasureSpec.makeMeasureSpec(buttonHeight, MeasureSpec.EXACTLY)
        );
        trackTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, trackTitle.getMeasuredHeight() * FONT_SIZE_HEIGHT_RATIO);

        playingIndicator.measure(
                MeasureSpec.makeMeasureSpec(size, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(buttonHeight, MeasureSpec.AT_MOST)
        );

        int shadowHeight = MeasureSpec.makeMeasureSpec(this.shadowHeight, MeasureSpec.EXACTLY);
        buttonsShadow.measure(sizeSpec, shadowHeight);
        trackTitleShadow.measure(sizeSpec, shadowHeight);

        setMeasuredDimension(
                sizeSpec,
                MeasureSpec.makeMeasureSpec((int) (size * HEIGHT_WIDTH_RATIO), MeasureSpec.EXACTLY)
        );
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        cover.layout(0, 0, cover.getMeasuredWidth(), cover.getMeasuredHeight());

        int height = getMeasuredHeight();
        int width = getMeasuredWidth();

        // bottom start card
        addButton.layout(
                0,
                height - addButton.getMeasuredHeight(),
                addButton.getMeasuredWidth(),
                height
        );

        // bottom card, left of add button.
        playButton.layout(
                addButton.getMeasuredWidth(),
                height - playButton.getMeasuredHeight(),
                addButton.getMeasuredWidth() + playButton.getMeasuredWidth(),
                height
        );

        // buttons shadow
        int buttonsTop = playButton.getTop();
        buttonsShadow.layout(0, buttonsTop - buttonsShadow.getMeasuredHeight(), width, buttonsTop);

        // full width above buttons.
        trackTitle.layout(
                0,
                height - addButton.getMeasuredHeight() - trackTitle.getMeasuredHeight(),
                trackTitle.getMeasuredWidth(),
                height - addButton.getMeasuredHeight()
        );

        // track title shadow
        int titleTop = trackTitle.getTop();
        trackTitleShadow.layout(0, titleTop - trackTitleShadow.getMeasuredHeight(), width, titleTop);

        // play indicator upper left corner
        playingIndicator.layout(0, 0, playingIndicator.getMeasuredWidth(), playingIndicator.getMeasuredHeight());
    }

    /**
     * Set the {@link SoundCloudTrack} which must be displayed.
     *
     * @param track track to display.
     */
    public void presentData(@NonNull SoundCloudTrack track) {
        this.track = track;
        trackTitle.setText(track.getTitle());
        Picasso.with(getContext()).load(
                SoundCloudArtworkHelper.getArtworkUrl(track, SoundCloudArtworkHelper.XXXLARGE)
        ).fit().centerInside().into(cover);
    }

    /**
     * Set a listener to catch view events.
     *
     * @param listener listener used to catch view events.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Display emphasis according to the playing state.
     *
     * @param isPlaying true if the track is currently being played, false otherwise.
     */
    public void setBackground(boolean isPlaying) {
        if (isPlaying) {
            playingIndicator.setVisibility(VISIBLE);
        } else {
            playingIndicator.setVisibility(INVISIBLE);
        }
    }

    /**
     * Initialize internal component.
     *
     * @param context holding context.
     */
    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.home_track_view, this);
        trackTitle = ((TextView) findViewById(R.id.home_track_view_track_title));
        cover = ((ImageView) findViewById(R.id.home_track_view_cover));
        addButton = ((TextView) findViewById(R.id.home_track_view_add_button));
        playButton = ((TextView) findViewById(R.id.home_track_view_play_button));
        playingIndicator = findViewById(R.id.home_track_view_playing_indicator);
        trackTitleShadow = findViewById(R.id.home_track_view_track_title_shadow);
        buttonsShadow = findViewById(R.id.home_track_view_buttons_shadow);

        OnClickListener internalListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.home_track_view_play_button:
                        if (listener != null) {
                            listener.onPlayTrackRequested(track);
                        }
                        break;
                    case R.id.home_track_view_add_button:
                        if (listener != null) {
                            listener.onAddToPlaylistRequested(track);
                        }
                        break;
                    default:
                        throw new IllegalStateException("Click not handled on : " + v);
                }
            }
        };

        playButton.setOnClickListener(internalListener);
        addButton.setOnClickListener(internalListener);

        int normalColor = ContextCompat.getColor(context, R.color.track_view_button_normal);
        int pressedColor = ContextCompat.getColor(context, R.color.track_view_button_pressed);
        BackgroundUtils.buildBackgroundDrawable(playButton, normalColor, pressedColor);
        BackgroundUtils.buildBackgroundDrawable(addButton, normalColor, pressedColor);

        shadowHeight = context.getResources().getDimensionPixelSize(R.dimen.shadow_size);
    }

    /**
     * Listener used to catch view events.
     */
    public interface Listener {
        /**
         * Called when the user wants to play the given track.
         *
         * @param track track to play.
         */
        void onPlayTrackRequested(SoundCloudTrack track);

        /**
         * Called when the user wants to add the track to the current playlist.
         *
         * @param track track to add to the playlist.
         */
        void onAddToPlaylistRequested(SoundCloudTrack track);
    }
}
