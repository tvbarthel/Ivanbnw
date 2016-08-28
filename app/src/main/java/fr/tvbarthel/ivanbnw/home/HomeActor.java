package fr.tvbarthel.ivanbnw.home;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;

/**
 * Actor used to define the business logic need by the home.
 */
public interface HomeActor {

    /**
     * Attach a spectator which is expecting callback from the actor.
     *
     * @param spectator spectator interested in actor callback.
     */
    void attachSpectator(HomeSpectator spectator);

    /**
     * Detach the previous attached spectator.
     * <p/>
     * Must unregister every internal listeners.
     */
    void detachSpectator();

    /**
     * Must retrieve the home data.
     * <p/>
     * If retrieving process might take time, aka async retrieval,
     * {@link HomeSpectator#onDisplayLoadingIndicatorRequested()} must be called
     * to ensure that user will have visual feedback on what is happening.
     * <p/>
     * See also, {@link HomeSpectator#onHomeDataRetrieved(HomeData)}
     */
    void requestHomeData();

    /**
     * Must play the requested track.
     * <p/>
     * See also: {@link HomeSpectator#onTrackPlaying(SoundCloudTrack)}
     *
     * @param track track to play.
     */
    void requestPlay(SoundCloudTrack track);

    /**
     * Must add the given track at the end of the current playlist.
     *
     * @param track track to add to the current playback queue.
     */
    void addToCurrentQueue(SoundCloudTrack track);
}

