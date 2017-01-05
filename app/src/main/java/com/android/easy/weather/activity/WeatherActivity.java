package com.android.easy.weather.activity;

import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.easy.weather.R;
import com.android.easy.weather.base.BaseActivity;
import com.android.easy.weather.fragment.ProgressDialogFragment;
import com.android.easy.weather.fragment.SelectLocFragment;
import com.android.easy.weather.fragment.WeatherFragment;
import com.android.easy.weather.model.SelectCity;
import com.android.easy.weather.model.Weather;
import com.android.easy.weather.model.Weather.HeWeather;
import com.android.easy.weather.net.ApiManager;
import com.android.easy.weather.net.RetrofitClient;
import com.android.easy.weather.util.HttpUtil;
import com.android.easy.weather.util.SharePreferencesHelper;
import com.android.easy.weather.util.ToastUtil;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Callback;
import okhttp3.Response;
import retrofit2.Call;

public class WeatherActivity extends BaseActivity {
    @BindView(R.id.rl_)
    RelativeLayout mRL;                                 //  根布局
    private long mExitTime;                             //  退出时间
    @BindView(R.id.iv_show_loc_list)
    ImageView mShowLocList;                             //  用于显示已选择城市列表的按钮
    @BindView(R.id.tv_title_city_name)
    TextView mTitleCityName;                            //  标题栏显示当前城市名字
    @BindView(R.id.tv_title_update_time)
    TextView mTitleUpdateTime;                          //  标题栏用于当前天气最后更新时间
    @BindView(R.id.tv_show_bg_pic)
    ImageView mShowBgPic;                               //  背景图 用于显示每日一图
    @BindView(R.id.dl_drawer)
    DrawerLayout mDrawerLayout;                         //  抽屉布局用于显示
    private String mWeatherId;                          //  拿到的天气Id
    public static final String MY_WEATHER_KEY           //  自己申请的获取天气的Key(这里用的郭神的, 因为自己申请的只显示未来三天的天气预报)
            = "bc0418b57b2d4918819d3974ac1285d9";
    private WeatherFragment mWeatherFragment;           //  填充到布局中的Fragment
    private ApiManager mApiManager;                     //  获取网络接口的管理类
    private boolean mIsFirst = true;                    //  是否第一次加载数据
    public static final String NO_CLOSE = "no_close";   //  常量
    private Realm mRealm;                               //  Realm实例
    private boolean mNoPause;                           //  通过它判断是否执行onPause函数
    private boolean mIsReturn = true;                   //  是否是通过返回键返回, 通过它判断时候执行onRestart函数

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weather;
    }

    @Override
    protected void getData() {
        //  先获取上个界面传递过来的weather_id
        mWeatherId = getIntent().getStringExtra(SelectLocFragment.WEATHER_ID);
        if (TextUtils.isEmpty(mWeatherId)) {
            ToastUtil.showShortToast("天气信息获取失败...");
        } else {
            //  天气信息
            mApiManager = RetrofitClient.getInstance().getApiManager();
            getWeatherInfo();
            //  每日一图
            String requestUrl = RetrofitClient.BASE_URL + "bing_pic";
            HttpUtil.sendOkHttpRequest(requestUrl, new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {

                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {
                    final String picUrl = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Picasso.with(WeatherActivity.this)
                                    .load(picUrl)
                                    .into(mShowBgPic);
                        }
                    });
                }
            });
        }
    }

    public void getWeatherInfo() {
        //  第一次显示会有等待框
        if (mIsFirst) {
            ProgressDialogFragment fragment = ProgressDialogFragment.newInstance("正在获取天气数据...");
            fragment.show(getSupportFragmentManager(), "");
            Call<Weather> call = mApiManager.getWeatherInfo(mWeatherId, MY_WEATHER_KEY);
            getData(call, fragment);
            mIsFirst = false;
        } else {
            Call<Weather> call = mApiManager.getWeatherInfo(mWeatherId, MY_WEATHER_KEY);
            getData(call);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        //  因为是站内单例启动模式, 所以在此启动通过这里拿到新的WeatherId
        super.onNewIntent(intent);
        mWeatherId = intent.getStringExtra(SelectLocFragment.WEATHER_ID);
        mIsReturn = false;
        getWeatherInfo();
    }

    @Override
    protected void initView() {
        //  加载Fragment
        replaceFragment();
        //  如果是5.0以上就调整布局
        if (isImmersion() && Build.VERSION.SDK_INT >= 21) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mRL.getLayoutParams();
            lp.setMargins(0, 40, 0, 0);
            mRL.setLayoutParams(lp);
        }
    }

    @Override
    protected void initListener() {
        //  跳转到编辑城市的Activity
        mShowLocList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, EditSelectCityActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSuccess(Object data) {
        if (data instanceof Weather) {
            HeWeather heWeather = ((Weather) data).getHeWeather().get(0);
            //  设置城市和最后更新时间
            mTitleCityName.setText(heWeather.getBasic().getCity());
            String[] times = heWeather.getBasic().getUpdate().getLoc().split(" ");
            mTitleUpdateTime.setText("官方更新时间: " + times[1]);
            if (mWeatherFragment == null) {
                replaceFragment();
            }
            mWeatherFragment.setWeatherInfo(heWeather);
        } else if (data instanceof String) {
            //  暂时没用上
            Picasso.with(WeatherActivity.this)
                    .load((String) data)
                    .into(mShowBgPic);
        }
    }

    /**
     * 显示指定Fragment
     */
    private void replaceFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mWeatherFragment = WeatherFragment.newInstance();
        transaction.replace(R.id.fl_container, mWeatherFragment);
        transaction.commit();
    }

    @Override
    protected boolean isImmersion() {
        return true;
    }

    /**
     * 退出应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //  双击退出
        if (doubleClickExit()) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if ((SystemClock.uptimeMillis() - mExitTime) > 2000) {
                    ToastUtil.showShortToast(getString(R.string.exit_app));
                    mExitTime = SystemClock.uptimeMillis();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 是否可以双击返回键退出应用
     *
     * @return
     */
    protected boolean doubleClickExit() {
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNoPause) {
            return;
        }
        //  保存当前天气信息
        SharePreferencesHelper.putString(SharePreferencesHelper.CURRENT_WEATHER_ID,
                mWeatherId, SharePreferencesHelper.WEATHER_INFO);
        mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SelectCity city = new SelectCity();
                city.setCityName(mTitleCityName.getText().toString());
                city.setWeatherId(mWeatherId);
                realm.copyToRealmOrUpdate(city);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!mIsReturn) {
            mIsReturn = true;
            return;
        }
        //  判断从编辑界面返回时是否全部删除以及是否还有编辑前的城市
        RealmResults results = mRealm.where(SelectCity.class).findAll();
        if (results.size() != 0) {
            boolean isReloadWeather = true;
            for (SelectCity city : (List<SelectCity>)results) {
                if (mWeatherId.equals(city.getWeatherId())) {
                    isReloadWeather = false;
                }
            }
            if (isReloadWeather) {
                mWeatherId = ((SelectCity)results.get(0)).getWeatherId();
                getWeatherInfo();
            }
            mNoPause = false;
        } else {
            //  都删除了重新选择地区
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(NO_CLOSE, false);
            startActivity(intent);
            mNoPause = true;
            finish();
        }
    }
}
