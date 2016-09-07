package fr.tvbarthel.ivanbnw.home.player;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.ivanbnw.player.PlaylistActor;
import fr.tvbarthel.ivanbnw.player.PlaylistSpectator;

/**
 * Implementation completely mocked.
 */
public class MockedPlaylistActorImpl implements PlaylistActor {

    private final ArrayList<SoundCloudTrack> mockedTracks;
    private final ArrayList<SoundCloudTrack> currentTracks;
    private PlaylistSpectator spectator;

    /**
     * Implementation completely mocked.
     *
     * @param mockedTracks mocked tracks to simulate fake adds.
     */
    public MockedPlaylistActorImpl(ArrayList<SoundCloudTrack> mockedTracks) {
        currentTracks = new ArrayList<>();
        this.mockedTracks = mockedTracks;
    }

    @Override
    public void attachSpectator(PlaylistSpectator spectator) {
        this.spectator = spectator;

        new CountDownTimer(6000, 6000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (MockedPlaylistActorImpl.this.spectator != null) {
                    SoundCloudTrack addedTrack = mockedTracks.get(0);
                    currentTracks.add(addedTrack);
                    MockedPlaylistActorImpl.this.spectator.onTrackAdded(addedTrack);
                }
            }
        }.start();
    }

    @Override
    public void detachSpectator() {
        this.spectator = null;
    }

    @NonNull
    @Override
    public ArrayList<SoundCloudTrack> getCurrentPlaylistTracks() {
        return currentTracks;
    }

    @Override
    public void removeTrack(int playlistIndex) {
        SoundCloudTrack removedTrack = currentTracks.remove(playlistIndex);
        if (spectator != null) {
            spectator.onTrackRemoved(removedTrack, currentTracks.isEmpty());
        }
    }
}
