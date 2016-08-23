package fr.tvbarthel.ivanbnw.home.track;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.ivanbnw.R;

/**
 * Used to display a {@link fr.tvbarthel.cheerleader.library.client.SoundCloudTrack}
 * inside a {@link android.support.v7.widget.CardView}
 */
public class CardTrackView extends CardView {

    private TrackView trackView;
    private Listener listener;
    private TrackView.Listener internalListener;

    /**
     * Used to display a {@link fr.tvbarthel.cheerleader.library.client.SoundCloudTrack}
     * inside a {@link android.support.v7.widget.CardView}
     *
     * @param context holding context.
     */
    public CardTrackView(Context context) {
        this(context, null);
    }

    /**
     * Used to display a {@link fr.tvbarthel.cheerleader.library.client.SoundCloudTrack}
     * inside a {@link android.support.v7.widget.CardView}
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public CardTrackView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Used to display a {@link fr.tvbarthel.cheerleader.library.client.SoundCloudTrack}
     * inside a {@link android.support.v7.widget.CardView}
     *
     * @param context      holding context.
     * @param attrs        attr from xml.
     * @param defStyleAttr style from xml.
     */
    public CardTrackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    /**
     * Set the {@link SoundCloudTrack} which must be displayed inside the card.
     *
     * @param track track to display.
     */
    public void presentData(@NonNull SoundCloudTrack track) {
        trackView.presentData(track);
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
        trackView.setBackground(isPlaying);
    }

    /**
     * Initialize internal component.
     *
     * @param context holding context.
     */
    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.card_track_view, this);
        trackView = ((TrackView) findViewById(R.id.card_track_view_track_view));

        internalListener = new TrackView.Listener() {
            @Override
            public void onPlayTrackRequested(SoundCloudTrack track) {
                if (listener != null) {
                    listener.onPlayTrackRequested(track);
                }
            }

            @Override
            public void onAddToPlaylistRequested(SoundCloudTrack track) {
                if (listener != null) {
                    listener.onAddToPlaylistRequested(track);
                }
            }
        };
        trackView.setListener(internalListener);

        Resources resources = context.getResources();
        setRadius(resources.getDimensionPixelSize(R.dimen.card_track_view_radius));
        setCardElevation(resources.getDimensionPixelSize(R.dimen.card_track_view_elevation));
    }

    /**
     * Listener used to catch card view events.
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
