package fr.tvbarthel.ivanbnw.home.player;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.cheerleader.library.player.CheerleaderPlayer;
import fr.tvbarthel.cheerleader.library.player.CheerleaderPlaylistListener;
import fr.tvbarthel.ivanbnw.player.PlaylistActor;
import fr.tvbarthel.ivanbnw.player.PlaylistSpectator;

/**
 * Implementation based on {@link fr.tvbarthel.cheerleader.library.player.CheerleaderPlayer}
 */
public class PlaylistActorImpl implements PlaylistActor, CheerleaderPlaylistListener {

    private final CheerleaderPlayer player;
    private PlaylistSpectator spectator;

    /**
     * Implementation based on {@link fr.tvbarthel.cheerleader.library.player.CheerleaderPlayer}
     *
     * @param player cheerleader player.
     */
    public PlaylistActorImpl(CheerleaderPlayer player) {
        this.player = player;
    }

    @Override
    public void attachSpectator(PlaylistSpectator spectator) {
        this.spectator = spectator;
        player.registerPlaylistListener(this);
    }

    @Override
    public void detachSpectator() {
        this.spectator = null;
        player.unregisterPlaylistListener(this);
    }

    @NonNull
    @Override
    public ArrayList<SoundCloudTrack> getCurrentPlaylistTracks() {
        return player.getTracks();
    }

    @Override
    public void removeTrack(int playlistIndex) {
        player.removeTrack(playlistIndex);
    }

    ///////////////////////////////////////////////////////////////////////
    ////////////////////////// PLAYLIST LISTENER //////////////////////////
    ///////////////////////////////////////////////////////////////////////


    @Override
    public void onTrackAdded(SoundCloudTrack track) {
        if (spectator != null) {
            spectator.onTrackAdded(track);
        }
    }

    @Override
    public void onTrackRemoved(SoundCloudTrack track, boolean isEmpty) {
        if (spectator != null) {
            spectator.onTrackRemoved(track, isEmpty);
        }
    }
}
