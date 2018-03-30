package com.amap.tripdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyScrollView.OnScrollListener {
    private String TAG = "Main4Activity";


    private MyScrollView myScrollView;
    private LinearLayout back;
    private LinearLayout linear_myBack;
    private Button btn_back;
    private RelativeLayout layout_top;
    private Button btn_test;
    private boolean lastIsShow = false;
    TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
    TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            -1.0f);
    /**
     * 购买布局的高度
     */
    private int buyLayoutHeight;
    /**
     * myScrollView与其父类布局的顶部距离
     */
    private int myScrollViewTop;

    /**
     * 购买布局与其父类布局的顶部距离
     */
    private int buyLayoutTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        myScrollView = (MyScrollView) findViewById(R.id.bounceScrollView);
        back = (LinearLayout) findViewById(R.id.back);
        linear_myBack = (LinearLayout) findViewById(R.id.linear_myBack);
        layout_top = (RelativeLayout) findViewById(R.id.layout_top);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_test = (Button) findViewById(R.id.btn_test);
        mShowAction.setDuration(100);
        mHiddenAction.setDuration(100);
        myScrollView.setOnScrollListener(this);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "test", Toast.LENGTH_LONG).show();
            }
        });
        linear_myBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        ViewTreeObserver vto2 = layout_top.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout_top.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                buyLayoutHeight = layout_top.getHeight();
                buyLayoutTop = layout_top.getTop();
                Log.e(TAG, "buyLayoutHeight" + buyLayoutHeight);
                Log.e(TAG, "buyLayoutTop" + buyLayoutTop);
            }
        });
        ViewTreeObserver vto1 = myScrollView.getViewTreeObserver();
        vto1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                myScrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                myScrollViewTop = myScrollView.getTop();
                Log.e(TAG, "myScrollViewTop" + myScrollViewTop);
            }
        });
    }

    /**
     * 窗口有焦点的时候，即所有的布局绘制完毕的时候，我们来获取购买布局的高度和myScrollView距离父类布局的顶部位置
     */
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            buyLayoutHeight = linear_myBack.getHeight();
//            buyLayoutTop = linear_myBack.getTop();
//
//            myScrollViewTop = myScrollView.getTop();
//            Log.e(TAG, "buyLayoutHeight" + buyLayoutHeight);
//            Log.e(TAG, "buyLayoutTop" + buyLayoutTop);
//            Log.e(TAG, "myScrollViewTop" + myScrollViewTop);
//        }
//    }

    /**
     * 滚动的回调方法，当滚动的Y距离大于或者等于 购买布局距离父类布局顶部的位置，就显示购买的悬浮框
     * 当滚动的Y的距离小于 购买布局距离父类布局顶部的位置加上购买布局的高度就移除购买的悬浮框
     */
    @Override
    public void onScroll(int scrollY) {
//        Log.e(TAG, "scrollY=" + scrollY + "  buyLayoutTop=" + buyLayoutTop);
        Log.e(TAG, "getScrollY=" + myScrollView.getScrollY() + "  buyLayoutHeight=" + buyLayoutHeight);
        if (scrollY > buyLayoutHeight) {
            if (btn_back.getVisibility() == View.GONE) {
//                showSuspend();
//                Log.e(TAG, "showSuspend");

                btn_back.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                linear_myBack.setClickable(true);
                layout_top.setVisibility(View.GONE);
                layout_top.startAnimation(mHiddenAction);
            }
        } else if (scrollY <= buyLayoutHeight) {
            if (btn_back.getVisibility() == View.VISIBLE) {
//                removeSuspend();
//                Log.e(TAG, "removeSuspend");
                btn_back.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
                linear_myBack.setClickable(false);
                layout_top.setVisibility(View.VISIBLE);
                layout_top.startAnimation(mShowAction);
            }
        }
    }

    private void reset() {
        new Handler().post(runnable);
        btn_back.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        linear_myBack.setClickable(false);
        layout_top.startAnimation(mShowAction);
        layout_top.setVisibility(View.VISIBLE);
    }

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {

            myScrollView.scrollToPosition(0, 0);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        getAndroiodScreenProperty();
    }

    public void getAndroiodScreenProperty() {
//        buyLayoutHeight = linear_myBack.getHeight();
//        buyLayoutTop = linear_myBack.getTop();
//
//        myScrollViewTop = myScrollView.getTop();
//        Log.e(TAG, "buyLayoutHeight" + buyLayoutHeight);
//        Log.e(TAG, "buyLayoutTop" + buyLayoutTop);
//        Log.e(TAG, "myScrollViewTop" + myScrollViewTop);


//        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;         // 屏幕宽度（像素）
//        int height = dm.heightPixels;       // 屏幕高度（像素）
//        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
//        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
//        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
//        int screenHeight = (int) (height / density);// 屏幕高度(dp)
//
//
//        Log.e("h_bl", "屏幕宽度（像素）：" + width);
//        Log.e("h_bl", "屏幕高度（像素）：" + height);
//        Log.e("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
//        Log.e("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
//        Log.e("h_bl", "屏幕宽度（dp）：" + screenWidth);
//        Log.e("h_bl", "屏幕高度（dp）：" + screenHeight);
//        myScrollView.setScreenHeight(height);
    }


//    @Override
//    public void scrollY(float scrollY) {
//        boolean isShow = false;
//        if (scrollY >= 300) {
//            isShow = true;
//        } else {
//            isShow = false;
//        }
//        if (isShow == lastIsShow) {
//            return;
//        } else {
//            lastIsShow = isShow;
//        }
//        if (lastIsShow == true) {
//            btn_back.setVisibility(View.VISIBLE);
//            back.setVisibility(View.VISIBLE);
//            linear_myBack.setClickable(true);
//            layout_top.setVisibility(View.GONE);
//            layout_top.startAnimation(mHiddenAction);
//        } else {
//            btn_back.setVisibility(View.GONE);
//            back.setVisibility(View.GONE);
//            linear_myBack.setClickable(false);
//            layout_top.setVisibility(View.VISIBLE);
//            layout_top.startAnimation(mShowAction);
//        }
//    }
}
