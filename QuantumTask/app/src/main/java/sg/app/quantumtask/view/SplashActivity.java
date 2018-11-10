package sg.app.quantumtask.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.app.quantumtask.R;
import sg.app.quantumtask.beans.DictionaryResponse;
import sg.app.quantumtask.di.component.DaggerNetworkComponent;
import sg.app.quantumtask.di.component.NetworkComponent;
import sg.app.quantumtask.di.module.NetworkModule;
import sg.app.quantumtask.models.DictionaryDetailModelImp;
import sg.app.quantumtask.presenter.DictionaryDetailPresenter;
import sg.app.quantumtask.presenter.impl.DictionaryDetailPresenterImpl;
import sg.app.quantumtask.util.CommonUtils;
import sg.app.quantumtask.util.DictionaryParcelable;

public class SplashActivity extends AppCompatActivity implements DictionaryDetailPresenter.View {

    private static int SPLASH_TIME_OUT = 3000;
    private NetworkComponent networkComponentDeps;
    private DictionaryDetailPresenterImpl dictionaryDetailPresenter;
    @Inject
    public DictionaryDetailModelImp dictionaryDetailModelImp;
    @BindView(R.id.pb_loader)
    ProgressBar loaderView;
    @BindView(R.id.tv_error_msg)
    TextView errorMsgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(SplashActivity.this);
        networkComponentDeps = DaggerNetworkComponent
                .builder()
                .networkModule(new NetworkModule(SplashActivity.this)).build();
        getNetworkComponentDeps().inject(SplashActivity.this);
        dictionaryDetailPresenter =
                new DictionaryDetailPresenterImpl(dictionaryDetailModelImp, SplashActivity.this);
        dictionaryDetailPresenter.getDictionaryDetail();
    }

    public NetworkComponent getNetworkComponentDeps() {
        return networkComponentDeps;
    }

    @Override
    public void onGetDictionarySuccess(final DictionaryResponse dictionaryResponse) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<DictionaryParcelable> arraylist = new ArrayList<DictionaryParcelable>();
                for (int i = 0; i < dictionaryResponse.getDictionary().size(); i++) {
                    DictionaryParcelable dp = new DictionaryParcelable(dictionaryResponse.getDictionary().get(i).getWord(),
                            dictionaryResponse.getDictionary().get(i).getFrequency());
                    arraylist.add(dp);
                }
                ArrayList<HashMap<String, Integer>> arl = new ArrayList<HashMap<String, Integer>>();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("dictionary_list", arraylist);
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                i.putExtras(bundle);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onGetDictionaryFail(DictionaryResponse dictionaryResponse) {
    }

    @Override
    public void onNetworkError(String errorMsg) {
        errorMsgView.setText(errorMsg);
        loaderView.setVisibility(View.GONE);
        errorMsgView.setVisibility(View.VISIBLE);
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
