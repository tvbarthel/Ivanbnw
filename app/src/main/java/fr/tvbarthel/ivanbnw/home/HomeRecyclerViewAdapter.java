package fr.tvbarthel.ivanbnw.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.ivanbnw.R;
import fr.tvbarthel.ivanbnw.home.header.ArtistHeaderView;
import fr.tvbarthel.ivanbnw.home.track.CardTrackView;

/**
 * Adapter used to render {@link HomeData} inside
 * a {@link android.support.v7.widget.RecyclerView}
 */
class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    /**
     * View type for artist view.
     */
    private static final int TYPE_ARTIST = 0x00000001;

    /**
     * View type for track view.
     */
    private static final int TYPE_TRACK = 0x00000002;
    private final int padding;

    /**
     * Current {@link HomeData} adapted.
     */
    private HomeData homeData;

    /**
     * Artist tracks.
     */
    private ArrayList<SoundCloudTrack> artistTracks;

    /**
     * Listener.
     */
    private Listener listener;

    /**
     * Listener used to catch {@link CardTrackView} events.
     */
    private CardTrackView.Listener internalTrackViewListener;

    /**
     * Position of the current played track.
     */
    private int currentPlayingTrackIndex = -1;

    /**
     * Adapter used to render {@link HomeData} inside
     * a {@link android.support.v7.widget.RecyclerView}
     *
     * @param context context used to initialize internal component.
     */
    public HomeRecyclerViewAdapter(Context context) {
        internalTrackViewListener = new CardTrackView.Listener() {
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

        padding = context.getResources().getDimensionPixelSize(R.dimen.default_padding_half);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        switch (viewType) {
            case TYPE_ARTIST:
                ArtistHeaderView artistView = new ArtistHeaderView(parent.getContext());
                viewHolder = new ViewHolder(artistView);
                break;
            case TYPE_TRACK:
                CardTrackView trackView = new CardTrackView(parent.getContext());
                trackView.setListener(internalTrackViewListener);

                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(padding, padding, padding, padding);
                trackView.setLayoutParams(layoutParams);
                viewHolder = new ViewHolder(trackView);
                break;
            default:
                throw new IllegalStateException("view type not handled : " + viewType);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_ARTIST:
                holder.artistView.presentData(homeData.getArtist());
                break;
            case TYPE_TRACK:
                // -1 since artist view is display at first position.
                int trackPosition = position - 1;
                holder.trackView.setBackground(trackPosition == currentPlayingTrackIndex);
                holder.trackView.presentData(artistTracks.get(trackPosition));
                break;
            default:
                throw new IllegalStateException("view type not handled : " + holder.getItemViewType());
        }
    }

    @Override
    public int getItemCount() {
        if (artistTracks == null) {
            return 0;
        } else {
            return artistTracks.size() + 1; // for artist view.
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ARTIST;
        } else {
            return TYPE_TRACK;
        }
    }

    /**
     * Set the {@link HomeData} which must be adapted.
     *
     * @param homeData home data to adapt.
     */
    public void setHomeData(@NonNull HomeData homeData) {
        this.homeData = homeData;
        artistTracks = homeData.getTracks();
    }

    /**
     * Set a listener to catch adapted views events.
     *
     * @param listener listener.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Notify that current playing track has changed.
     *
     * @param track current played track.
     */
    public void notifyPlayingTrackChanged(@NonNull SoundCloudTrack track) {
        ArrayList<SoundCloudTrack> tracks = homeData.getTracks();
        int size = tracks.size();
        int newCurrentPlayingTrackIndex = -1;
        for (int i = 0; i < size; i++) {
            if (track.equals(tracks.get(i))) {
                newCurrentPlayingTrackIndex = i;
                break;
            }
        }
        // update the view of the current playing track.
        if (currentPlayingTrackIndex != -1) {
            notifyItemChanged(currentPlayingTrackIndex + 1);
        }
        // update view for the new playing track.
        currentPlayingTrackIndex = newCurrentPlayingTrackIndex;
        if (currentPlayingTrackIndex != -1) {
            notifyItemChanged(currentPlayingTrackIndex + 1);
        }
    }

    /**
     * View Holder pattern.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ArtistHeaderView artistView;
        private CardTrackView trackView;

        /**
         * View holder pattern.
         *
         * @param artistView artistView to hold.
         */
        public ViewHolder(ArtistHeaderView artistView) {
            super(artistView);
            this.artistView = artistView;
        }

        /**
         * View holder pattern.
         *
         * @param trackView trackView to hold.
         */
        public ViewHolder(CardTrackView trackView) {
            super(trackView);
            this.trackView = trackView;
        }

    }

    /**
     * Listener used to catch adapted views events.
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
