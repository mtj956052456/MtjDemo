package com.example.pmprogect.fragment.home.measure;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.pmprogect.R;
import com.example.pmprogect.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 动态设置item的宽高
 */
public class MeasureActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ed_width)
    EditText mEdWidth;
    @BindView(R.id.ed_height)
    EditText mEdHeight;
    @BindView(R.id.btn_change)
    Button mBtnChange;

    private MeasureAdapter mMeasureAdapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int setView() {
        return R.layout.activity_measure;
    }

    private void initView() {
        mList.add("我是左侧的固定字体-我是测量的字体");
//        mList.add("我是左侧的固定字体-我是测量的字体");
//        mList.add("我是左侧的固定字体-我是测量的字体");
        mMeasureAdapter = new MeasureAdapter(R.layout.item_measure_layout, mList);
        mRecyclerView.setAdapter(mMeasureAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private int edWidth = 0;
    private int edHeight = 0;

    @OnClick(R.id.btn_change)
    public void onViewClicked() {
        try {
            edWidth = Integer.parseInt(mEdWidth.getText().toString());
            edHeight = Integer.parseInt(mEdHeight.getText().toString());
            mMeasureAdapter.setWidthHeight(edWidth, edHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class MeasureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        private DisplayMetrics dm = getResources().getDisplayMetrics();
        private int height, width;

        public MeasureAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
            height = dm.heightPixels;
            width = dm.widthPixels;
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView mLeftText = helper.getView(R.id.item_left_text);
            TextView mRightText = helper.getView(R.id.item_right_text);
            String[] split = item.split("-");
            mLeftText.setText(split[0]);
            mRightText.setText(split[1]);
            //Log.e(TAG, "heigth: " + height + "  width: " + width);

            int width1 = measureWidth(mLeftText);
            int width2 = measureWidth(mRightText);
            int height1 = measureHeight(mLeftText);
            int height2 = measureHeight(mRightText);
            Log.e(TAG, "mLeftText width=" + width1);
            Log.e(TAG, "mRightText width2=" + width2);
            setViewFullScreen(mRightText, edWidth, edHeight);
        }

        private int edWidth = 200, edHeight = 200;

        public void setWidthHeight(int edWidth, int edHeight) {
            this.edWidth = edWidth;
            this.edHeight = edHeight;
            notifyDataSetChanged();
        }
    }

    //设置view的大小
    private void setViewFullScreen(View view, int width, int height) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    //测量View控件的宽度
    private int measureWidth(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, 0);
        return view.getMeasuredWidth();
    }

    //测量View控件的高度
    private int measureHeight(View view) {
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(height, 0);
        return view.getMeasuredHeight();
    }


    public static int sp2px(Context c, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

    public static float dp2pxF(Context c, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }


}
