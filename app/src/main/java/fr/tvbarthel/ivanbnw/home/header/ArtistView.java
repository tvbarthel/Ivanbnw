package fr.tvbarthel.ivanbnw.home.header;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.tvbarthel.cheerleader.library.client.SoundCloudUser;
import fr.tvbarthel.cheerleader.library.helpers.SoundCloudArtworkHelper;
import fr.tvbarthel.ivanbnw.R;

/**
 * View used to display the {@link SoundCloudUser} data
 * in the {@link fr.tvbarthel.ivanbnw.home.HomeView}
 */
class ArtistView extends CardView {

    private ImageView artistCover;
    private SoundCloudUser artist;
    private int size;
    private TextView followers;
    private TextView tracks;

    /**
     * View used to display the {@link SoundCloudUser} data
     * in the {@link fr.tvbarthel.ivanbnw.home.HomeView}
     *
     * @param context holding context.
     */
    public ArtistView(Context context) {
        this(context, null);
    }

    /**
     * View used to display the {@link SoundCloudUser} data
     * in the {@link fr.tvbarthel.ivanbnw.home.HomeView}
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public ArtistView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * View used to display the {@link SoundCloudUser} data
     * in the {@link fr.tvbarthel.ivanbnw.home.HomeView}
     *
     * @param context      holding context.
     * @param attrs        attr from xml.
     * @param defStyleAttr style from xml.
     */
    public ArtistView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(sizeSpec, sizeSpec);
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
        followers.setText(String.valueOf(artist.getFollowersCount()));
        tracks.setText(String.valueOf(artist.getTrackCount()));
    }

    /**
     * Initialize internal component.
     *
     * @param context holding context.
     */
    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.home_artist_view, this);
        artistCover = ((ImageView) findViewById(R.id.home_artist_view_cover));
        Resources resources = context.getResources();
        size = resources.getDimensionPixelSize(R.dimen.artist_view_size);

        followers = ((TextView) findViewById(R.id.home_artist_view_followers));
        tracks = ((TextView) findViewById(R.id.home_artist_view_tracks));

        setRadius(resources.getDimensionPixelSize(R.dimen.artist_view_card_radius));
    }
}
