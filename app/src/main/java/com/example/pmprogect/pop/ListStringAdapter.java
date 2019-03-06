package com.example.pmprogect.pop;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.pmprogect.R;
import com.example.pmprogect.util.TextUtil;

import java.util.List;

/**
 * lenovo
 * 20180326
 * des:
 */

public class ListStringAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ListStringAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text1, TextUtil.nullToStr(item));
        helper.addOnClickListener(R.id.text1);
    }
}
