package fr.tvbarthel.ivanbnw.core;

import javax.inject.Singleton;

import dagger.Component;
import fr.tvbarthel.ivanbnw.home.HomeModule;
import fr.tvbarthel.ivanbnw.home.HomeView;
import fr.tvbarthel.ivanbnw.home.player.PlaylistModule;
import fr.tvbarthel.ivanbnw.player.PlaylistView;

/**
 * Component whose lifetime is going to be the lifetime of the application.
 */

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {
        ApplicationModule.class,
        HomeModule.class,
        CoreModule.class,
        PlaylistModule.class
})
public interface ApplicationComponent {

    /**
     * Field injections of any dependencies of the app
     *
     * @param homeView dashboardView for which dependencies must be satisfied.
     */
    void inject(HomeView homeView);

    /**
     * Field injections of any dependencies of the app
     *
     * @param playlistView playlist view for which dependencies must be satisfied.
     */
    void inject(PlaylistView playlistView);
}
