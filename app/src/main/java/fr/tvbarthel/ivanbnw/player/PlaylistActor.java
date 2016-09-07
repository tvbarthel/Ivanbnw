package fr.tvbarthel.ivanbnw.player;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;

/**
 * Actor used to define the business logic need by the playlist.
 */
public interface PlaylistActor {

    /**
     * Attach a spectator to get callback from the actor.
     * <p/>
     * Might want register every internal Listener.
     * <p/>
     * See also: {@link PlaylistActor#detachSpectator()}
     *
     * @param spectator spectator to attached.
     */
    void attachSpectator(PlaylistSpectator spectator);

    /**
     * Detach the previous attached spectator.
     * <p/>
     * Must unregistered avery internal listener.
     * <p/>
     * See also: {@link PlaylistActor#attachSpectator(PlaylistSpectator)}
     */
    void detachSpectator();

    /**
     * Used to retrieve the current tracks inside the playlist.
     *
     * @return current playlist tracks.
     */
    @NonNull
    ArrayList<SoundCloudTrack> getCurrentPlaylistTracks();

    /**
     * Remove a track from the current playlist.
     * <p/>
     * Must remove a track from the playlist.
     * <p/>
     * See also: {@link PlaylistSpectator#onTrackRemoved(SoundCloudTrack, boolean)}
     *
     * @param playlistIndex index of the track to remove.
     */
    void removeTrack(int playlistIndex);
}
