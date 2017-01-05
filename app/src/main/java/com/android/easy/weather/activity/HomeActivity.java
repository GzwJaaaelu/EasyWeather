package com.android.easy.weather.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.easy.weather.R;
import com.android.easy.weather.base.BaseActivity;
import com.android.easy.weather.fragment.SelectLocFragment;
import com.android.easy.weather.model.SelectCity;
import com.android.easy.weather.util.SharePreferencesHelper;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.android.easy.weather.fragment.SelectLocFragment.LEVEL_CITY;
import static com.android.easy.weather.fragment.SelectLocFragment.LEVEL_PROVINCE;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;                     //  Toolbar
    @BindView(R.id.toolbar_title)
    protected TextView mToolbarTitle;               //  用于显示中间标题的控件
    private SelectLocFragment mCurFragment;         //  用于显示城市列表的Fragment

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void getData() {
        boolean isClose = getIntent().getBooleanExtra(WeatherActivity.NO_CLOSE, true);
        //  判断是否需要进行自动finish, 因为在之后手动切换城市的时候不用下面的逻辑
        if (isClose) {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<SelectCity> realmResults = realm.where(SelectCity.class).findAll();
            //  判断是否有已保存过得数据
            if (!realmResults.isEmpty()) {
                Intent intent = new Intent(this, WeatherActivity.class);
                //  获取最后一次显示的城市的天气Id
                String currentWeatherId = SharePreferencesHelper.getString(SharePreferencesHelper.CURRENT_WEATHER_ID,
                        SharePreferencesHelper.WEATHER_INFO);
                if (!TextUtils.isEmpty(currentWeatherId)) {
                    intent.putExtra(SelectLocFragment.WEATHER_ID, currentWeatherId);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    @Override
    protected void initView() {
        //  显示指定Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mCurFragment = SelectLocFragment.newInstance();
        transaction.replace(R.id.fl_container, mCurFragment);
        transaction.commit();
        //  初始化toolbar
        initToolbar(mToolbar);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onSuccess(Object data) {

    }

    /**
     * 重写返回键逻辑
     */
    @Override
    public void onBackPressed() {
        //  如果不是省列表就返回上一级列表直到显示为省列表时就会执行真正的返回键逻辑
        //  这里假装是点击事件, 保证数据正常显示
        mCurFragment.setIsClick(true);
        int currentLevel = mCurFragment.getCurrentLevel();
        if (currentLevel == SelectLocFragment.LEVEL_COUNTY) {
            mCurFragment.setCurrentLevel(LEVEL_CITY);
            mCurFragment.queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            mCurFragment.setCurrentLevel(LEVEL_PROVINCE);
            mCurFragment.queryProvinces();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 动态设置标题显示
     * @param title
     */
    public void setToolbarTitle (String title) {
        mToolbarTitle.setText(title);
    }
}
