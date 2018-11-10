package sg.app.quantumtask.models;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import sg.app.quantumtask.beans.DictionaryResponse;
import sg.app.quantumtask.network.NetworkError;
import sg.app.quantumtask.network.NetworkService;

public class DictionaryDetailModelImp {
    private final NetworkService networkService;

    public DictionaryDetailModelImp(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getDictionaryListDetail(final DictionaryDetailModelCallback
                                                        dictionaryDetailModelCallback) {
        return networkService.dictionaryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DictionaryResponse>>() {
                    @Override
                    public Observable<? extends DictionaryResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<DictionaryResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        dictionaryDetailModelCallback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(DictionaryResponse dictionaryResponse) {
                        if (dictionaryResponse.getDictionary() != null) {
                            dictionaryDetailModelCallback.onDictionaryDetailSuccess(dictionaryResponse);
                        } else {
                            dictionaryDetailModelCallback.onDictionaryDetailFail(dictionaryResponse);
                        }
                    }
                });
    }


    public interface DictionaryDetailModelCallback {
        void onDictionaryDetailSuccess(DictionaryResponse dictionaryResponse);

        void onDictionaryDetailFail(DictionaryResponse dictionaryResponse);

        void onError(NetworkError networkError);

    }

}

