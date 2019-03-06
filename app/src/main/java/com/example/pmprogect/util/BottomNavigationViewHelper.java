package com.example.pmprogect.util;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import java.lang.reflect.Field;

public class BottomNavigationViewHelper {

    // 利用发射机制，改变 item 的 mShiftingMode 变量
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView navigationView) {
        BottomNavigationMenuView menuView =(BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            int labelVisibilityMode = menuView.getLabelVisibilityMode();
            Log.e("MTJ", "disableShiftMode: "+labelVisibilityMode );

            Field shiftingMode = menuView.getClass().getDeclaredField("labelVisibilityMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setInt(menuView, 4);//设置底部按钮的个数
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShifting(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
           e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}