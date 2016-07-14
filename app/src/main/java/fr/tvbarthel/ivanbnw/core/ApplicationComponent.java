package fr.tvbarthel.ivanbnw.core;

import javax.inject.Singleton;

import dagger.Component;
import fr.tvbarthel.ivanbnw.MainActivity;
import fr.tvbarthel.ivanbnw.home.HomeModule;
import fr.tvbarthel.ivanbnw.home.HomeView;

/**
 * Component whose lifetime is going to be the lifetime of the application.
 */

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {
        ApplicationModule.class,
        HomeModule.class
})
public interface ApplicationComponent {

    /**
     * Field injections of any dependencies of the app
     *
     * @param mainActivity activity for which dependencies must be satisfied.
     */
    void inject(MainActivity mainActivity);

    /**
     * Field injections of any dependencies of the app
     *
     * @param homeView dashboardView for which dependencies must be satisfied.
     */
    void inject(HomeView homeView);
}
