package com.example.pmprogect.fragment.home.test;

import com.example.pmprogect.R;
import com.example.pmprogect.base.BaseNoBarActivity;
import com.example.pmprogect.fragment.home.test.customview.CustomToolbar;

import butterknife.BindView;

public class TestActivity extends BaseNoBarActivity {

    @BindView(R.id.customtoolbar)
    CustomToolbar mCustomtoolbar;

    @Override
    protected int setView() {
        return R.layout.activity_test;
    }

    @Override
    protected void afterBinder() {
        setHeadView(mCustomtoolbar);
        mCustomtoolbar.setMainTitle("自定义View");

    }

}
