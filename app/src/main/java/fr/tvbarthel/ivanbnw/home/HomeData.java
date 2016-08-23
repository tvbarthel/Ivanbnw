package fr.tvbarthel.ivanbnw.home;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.cheerleader.library.client.SoundCloudUser;

/**
 * Encapsulate data linked to the home.
 */
class HomeData {

    /**
     * Artist for which the home has been build.
     */
    private final SoundCloudUser artist;

    /**
     * Artist's tracks.
     */
    private final ArrayList<SoundCloudTrack> tracks;

    /**
     * Encapsulate data linked to the home.
     *
     * @param artist           Artist for which the home has been build.
     * @param soundCloudTracks Artist's tracks.
     */
    public HomeData(@NonNull SoundCloudUser artist, ArrayList<SoundCloudTrack> soundCloudTracks) {
        this.artist = artist;
        this.tracks = soundCloudTracks;
    }

    /**
     * Artist for which the home has been build.
     *
     * @return Artist for which the home has been build.
     */
    @NonNull
    public SoundCloudUser getArtist() {
        return artist;
    }

    /**
     * Access to the artist's tracks.
     *
     * @return Artist's tracks.
     */
    public ArrayList<SoundCloudTrack> getTracks() {
        return tracks;
    }
}
