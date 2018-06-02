package co.tpcreative.common.ui;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import co.tpcreative.R;


public class RelativeLayoutCustom extends RelativeLayout {
    Context context;
    Animation inAnimation;
    Animation outAnimation;
    public static final String TAG= RelativeLayoutCustom.class.getSimpleName();

    public RelativeLayoutCustom(Context context)
    {
        super(context);
        this.context = context;
        initAnimations();
    }

    public RelativeLayoutCustom(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        initAnimations();
    }

    public RelativeLayoutCustom(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context = context;
        initAnimations();
    }

    private void initAnimations()
    {
        inAnimation = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.in_animation);
        outAnimation = (Animation) AnimationUtils.loadAnimation(context, R.anim.out_animation);
    }

    public void show()
    {
        if (isVisible()) return;
        show(true);
    }

    public void show(boolean withAnimation)
    {
        if (withAnimation) this.startAnimation(inAnimation);
        this.setVisibility(View.VISIBLE);
    }

    public void hide()
    {
        if (!isVisible()) return;
        hide(true);
    }

    public void hide(boolean withAnimation)
    {
        if (withAnimation) this.startAnimation(outAnimation);
        this.setVisibility(View.GONE);
    }

    public boolean isVisible()
    {
        return (this.getVisibility() == View.VISIBLE);
    }

    public void overrideDefaultInAnimation(Animation inAnimation)
    {
        this.inAnimation = inAnimation;
    }

    public void overrideDefaultOutAnimation(Animation outAnimation)
    {
        this.outAnimation = outAnimation;
    }

    public void setWidth(int pixels){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        //int deviceHeight = displayMetrics.heightPixels;
        float scale = getContext().getResources().getDisplayMetrics().density;
        int px = (int) (pixels * scale + 0.5f);
        if (px>deviceWidth){
            Log.d(TAG,"Please check input!!!");
            return;
        }
        super.getLayoutParams().width = px;
    }

    public void onDefaultWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        float scale = getContext().getResources().getDisplayMetrics().density;
        int px = (int) (100 * scale + 0.5f);
        super.getLayoutParams().width = deviceWidth-px;
    }
    public void setHeight(int pixels){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceHeight = displayMetrics.heightPixels;
        float scale = getContext().getResources().getDisplayMetrics().density;
        int px = (int) (pixels * scale + 0.5f);
        if (px>deviceHeight){
            Log.d(TAG,"Please check input!!!");
            return;
        }
        super.getLayoutParams().height = px;
    }
    public void onDefaultHeight(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceHeight = displayMetrics.heightPixels;
        float scale = getContext().getResources().getDisplayMetrics().density;
        int px = (int) (100 * scale + 0.5f);
        super.getLayoutParams().height = deviceHeight-px;
    }
}
