package com.uoko.uk.ui.dialog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.uoko.uk.R;
import com.uoko.uk.enity.Fileds;
import com.uoko.uk.utils.RXclick;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/**
 * 作者: xwb on 2017/12/14
 * 描述:
 */

public class PermissionInfoDialog extends BaseUKDialog {
    @BindView(R.id.permission_info_txt)
    TextView permissionInfoTxt;
    @BindView(R.id.open_per_btn)
    Button openPerBtn;
    Unbinder unbinder;

    @Override
    protected int getlayoutId() {
        return R.layout.dl_permission_info_layout;
    }

    @Override
    protected void initView(View view, Activity ac) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initData(Activity ac, Bundle savedInstanceState) {
        permissionInfoTxt.setText(getArguments().getString(Fileds.PERMISSION_DEATIL));
        RXclick.addClick(openPerBtn, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                openPermission();
                dismiss();
            }
        });
    }

    public static PermissionInfoDialog newInstance(String perDeatil) {

        Bundle args = new Bundle();
        args.putString(Fileds.PERMISSION_DEATIL,perDeatil);
        PermissionInfoDialog fragment = new PermissionInfoDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void openPermission(){
        dismiss();
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
