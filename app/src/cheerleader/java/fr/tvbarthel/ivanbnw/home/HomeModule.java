package fr.tvbarthel.ivanbnw.home;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module used to provide every components linked to the home.
 */
@Module
public class HomeModule {

    /**
     * Provide a {@link HomeInteractor} to the graph.
     *
     * @return {@link HomeInteractor} implementation.
     */
    @Provides
    @Singleton
    public HomeInteractor provideHomeInteractor() {
        return new MockedHomeInteractorImpl();
    }
}
