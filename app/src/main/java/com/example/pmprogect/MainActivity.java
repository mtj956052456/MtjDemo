package com.example.pmprogect;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.pmprogect.base.BaseNoBarActivity;
import com.example.pmprogect.fragment.explore.Fragment2;
import com.example.pmprogect.fragment.explore.adapter.ViewPagerAdapter;
import com.example.pmprogect.fragment.home.Fragment1;
import com.example.pmprogect.fragment.home.test.customview.CustomToolbar;
import com.example.pmprogect.fragment.home.test.customview.NoScrollViewPager;
import com.example.pmprogect.fragment.map.Fragment3;
import com.example.pmprogect.fragment.me.Fragment4;
import com.example.pmprogect.util.BottomNavigationViewHelper;

import butterknife.BindView;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends BaseNoBarActivity {

    @BindView(R.id.customtoolbar)
    CustomToolbar mCustomtoolbar;
    @BindView(R.id.bnv_menu)
    BottomNavigationView mBnvMenu;
    @BindView(R.id.viewpager)
    NoScrollViewPager mViewpager;


    private MenuItem menuItem;

    @Override
    protected int setView() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterBinder() {
        setHeadView(mCustomtoolbar);
        initView();
    }

    private void initView() {

        mCustomtoolbar.setMainTitle("主页");
        mCustomtoolbar.setMainTitleLeftDrawable(R.drawable.ic_action_home);
        mCustomtoolbar.setLeftViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击左侧标签", Toast.LENGTH_SHORT).show();
            }
        });

        mBnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        mViewpager.setCurrentItem(0);
                        break;
                    case R.id.action_explore:
                        mViewpager.setCurrentItem(1);
                        break;
                    case R.id.action_map:
                        mViewpager.setCurrentItem(2);
                        break;
                    case R.id.action_me:
                        mViewpager.setCurrentItem(3);
                        break;
                }
                return false;
            }
        });

        BottomNavigationViewHelper.disableShiftMode(mBnvMenu);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            //if (menuItem != null) {menuItem.setChecked(false);} else {  mBnvMenu.getMenu().getItem(0).setChecked(false);}
                menuItem = mBnvMenu.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setupViewPager(mViewpager);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment1());
        adapter.addFragment(new Fragment2());
        adapter.addFragment(new Fragment3());
        adapter.addFragment(new Fragment4());
        viewPager.setAdapter(adapter);
    }


}
