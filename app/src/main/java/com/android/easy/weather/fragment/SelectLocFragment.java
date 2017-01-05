package com.android.easy.weather.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.easy.weather.R;
import com.android.easy.weather.activity.HomeActivity;
import com.android.easy.weather.activity.WeatherActivity;
import com.android.easy.weather.base.BaseFragment;
import com.android.easy.weather.model.City;
import com.android.easy.weather.model.County;
import com.android.easy.weather.model.Province;
import com.android.easy.weather.net.ApiManager;
import com.android.easy.weather.net.RetrofitClient;
import com.android.easy.weather.util.LoggerUtil;
import com.android.easy.weather.util.PermissionUtil;
import com.android.easy.weather.util.SHA1Util;
import com.android.easy.weather.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import retrofit2.Call;

/**
 * Created by admin on 2016/12/29.
 */

public class SelectLocFragment extends BaseFragment implements PermissionUtil.OnPermissionChangeListener {
    @BindView(R.id.lv_show_list)
    ListView mListView;                             //  用于显示列表的Android控件
    @BindView(R.id.ll_loading_find_location)
    LinearLayout mLoadingFindLocationLayout;        //  HeatherVi中用于显示正在获取位置的布局
    @BindView(R.id.tv_location_info)
    TextView mLocationInfoView;                     //  HeatherVi中用于展示当前位置的控件
    @BindView(R.id.btn_use_this_location)
    Button mUseThisLocationBtn;                     //  HeatherVi中用于点击使用当前位置的控件
    @BindView(R.id.ll_already_get_location)
    RelativeLayout mAlreadyGetLocationLayout;       //  HeatherVi中用于显示位置信息的布局
    public static final int LEVEL_PROVINCE = 0;     //  用于表示等级的常量 表示省
    public static final int LEVEL_CITY = 1;         //  同上 表示市
    public static final int LEVEL_COUNTY = 2;       //  同上 表示县
    private ProgressDialogFragment mDialogFragment; //  数据加载过程中显示的弹框
    private List<Province> mProvinceList;           //  省数据列表
    private List<City> mCityList;                   //  市数据列表
    private List<County> mCountyList;               //  县数据列表
    private Province mSelectProvince;               //  选中的省
    private City mSelectCity;                       //  选中的市
    private int mCurrentLevel;                      //  当前选中的级别 (用刚才定义常量来配对)
    private ArrayAdapter<String> mAdapter;          //  ListView的数据适配器
    private List<String> mDataList;                 //  数据列表
    private ApiManager mApiManager;                 //  网络访问管理接口
    private Realm mRealm;                           //  操作数据库的实例
    private View mHeaderView;                       //  ListView的头布局
    private AMapLocationClient locationClient;      //  高德地图的一个类
    private boolean mIsFirstGetLoc = true;          //  是否第一次定位
    private String mLocationCityName;               //  定位中拿到的城市名字
    private String mWeatherId;                      //  当前县的天气Id
    private boolean mIsClick;                       //  点击条目
    private String mLocationCountyName;             //  选定的县的名字
    public static final String WEATHER_ID = "weather_id";

    /**
     * 获取SelectLocFragment实例
     * @return
     */
    public static SelectLocFragment newInstance() {
        Bundle args = new Bundle();
        SelectLocFragment fragment = new SelectLocFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void getData() {
        //  初始化各种列表防止空指针
        mDataList = new ArrayList<>();
        mProvinceList = new ArrayList<>();
        mCityList = new ArrayList<>();
        mCountyList = new ArrayList<>();
        mRealm = Realm.getDefaultInstance();
        mApiManager = RetrofitClient.getInstance().getApiManager();
        //  初始化界面时获取全国省列表
        queryProvinces();
        //  初始化定位
        initLocation();
        //  权限
        PermissionUtil.getInstance().setPermissionListener(this);
        PermissionUtil.getInstance().getLocationPermission();
        final String SHA1 = SHA1Util.getCertificateSHA1Fingerprint(getActivity());
        LoggerUtil.printGeneralLog(SHA1);
        String packageName = getActivity().getPackageName();
        LoggerUtil.printGeneralLog("package name = " + packageName);
    }

    @Override
    protected void initView() {
        //  初始化ListView
        mAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_simple_text, mDataList);
        mListView.setAdapter(mAdapter);
        mAlreadyGetLocationLayout.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.include_content_main;
    }

