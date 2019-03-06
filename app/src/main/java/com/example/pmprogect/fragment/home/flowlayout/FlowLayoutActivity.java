package com.example.pmprogect.fragment.home.flowlayout;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.example.pmprogect.R;
import com.example.pmprogect.base.BaseNoBarActivity;
import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式布局
 */
public class FlowLayoutActivity extends BaseNoBarActivity {

    private static final String TAG = FlowLayoutActivity.class.getSimpleName();

    private List<String> list = new ArrayList<>();

    @Override
    protected int setView() {
        return R.layout.activity_flow_layout;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("流式布局");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(10)));
        recyclerView.setLayoutManager(new FlowLayoutManager());

        for (int i = 0; i < 20; i++) {
            if (i == 3) {
                list.add("yiwaikjnxsacsadasdnol");
            } else {

                list.add("第" + i + "sss个");
            }
        }
        FlowLayoutAdapter flowAdapter = new FlowLayoutAdapter(list, this);
        recyclerView.setAdapter(flowAdapter);
    }

    private int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }
}