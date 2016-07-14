package fr.tvbarthel.ivanbnw.core;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module used to provide any components linked to the
 * application.
 */
@Module
public class ApplicationModule {

    /**
     * Holding application.
     */
    private final IvanbApplication application;

    /**
     * Module used to provide any components linked to the
     * application.
     *
     * @param application holding application.
     */
    public ApplicationModule(IvanbApplication application) {
        this.application = application;
    }

    /**
     * Provide the application for the graph.
     *
     * @return holding application.
     */
    @Provides
    @Singleton
    public IvanbApplication provideApplication() {
        return application;
    }

    /**
     * Provide the context of the holding application for the graph.
     *
     * @return holding application context.
     */
    @Provides
    @Singleton
    public Context provideContext() {
        return application.getApplicationContext();
    }
}
