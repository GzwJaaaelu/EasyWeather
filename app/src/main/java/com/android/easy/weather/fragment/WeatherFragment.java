package com.android.easy.weather.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.easy.weather.R;
import com.android.easy.weather.activity.WeatherActivity;
import com.android.easy.weather.base.BaseFragment;
import com.android.easy.weather.model.SelectCity;
import com.android.easy.weather.model.Weather.HeWeather;
import com.android.easy.weather.model.Weather.HeWeather.DailyForecast;
import com.android.easy.weather.util.LoggerUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/29.
 */

public class WeatherFragment extends BaseFragment {
    @BindView(R.id.tv_degree)
    TextView mDegree;                       //  显示当前温度
    @BindView(R.id.tv_weather_info)
    TextView mWeatherInfo;                  //  显示天气信息 如: 晴/多云之类
    @BindView(R.id.iv_weather_icon)
    ImageView mWeatherIcon;                 //  当前天气对应的天气图标
    @BindView(R.id.tv_weather_quality)
    TextView mWeatherQuality;               //  显示当前空气质量
    @BindView(R.id.rl_forecast_layout)
    RecyclerView mForecastList;             //  未来天气的预报值
    @BindView(R.id.tv_aqi)
    TextView mAqi;                          //  显示API值
    @BindView(R.id.tv_pm25)
    TextView mPm25;                         //  显示PM2.5值
    @BindView(R.id.tv_comfort)
    TextView mComfort;                      //  舒适度描述
    @BindView(R.id.tv_car_wash)
    TextView mCarWash;                      //  洗车描述
    @BindView(R.id.tv_sport)
    TextView mSport;                        //  运动描述
    @BindView(R.id.sv_view_scroll)
    ScrollView mViewScroll;                 //  滚动条
    @BindView(R.id.srl_refresh)
    public SwipeRefreshLayout swipeRefresh;
    private HeWeather mWeatherData;         //  天气信息数据
    private List<DailyForecast> mDailyForecast;
    private WeatherForecastAdapter mAdapter;

    /**
     * 返回WeatherFragment实例
     *
     * @return
     */
    public static WeatherFragment newInstance() {

        Bundle args = new Bundle();

        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void getData() {
        mDailyForecast = new ArrayList<>();
    }

    @Override
    protected void initView() {
        mForecastList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WeatherForecastAdapter();
        mForecastList.setAdapter(mAdapter);

        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
    }

    @Override
    protected void initListener() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((WeatherActivity)getActivity()).getWeatherInfo();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_show_weather;
    }

    /**
     * 刷新数据
     * @param weatherInfo
     */
    public void setWeatherInfo(HeWeather weatherInfo) {
        mWeatherData = weatherInfo;
        mDegree.setText(mWeatherData.getNow().getTmp() + "℃");
        mWeatherInfo.setText(mWeatherData.getNow().getCond().getTxt());
        String weatherIconUri = getWeatherIcon(Integer.valueOf(mWeatherData.getNow().getCond().getCode()));
        Picasso.with(getActivity())
                .load(weatherIconUri)
                .resize(150, 150)
                .centerCrop()
                .into(this.mWeatherIcon);
        if (mWeatherData.getAqi() != null) {
            mWeatherQuality.setText(mWeatherData.getAqi().getCity().getQlty());
            mAqi.setText(mWeatherData.getAqi().getCity().getAqi());
            mPm25.setText(mWeatherData.getAqi().getCity().getPm25());
        }
        mDailyForecast = weatherInfo.getDaily_forecast();
        mAdapter.notifyDataSetChanged();
        mComfort.setText("舒适度" + ":   "
                + mWeatherData.getSuggestion().getComf().getTxt());
        mCarWash.setText("洗车指数" + ":   "
                + mWeatherData.getSuggestion().getCw().getTxt());
        mSport.setText("运动指数" + ":   "
                + mWeatherData.getSuggestion().getSport().getTxt());
        swipeRefresh.setRefreshing(false);
    }

