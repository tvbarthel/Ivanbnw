package fr.tvbarthel.ivanbnw.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import javax.inject.Inject;

import fr.tvbarthel.ivanbnw.R;
import fr.tvbarthel.ivanbnw.core.IvanbApplication;

/**
 * View used to display the home to the user.
 */
public class HomeView extends FrameLayout {

    /**
     * Interactor implementation.
     */
    @Inject
    protected HomeInteractor homeInteractor;

    /**
     * Internal presenter used to not expose presenter
     * methods to {@link HomeView} user.
     */
    private HomePresenter internalPresenter;

    private View loadingView;
    private View dataView;

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


    private void displayHomeData() {
        loadingView.animate().alpha(0f).setDuration(300).setListener(null);
        dataView.animate().alpha(1f).setDuration(300).setListener(null);
    }


    private void displayLoadingView() {
        loadingView.animate().alpha(1f).setDuration(300).setListener(null);
        dataView.animate().alpha(0f).setDuration(300).setListener(null);
    }

    /**
     * Initialise internal component.
     *
     * @param context holding context.
     */
    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.home_view, this);

        loadingView = findViewById(R.id.home_view_dummy_progress);
        dataView = findViewById(R.id.home_view_dummy_text);

        IvanbApplication.component().inject(this);

        internalPresenter = new HomePresenter() {
            @Override
            public void onDisplayLoadingIndicatorRequested() {
                displayLoadingView();
            }

            @Override
            public void onHomeDataRetrieved() {
                displayHomeData();
            }
        };

        homeInteractor.setPresenter(internalPresenter);
        loadingView.setAlpha(0f);
        dataView.setAlpha(0f);
        homeInteractor.requestHomeData();
    }
}
