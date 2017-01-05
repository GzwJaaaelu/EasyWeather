package com.android.easy.weather.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.easy.weather.R;
import com.android.easy.weather.base.BaseActivity;
import com.android.easy.weather.fragment.SelectLocFragment;
import com.android.easy.weather.model.SelectCity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.android.easy.weather.activity.WeatherActivity.NO_CLOSE;

public class EditSelectCityActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;                     //  Toolbar
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;                         //  Toolbar中间的标题
    @BindView(R.id.tv_hint)
    TextView mHint;                                 //  灰色栏的提示文字(非编辑模式显示)
    @BindView(R.id.iv_choose_done)
    ImageView mChooseDone;                          //  切换编辑模式后的对勾
    @BindView(R.id.tv_choose_count)
    TextView mChooseCount;                          //  切换编辑模式后的显示选择个数的TextView
    @BindView(R.id.iv_delete_choose)
    ImageView mDeleteChoose;                        //  切换编辑模式后的删除图标
    @BindView(R.id.rl_city_list)
    RecyclerView mCityListView;                     //  列表控件
    @BindView(R.id.in_edit_bar)
    View mEditBarView;                              //  编辑模式界面
    private List<SelectCity> mSelectCities;         //  用来存已选的城市列表
    private SelectCityListAdapter mAdapter;         //  适配器
    public boolean mIsEditList = false;             //  是否正在编辑
    public List<Integer> mDeleteList;               //  选择要删除的条目的下标
    private Realm mRealm;                           //  Realm实例
    private RealmResults<SelectCity> mResults;      //  Realm数据库的结果集

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_select_city;
    }

    @Override
    protected void getData() {
        //  查询已经存入本地的已选城市列表
        mRealm = Realm.getDefaultInstance();
        mResults = mRealm.where(SelectCity.class).findAll();
        mSelectCities = mResults;
        mDeleteList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        mCityListView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SelectCityListAdapter();
        mCityListView.setAdapter(mAdapter);
        initToolbar(mToolbar);
        mToolbarTitle.setText("已选城市列表");
    }

    @Override
    protected void initListener() {
        //  对勾逻辑 什么也不做只是切换界面显示
        mChooseDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditVisibility(false);
                setEditList(false);
            }
        });
        //  删除逻辑
        mDeleteChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(mDeleteList);
                for (int i = mDeleteList.size() - 1; i >= 0; i--) {
                    final int index = mDeleteList.get(i);
                    deleteItemData(index);
                }
                setEditList(false);
                setEditVisibility(false);
                mDeleteList.clear();
                mChooseCount.setText("已选择" + mDeleteList.size() + "个");
            }
        });
    }

    /**
     * 删除数据 在后台线程进行
     */
    private void deleteItemData(final int index) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mResults.deleteFromRealm(index);
            }
        });
    }

    @Override
    protected void onSuccess(Object data) {

    }

    /**
     * 设置是否为编辑模式并刷新列表
     * @param isEditList
     */
    public void setEditList(boolean isEditList) {
        this.mIsEditList = isEditList;
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 是否可见编辑界面，用于编辑界面非编辑界面的切换
     * @param visibility
     */
    public void setEditVisibility(boolean visibility) {
        mEditBarView.setVisibility(visibility ? View.VISIBLE : View.GONE);
        mHint.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    /**
     * 手机右上角的小菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 手机右上角小菜单的选择侦听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra(NO_CLOSE, false);
                startActivity(intent);
                return true;
            case R.id.action_edit:
                setEditVisibility(true);
                setEditList(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class SelectCityListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_delete_checkbox)
        CheckBox deleteCheckbox;                //  删除的单选框
        @BindView(R.id.tv_curr_num)
        TextView currNum;                       //  当前的下标数
        @BindView(R.id.tv_city_name)
        TextView cityName;                      //  城市名
        @BindView(R.id.iv_go_weather_info)
        ImageView goWeatherInfo;                //  用来跳转的按钮

        public SelectCityListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //  切换到其他城市的天气信息
            goWeatherInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EditSelectCityActivity.this, WeatherActivity.class);
                    //  获取当前点击的城市的天气Id
                    intent.putExtra(SelectLocFragment.WEATHER_ID, mSelectCities.get(getAdapterPosition()).getWeatherId());
                    startActivity(intent);
                    finish();
                }
            });
            //  删除城市的单选框
            deleteCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (mIsEditList) {
                        if (mDeleteList.contains(getAdapterPosition())) {
                            mDeleteList.remove((Integer)getAdapterPosition());
                        } else {
                            mDeleteList.add(getAdapterPosition());
                        }
                        mChooseCount.setText("已选择" + mDeleteList.size() + "个");
                    }
                }
            });
        }

        /**
         * 设置数据
         * @param position  条目下标
         */
        public void setData(int position) {
            cityName.setText(mSelectCities.get(position).getCityName());
            currNum.setText(position + 1 + "");
            deleteCheckbox.setChecked(false);
        }

        /**
         * 是否被选中
         */
        public void isShowCheckBox() {
            deleteCheckbox.setVisibility(mIsEditList ? View.VISIBLE : View.GONE);
            currNum.setVisibility(mIsEditList ? View.INVISIBLE : View.VISIBLE);
        }
    }

    class SelectCityListAdapter extends RecyclerView.Adapter<SelectCityListHolder> {

        @Override
        public SelectCityListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_select_city, parent, false);
            return new SelectCityListHolder(view);
        }

        @Override
        public void onBindViewHolder(SelectCityListHolder holder, int position) {
            holder.setData(position);
            holder.isShowCheckBox();
        }

        @Override
        public int getItemCount() {
            return mSelectCities.size();
        }
    }

    @Override
    public void onBackPressed() {
        //  重写逻辑, 如果处于编辑模式就先切换回普通界面
        if (mIsEditList) {
            setEditVisibility(false);
            setEditList(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  并关闭Realm
        mRealm.close();
    }
}
