package com.example.pmprogect.fragment.explore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pmprogect.R;
import com.example.pmprogect.fragment.explore.entity.PMBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 蛟龙之子 on 2017/11/6.
 */

public class PMAdapter extends RecyclerView.Adapter<PMAdapter.ViewHolder> {

    private Context mContext;
    private List<PMBean.RowsBean> data;
    public PMAdapter(List<PMBean.RowsBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.pm_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PMBean.RowsBean pmBean = data.get(position);
        holder.name.setText(pmBean.getUsername());
        holder.time.setText(pmBean.getTime());
        holder.content.setText(pmBean.getContent());
        Glide.with(mContext).load(pmBean.getHeadimg()).into(holder.img_big);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView user_img;
        ImageView img_1, img_2, img_big;
        TextView name, time, content;

        public ViewHolder(View view) {
            super(view);
            user_img = (CircleImageView) view.findViewById(R.id.user_img);
            img_1 = (ImageView) view.findViewById(R.id.img_1);
            img_2 = (ImageView) view.findViewById(R.id.img_2);
            img_big = (ImageView) view.findViewById(R.id.img_big);
            name = (TextView) view.findViewById(R.id.name);
            time = (TextView) view.findViewById(R.id.time);
            content = (TextView) view.findViewById(R.id.content);
        }
    }
}
