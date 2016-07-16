package fr.tvbarthel.ivanbnw.core;

import fr.tvbarthel.ivanbnw.home.HomeInteractor;
import fr.tvbarthel.ivanbnw.home.HomeModule;

import static org.mockito.Mockito.mock;

/**
 * Application used for instrumentation testing.
 * <p/>
 * Basically, it's allow to inject mocked interactor thanks to mockito.
 * Thus, each instrument test can specify it's own behavior.
 */
public class InstrumentationIvanbApplication extends IvanbApplication {

    public HomeInteractor mockedHomeInteractor;

    @Override
    protected ApplicationComponent initComponent() {
        // create mocked interactor.
        mockedHomeInteractor = mock(HomeInteractor.class);

        return DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .homeModule(new HomeModule() {
                    @Override
                    public HomeInteractor provideHomeInteractor() {
                        return mockedHomeInteractor;
                    }
                })
                .build();
    }
}
