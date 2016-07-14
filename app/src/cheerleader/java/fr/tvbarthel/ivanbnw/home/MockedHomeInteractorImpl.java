package fr.tvbarthel.ivanbnw.home;

import android.os.CountDownTimer;

/**
 * Implementation completely mocked.
 * TODO waiting for cheerleader dependencies.
 */
class MockedHomeInteractorImpl implements HomeInteractor {

    private HomePresenter presenter;

    @Override
    public void setPresenter(HomePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void requestHomeData() {
        presenter.onDisplayLoadingIndicatorRequested();

        new CountDownTimer(3000, 3000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (presenter != null) {
                    presenter.onHomeDataRetrieved();
                }
            }
        }.start();
    }
}
