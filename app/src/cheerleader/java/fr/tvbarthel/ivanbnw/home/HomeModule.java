package fr.tvbarthel.ivanbnw.home;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.tvbarthel.cheerleader.library.client.CheerleaderClient;
import fr.tvbarthel.cheerleader.library.player.CheerleaderPlayer;

/**
 * Module used to provide every components linked to the home.
 */
@Module
public class HomeModule {

    /**
     * Provide a {@link HomeActor} to the graph.
     *
     * @param cheerleaderClient client used to retrieve artist data from sound cloud.
     * @param cheerleaderPlayer player used to handle playback.
     * @return {@link HomeActor} implementation.
     */
    @Provides
    @Singleton
    @NonNull
    public HomeActor provideHomeActor(
            @NonNull CheerleaderClient cheerleaderClient,
            @NonNull CheerleaderPlayer cheerleaderPlayer) {
        return new HomeActorImpl(cheerleaderClient, cheerleaderPlayer);
    }
}
