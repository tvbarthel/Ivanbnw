package fr.tvbarthel.ivanbnw.home;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import fr.tvbarthel.cheerleader.library.client.CheerleaderClient;
import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.cheerleader.library.client.SoundCloudUser;
import fr.tvbarthel.cheerleader.library.player.CheerleaderPlayer;
import fr.tvbarthel.cheerleader.library.player.CheerleaderPlayerListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Implementation based on Cheerleader project.
 */
class HomeActorImpl implements HomeActor {

    private final CheerleaderClient cheerleaderClient;
    private final CheerleaderPlayer cheerleaderPlayer;
    private HomeSpectator spectator;
    private CheerleaderPlayerListener internalCheerleaderPlayerListener;

    /**
     * Implementation based on Cheerleader project.
     *
     * @param cheerleaderClient client used to retrieve artist data.
     * @param cheerleaderPlayer player used to handle playback.
     */
    public HomeActorImpl(
            @NonNull CheerleaderClient cheerleaderClient,
            @NonNull CheerleaderPlayer cheerleaderPlayer) {
        this.cheerleaderClient = cheerleaderClient;
        this.cheerleaderPlayer = cheerleaderPlayer;
        createInternalListener();
    }

    @Override
    public void attachSpectator(HomeSpectator spectator) {
        this.spectator = spectator;
    }

    @Override
    public void detachSpectator() {
        this.spectator = null;
        cheerleaderPlayer.unregisterPlayerListener(internalCheerleaderPlayerListener);
    }

    @Override
    public void requestHomeData() {
        spectator.onDisplayLoadingIndicatorRequested();

        Observable<SoundCloudUser> artisteObservable = cheerleaderClient.getArtistProfile();

        Observable<ArrayList<SoundCloudTrack>> tracksObservable = cheerleaderClient.getArtistTracks();

        artisteObservable.zipWith(tracksObservable, mergeArtistAndTracks())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onHomeDataRetrieved());
    }

    @Override
    public void requestPlay(SoundCloudTrack track) {
        cheerleaderPlayer.addTrack(track, true);
    }

    @Override
    public void addToCurrentQueue(SoundCloudTrack track) {
        cheerleaderPlayer.addTrack(track, false);
    }

    @NonNull
    private Subscriber<HomeData> onHomeDataRetrieved() {
        return new Subscriber<HomeData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HomeData homeData) {
                if (spectator != null) {
                    spectator.onHomeDataRetrieved(homeData);
                }
                // start listening to player events in order to update the home track views.
                cheerleaderPlayer.registerPlayerListener(internalCheerleaderPlayerListener);
            }
        };
    }

    @NonNull
    private Func2<SoundCloudUser, ArrayList<SoundCloudTrack>, HomeData> mergeArtistAndTracks() {
        return new Func2<SoundCloudUser, ArrayList<SoundCloudTrack>, HomeData>() {
            @Override
            public HomeData call(SoundCloudUser soundCloudUser, ArrayList<SoundCloudTrack> soundCloudTracks) {
                return new HomeData(soundCloudUser, soundCloudTracks);
            }
        };
    }

    private void createInternalListener() {
        internalCheerleaderPlayerListener = new CheerleaderPlayerListener() {

            @Override
            public void onPlayerPlay(SoundCloudTrack track, int position) {
                if (spectator != null) {
                    spectator.onTrackPlaying(track);
                }
            }

            @Override
            public void onPlayerPause() {

            }

            @Override
            public void onPlayerSeekTo(int milli) {

            }

            @Override
            public void onPlayerDestroyed() {

            }

            @Override
            public void onBufferingStarted() {

            }

            @Override
            public void onBufferingEnded() {

            }

            @Override
            public void onProgressChanged(int milli) {

            }
        };
    }
}
