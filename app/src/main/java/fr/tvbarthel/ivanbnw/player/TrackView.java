package fr.tvbarthel.ivanbnw.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.ivanbnw.R;

/**
 * View used to display a
 * {@link fr.tvbarthel.cheerleader.library.client.SoundCloudTrack}
 * inside the {@link PlaylistView}
 */
class TrackView extends FrameLayout {

    private TextView trackTitle;

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
    }

    /**
     * Initialize internal component.
     *
     * @param context holding context.
     */
    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.playlist_track_view, this);
        trackTitle = ((TextView) findViewById(R.id.playlist_track_view_title));
    }
}
