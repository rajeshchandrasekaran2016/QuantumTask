package sg.app.quantumtask;

import android.content.Context;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Subscription;
import sg.app.quantumtask.beans.DictionaryResponse;
import sg.app.quantumtask.models.DictionaryDetailModelImp;
import sg.app.quantumtask.network.NetworkError;
import sg.app.quantumtask.network.NetworkService;
import sg.app.quantumtask.presenter.DictionaryDetailPresenter;
import sg.app.quantumtask.presenter.impl.DictionaryDetailPresenterImpl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SplachScreenUnitTest {

    @Mock
    private DictionaryDetailModelImp dictionaryDetailModelImp;

    @Mock
    private NetworkService networkService;

    @Mock
    private DictionaryDetailPresenter.View crewView;

    private DictionaryDetailPresenterImpl dictionaryDetailPresenter;

    @Mock
    private Context context;

    @Mock
    private Subscription subscription;

    @Mock
    DictionaryResponse dictionaryResponse;

    @Captor
    private ArgumentCaptor<DictionaryDetailModelImp.DictionaryDetailModelCallback>
            dictionaryDetailModelCallbackArgumentCaptor;

    @Before
    public void setupMocksAndView() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void populateCrewList_callApiAndUpdateViewOnSuccess() {


        when(dictionaryDetailModelImp.getDictionaryListDetail(
                dictionaryDetailModelCallbackArgumentCaptor.capture())).thenReturn(subscription);
        dictionaryDetailPresenter = new DictionaryDetailPresenterImpl(dictionaryDetailModelImp, crewView);
        dictionaryDetailPresenter.getDictionaryDetail();
        verify(dictionaryDetailModelImp).getDictionaryListDetail(dictionaryDetailModelCallbackArgumentCaptor.capture());
        dictionaryDetailModelCallbackArgumentCaptor.getValue().onDictionaryDetailSuccess(dictionaryResponse);
        verify(crewView).onGetDictionarySuccess(dictionaryResponse);
        //dictionaryDetailModelCallbackArgumentCaptor.getValue().onError(new NetworkError(new NullPointerException()));
        //verify(crewView).onGetDictionaryFail(dictionaryResponse);
        dictionaryDetailModelCallbackArgumentCaptor.getValue().onDictionaryDetailFail(dictionaryResponse);
        verify(crewView).onGetDictionaryFail(dictionaryResponse);
    }

}
