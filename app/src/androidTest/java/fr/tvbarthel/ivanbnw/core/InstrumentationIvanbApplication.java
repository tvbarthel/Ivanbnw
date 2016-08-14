package fr.tvbarthel.ivanbnw.core;

import fr.tvbarthel.cheerleader.library.client.MockedModelProvider;
import fr.tvbarthel.ivanbnw.home.HomeActor;
import fr.tvbarthel.ivanbnw.home.HomeModule;

import static org.mockito.Mockito.mock;

/**
 * Application used for instrumentation testing.
 * <p/>
 * Basically, it's allow to inject mocked actor thanks to mockito.
 * Thus, each instrument test can specify it's own behavior.
 */
public class InstrumentationIvanbApplication extends IvanbApplication {

    public HomeActor mockedHomeActor;

    @Override
    protected ApplicationComponent initComponent() {
        // create mocked actor.
        mockedHomeActor = mock(HomeActor.class);

        return DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .homeModule(new HomeModule() {

                    @Override
                    public HomeActor provideHomeActor(MockedModelProvider mockedModelProvider) {
                        return mockedHomeActor;
                    }
                })
                .build();
    }
}
