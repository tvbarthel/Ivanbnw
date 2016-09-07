package fr.tvbarthel.ivanbnw.player;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.netcosports.recyclergesture.library.swipe.SwipeToDismissGesture;

import java.util.ArrayList;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;

/**
 * Used to adapt {@link TrackView} and {@link PlayerView}
 * inside a {@link RecyclerView}
 */
class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder>
        implements SwipeToDismissGesture.Dismisser {

    private static final int TYPE_PLAYER = 0x00000001;
    private static final int TYPE_TRACK = 0x00000002;

    private final ArrayList<SoundCloudTrack> tracks;
    private Listener listener;

    /**
     * Used to adapt {@link TrackView} and {@link PlayerView}
     * inside a {@link RecyclerView}
     *
     * @param tracks tracks to adapt.
     */
    public PlaylistAdapter(@NonNull ArrayList<SoundCloudTrack> tracks) {
        this.tracks = tracks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_PLAYER:
                PlayerView playerView = new PlayerView(parent.getContext());
                playerView.setLayoutParams(
                        new RecyclerView.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                );
                return new ViewHolder(playerView);
            case TYPE_TRACK:
                TrackView trackView = new TrackView(parent.getContext());
                trackView.setLayoutParams(
                        new RecyclerView.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                );
                return new ViewHolder(trackView);
            default:
                throw new IllegalStateException("View type not handled : " + viewType);

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_PLAYER:
                // nothing
                break;
            case TYPE_TRACK:
                // -1 for the header
                holder.trackView.presentData(tracks.get(position - 1));
                break;
            default:
                throw new IllegalStateException("View type not handled :" + holder.getItemViewType());
        }
    }

    @Override
    public int getItemCount() {
        return tracks.size() + 1; // player
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_PLAYER;
        } else {
            return TYPE_TRACK;
        }
    }

    @Override
    public void dismiss(int i) {
        if (listener != null) {
            tracks.remove(i - 1);
            listener.onRemoveTrackFromPlaylistRequested(i - 1);
        }
    }

    /**
     * Set a listener used to catch adapted view events.
     *
     * @param listener listener used to catch view events.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Notify that a track has been added at the end of the playlist.
     */
    public void notifyTrackInserted() {
        notifyItemInserted(tracks.size() + 1);
    }

    /**
     * Notify that a track has been removed from the playlist.
     *
     * @param i index of the removed track.
     */
    public void notifyTrackRemoved(int i) {
        notifyItemRemoved(i + 1);
    }


    /**
     * View Holder pattern.
     */
    public static final class ViewHolder extends RecyclerView.ViewHolder {

        private PlayerView playerView;
        private TrackView trackView;

        /**
         * View holder pattern.
         *
         * @param playerView player view to hold.
         */
        public ViewHolder(PlayerView playerView) {
            super(playerView);
            this.playerView = playerView;
        }

        /**
         * View holder pattern.
         *
         * @param trackView track view to hold.
         */
        public ViewHolder(TrackView trackView) {
            super(trackView);
            this.trackView = trackView;
        }
    }

    /**
     * Listener used to catch adapted view events.
     */
    public interface Listener {
        /**
         * Called when the user wants to remove a track from the current playlist.
         *
         * @param playlistIndex index of the track to remove inside the current playlist.
         */
        void onRemoveTrackFromPlaylistRequested(int playlistIndex);
    }
}
