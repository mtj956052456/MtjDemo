package com.example.pmprogect.fragment.explore;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.pmprogect.R;
import com.example.pmprogect.base.BaseFragment;
import com.example.pmprogect.fragment.explore.adapter.PMAdapter;
import com.example.pmprogect.fragment.explore.entity.PMBean;
import com.example.pmprogect.util.StringPMUtil;
import com.google.gson.Gson;

import butterknife.BindView;


public class Fragment2 extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private PMBean pmList;


    @Override
    protected int setView() {
        return R.layout.fragment_fragment2;
    }

    @Override
    protected void firstBinder() {
        initData();
        initView();
    }

    private void initData() {
        pmList = getPMList(StringPMUtil.mData);
    }

    private void initView() {
        PMAdapter pmAdapter = new PMAdapter(pmList.getRows());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(pmAdapter);

    }

    private static PMBean getPMList(String mData) {
        Gson gson = new Gson();
        PMBean pmBean = gson.fromJson(mData, PMBean.class);
        return pmBean;
    }

}
