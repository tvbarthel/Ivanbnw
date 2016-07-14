package fr.tvbarthel.ivanbnw.home;

/**
 * Interactor used to define the business logic need by the home.
 */
public interface HomeInteractor {

    /**
     * Set the presenter which is expecting callback from the interactor.
     *
     * @param presenter presenter interested in interactor callback.
     */
    void setPresenter(HomePresenter presenter);

    /**
     * Must retrieve the home data.
     * <p/>
     * If retrieving process might take time, aka async retrieval,
     * {@link HomePresenter#onDisplayLoadingIndicatorRequested()} must be called
     * to ensure that user will have visual feedback on what is happening.
     * <p/>
     * See also, {@link HomePresenter#onHomeDataRetrieved()}
     */
    void requestHomeData();

}
