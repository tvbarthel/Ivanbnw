package fr.tvbarthel.ivanbnw.player;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.ivanbnw.R;
import fr.tvbarthel.ivanbnw.utils.BackgroundUtils;

/**
 * View used to display a
 * {@link fr.tvbarthel.cheerleader.library.client.SoundCloudTrack}
 * inside the {@link PlaylistView}
 */
class TrackView extends FrameLayout {

    private TextView trackTitle;
    private TextView duration;
    private ImageView jacket;
    private String durationFormat;
    private int selectedBackground;

    /**
     * View used to display a
     * {@link fr.tvbarthel.cheerleader.library.client.SoundCloudTrack}
     * inside the {@link PlaylistView}
     *
     * @param context holding context.
     */
    public TrackView(Context context) {
        this(context, null);
    }

    /**
     * View used to display a
     * {@link fr.tvbarthel.cheerleader.library.client.SoundCloudTrack}
     * inside the {@link PlaylistView}
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public TrackView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * View used to display a
     * {@link fr.tvbarthel.cheerleader.library.client.SoundCloudTrack}
     * inside the {@link PlaylistView}
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
     * Set the track which must be displayed inside the view.
     *
     * @param track current track to display.
     */
    public void presentData(@NonNull SoundCloudTrack track) {
        trackTitle.setText(track.getTitle());
        long seconds = track.getDurationInMilli() / 1000;
        duration.setText(String.format(durationFormat, seconds / 60, seconds % 60));
        Picasso.with(getContext())
            .load(track.getArtworkUrl())
            .centerInside()
            .fit()
            .into(jacket);
    }

    /**
     * Initialize internal component.
     *
     * @param context holding context.
     */
    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.playlist_track_view, this);
        trackTitle = ((TextView) findViewById(R.id.playlist_track_view_title));
        duration = ((TextView) findViewById(R.id.playlist_track_view_duration));
        jacket = ((ImageView) findViewById(R.id.playlist_track_view_jacket));
        durationFormat = context.getString(R.string.duration);

        setBackground(ContextCompat.getDrawable(context, R.drawable.playlist_track_view_background));

        int padding = context.getResources().getDimensionPixelSize(R.dimen.default_padding);
        setPadding(padding, padding / 2, padding, padding / 2);

        setForeground(
            BackgroundUtils.buildBackgroundDrawable(
                Color.TRANSPARENT,
                ContextCompat.getColor(context, R.color.playlist_track_view_background_pressed)
            )
        );
    }
}
