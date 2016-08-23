package fr.tvbarthel.ivanbnw.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.ivanbnw.R;
import fr.tvbarthel.ivanbnw.utils.BackgroundUtils;

/**
 * View used to display a {@link SoundCloudTrack} in the
 * {@link HomeView}
 */
class TrackView extends FrameLayout {

    private TextView trackTitle;
    private SoundCloudTrack track;
    private Listener listener;
    private Drawable nonPlayingBackground;
    private Drawable playingBackground;

    /**
     * View used to display a {@link SoundCloudTrack} in the
     * {@link HomeView}
     *
     * @param context holding context.
     */
    public TrackView(Context context) {
        this(context, null);
    }

    /**
     * View used to display a {@link SoundCloudTrack} in the
     * {@link HomeView}
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public TrackView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * View used to display a {@link SoundCloudTrack} in the
     * {@link HomeView}
     *
     * @param context      holding context.
     * @param attrs        attr from xml.
     * @param defStyleAttr style from xml.
     */
    public TrackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    /**
     * Set the {@link SoundCloudTrack} which must be displayed.
     *
     * @param track track to display.
     */
    public void presentData(@NonNull SoundCloudTrack track) {
        this.track = track;
        trackTitle.setText(track.getTitle());
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
            BackgroundUtils.setBackground(this, playingBackground);
        } else {
            BackgroundUtils.setBackground(this, nonPlayingBackground);
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

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPlayTrackRequested(track);
                }
            }
        });

        nonPlayingBackground = BackgroundUtils.buildBackgroundDrawable(
                ContextCompat.getColor(context, R.color.track_view_normal_color_not_playing),
                ContextCompat.getColor(context, R.color.track_view_pressed_color_not_playing)
        );

        playingBackground = BackgroundUtils.buildBackgroundDrawable(
                ContextCompat.getColor(context, R.color.track_view_normal_color_playing),
                ContextCompat.getColor(context, R.color.track_view_pressed_color_playing)
        );
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
    }
}
