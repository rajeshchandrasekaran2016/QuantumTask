package sg.app.quantumtask.di.module;


import android.app.Activity;
import android.util.Log;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import sg.app.quantumtask.models.DictionaryDetailModelImp;
import sg.app.quantumtask.network.NetworkService;
import sg.app.quantumtask.network.TLSSocketFactory;
import sg.app.quantumtask.util.AppConstant;

@Module
public class NetworkModule {
    File cacheFile;
    Activity activity;
    TLSSocketFactory tlsSocketFactory = null;

    public NetworkModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    Retrofit provideCall() {
        try {
            tlsSocketFactory = new TLSSocketFactory();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            Log.d("TAG", "Failed to create Socket connection ");
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.systemDefaultTrustManager())
                .addInterceptor(
                        new HttpLoggingInterceptor(
                                new HttpLoggingInterceptor.Logger() {
                                    @Override
                                    public void log(String message) {
                                        Log.d("TAG", "response ::::: " + message);
                                    }
                                }
                        ).setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build();
        return new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkService providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public DictionaryDetailModelImp providesDictionaryListService(
            NetworkService networkService) {
        return new DictionaryDetailModelImp(networkService);
    }

}
