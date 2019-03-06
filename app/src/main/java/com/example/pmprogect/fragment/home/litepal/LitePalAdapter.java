package com.example.pmprogect.fragment.home.litepal;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.pmprogect.R;

import java.util.List;

/**
 * @author mtj
 * @time 2018/10/31 2018 10
 * @des
 */

public class LitePalAdapter extends BaseQuickAdapter<News, BaseViewHolder> {

    public LitePalAdapter(int layoutResId, @Nullable List<News> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        try {
            helper.setText(R.id.tv_item_id, String.valueOf(item.getId()));
            helper.setText(R.id.tv_item_title, String.valueOf(item.getTitle()));
            helper.setText(R.id.tv_item_content, String.valueOf(item.getContent()));
            helper.setText(R.id.tv_item_page,String.valueOf(item.getPage()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