    @Override
    protected void initListener() {
        //  为ListView设置条目侦听
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                //  判断当前处于某一级 从而触发相应的逻辑
                //  得到header的总数量
                int headerViewsCount = mListView.getHeaderViewsCount();
                //  得到新的修正后的position
                int position = i - headerViewsCount;
                mIsClick = true;
                switch (mCurrentLevel) {
                    case LEVEL_PROVINCE:
                        //  点击省信息时保存点击的省，并去获取该省的的试列表
                        mSelectProvince = mProvinceList.get(position);
                        mCurrentLevel = LEVEL_CITY;
                        queryCities();
                        break;
                    case LEVEL_CITY:
                        //  同上保存市信息，并去查询该市下面的县信息
                        LoggerUtil.printGeneralLog(mCityList.size());
                        mSelectCity = mCityList.get(position);
                        mCurrentLevel = LEVEL_COUNTY;
                        queryCounties();
                        break;
                    case LEVEL_COUNTY:
                        //  获取查询天气时所需的weatherId
                        ((HomeActivity)getActivity()).setToolbarTitle(mCountyList.get(position).getCountyName());
                        mWeatherId = mCountyList.get(position).getWeatherId();
                        goWeatherActivity();
                        LoggerUtil.printGeneralLog(mCountyList.get(position).getCountyName() + "    " + mWeatherId);
                        break;
                }
            }
        });
        mUseThisLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goWeatherActivity();
            }
        });
    }

    @Override
    protected void onSuccess(Object object) {
        //  判断数据类型
        if (object instanceof List) {
            //  共同逻辑是设置ListView数据并保存到本地数据库中
            if (((List) object).get(0) instanceof Province) {
                mProvinceList = (List<Province>) object;
                setDataList(LEVEL_PROVINCE);
                saveData(LEVEL_PROVINCE);
            } else if (((List) object).get(0) instanceof City) {
                mCityList = (List<City>) object;
                setDataList(LEVEL_CITY);
                //  设置父id
                for (City city : mCityList) {
                    city.setProvinceId(mSelectProvince.getProvinceCode());
                }
                saveData(LEVEL_CITY);
            } else {
                mCountyList = (List<County>) object;
                setDataList(LEVEL_COUNTY);
                //  设置父id
                for (County county : mCountyList) {
                    county.setCityId(mSelectCity.getCityCode());
                }
                saveData(LEVEL_COUNTY);
            }
        }
    }

    /**
     * 保存列表到本地数据库
     *
     * @param type
     */
    private void saveData(final int type) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //  使用Realm保存数据, 该方法为异步的且自动开启事务
                switch (type) {
                    case LEVEL_PROVINCE:
                        realm.copyToRealmOrUpdate(mProvinceList);
                        break;
                    case LEVEL_CITY:
                        realm.copyToRealmOrUpdate(mCityList);
                        break;
                    case LEVEL_COUNTY:
                        realm.copyToRealmOrUpdate(mCountyList);
                        break;
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //  成功的回调
                switch (type) {
                    case LEVEL_PROVINCE:
                        ToastUtil.showShortToast("省列表保存成功");
                        break;
                    case LEVEL_CITY:
                        ToastUtil.showShortToast("市列表保存成功");
                        break;
                    case LEVEL_COUNTY:
                        ToastUtil.showShortToast("县列表保存成功");
                        break;
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                //  失败的回调
                LoggerUtil.printExceptionOnly(new Exception(error));
            }
        });

    }

    /**
     * 根据传进来的int值进行判断 从而为mDateList赋值
     * 判断是否是定位还是点击获取数据 通过mIsClick区分
     *
     * @param type
     */
    private void setDataList(int type) {
        //  保证定位后会显示省列表
        if (mIsClick) {
            mDataList.clear();
        }
        switch (type) {
            case LEVEL_PROVINCE:
                for (Province province : mProvinceList) {
                    mDataList.add(province.getProvinceName());
                }
                break;
            case LEVEL_CITY:
                for (City city : mCityList) {
                    //  判断是否为点击事件
                    if (mIsClick) {
                        mDataList.add(city.getCityName());
                    } else {
                        if (mLocationCityName.contains(city.getCityName())) {
                            mSelectCity = city;
                            queryCounties();
                        }
                    }
                }
                break;
            case LEVEL_COUNTY:
                for (County county : mCountyList) {
                    //  判断是否为点击事件
                    if (mIsClick) {
                        mDataList.add(county.getCountyName());
                    } else {
                        if (mLocationCountyName.contains(county.getCountyName())
                                || mLocationCityName.contains(county.getCountyName())) {
                            mWeatherId = county.getWeatherId();
                            LoggerUtil.printGeneralLog(mLocationCountyName + "  " + mWeatherId);
                        }
                    }
                }
                break;
        }
        if (mAdapter != null) {
            if (mIsClick || mCurrentLevel == LEVEL_PROVINCE) {
                //  获取要显示的数据后刷新ListView
                mAdapter.notifyDataSetChanged();
            }
        }
        mIsClick = false;
    }

    /**
     * 查询省列表
     */
    public void queryProvinces() {
        //  设置Toolbar显示
        ((HomeActivity)getActivity()).setToolbarTitle("中国");
        // 先去本地数据库拿数据,如果还没有会去做网络请求
        mProvinceList = mRealm.where(Province.class).findAll();
        if (!mProvinceList.isEmpty()) {
            setDataList(mCurrentLevel);
        } else {
            Call<List<Province>> call = mApiManager.getChinaProvince();
            getData(call);
        }
    }

    /**
     * 查询某省的市列表
     */
    public void queryCities() {
        //  设置Toolbar显示
        ((HomeActivity)getActivity()).setToolbarTitle(mSelectProvince.getProvinceName());
        //  先去本地数据库拿数据,如果还没有会去做网络请求
        mCityList = mRealm.where(City.class).equalTo("provinceId", mSelectProvince.getProvinceCode()).findAll();
        if (!mCityList.isEmpty()) {
            setDataList(LEVEL_CITY);
        } else {
            Call<List<City>> call = mApiManager.getChinaCity(mSelectProvince.getProvinceCode());
            getData(call);
        }
    }

    /**
     * 查询某市的县列表
     */
    public void queryCounties() {
        //  设置Toolbar显示
        ((HomeActivity)getActivity()).setToolbarTitle(mSelectCity.getCityName());
        //  先去本地数据库拿数据,如果还没有会去做网络请求
        mCountyList = mRealm.where(County.class).equalTo("cityId", mSelectCity.getCityCode()).findAll();
        if (!mCountyList.isEmpty()) {
            setDataList(LEVEL_COUNTY);
        } else {
            Call<List<County>> call = mApiManager.getChinaCounty(mSelectProvince.getProvinceCode(),
                    mSelectCity.getCityCode());
            getData(call);
        }
    }

    /**
     * 权限获取的时候的回调
     */
    @Override
    public void onGranted(int permissionType) {
        switch (permissionType) {
            case PermissionUtil.LOCATION_PERMISSION:
                // 启动定位
                locationClient.startLocation();
                break;
        }
    }

    /**
     * 权限拒绝的时候的回调
     */
    @Override
    public void onDenied(int permissionType) {
        switch (permissionType) {
            case PermissionUtil.LOCATION_PERMISSION:
                ToastUtil.showShortToast("您拒绝了App获取您的位置, 请您通过列表选择确定要查看天气的位置...");
                mAlreadyGetLocationLayout.setVisibility(View.VISIBLE);
                mLoadingFindLocationLayout.setVisibility(View.GONE);
                mUseThisLocationBtn.setText("我还要使用定位!!!");
                mLocationInfoView.setText("您还可以通过列表选择确定要查看天气的位置...");
                break;
        }
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(getActivity());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setGpsFirst(false);
        //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setHttpTimeOut(30000);
        //可选，设置定位间隔。默认为2秒
        mOption.setInterval(2000);
        //可选，设置是否返回逆地理地址信息。默认是true
        mOption.setNeedAddress(true);
        //可选，设置是否单次定位。默认是false
        mOption.setOnceLocation(false);
        //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        mOption.setOnceLocationLatest(false);
        //开启缓存
        mOption.setLocationCacheEnable(true);
        //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        //可选，设置是否使用传感器。默认是false
        mOption.setSensorEnable(false);
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(final AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
                if (mIsFirstGetLoc) {
                    mIsFirstGetLoc = false;
                    //  获取城市名字和县名字
                    mLocationCityName = loc.getCity();
                    mLocationCountyName = loc.getDistrict();
                    //  修改视图
                    mAlreadyGetLocationLayout.setVisibility(View.VISIBLE);
                    mLoadingFindLocationLayout.setVisibility(View.GONE);
                    if ("".equals(mLocationCityName)
                            || "".equals(mLocationCountyName)) {
                        mLocationInfoView.setText("获取当前位置失败...      失败原因: "
                                + loc.getErrorInfo());
                        mUseThisLocationBtn.setVisibility(View.GONE);

                    } else {
                        mLocationInfoView.setText("当前位置: " + loc.getProvince()
                                + mLocationCityName + mLocationCountyName);
                    }
                    //  定位成功后去获取weatherId
                    for (Province province : mProvinceList) {
                        if (loc.getProvince().contains(province.getProvinceName())) {
                            mSelectProvince = province;
                            queryCities();
                        }
                    }
                }
            } else {
                ToastUtil.showShortToast("获取当前位置失败...");
            }
        }
    };

    /**
     * 设置是否点击, 供Fragment调用
     * @param isClick
     */
    public void setIsClick(boolean isClick) {
        this.mIsClick = isClick;
    }

    /**
     * 获取当前城市等级, 供Fragment调用
     * @return
     */
    public int getCurrentLevel() {
        return mCurrentLevel;
    }

    /**
     * 设置当前城市等级, 供Fragment调用
     * @param currentLevel
     */
    public void setCurrentLevel(int currentLevel) {
        this.mCurrentLevel = currentLevel;
    }

    /**
     * 跳转到WeatherActivity
     */
    private void goWeatherActivity() {
        Intent intent = new Intent(getActivity(), WeatherActivity.class);
        intent.putExtra(WEATHER_ID, mWeatherId);
        startActivity(intent);
        getActivity().finish();
    }
}
