package com.example.pmprogect.util;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by C4B on 2017/9/3.
 * GoodLuck No Bug
 */

public class Lg {
    private static boolean isTest = true;

    /**
     * 日志打印工具类
     *
     * @param msg
     */
    public static void e(@NonNull String msg) {
        if (isTest) {
            Log.e("MTJ", msg);
        }
    }

    public static void a(@NonNull String msg) {
        if (isTest) {
            Log.e("MTJ", "a");
        }
    }

    public static void b(@NonNull String msg) {
        if (isTest) {
            Log.e("MTJ", "b");
        }
    }

    public static void c(@NonNull String msg) {
        if (isTest) {
            Log.e("MTJ", "c");
        }
    }

    public static void d(@NonNull String msg) {
        if (isTest) {
            Log.e("MTJ", "d");
        }
    }

}
