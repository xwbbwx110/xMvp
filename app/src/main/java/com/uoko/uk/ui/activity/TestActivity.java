package com.uoko.uk.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.uoko.uk.R;
import com.uoko.uk.mvp.contract.TestContract;
import com.uoko.uk.mvp.presenter.InstallPresenter;
import com.uoko.uk.mvp.presenter.TestPresenter;
import com.uoko.uk.rxbus.RxBus;
import com.uoko.uk.rxbus.Subscribe;
import com.uoko.uk.rxbus.ThreadMode;
import com.uoko.uk.utils.CheckPermission;
import com.uoko.uk.utils.ContextUtils;
import com.uoko.uk.utils.LogUtils;
import com.uoko.uk.utils.RXclick;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 作者: xwb on 2017/12/12
 * 描述:
 */
@InstallPresenter(presenterObject = TestPresenter.class)
public class TestActivity extends BaseUKActivity<TestPresenter> implements TestContract.ITestView {
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.get_data_btn)
    Button getDataBtn;

    @Override
    protected int getlayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        RXclick.addClick(getDataBtn, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                getmPresenter().getWeatherToCity();
                RxBus.get().send(1005,"测试字符");


//                if(!CheckPermission.isGranted(TestActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)){
//                    CheckPermission.ApplyPermission(TestActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION,"应用需要定位权限，否则应用没法工作");
//                }

                CheckPermission.DirectApplyPermission(TestActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION,"应用需要定位权限，否则应用没法工作");

            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ContextUtils.init(this);

    }

    @Override
    public void changeInfo(String info) {
        txtInfo.setText(info);
    }


    @Subscribe(code =1005,threadMode = ThreadMode.MAIN)
    public void receive1005(String msg){
        LogUtils.e("RxBus-->",msg);
    }
}
