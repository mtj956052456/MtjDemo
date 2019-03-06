package com.example.pmprogect.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by BOM on 2018/1/3 0003.
 */

public abstract class BaseNoBarActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);

    }

    /**
     * 给控件添加一个状态栏的高度,同时用padding把这段高度给占掉,让布局正常显示
     */
    protected void setHeadView(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (statusId>0){
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                int height = layoutParams.height;
                int dimensionPixelSize = getResources().getDimensionPixelSize(statusId);
                height=height+dimensionPixelSize;
                layoutParams.height=height;
                int left = view.getPaddingLeft();
                int top = view.getPaddingTop();
                int right = view.getPaddingRight();
                int bottom = view.getPaddingBottom();
                top=top+dimensionPixelSize;
                view.setPadding(left,top,right,bottom);
                view.setLayoutParams(layoutParams);
            }
        }
    }
}
