package com.example.pmprogect.fragment.home.litepal;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pmprogect.R;
import com.example.pmprogect.base.BaseNoBarActivity;
import com.example.pmprogect.fragment.home.test.customview.CustomToolbar;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LitePalActivity extends BaseNoBarActivity {

    @BindView(R.id.customtoolbar)
    CustomToolbar mCustomtoolbar;
    @BindView(R.id.recycler_litepal)
    RecyclerView mRecyclerLitepal;

    LitePalAdapter mAdapter;
    @BindView(R.id.tv_add)
    Button mTvAdd;
    @BindView(R.id.tv_delete)
    Button mTvDelete;
    @BindView(R.id.tv_update)
    Button mTvUpdate;
    @BindView(R.id.tv_query)
    Button mTvQuery;
    @BindView(R.id.tv_query_all)
    Button mTvQueryAll;

    private List<News> mList = new ArrayList<>();

    @Override
    protected int setView() {
        return R.layout.activity_lite_pal;
    }

    @Override
    protected void afterBinder() {
        super.afterBinder();
        setHeadView(mCustomtoolbar);
        mCustomtoolbar.setMainTitle("数据库");
        initView();
    }

    private void initView() {
        mAdapter = new LitePalAdapter(R.layout.item_litepal, mList);
        mRecyclerLitepal.setAdapter(mAdapter);
        mRecyclerLitepal.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick({R.id.tv_add, R.id.tv_delete, R.id.tv_update, R.id.tv_query, R.id.tv_query_all, R.id.lt_main_title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lt_main_title_left:
                finish();
                break;
            case R.id.tv_add:
                add();
                break;
            case R.id.tv_delete:
                delete();
                break;
            case R.id.tv_update:
                update();
                break;
            case R.id.tv_query:
                find();
                break;
            case R.id.tv_query_all:
                findAll();
                break;
        }
    }

    private void update() {
        mList.clear();
        List<News> all = LitePal.findAll(News.class);
        if (all.size() >= 0) {
            News news = all.get(0);
            news.setContent("我改了你的源码");
            news.save();
            findAll();
            Toast.makeText(this, "更改了第一条数据", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();

        }
    }


    private void delete() {
        mList.clear();
        LitePal.deleteAll(News.class);
        findAll();
        Toast.makeText(this, "删除所有数据", Toast.LENGTH_SHORT).show();
    }

    private void add() {
        mList.clear();
        News news = new News();
        news.setTitle("标题");
        news.setContent("哈哈哈");
        news.setPage(200);
        news.save();
        findAll();
        Toast.makeText(this, "增加一条数据", Toast.LENGTH_SHORT).show();
    }

    private void find() {
        mList.clear();
        List<News> all = LitePal.findAll(News.class);
        if (all.size() >= 0) {
            News news = LitePal.find(News.class, 0);
            mList.add(news);
            mAdapter.notifyDataSetChanged();
            Toast.makeText(this, "查询第一条数据", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
        }
    }

    private void findAll() {
        mList.clear();
        List<News> all = LitePal.findAll(News.class);
        mList.addAll(all);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "查询所有数据", Toast.LENGTH_SHORT).show();
    }
}

