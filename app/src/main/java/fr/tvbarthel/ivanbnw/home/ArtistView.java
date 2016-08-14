package fr.tvbarthel.ivanbnw.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import fr.tvbarthel.cheerleader.library.client.SoundCloudUser;
import fr.tvbarthel.cheerleader.library.helpers.SoundCloudArtworkHelper;
import fr.tvbarthel.ivanbnw.R;

/**
 * View used to display the {@link SoundCloudUser} data
 * in the {@link HomeView}
 */
class ArtistView extends FrameLayout {

    private ImageView artistCover;
    private SoundCloudUser artist;

    /**
     * View used to display the {@link SoundCloudUser} data
     * in the {@link HomeView}
     *
     * @param context holding context.
     */
    public ArtistView(Context context) {
        this(context, null);
    }

    /**
     * View used to display the {@link SoundCloudUser} data
     * in the {@link HomeView}
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public ArtistView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * View used to display the {@link SoundCloudUser} data
     * in the {@link HomeView}
     *
     * @param context      holding context.
     * @param attrs        attr from xml.
     * @param defStyleAttr style from xml.
     */
    public ArtistView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    /**
     * Set the {@link SoundCloudUser} which must be displayed.
     *
     * @param artist artist to display.
     */
    public void presentData(@NonNull SoundCloudUser artist) {
        this.artist = artist;
        Picasso.with(getContext()).load(
                SoundCloudArtworkHelper.getCoverUrl(artist, SoundCloudArtworkHelper.XXXLARGE)
        ).fit().centerCrop().into(artistCover);
    }

    /**
     * Initialize internal component.
     *
     * @param context holding context.
     */
    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.home_artist_view, this);
        artistCover = ((ImageView) findViewById(R.id.home_artist_view_cover));
    }
}
