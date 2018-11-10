package sg.app.quantumtask.presenter.impl;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import sg.app.quantumtask.beans.DictionaryResponse;
import sg.app.quantumtask.models.DictionaryDetailModelImp;
import sg.app.quantumtask.network.NetworkError;
import sg.app.quantumtask.presenter.DictionaryDetailPresenter;

public class DictionaryDetailPresenterImpl {
    private final DictionaryDetailModelImp dictionaryDetailModelImp;
    private final DictionaryDetailPresenter.View baseView;
    private CompositeSubscription subscriptions;

    public DictionaryDetailPresenterImpl(DictionaryDetailModelImp dictionaryDetailModelImp,
                                         DictionaryDetailPresenter.View baseView) {
        this.dictionaryDetailModelImp = dictionaryDetailModelImp;
        this.baseView = baseView;
        this.subscriptions = new CompositeSubscription();
    }

    public void getDictionaryDetail() {
        Subscription subscription = dictionaryDetailModelImp.getDictionaryListDetail(
                new DictionaryDetailModelImp.DictionaryDetailModelCallback() {
                    @Override
                    public void onDictionaryDetailSuccess(DictionaryResponse dictionaryResponse) {
                        baseView.onGetDictionarySuccess(dictionaryResponse);
                    }

                    @Override
                    public void onDictionaryDetailFail(DictionaryResponse dictionaryResponse) {
                        baseView.onGetDictionaryFail(dictionaryResponse);
                    }

                    @Override
                    public void onError(NetworkError networkError) {
                        baseView.onNetworkError(networkError.getAppErrorMessage());
                    }

                });

        subscriptions.add(subscription);
    }


    public void onStop() {
        subscriptions.unsubscribe();
    }


}
