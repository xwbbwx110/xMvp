package com.xwb.wb.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uoko.uk.R;
import com.xwb.wb.mvp.contract.TestContract;
import com.xwb.wb.mvp.presenter.InstallPresenter;
import com.xwb.wb.mvp.presenter.TestPresenter;
import com.xwb.wb.rxbus.RxBus;
import com.xwb.wb.rxbus.Subscribe;
import com.xwb.wb.rxbus.ThreadMode;
import com.xwb.wb.ui.dialog.LoadingDialog;
import com.xwb.wb.ui.widget.LoadingConstraintLayout;
import com.xwb.wb.utils.CheckPermission;
import com.xwb.wb.utils.LogUtils;
import com.xwb.wb.utils.RXclick;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 作者: xwb on 2017/12/12
 * 描述:
 */
@InstallPresenter(presenterObject = TestPresenter.class)
public class TestActivity extends BaseWBActivity<TestPresenter> implements TestContract.ITestView {
    @BindView(R.id.get_data_btn)
    Button getDataBtn;
    @BindView(R.id.content_view)
    LoadingConstraintLayout contentView;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.opear_lay)
    LinearLayout opearLay;
    @BindView((R.id.test_txt))
    TextView testTxt;

    private LoadingDialog loadingDialog;


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
                RxBus.get().send(1005, "测试字符");

//                if(!CheckPermission.isGranted(TestActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)){
//                    CheckPermission.ApplyPermission(TestActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION,"应用需要定位权限，否则应用没法工作");
//                }
                CheckPermission.DirectApplyPermission(TestActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION, "应用需要定位权限，否则应用没法工作");

            }
        });
    }


    @Override
    public LoadingConstraintLayout loadingToLayout() {
        return contentView;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        RXclick.addClick(btn1, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                contentView.inLoading("少侠，请稍等.....");
            }
        });

        RXclick.addClick(btn2, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                contentView.loadSuccess();
            }
        });
        RXclick.addClick(btn3, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {

                contentView.loadFailure(R.mipmap.wifi_error_im, "糟糕出问题了", "您的网络好像没有连接，检查您的网络", new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        contentView.inLoading("少侠，再次尝试.....");
                    }
                });
            }
        });

    }

    @Override
    public void changeInfo(String info) {
        testTxt.setText(info);
    }


    @Subscribe(code = 1005, threadMode = ThreadMode.MAIN)
    public void receive1005(String msg) {
        LogUtils.e("RxBus-->", msg);
    }

}
