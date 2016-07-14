package fr.tvbarthel.ivanbnw.core;

import android.app.Application;

/**
 * Application used to build the app component and the whole graph.
 */
public class IvanbApplication extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    /**
     * Access to the application component.
     *
     * @return application component.
     */
    public static ApplicationComponent component() {
        return applicationComponent;
    }
}
