package com.example.pmprogect.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import io.reactivex.annotations.NonNull;

/**
 * lenovo
 * 20180408
 * des:
 */

public class PermissionUtil {

    //     String[] strs = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
    //     Manifest.permission.ACCESS_FINE_LOCATION,
    //     Manifest.permission.READ_EXTERNAL_STORAGE,
    //     Manifest.permission.WRITE_EXTERNAL_STORAGE,
    //     Manifest.permission.READ_PHONE_STATE};

    /**
     * Android 6.0 新特性  请求多个权限
     *
     * @param activity
     * @param permissions
     * @return
     */
    public static void checkPermissions(Activity activity, @NonNull String[] permissions,int requesCode) {
        // 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
        if (Build.VERSION.SDK_INT >= 23) {
            //验证是否许可权限
            for (String str : permissions) {
                if (activity.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    activity.requestPermissions(permissions, requesCode);
                }
            }
        }
    }

    /**
     * 检查单个权限
     * @param activity
     * @param permission
     * @return
     */
    public static boolean checkPermission(Activity activity, String permission) {
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }


}
