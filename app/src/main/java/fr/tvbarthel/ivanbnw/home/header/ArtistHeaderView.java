package fr.tvbarthel.ivanbnw.home.header;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.TextView;

import fr.tvbarthel.cheerleader.library.client.SoundCloudUser;
import fr.tvbarthel.ivanbnw.R;

/**
 * {@link HeaderView} used to display a
 * {@link fr.tvbarthel.cheerleader.library.client.SoundCloudUser}
 */
public class ArtistHeaderView extends HeaderView {

    private ArtistView artistView;
    private TextView artistName;

    /**
     * {@link HeaderView} used to display a
     * {@link fr.tvbarthel.cheerleader.library.client.SoundCloudUser}
     *
     * @param context holding context.
     */
    public ArtistHeaderView(Context context) {
        super(context);
    }

    /**
     * Set the {@link SoundCloudUser} which must be displayed.
     *
     * @param artist artist to display.
     */
    public void presentData(@NonNull SoundCloudUser artist) {
        artistView.presentData(artist);
        artistName.setText(artist.getFullName());
    }

    @Override
    protected void initialize(Context context) {
        super.initialize(context);
        LayoutInflater.from(context).inflate(R.layout.artist_header_view, this);
        artistView = ((ArtistView) findViewById(R.id.artist_header_view_artist_view));
        artistName = ((TextView) findViewById(R.id.artist_header_view_artist_name));
    }
}
