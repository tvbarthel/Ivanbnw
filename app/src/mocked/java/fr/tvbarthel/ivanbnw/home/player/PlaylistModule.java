package fr.tvbarthel.ivanbnw.home.player;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.tvbarthel.cheerleader.library.client.MockedModelProvider;
import fr.tvbarthel.ivanbnw.player.PlaylistActor;

/**
 * Module used to provide every components linked to the playlist.
 */
@Module
public class PlaylistModule {

    /**
     * Provide playlist actor.
     *
     * @param mockedModelProvider mocked provider used to simulate fake data.
     * @return {@link PlaylistActor} implementation.
     */
    @Provides
    @Singleton
    @NonNull
    PlaylistActor providePlaylistActor(MockedModelProvider mockedModelProvider) {
        return new MockedPlaylistActorImpl(mockedModelProvider.getMockedTracks());
    }
}
