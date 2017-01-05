package com.android.easy.weather.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.easy.weather.util.LoggerUtil;
import com.android.easy.weather.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2016/12/22.
 */

public abstract class BaseFragment extends Fragment{
    private View mConvertView;
    private boolean mIsButterKnife;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutResId() == 0) {
            throw new IllegalArgumentException("Didn't find the specified layout!");
        }
        mConvertView = inflater.inflate(getLayoutResId(), container, false);
        mIsButterKnife = isButterKnifeInject();
        if (mIsButterKnife) {
            unbinder = ButterKnife.bind(this, mConvertView);
        }
        getData();
        initView();
        initListener();
        return mConvertView;
    }

    /**
     * 页面初始化时获取数据, 包括需要请求的数据和Activity传递过来的数据
     */
    protected abstract void getData();

    /**
     * 网络请求, 用于获取列表项数据
     * @param call  请求
     * @param <T>   请求的具体数据
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
     * 网络请求成功的回调
     * @param data
     */
    protected void onSuccess(Object data) {

    }

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化控件侦听
     */
    protected abstract void initListener();

    /**
     * 设置Fragment的布局Id
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 是否使用第三方快速获取控件
     * @return
     */
    protected boolean isButterKnifeInject() {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mIsButterKnife && unbinder != null) {
            unbinder.unbind();
            mIsButterKnife = false;
        }
    }
}
