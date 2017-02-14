package fr.tvbarthel.ivanbnw.core;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.tvbarthel.cheerleader.library.client.CheerleaderClient;
import fr.tvbarthel.cheerleader.library.player.CheerleaderPlayer;
import fr.tvbarthel.ivanbnw.MainActivity;
import fr.tvbarthel.ivanbnw.R;

/**
 * Module used to provide every core component.
 */
@Module
public class CoreModule {

    /**
     * Provide the {@link CheerleaderClient} used to retrieved IvanB info and tracks.
     *
     * @param context context used to initialize the client.
     * @return well instantiated client.
     */
    @Provides
    @Singleton
    protected CheerleaderClient provideCheerleaderClient(Context context) {
        return new CheerleaderClient.Builder().from(context)
            .with(R.string.client_id)
            .log(CheerleaderClient.LOG_RETROFIT)
            .supports(context.getString(R.string.ivanbnw_sound_cloud_id))
            .build();
    }

    /**
     * Provide the {@link CheerleaderPlayer} used to handle playback.
     *
     * @param context context used to initialize internal component.
     * @return well instantiated player.
     */
    @Provides
    @Singleton
    protected CheerleaderPlayer provideCheerleaderPlayer(Context context) {
        return new CheerleaderPlayer.Builder().from(context)
            .notificationActivity(MainActivity.class)
            .with(R.string.client_id)
            .build();
    }
}
