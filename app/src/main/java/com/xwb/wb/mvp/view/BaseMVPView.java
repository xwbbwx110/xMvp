package com.xwb.wb.mvp.view;

import com.xwb.wb.ui.activity.BaseWBActivity;
import com.xwb.wb.ui.dialog.BaseWBDialog;
import com.xwb.wb.ui.widget.LoadingConstraintLayout;

/**
 * 作者: xwb on 2017/12/11
 * 描述:可以啥都没有，用来约束类型，
 * 也可以写一些 共用方法
 */

public interface BaseMVPView {
    LoadingConstraintLayout getLoadingToLayout();
    BaseWBDialog getLoadingToDialog();
    BaseWBActivity getCurAc();

}
