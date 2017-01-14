package fr.tvbarthel.ivanbnw.player;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.netcosports.recyclergesture.library.swipe.SwipeToDismissDirection;
import com.netcosports.recyclergesture.library.swipe.SwipeToDismissGesture;
import com.netcosports.recyclergesture.library.swipe.SwipeToDismissStrategy;

import java.util.ArrayList;

import javax.inject.Inject;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.ivanbnw.R;
import fr.tvbarthel.ivanbnw.core.IvanbApplication;

/**
 * View used to display the current playlist to the user
 * as well as a player at a top of the playlist.
 */
public class PlaylistView extends FrameLayout {

    /**
     * Actor used to interact with the playlist.
     */
    @Inject
    protected PlaylistActor playlistActor;

    private RecyclerView recyclerView;
    private ArrayList<SoundCloudTrack> currentPlaylistTracks;
    private PlaylistAdapter playlistAdapter;
    private int playerViewHeight;
    private PlaylistSpectator internalPlaylistSpectator;
    private PlaylistAdapter.Listener internalPlaylistAdapterListener;
    private LinearLayoutManager layoutManager;
    private int scrollY;

    /**
     * * View used to display the current playlist to the user
     * as well as a player at a top of the playlist.
     *
     * @param context holding context.
     */
    public PlaylistView(Context context) {
        this(context, null);
    }

    /**
     * * View used to display the current playlist to the user
     * as well as a player at a top of the playlist.
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public PlaylistView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * * View used to display the current playlist to the user
     * as well as a player at a top of the playlist.
     *
     * @param context      holding context.
     * @param attrs        attr from xml.
     * @param defStyleAttr style from xml.
     */
    public PlaylistView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        playlistActor.attachSpectator(internalPlaylistSpectator);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        playlistActor.detachSpectator();
    }

    /**
     * Used to know if the view as handled the back press events.
     *
     * @return true if the back press is handled internally.
     */
    public boolean onBackPressed() {
        View firstChild = layoutManager.getChildAt(0);
        if (firstChild != null && firstChild.getTop() < recyclerView.getHeight() - firstChild.getHeight()) {
            recyclerView.smoothScrollToPosition(0);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Initialize internal component.
     *
     * @param context holding context.
     */
    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.playlist_view, this);

        IvanbApplication.component().inject(this);

        initializeInternalSpectator();

        initializeRecyclerView(context);
    }

    /**
     * Initialize recycler view used to render the current playlist tracks.
     *
     * @param context holding context.
     */
    private void initializeRecyclerView(Context context) {
        recyclerView = ((RecyclerView) findViewById(R.id.playlist_view_recycler_view));

        currentPlaylistTracks = new ArrayList<>();

        currentPlaylistTracks.addAll(playlistActor.getCurrentPlaylistTracks());

        initializeAdapter();

        playerViewHeight = context.getResources().getDimensionPixelSize(R.dimen.player_view_height);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scrollY += dy;
            }
        });

        recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                recyclerView.setPadding(
                        recyclerView.getPaddingLeft(),
                        recyclerView.getHeight() - playerViewHeight,
                        recyclerView.getPaddingRight(),
                        recyclerView.getPaddingBottom()

                );

                recyclerView.setAdapter(playlistAdapter);

                // attach the dismiss gesture.
                new SwipeToDismissGesture.Builder(SwipeToDismissDirection.HORIZONTAL)
                        .on(recyclerView)
                        .apply(new DismissStrategy())
                        .backgroundColor(
                                ContextCompat.getColor(recyclerView.getContext(), R.color.grey)
                        )
                        .build();

                if (currentPlaylistTracks.size() == 0) {
                    recyclerView.setTranslationY(playerViewHeight);
                }
                return true;
            }
        });
    }

    private void initializeAdapter() {
        playlistAdapter = new PlaylistAdapter(currentPlaylistTracks);
        internalPlaylistAdapterListener = new PlaylistAdapter.Listener() {
            @Override
            public void onRemoveTrackFromPlaylistRequested(int playlistIndex) {
                playlistActor.removeTrack(playlistIndex);
            }
        };
        playlistAdapter.setListener(internalPlaylistAdapterListener);
    }

    /**
     * Initialize the internal spectator used to retrieve callback.
     */
    private void initializeInternalSpectator() {
        internalPlaylistSpectator = new PlaylistSpectator() {

            @Override
            public void onTrackAdded(SoundCloudTrack track) {
                if (currentPlaylistTracks.isEmpty()) {
                    recyclerView.setVisibility(VISIBLE);
                    recyclerView.animate()
                            .translationY(0f)
                            .setStartDelay(0)
                            .setDuration(300)
                            .setListener(null);
                }
                currentPlaylistTracks.add(track);
                playlistAdapter.notifyTrackInserted();
            }

            @Override
            public void onTrackRemoved(SoundCloudTrack track, boolean isEmpty) {
                if (isEmpty) {
                    recyclerView.animate()
                            .translationY(playerViewHeight)
                            .setStartDelay(300)
                            .setDuration(300)
                            .setListener(null);
                }
            }
        };
    }

    /**
     * Swipe to dismiss strategy used to disable swipe to dismiss on the header.
     */
    private static class DismissStrategy extends SwipeToDismissStrategy {
        @Override
        public SwipeToDismissDirection getDismissDirection(int position) {
            if (position == 0) {
                return SwipeToDismissDirection.NONE;
            } else {
                return SwipeToDismissDirection.HORIZONTAL;
            }
        }
    }
}
