package com.example.pmprogect.fragment.home;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pmprogect.R;
import com.example.pmprogect.base.BaseFragment;
import com.example.pmprogect.base.Constant;
import com.example.pmprogect.fragment.home.flowlayout.FlowLayoutActivity;
import com.example.pmprogect.fragment.home.litepal.LitePalActivity;
import com.example.pmprogect.fragment.home.measure.MeasureActivity;
import com.example.pmprogect.fragment.home.multithreading.MultithreadingActivity;
import com.example.pmprogect.fragment.home.notice.NoticeActivity;
import com.example.pmprogect.fragment.home.test.TestActivity;
import com.example.pmprogect.fragment.home.umengtest.UmengTestActivity;
import com.example.pmprogect.fragment.home.woolGlass.WoolGlassActivity;
import com.example.pmprogect.util.OkHttpCallBack;
import com.example.pmprogect.util.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;


public class Fragment1 extends BaseFragment {


    @Override
    protected int setView() {
        return R.layout.fragment_fragment1;
    }

    @Override
    protected void firstBinder() {

    }

    private void initData() {
        Map<String,String> map = new HashMap<>();
        map.put("city", "郑州");
        OkHttpManager.getInstance().PostRx("SUPERVISEAPI_GETQUESTIONANDTYPE", map, Constant.URL, new OkHttpCallBack.okCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("MTJ", "onSuccess: " + result);
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                Log.e("MTJ", "onError: " + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.btn_request, R.id.MesaureActivity, R.id.TestActivity, R.id.UmengTestActivity, R.id.NoticeActivity, R.id.FlowLayoutActivity, R.id.MultithreadingActivity, R.id.WoolGlassActivity, R.id.LitePalActivity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.TestActivity:
                startActivity(new Intent(getActivity(), TestActivity.class));
                break;
            case R.id.UmengTestActivity:
                startActivity(new Intent(getActivity(), UmengTestActivity.class));
                break;
            case R.id.NoticeActivity:
                startActivity(new Intent(getActivity(), NoticeActivity.class));
                break;
            case R.id.FlowLayoutActivity:
                startActivity(new Intent(getActivity(), FlowLayoutActivity.class));
                break;
            case R.id.MultithreadingActivity:
                startActivity(new Intent(getActivity(), MultithreadingActivity.class));
                break;
            case R.id.MesaureActivity:
                startActivity(new Intent(getActivity(), MeasureActivity.class));
                break;
            case R.id.WoolGlassActivity:
                startActivity(new Intent(getActivity(), WoolGlassActivity.class));
                break;
            case R.id.LitePalActivity:
                startActivity(new Intent(getActivity(), LitePalActivity.class));
                break;
            case R.id.btn_request:
                initData();
                break;
        }
    }

}
