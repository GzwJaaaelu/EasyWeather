package com.android.easy.weather.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.easy.weather.R;
import com.android.easy.weather.fragment.ProgressDialogFragment;
import com.android.easy.weather.util.LoggerUtil;
import com.android.easy.weather.util.ToastUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2016/12/22.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            if (isImmersion() && Build.VERSION.SDK_INT >= 21) {
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
            //  设置界面布局
            setContentView(getLayoutId());
            //  开启View绑定
            ButterKnife.bind(this);
            getData();
            initView();
//            initToolbar();
            initListener();
        } else {
            throw new IllegalArgumentException("No found the specified layout");
        }
    }

    /**
     * 获取当前界面布局
     *
     * @return 当前界面布局的ResId
     */
    protected abstract int getLayoutId();

    /**
     * 获取当前界面所需的数据
     */
    protected abstract void getData();

    /**
     * 初始化当前界面的控件
     */
    protected abstract void initView();

    /**
     * 初始化当前界面的标题栏
     */
    protected void initToolbar(Toolbar mToolbar) {
        setSupportActionBar(mToolbar);
        //  设置了左上角的返回按钮
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //  去掉自带的Title 是的标题居中显示
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

    /**
     * 初始化控件侦听
     */
    protected abstract void initListener();

    /**
     * 网络请求  用于获取列表项数据
     *
     * @param call 请求
     * @param <T>  请求的具体数据
     */
    protected <T> void getData(Call<T> call) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                ToastUtil.showShortToast("网络好像出了问题...");
                LoggerUtil.printExceptionOnly(new Exception(t));
            }
        });
    }

    /**
     * 网络请求  用于获取列表项数据
     *
     * @param call 请求
     * @param <T>  请求的具体数据
     */
    protected <T> void getData(Call<T> call, final ProgressDialogFragment fragment) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    onSuccess(response.body());
                }
                fragment.dismiss();
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                ToastUtil.showShortToast("网络好像出了问题...");
                LoggerUtil.printExceptionOnly(new Exception(t));
                fragment.dismiss();
            }
        });
    }

    /**
     * 网络访问成功会调用该方法
     *
     * @param data
     */
    protected abstract void onSuccess(Object data);

    /**
     * 是否显示透明标题栏(沉浸式)
     *
     * @return 是 or 否
     */
    protected boolean isImmersion() {
        return false;
    }

}
