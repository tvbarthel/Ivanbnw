package fr.tvbarthel.ivanbnw.test;

import android.support.test.espresso.matcher.ViewMatchers;
import android.test.suitebuilder.annotation.LargeTest;

import com.squareup.spoon.Spoon;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import fr.tvbarthel.ivanbnw.MainActivity;
import fr.tvbarthel.ivanbnw.R;
import fr.tvbarthel.ivanbnw.core.InstrumentationIvanbApplication;
import fr.tvbarthel.ivanbnw.core.LocalizedActivityInstrumentationTestCase2;
import fr.tvbarthel.ivanbnw.home.HomeInteractor;
import fr.tvbarthel.ivanbnw.home.HomePresenter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

/**
 * Instrumentation test for the main activity.
 */
public class MainActivityTest extends LocalizedActivityInstrumentationTestCase2<MainActivity> {

    private HomeInteractor mockedHomeInteractor;
    private HomePresenter homePresenter;

    /**
     * Instrumentation test for the main activity.
     */
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        InstrumentationIvanbApplication app = (InstrumentationIvanbApplication)
                getInstrumentation().getTargetContext().getApplicationContext();

        mockedHomeInteractor = app.mockedHomeInteractor;

        reset(mockedHomeInteractor);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                homePresenter = ((HomePresenter) invocation.getArguments()[0]);
                return null;
            }
        }).when(mockedHomeInteractor).setPresenter(any(HomePresenter.class));

    }

    /**
     * Ensure that behaviors of presenter implementation linked to the
     * home data interactor wont break.
     *
     * @throws Exception
     */
    @LargeTest
    public void testHomeDataRetrieval() throws Exception {

        // prepare request home data answer.
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                homePresenter.onHomeDataRetrieved();
                return null;
            }
        }).when(mockedHomeInteractor).requestHomeData();

        // start test
        getActivity();

        // ensure that presenter is well set to the interactor.
        verify(mockedHomeInteractor, atLeastOnce()).setPresenter(any(HomePresenter.class));

        // ensure that request home data is well asked.
        verify(mockedHomeInteractor, atLeastOnce()).requestHomeData();

        // ensure that loading is well hidden.
        onView(ViewMatchers.withId(R.id.home_view_dummy_progress))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

        // ensure that home data are well displayed.
        onView(withId(R.id.home_view_dummy_text))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // ensure that data are the expected one.
        onView(withId(R.id.home_view_dummy_text))
                .check(matches(withText(R.string.welcome)));

        // take a screen shot.
        Spoon.screenshot(getActivity(), "Home_Data_Retrieved", getClass().getName(), "testHomeDataRetrieval");
    }
}
