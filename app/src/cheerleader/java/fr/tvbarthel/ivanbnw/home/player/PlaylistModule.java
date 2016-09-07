package fr.tvbarthel.ivanbnw.home.player;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.tvbarthel.cheerleader.library.player.CheerleaderPlayer;
import fr.tvbarthel.ivanbnw.player.PlaylistActor;

/**
 * Module used to provide every components linked to the playlist.
 */
@Module
public class PlaylistModule {

    /**
     * Provide playlist actor.
     *
     * @param cheerleaderPlayer cheerleader player used to access to the current playlist.
     * @return {@link fr.tvbarthel.ivanbnw.player.PlaylistActor} implementation.
     */
    @Provides
    @Singleton
    @NonNull
    PlaylistActor providePlaylistActor(@NonNull CheerleaderPlayer cheerleaderPlayer) {
        return new PlaylistActorImpl(cheerleaderPlayer);
    }
}
