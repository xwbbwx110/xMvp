package com.xwb.wb.ui.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uoko.uk.R;
import com.xwb.wb.utils.RXclick;
import com.xwb.wb.utils.ScreenUtils;
import com.wang.avi.AVLoadingIndicatorView;

import io.reactivex.functions.Consumer;

/**
 * 作者: xwb on 2017/12/27
 * 描述:带加载的layout
 */

public class LoadingConstraintLayout extends ConstraintLayout{
    private static final String LOADING_TAG = "wb_loading_tag";
    private  LinearLayout mLoadView;
    private AVLoadingIndicatorView mLoad_bar;
    private TextView mHint_title,mHint_content;
    private Button mOpear_btn;
    private ImageView mErrorIm;
    private int mTitleTxtColor,mContentTxtColor,mBtnTxtColor,mDefalutColor;
    private int mScreenHeight;



    public LoadingConstraintLayout(Context context) {
        this(context,null);
    }

    public LoadingConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDefalutColor = Color.WHITE;
        mScreenHeight = ScreenUtils.getScreenHeight(context)/2;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingConstraintLayout);
        mTitleTxtColor = typedArray.getColor(R.styleable.LoadingConstraintLayout_titileColor,mDefalutColor);
        mContentTxtColor = typedArray.getColor(R.styleable.LoadingConstraintLayout_contentColor,mDefalutColor);
        mBtnTxtColor = typedArray.getColor(R.styleable.LoadingConstraintLayout_btnTxtColor,mDefalutColor);


    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        buildLoadView(getContext());
        addView(mLoadView);
        mLoadView.setVisibility(View.GONE);
    }

    private void buildLoadView(Context context) {
        mLoadView =  new LinearLayout(context);
        mLoadView.setTag(LOADING_TAG);
        mLoadView.setOrientation(LinearLayout.VERTICAL);
        mLoadView.setGravity(Gravity.CENTER);
        LayoutParams loadViewParmas =  new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,mScreenHeight);
        loadViewParmas.leftToLeft = LayoutParams.PARENT_ID;
        loadViewParmas.topToTop = LayoutParams.PARENT_ID;
        loadViewParmas.rightToRight = LayoutParams.PARENT_ID;
        loadViewParmas.bottomToBottom = LayoutParams.PARENT_ID;
        mLoadView.setLayoutParams(loadViewParmas);
        LinearLayout.LayoutParams childParmas =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        childParmas.topMargin = ScreenUtils.dp2px(context,6);
        LinearLayout.LayoutParams errorParmas =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        errorParmas.bottomMargin = ScreenUtils.dp2px(context,10);
          mLoad_bar =  new AVLoadingIndicatorView(context,null, R.style.AVLoadingIndicatorView);
        mLoad_bar.setIndicator("BallTrianglePathIndicator");
        mLoad_bar.setIndicatorColor(Color.WHITE);
        mLoad_bar.setLayoutParams(childParmas);
        mErrorIm =  new ImageView(context);
        mErrorIm.setLayoutParams(errorParmas);
        mHint_title =  new TextView(context);
        mHint_title.setLayoutParams(childParmas);
        mHint_content = new TextView(context);
        mHint_content.setLayoutParams(childParmas);
        mOpear_btn = new Button(context);
        mOpear_btn.setLayoutParams(childParmas);
        mHint_title.setTextColor(mTitleTxtColor);
        mHint_content.setTextColor(mContentTxtColor);
        mOpear_btn.setTextColor(mBtnTxtColor);
        mErrorIm.setVisibility(View.GONE);
        mLoadView.addView(mErrorIm);
        mLoadView.addView(mLoad_bar);
        mLoadView.addView(mHint_title);
        mLoadView.addView(mHint_content);
        mLoadView.addView(mOpear_btn);





    }

    private void isShowContentView(boolean isShow){
        for (int i=0;i<getChildCount();i++){
            if(getChildAt(i).getTag()!=null&&((String)getChildAt(i).getTag()).equals(LOADING_TAG))
                continue;
            if(isShow){
                applyAnimation(getChildAt(i),0f,1f,450);
            }else{
                applyAnimation(getChildAt(i),1f,0f,0);
            }
        }
    }
    /**
     * 加载成功之后调用
     */
    public void loadSuccess(){
        applyAnimation(mLoad_bar,1f,0f,0f,-130f);
        applyAnimation(mHint_content,1f,0f,0f,130f);
        isShowContentView(true);
//        mLoadView.setVisibility(View.GONE);
    }

    /**
     * 开始加载
     */
    public void inLoading(){
        inLoading("");
    }

    /**
     * 开始加载，带描述
     * @param loadingDes
     */
    public void inLoading(String loadingDes){
        isShowContentView(false);
        showContent(loadingDes);
        hideTitle(View.INVISIBLE);
        hideErrorIm();
        hideOpearBtn(View.INVISIBLE);
        mLoad_bar.setVisibility(View.VISIBLE);
        mLoad_bar.smoothToShow();
        mLoadView.setVisibility(View.VISIBLE);
        applyAnimation(mLoad_bar,0f,1f,-130f,0f);
        applyAnimation(mHint_content,0f,1f,130f,0f);
    }

    /**
     * 失败的时候调用
     * @param imId 显示的图片
     * @param title 错误标题
     * @param des  错误描述
     * @param consumer 操作回调
     */
    public void loadFailure(int imId, String title, String des, Consumer consumer){
        isShowContentView(false);
        if(imId>0){
            mErrorIm.setImageResource(imId);
        }
        showTitle(title);
        showContent(des);
        mLoad_bar.setVisibility(View.GONE);
        showErrorIm();
        RXclick.addClick(mOpear_btn,consumer);
        showOpearBtn("try again");
        mLoadView.setVisibility(View.VISIBLE);
        applyAnimation(mErrorIm,0f,1f,-160f,0f);
        applyAnimation(mHint_title,0f,1f,100f,0f);
        applyAnimation(mHint_content,0f,1f,150f,0f);
        applyAnimation(mOpear_btn,0f,1f,200f,0f);

    }



    private void hideTitle(int type){
        if(mHint_title!=null){
            mHint_title.clearAnimation();
            mHint_title.setVisibility(type);
        }
    }

    private void  hideContent(int type){
        if(mHint_content!=null){
            mHint_content.clearAnimation();
            mHint_content.setVisibility(type);
        }

    }

    private void hideOpearBtn(int type){
        if(mOpear_btn!=null) {
            mOpear_btn.clearAnimation();
            mOpear_btn.setVisibility(type);
        }
    }

    private void showTitle(String hintTxt){
        if(mHint_title==null)
            return;

         mHint_title.setVisibility(View.VISIBLE);
        mHint_title.setText(hintTxt);

    }
    private void showContent(String hintTxt){
        if(mHint_content==null)
            return;

        mHint_content.setVisibility(View.VISIBLE);
        mHint_content.setText(hintTxt);
    }

    private void showOpearBtn(String hintTxt){
        if(mOpear_btn==null)
            return;

        mOpear_btn.setVisibility(View.VISIBLE);
        mOpear_btn.setText(hintTxt);
    }


    private void showLoadBar(){

        mLoad_bar.setVisibility(View.VISIBLE);
    }

    private void hideLoadbar(){
        if(mLoad_bar!=null){
            mLoad_bar.clearAnimation();
            mLoad_bar.setVisibility(View.INVISIBLE);
        }
    }

    private void showErrorIm(){
        mErrorIm.setVisibility(View.VISIBLE);
    }

    private void hideErrorIm(){
        mErrorIm.setVisibility(View.GONE);
    }





    private void applyAnimation(final View view,float vaStart,float vaEnd,int dur){
        final ValueAnimator  vaAlpha = ObjectAnimator.ofFloat(vaStart, vaEnd);
        vaAlpha.setDuration(dur);
        vaAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setAlpha((Float) valueAnimator.getAnimatedValue());
                view.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        vaAlpha.start();
    }


    private void applyAnimation(final View view,float vaStart,float vaEnd,float startTranslationY,float endTranslationY){
        //值动画
        final ValueAnimator  vaAlphaAnim = ObjectAnimator.ofFloat(vaStart, vaEnd);
        vaAlphaAnim.setDuration(450);
        vaAlphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setAlpha((Float) valueAnimator.getAnimatedValue());
                view.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        //春天动画
        final SpringAnimation springAn =  new SpringAnimation(view, DynamicAnimation.TRANSLATION_Y,endTranslationY);
        springAn.setStartValue(startTranslationY);
        springAn.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        springAn.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);

        vaAlphaAnim.start();
        springAn.start();

    }

}
