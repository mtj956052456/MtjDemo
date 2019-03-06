package com.example.pmprogect.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pmprogect.util.Lg;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BOM on 2018/1/2 0002.
 */

public abstract class BaseFragment extends Fragment {
    private View rootView;
    private Unbinder unBinder;
    private FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(setView(), container, false);
            unBinder = ButterKnife.bind(this, rootView);
            firstBinder();
        } else {
            if (null != rootView.getParent()) {
                ViewGroup parent = (ViewGroup) rootView.getParent();
                parent.removeAllViews();
            }
            unBinder = ButterKnife.bind(this, rootView);
            otherBinder();
        }
        fm = getActivity().getSupportFragmentManager();
        return rootView;
    }

    /**
     * 传入需要绑定的fragment布局
     */
    protected abstract int setView();

    /**
     * 首次用黄油刀绑定
     */
    protected abstract void firstBinder();

    /**
     * 黄油刀绑定过一次后,界面destroy恢复后触发的方法
     */
    private void otherBinder() {
    }


    /**
     * 给控件添加一个状态栏的高度,同时用padding把这段高度给占掉,让布局正产显示
     */
    protected void setHeadView(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (statusId > 0) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                int height = layoutParams.height;
                int dimensionPixelSize = getResources().getDimensionPixelSize(statusId);
                height = height + dimensionPixelSize;
                layoutParams.height = height;
                int left = view.getPaddingLeft();
                int top = view.getPaddingTop();
                int right = view.getPaddingRight();
                int bottom = view.getPaddingBottom();
                top = top + dimensionPixelSize;
                view.setPadding(left, top, right, bottom);
                view.setLayoutParams(layoutParams);
            }
        }
    }

    protected void intoActivity(Class<?> cls) {
        startActivity(new Intent(getActivity(), cls));
    }

    @Override
    public void onDestroyView() {
        if (unBinder != null) {
            unBinder.unbind();
            unBinder = null;
        }
        super.onDestroyView();
    }

    protected boolean isEmpty(String msg) {
        return TextUtils.isEmpty(msg);
    }

    protected void setTv(String msg, TextView tv) {
        if (!TextUtils.isEmpty(msg)) {
            if (null != tv) {
                tv.setText(msg);
            } else {
                Lg.e("控件空指针");
            }
        } else {
            Lg.e("msg的数据为空");
        }
    }
    protected boolean isEmpty(List<?> list) {
        return null == list || list.isEmpty();
    }
    protected void setVisible(View view,boolean isVisible){
        if (null!=view){
            if (isVisible){
                view.setVisibility(View.VISIBLE);
            }else {
                view.setVisibility(View.GONE);
            }
        }
    }
}
