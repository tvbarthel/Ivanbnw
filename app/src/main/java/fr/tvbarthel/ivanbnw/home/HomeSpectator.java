package fr.tvbarthel.ivanbnw.home;

import android.support.annotation.NonNull;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;

/**
 * Spectator used to define the visual needs linked to the home actor.
 */
public interface HomeSpectator {

    /**
     * Called by the {@link HomeActor} when data retrieving is in progress.
     * <p/>
     * Must display visual indicator to help the user to understand that retrieving data process
     * is on the way.
     */
    void onDisplayLoadingIndicatorRequested();

    /**
     * Called by the {@link HomeActor} when home data have been retrieved.
     * <p/>
     * Must display the retrieved data to the user.
     * <p/>
     * See also, {@link HomeActor#requestHomeData()}
     *
     * @param homeData retrieved home data.
     */
    void onHomeDataRetrieved(@NonNull HomeData homeData);

    /**
     * Called when a given track is playing.
     * <p/>
     * See also: {@link HomeActor#requestPlay(SoundCloudTrack)}
     *
     * @param track track currently played.
     */
    void onTrackPlaying(SoundCloudTrack track);

}
