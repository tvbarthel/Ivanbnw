package fr.tvbarthel.cheerleader.library.client;

import java.util.ArrayList;

/**
 * Provide mocked model from cheerleader library.
 */
public final class MockedModelProvider {

    /**
     * Retrieve a mocked {@link SoundCloudUser}
     *
     * @return mocked {@link SoundCloudUser}
     */
    public SoundCloudUser getMockedArtist() {
        SoundCloudUser artist = new SoundCloudUser();
        artist.setFullName("Ivan BnW");
        artist.setFollowersCount(21453);
        artist.setTrackCount(37);
        artist.setAvatarUrl("https://i1.sndcdn.com/artworks-000164502660-la56ky-t500x500.jpg");
        return artist;
    }

    /**
     * Retrieve mocked {@link SoundCloudTrack}
     *
     * @return mocked {@link SoundCloudTrack}
     */
    public ArrayList<SoundCloudTrack> getMockedTracks() {
        ArrayList<SoundCloudTrack> tracks = new ArrayList<>();
        SoundCloudTrack track = new SoundCloudTrack();
        track.setTitle("Track 1");
        track.setId(1);
        tracks.add(track);

        track = new SoundCloudTrack();
        track.setTitle("Track 2");
        track.setId(2);
        tracks.add(track);

        track = new SoundCloudTrack();
        track.setTitle("Track 3");
        track.setId(3);
        tracks.add(track);

        track = new SoundCloudTrack();
        track.setTitle("Track 4");
        track.setId(4);
        tracks.add(track);

        return tracks;
    }

}
