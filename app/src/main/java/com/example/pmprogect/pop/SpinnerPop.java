package com.example.pmprogect.pop;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.pmprogect.R;
import com.example.pmprogect.base.BasePopWindow;

import java.util.List;

/**
 * lenovo
 * 20180326
 * des:  pop下拉框适配器
 */

public class SpinnerPop extends BasePopWindow {

    private RecyclerView mRecyclerView;
    private List<String> mList;
    private ListStringAdapter mAdapter;
    private FrameLayout frameLayout;

    public SpinnerPop(Activity activity, List<String> list) {
        super(activity);
        mList = list;
        initCanTouch();
    }

    public ListStringAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected View SetView() {
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.spinner_layout, null);
        mRecyclerView = inflate.findViewById(R.id.recyclerSpinner);
        //setMargins( dp2px(mActivity, 80), 0, 0, 0);  //设置margin值

         frameLayout = inflate.findViewById(R.id.frameLayoutMain);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ListStringAdapter(R.layout.item_text, mList);
        mRecyclerView.setAdapter(mAdapter);
        return inflate;
    }


    /**
     * 设置margin值
     */
    public void setMargins(int l, int t, int r, int b) {
        l = dp2px(mActivity, l);
        t = dp2px(mActivity, t);
        r = dp2px(mActivity, r);
        b = dp2px(mActivity, b);
        if (mRecyclerView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) mRecyclerView.getLayoutParams();
            p.setMargins(l, t, r, b);
            mRecyclerView.requestLayout();
        }

    }
    /**
     * 设置margin值
     */
    public void setWidthHeight(int width,int height) {
        if (mRecyclerView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.LayoutParams p = mRecyclerView.getLayoutParams();
            if(width == -1){
                p.width = ViewGroup.MarginLayoutParams.MATCH_PARENT;
            }else {
                width = dp2px(mActivity, width);
                p.width = width;
            }
            if(height == -2){
                p.height = ViewGroup.MarginLayoutParams.WRAP_CONTENT;
            }else {
                height = dp2px(mActivity, height);
                p.height = height;
            }
            mRecyclerView.requestLayout();
        }

    }

    /**
     * 设置View位置
     * 1.左上边 2右上边  3.左下边  4.右下边  其他 : 居中
     */
    public void setGravity(int location) {
        if (mRecyclerView.getLayoutParams() instanceof ViewGroup.LayoutParams) {
            FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            if (location == 1) {
                param.gravity = Gravity.LEFT|Gravity.TOP;
            } else if (location == 2) {
                param.gravity = Gravity.RIGHT|Gravity.TOP;
            } else if (location == 3) {
                param.gravity = Gravity.LEFT|Gravity.BOTTOM;
            } else if (location == 4) {
                param.gravity = Gravity.RIGHT|Gravity.BOTTOM;
            } else {
                param.gravity = Gravity.CENTER;
            }
            mRecyclerView.setLayoutParams(param);
            mRecyclerView.requestLayout();
        }
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
