package fr.tvbarthel.ivanbnw.core;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.tvbarthel.cheerleader.library.client.MockedModelProvider;

/**
 * Module used to provide every core component.
 */
@Module
public class CoreModule {

    /**
     * Provide mocked model for the application.
     *
     * @return mocked model provider.
     */
    @Provides
    @Singleton
    MockedModelProvider provideMockeModelProvider() {
        return new MockedModelProvider();
    }
}
