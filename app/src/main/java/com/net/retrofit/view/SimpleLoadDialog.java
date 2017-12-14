package com.net.retrofit.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;


import com.net.retrofit.http.ProgressCancelListener;

import java.lang.ref.WeakReference;


/**
 *
 */
public class SimpleLoadDialog extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;
    private final WeakReference<Context> reference;

    private ProgressDialog mDialog;
    private String msg ;

    public SimpleLoadDialog(Context context, ProgressCancelListener mProgressCancelListener,
                            boolean cancelable , String msg) {
        super();
        this.reference = new WeakReference<Context>(context);
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
        this.msg = msg ;
    }

    private void create(){
        if (mDialog == null) {
            context  = reference.get();

            mDialog = ProgressDialog.show(context, null, msg);
//            View dialogView = LayoutInflater.from(context).inflate(
//                    R.layout.custom_sload_layout, null);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(cancelable);
//            mDialog.setContentView(dialogView);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(mProgressCancelListener!=null)
                        mProgressCancelListener.onCancelProgress();
                }
            });
//            Window dialogWindow = mDialog.getWindow();
//            dialogWindow.setGravity(Gravity.CENTER_VERTICAL
//                    | Gravity.CENTER_HORIZONTAL);
        }
        if (!mDialog.isShowing()&&context!=null) {
            mDialog.show();
        }
    }

    public void show(){
        create();
    }


    public  void dismiss() {
        context  = reference.get();
        if (mDialog != null&&mDialog.isShowing()&&!((Activity) context).isFinishing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                create();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismiss();
                break;
        }
    }
}
