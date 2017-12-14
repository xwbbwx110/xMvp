package com.uoko.uk.utils;

import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uoko.uk.ui.activity.BaseUKActivity;
import com.uoko.uk.ui.dialog.PermissionInfoDialog;

import io.reactivex.functions.Consumer;

/**
 * 作者: xwb on 2017/12/14
 * 描述: 需要申请的权限组
 *
 * CALENDAR,CAMERA,CONTACTS,LOCATION,MICROPHONE,PHONE,SENSORS
 * ,SMS,STORAGE
 */

public class CheckPermission {
    /**
     * 直接申请，包括判断和弹对话框
     */
    public static void DirectApplyPermission(BaseUKActivity ac,String permission,String perDeatil){
        if(!isGranted(ac,permission)){
            ApplyPermission(ac,permission,perDeatil);
        }
    }
    /**
     * 申请权限-》弹出对话框
     * @return
     */
    public static   void ApplyPermission(final BaseUKActivity ac, final String permission,final String perDeatil){
            new RxPermissions(ac)
                    .request(permission).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if(!aBoolean){
                        if(!ActivityCompat.shouldShowRequestPermissionRationale(ac,permission)){
                            PermissionInfoDialog.newInstance(perDeatil).show(ac.getSupportFragmentManager(),"perdialog");
                        }
                    }
                }
            });
    }
    /**
     * 是否已申请
     * @return
     */
    public static boolean isGranted(BaseUKActivity ac,String permission){

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M)
            return true;

        return  new RxPermissions(ac).isGranted(permission);
    }


}
