package sg.app.quantumtask.network;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import sg.app.quantumtask.beans.DictionaryResponse;

public interface NetworkService {
    @GET("dictionary-v2.json")
    Observable<DictionaryResponse> dictionaryList();

}
