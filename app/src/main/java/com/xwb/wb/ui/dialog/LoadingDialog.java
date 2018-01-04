package com.xwb.wb.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.uoko.uk.R;

/**
 * 作者: xwb on 2018/1/3
 * 描述:
 */

public class LoadingDialog extends BaseWBDialog {

    private String[] tipStrs;
    TextView dialog_tip_txt;
    public static LoadingDialog newInstance() {
        Bundle args = new Bundle();
        
        LoadingDialog fragment = new LoadingDialog();
        fragment.setArguments(args);
        return fragment;
    }



    
    
    @Override
    protected int getlayoutId() {
        return R.layout.dl_loading_layout;
    }

    @Override
    protected void initView(View view, Activity ac) {
        dialog_tip_txt = view.findViewById(R.id.dialog_tip_txt);
        dialog_tip_txt.setText(tipStrs[0]);
    }

    @Override
    protected void initData(Activity ac, Bundle savedInstanceState) {
        setCanceledOnTouchOutside(false);
    }

    @Override
    public BaseWBDialog setShowMsg(String... strs) {
        tipStrs = strs;
        return this;
    }
}
