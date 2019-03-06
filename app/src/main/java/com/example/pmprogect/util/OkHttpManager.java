package com.example.pmprogect.util;

import android.support.annotation.NonNull;

import com.example.pmprogect.encrypt.EncryptionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkHttpManager implements Serializable {
    /**
     * 单例,反序列化,反射
     */
    private static OkHttpClient mOkHttpClients;

    private OkHttpManager() {
        if (null != OkHttpViewHolder.OK_HTTP_MANAGER) {
            throw new RuntimeException("OkHttpManager is a private class,you can use method:OkHttpManager.getInstance()");
        }
    }

    private static class OkHttpViewHolder {
        private static final OkHttpManager OK_HTTP_MANAGER = new OkHttpManager();
    }

    public static OkHttpManager getInstance() {
        mOkHttpClients = new OkHttpClient();
        return OkHttpViewHolder.OK_HTTP_MANAGER;
    }

    private Object readResolve() throws ObjectStreamException {
        return OkHttpViewHolder.OK_HTTP_MANAGER;
    }

    public void PostRx(String method, Map map, final String url, final OkHttpCallBack.okCallBack okCallBack) {
        final String secret = EncryptionUtil.getRequestMsg(method, map);//加密
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Object> e) throws Exception {
                FormBody formBody = new FormBody.Builder()
                        .add("param", secret)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                mOkHttpClients.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e1) {
                        e.onError(e1);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String dataS = response.body().string();
                            String resultMsg = EncryptionUtil.getResult(dataS);//解密
                            JSONObject jsonObject = new JSONObject(resultMsg);
                            boolean success = jsonObject.getBoolean("success");
                            String errcode = jsonObject.getString("errcode");
                            String errmsg = jsonObject.getString("errmsg");
                            if (success) {
                                JSONObject object = jsonObject.getJSONObject("result");
                                boolean result = object.getBoolean("success");
                                String data = object.getString("data");
                                if (result) {
                                    e.onNext(data);
                                } else {
                                    e.onError(new Throwable(data));
                                }
                            } else {
                                e.onError(new Throwable("错误码:" + errcode + "  错误内容:" + errmsg));
                            }
                        } catch (JSONException e1) {
                            e.onError(new Throwable("解析错误"));
                        } catch (Exception e1) {
                            e.onError(new Throwable(e1.getMessage()));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object object) {
                        okCallBack.onSuccess(object);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        okCallBack.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
