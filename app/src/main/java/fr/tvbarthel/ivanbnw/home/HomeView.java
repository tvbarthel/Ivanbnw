package fr.tvbarthel.ivanbnw.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import javax.inject.Inject;

import fr.tvbarthel.cheerleader.library.client.SoundCloudTrack;
import fr.tvbarthel.ivanbnw.R;
import fr.tvbarthel.ivanbnw.core.IvanbApplication;
import fr.tvbarthel.ivanbnw.player.PlaylistView;

/**
 * View used to display the home to the user.
 */
public class HomeView extends FrameLayout {

    /**
     * Actor implementation.
     */
    @Inject
    protected HomeActor homeActor;

    /**
     * Internal spectator used to not expose spectator
     * methods to {@link HomeView} user.
     */
    private HomeSpectator internalSpectator;

    /**
     * Listener used to catch adapted view events.
     */
    private HomeRecyclerViewAdapter.Listener internalListener;

    private View loadingView;
    private RecyclerView recyclerView;
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    private int scrollY;
    private HomeBackgroundView backgroundView;
    private Interpolator interpolator;
    private PlaylistView playlistView;

    /**
     * View used to display the home to the user.
     *
     * @param context holding context.
     */
    public HomeView(Context context) {
        this(context, null);
    }

    /**
     * View used to display the home to the user.
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public HomeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * View used to display the home to the user.
     *
     * @param context      holding context.
     * @param attrs        attr from xml.
     * @param defStyleAttr style from xml.
     */
    public HomeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        homeActor.attachSpectator(internalSpectator);
        homeActor.requestHomeData();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        homeActor.detachSpectator();
    }

    /**
     * Used to know if the home view as handled the back press events.
     *
     * @return true if the back press is handled internally.
     */
    public boolean onBackPressed() {
        return playlistView.onBackPressed();
    }

    private void displayHomeData(@NonNull HomeData homeData) {
        if (loadingView.getVisibility() == VISIBLE) {
            loadingView.animate().alpha(0f).setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            loadingView.setVisibility(INVISIBLE);
                        }
                    });
        }
        if (recyclerView.getVisibility() != VISIBLE) {
            homeRecyclerViewAdapter.setHomeData(homeData);
            homeRecyclerViewAdapter.notifyDataSetChanged();
            backgroundView.animateIn();
            recyclerView.setAlpha(0f);
            recyclerView.setVisibility(VISIBLE);
            recyclerView.animate()
                    .alpha(1f)
                    .setStartDelay(500)
                    .setDuration(300)
                    .setInterpolator(interpolator)
                    .setListener(null);
        }
    }


    private void displayLoadingView() {
        if (loadingView.getVisibility() != VISIBLE) {
            loadingView.setAlpha(0f);
            loadingView.setVisibility(VISIBLE);
            loadingView.animate().alpha(1f).setDuration(300).setListener(null);
        }
        if (recyclerView.getVisibility() == VISIBLE) {
            recyclerView.animate().alpha(0f).setStartDelay(0)
                    .setDuration(300).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(INVISIBLE);
                }
            });
        }
    }

    private void displayEmphasisOnPlayingTrack(SoundCloudTrack track) {
        homeRecyclerViewAdapter.notifyPlayingTrackChanged(track);
    }

    /**
     * Initialise internal component.
     *
     * @param context holding context.
     */
    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.home_view, this);

        loadingView = findViewById(R.id.home_view_dummy_progress);
        playlistView = ((PlaylistView) findViewById(R.id.home_view_playlist_view));
        recyclerView = ((RecyclerView) findViewById(R.id.home_view_recycler));
        recyclerView.setLayoutManager(new HomeRecyclerLayoutManager(context));
        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(context);
        recyclerView.setAdapter(homeRecyclerViewAdapter);

        IvanbApplication.component().inject(this);

        internalSpectator = new HomeSpectator() {
            @Override
            public void onDisplayLoadingIndicatorRequested() {
                displayLoadingView();
            }

            @Override
            public void onHomeDataRetrieved(@NonNull HomeData homeData) {
                displayHomeData(homeData);
            }

            @Override
            public void onTrackPlaying(SoundCloudTrack track) {
                displayEmphasisOnPlayingTrack(track);
            }
        };

        recyclerView.setVisibility(INVISIBLE);
        loadingView.setVisibility(INVISIBLE);

        backgroundView = ((HomeBackgroundView) findViewById(R.id.home_view_background));

        initializeInternalListeners();

        interpolator = new DecelerateInterpolator();
    }

    /**
     * Initialize internal listener.
     */
    private void initializeInternalListeners() {
        internalListener = new HomeRecyclerViewAdapter.Listener() {
            @Override
            public void onPlayTrackRequested(SoundCloudTrack track) {
                homeActor.requestPlay(track);
            }

            @Override
            public void onAddToPlaylistRequested(SoundCloudTrack track) {
                homeActor.addToCurrentQueue(track);
            }
        };
        homeRecyclerViewAdapter.setListener(internalListener);

        scrollY = 0;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scrollY += dy;
                backgroundView.setScrollY(scrollY);
            }
        });
    }
}
