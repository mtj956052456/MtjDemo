package com.example.pmprogect.util;

/**
 * Created by C4B on 2017/9/3.
 * GoodLuck No Bug
 */

public class OkHttpCallBack {
    /**
     * okHttp的网络请求回调方法
     */
    public static abstract class okCallBack<T> {

        public abstract void onSuccess(T result);                                                   //一切正确的回调接口

        public void onError( Throwable e) {                                               //带call参数的回调
            e.printStackTrace();
        }
    }
}
