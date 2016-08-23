package fr.tvbarthel.ivanbnw.home;

import android.os.CountDownTimer;

import java.util.ArrayList;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.cheerleader.library.client.SoundCloudUser;

/**
 * Implementation completely mocked.
 */
class MockedHomeActorImpl implements HomeActor {

    private final HomeData homeData;
    private HomeSpectator spectator;

    /**
     * Implementation completely mocked.
     *
     * @param mockedArtist mocked artist.
     * @param mockedTracks mocked tracks.
     */
    public MockedHomeActorImpl(SoundCloudUser mockedArtist, ArrayList<SoundCloudTrack> mockedTracks) {
        // build mocked data.
        homeData = new HomeData(mockedArtist, mockedTracks);
    }

    @Override
    public void attachSpectator(HomeSpectator spectator) {
        this.spectator = spectator;
    }

    @Override
    public void detachSpectator() {
        this.spectator = null;
    }

    @Override
    public void requestHomeData() {
        spectator.onDisplayLoadingIndicatorRequested();

        new CountDownTimer(3000, 3000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (spectator != null) {
                    spectator.onHomeDataRetrieved(homeData);
                }
            }
        }.start();
    }

    @Override
    public void requestPlay(SoundCloudTrack track) {
        if (spectator != null) {
            spectator.onTrackPlaying(track);
        }
    }
}
