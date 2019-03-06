package com.example.pmprogect.fragment.home.umengtest;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.pmprogect.R;
import com.example.pmprogect.base.BaseNoBarActivity;
import com.example.pmprogect.fragment.home.test.customview.CustomToolbar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 友盟 Test
 */
public class UmengTestActivity extends BaseNoBarActivity {

    @BindView(R.id.add_tag)
    Button mAddTag;
    @BindView(R.id.delete_btn)
    Button mDeleteBtn;
    @BindView(R.id.show_btn)
    Button mShowBtn;
    @BindView(R.id.customtoolbar)
    CustomToolbar mCustomtoolbar;

    Handler mHandler;

//    PushAgent mPushAgent = PushAgent.getInstance(this);


    @Override
    protected int setView() {
        return R.layout.activity_umeng_test;
    }

    @Override
    protected void afterBinder() {
        mCustomtoolbar.setMainTitle("列表");
        setHeadView(mCustomtoolbar);

        initView();

    }

    private void initView() {
        mHandler = new Handler();
    }


    @OnClick({R.id.add_tag, R.id.delete_btn, R.id.show_btn})
    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.add_tag:
//                mPushAgent.getTagManager().addTags(new TagManager.TCallBack() {
//                    @Override
//                    public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (isSuccess) {
//                                    Toast.makeText(UmengTestActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(UmengTestActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                }, "郑州Tag");
//                break;
//            case R.id.delete_btn:
//                mPushAgent.getTagManager().deleteTags(new TagManager.TCallBack() {
//                    @Override
//                    public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (isSuccess) {
//                                    Toast.makeText(UmengTestActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(UmengTestActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                }, "郑州Tag");
//                break;
//            case R.id.show_btn:
//                mPushAgent.getTagManager().getTags(new TagManager.TagListCallBack() {
//                    @Override
//                    public void onMessage(final boolean isSuccess, final List<String> result) {
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (isSuccess) {
//                                    StringBuilder info = new StringBuilder();
//                                    if (result != null) {
//                                        for (int i = 0; i < result.size(); i++) {
//                                            String tag = result.get(i);
//                                            info.append(tag);
//                                            if (i != result.size() - 1) {
//                                                info.append("、");
//                                            }
//                                        }
//                                    }
//                                    Toast.makeText(UmengTestActivity.this, info.toString(), Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(UmengTestActivity.this, "暂无", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                });
//                break;
//        }
    }
}