    /**
     * 天气预报条目的Holder
     */
    class WeatherForecastHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_show_day)
        TextView showDay;
        @BindView(R.id.iv_weather_icon)
        ImageView weatherIcon;
        @BindView(R.id.tv_temperature_max_min)
        TextView temperatureMaxMin;
        @BindView(R.id.tv_weather_info)
        TextView weatherInfo;

        public WeatherForecastHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            showDay.setText(mDailyForecast.get(position).getDate());
            String weatherIconUri;
            Calendar time = Calendar.getInstance();
            int hour = time.get(Calendar.HOUR_OF_DAY);
            LoggerUtil.printGeneralLog("hour: " + hour);
            //  根据日/夜获取天气Icon 认为早七晚七是白天
            if (hour > 7 && hour < 19) {
                weatherInfo.setText(mDailyForecast.get(position).getCond().getTxt_d());
                weatherIconUri = getWeatherIcon(Integer.valueOf(mDailyForecast.get(position).getCond().getCode_d()));
            } else {
                weatherInfo.setText(mDailyForecast.get(position).getCond().getTxt_n());
                weatherIconUri = getWeatherIcon(Integer.valueOf(mDailyForecast.get(position).getCond().getCode_n()));
            }
            Picasso.with(getActivity())
                    .load(weatherIconUri)
                    .resize(150, 150)
                    .centerCrop()
                    .into(this.weatherIcon);
            temperatureMaxMin.setText(mDailyForecast.get(position).getTmp().getMax() + "℃"
                    + " / " + mDailyForecast.get(position).getTmp().getMin() + "℃");
        }
    }

    class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastHolder> {

        @Override
        public WeatherForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_forecast, parent, false);
            return new WeatherForecastHolder(view);
        }

        @Override
        public void onBindViewHolder(WeatherForecastHolder holder, int position) {
            holder.setData(position);
        }

        @Override
        public int getItemCount() {
            return mDailyForecast.size();
        }
    }

    /**
     * 获取不同天气对应的不同图标
     *
     * @param code
     * @return
     */
    private String getWeatherIcon(int code) {
        String weatherIconUri;
        switch (code) {
            case 100:
                //  晴
                weatherIconUri = "http://files.heweather.com/cond_icon/100.png";
                break;
            case 101:
                //  多云
                weatherIconUri = "http://files.heweather.com/cond_icon/101.png";
                break;
            case 102:
                //  少云
                weatherIconUri = "http://files.heweather.com/cond_icon/102.png";
                break;
            case 103:
                //  晴间多云
                weatherIconUri = "http://files.heweather.com/cond_icon/103.png";
                break;
            case 104:
                //  阴
                weatherIconUri = "http://files.heweather.com/cond_icon/104.png";
                break;
            case 200:
                //  有风
                weatherIconUri = "http://files.heweather.com/cond_icon/200.png";
                break;
            case 201:
                //  晴
                weatherIconUri = "http://files.heweather.com/cond_icon/201.png";
                break;
            case 202:
                //  平静
                weatherIconUri = "http://files.heweather.com/cond_icon/202.png";
                break;
            case 300:
                //  阵雨
                weatherIconUri = "http://files.heweather.com/cond_icon/300.png";
                break;
            case 301:
                //  强阵雨
                weatherIconUri = "http://files.heweather.com/cond_icon/301.png";
                break;
            case 302:
                //  雷阵雨
                weatherIconUri = "http://files.heweather.com/cond_icon/302.png";
                break;
            case 304:
                //  雷阵雨伴有冰雹
                weatherIconUri = "http://files.heweather.com/cond_icon/304.png";
                break;
            case 305:
                //  小雨
                weatherIconUri = "http://files.heweather.com/cond_icon/305.png";
                break;
            case 306:
                //  中雨
                weatherIconUri = "http://files.heweather.com/cond_icon/306.png";
                break;
            case 307:
                //  大雨
                weatherIconUri = "http://files.heweather.com/cond_icon/307.png";
                break;
            case 309:
                //  毛毛雨/细雨
                weatherIconUri = "http://files.heweather.com/cond_icon/309.png";
                break;
            case 310:
                //  暴雨
                weatherIconUri = "http://files.heweather.com/cond_icon/310.png";
                break;
            case 400:
                //  小雪
                weatherIconUri = "http://files.heweather.com/cond_icon/400.png";
                break;
            case 401:
                //  中雪
                weatherIconUri = "http://files.heweather.com/cond_icon/401.png";
                break;
            case 402:
                //  大雪
                weatherIconUri = "http://files.heweather.com/cond_icon/402.png";
                break;
            case 404:
                //  雨夹雪
                weatherIconUri = "http://files.heweather.com/cond_icon/404.png";
                break;
            case 405:
                //  雨雪天气
                weatherIconUri = "http://files.heweather.com/cond_icon/405.png";
                break;
            case 406:
                //  阵雨夹雪
                weatherIconUri = "http://files.heweather.com/cond_icon/406.png";
                break;
            case 501:
                //  雾
                weatherIconUri = "http://files.heweather.com/cond_icon/501.png";
                break;
            case 502:
                //  霾
                weatherIconUri = "http://files.heweather.com/cond_icon/502.png";
                break;
            default:
                //  未知
                weatherIconUri = "http://files.heweather.com/cond_icon/999.png";
        }
        return weatherIconUri;
    }

}
