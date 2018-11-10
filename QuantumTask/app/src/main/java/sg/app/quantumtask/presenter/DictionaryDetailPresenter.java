package sg.app.quantumtask.presenter;

import sg.app.quantumtask.beans.DictionaryResponse;

public interface DictionaryDetailPresenter {
    interface View {
        void onGetDictionarySuccess(DictionaryResponse dictionaryResponse);

        void onGetDictionaryFail(DictionaryResponse dictionaryResponse);

        void onNetworkError(String errorMsg);
    }
}
