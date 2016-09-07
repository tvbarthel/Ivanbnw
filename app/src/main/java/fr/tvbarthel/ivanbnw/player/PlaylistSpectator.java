package fr.tvbarthel.ivanbnw.player;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;

/**
 * Spectator used to define the visual needs linked to the playlist actor.
 */
public interface PlaylistSpectator {

    /**
     * Called when a tracks has been added to the player playlist.
     *
     * @param track track added.
     */
    void onTrackAdded(SoundCloudTrack track);


    /**
     * Called when a tracks has been removed from the player playlist.
     *
     * @param track   track removed.
     * @param isEmpty true if the playlist is empty after deletion.
     */
    void onTrackRemoved(SoundCloudTrack track, boolean isEmpty);

}
