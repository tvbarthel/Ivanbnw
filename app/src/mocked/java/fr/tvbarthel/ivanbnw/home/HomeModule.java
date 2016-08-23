package fr.tvbarthel.ivanbnw.home;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.tvbarthel.cheerleader.library.client.MockedModelProvider;

/**
 * Module used to provide every components linked to the home.
 */
@Module
public class HomeModule {

    /**
     * Provide a {@link HomeActor} to the graph.
     *
     * @param mockedModelProvider provider for mocked model.
     * @return {@link HomeActor} implementation.
     */
    @Provides
    @Singleton
    public HomeActor provideHomeActor(MockedModelProvider mockedModelProvider) {
        return new MockedHomeActorImpl(
                mockedModelProvider.getMockedArtist(),
                mockedModelProvider.getMockedTracks()
        );
    }
}
