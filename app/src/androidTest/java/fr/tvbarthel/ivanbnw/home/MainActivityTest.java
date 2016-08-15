package fr.tvbarthel.ivanbnw.home;

import android.support.test.espresso.matcher.ViewMatchers;
import android.test.suitebuilder.annotation.LargeTest;

import com.squareup.spoon.Spoon;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import fr.tvbarthel.cheerleader.library.client.MockedModelProvider;
import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.cheerleader.library.client.SoundCloudUser;
import fr.tvbarthel.ivanbnw.MainActivity;
import fr.tvbarthel.ivanbnw.R;
import fr.tvbarthel.ivanbnw.core.InstrumentationIvanbApplication;
import fr.tvbarthel.ivanbnw.core.LocalizedActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
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

    private HomeActor mockedHomeActor;
    private HomeSpectator homeSpectator;
    private HomeData mockedHomeData;

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

        mockedHomeActor = app.mockedHomeActor;

        MockedModelProvider mockedModelProvider = new MockedModelProvider();
        SoundCloudUser mockedArtist = mockedModelProvider.getMockedArtist();
        ArrayList<SoundCloudTrack> mockedTracks = mockedModelProvider.getMockedTracks();
        mockedHomeData = new HomeData(mockedArtist, mockedTracks);

        reset(mockedHomeActor);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                homeSpectator = ((HomeSpectator) invocation.getArguments()[0]);
                return null;
            }
        }).when(mockedHomeActor).attachSpectator(any(HomeSpectator.class));

    }

    /**
     * Ensure that behaviors of spectator implementation linked to the
     * home data actor wont break.
     *
     * @throws Exception
     */
    @LargeTest
    public void testHomeDataRetrieval() throws Exception {

        // prepare request home data answer.
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                homeSpectator.onHomeDataRetrieved(mockedHomeData);
                return null;
            }
        }).when(mockedHomeActor).requestHomeData();

        // start test
        getActivity();

        // ensure that spectator is well set to the actor.
        verify(mockedHomeActor, atLeastOnce()).attachSpectator(any(HomeSpectator.class));

        // ensure that request home data is well asked.
        verify(mockedHomeActor, atLeastOnce()).requestHomeData();

        // ensure that loading is well hidden.
        onView(ViewMatchers.withId(R.id.home_view_dummy_progress))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

        // ensure that header is well populated
        SoundCloudUser artist = mockedHomeData.getArtist();
        onView(withId(R.id.home_artist_view_followers))
                .check(matches(withText(String.valueOf(artist.getFollowersCount()))));
        onView(withId(R.id.home_artist_view_tracks))
                .check(matches(withText(String.valueOf(artist.getTrackCount()))));
        onView(withId(R.id.artist_header_view_artist_name))
                .check(matches(withText(artist.getFullName())));

        // take a screen shot.
        Spoon.screenshot(getActivity(), "Home_Data_Retrieved", getClass().getName(), "testHomeDataRetrieval");
    }

    /**
     * Ensure that track can be played from the home page.
     *
     * @throws Exception
     */
    @LargeTest
    public void testHomeRequestPlaying() throws Exception {

        // prepare request home data answer.
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                homeSpectator.onHomeDataRetrieved(mockedHomeData);
                return null;
            }
        }).when(mockedHomeActor).requestHomeData();

        // prepare play track answer
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                SoundCloudTrack trackToPlay = (SoundCloudTrack) invocationOnMock.getArguments()[0];
                homeSpectator.onTrackPlaying(trackToPlay);
                return null;
            }
        }).when(mockedHomeActor).requestPlay(any(SoundCloudTrack.class));

        //start test
        getActivity();

        // ensure that spectator is well set to the actor.
        verify(mockedHomeActor, atLeastOnce()).attachSpectator(any(HomeSpectator.class));

        // ensure that request home data is well asked.
        verify(mockedHomeActor, atLeastOnce()).requestHomeData();

        SoundCloudTrack track = mockedHomeData.getTracks().get(0);

        // take a screen shot.
        Spoon.screenshot(getActivity(), "Home_before_playing", getClass().getName(), "testHomeRequestPlaying");

        // request playing track
        onView(withText(track.getTitle())).perform(click());

        // verify that actor is well called after UI events
        verify(mockedHomeActor, atLeastOnce()).requestPlay(track);

        // take a screen shot.
        Spoon.screenshot(getActivity(), "Home_after_playing", getClass().getName(), "testHomeRequestPlaying");

    }
}
