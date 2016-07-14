package fr.tvbarthel.ivanbnw.home;

/**
 * Presenter used to define the visual needs linked to the home.
 */
public interface HomePresenter {

    /**
     * Called by the {@link HomeInteractor} when data retrieving is in progress.
     * <p/>
     * Must display visual indicator to help the user to understand that retrieving data process
     * is on the way.
     */
    void onDisplayLoadingIndicatorRequested();

    /**
     * Called by the {@link HomeInteractor} when home data have been retrieved.
     * <p/>
     * Must display the retrieved data to the user.
     * <p/>
     * See also, {@link HomeInteractor#requestHomeData()}
     */
    void onHomeDataRetrieved();
}
