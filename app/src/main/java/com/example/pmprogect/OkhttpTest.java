package com.example.pmprogect;

import android.util.Log;

import com.example.pmprogect.util.Base64;
import com.example.pmprogect.util.MD5Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 蛟龙之子 on 2017/11/6.
 */

public class OkhttpTest {

    private static  String urlPath = "http://palm.zq12369.com/api/palmapi.php";
    private static final String TAG = "OkhttpTest";
    private static final String appId = "4ab4aca1c61c78b89338c3e3804e0e9d";
    private static final String method = "NEWSAPI_GETQUESTIONLIST";
    public  static  void getRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("appId",appId)
                        .add("method",method)
                        .add("secret", MD5Util.encrypt(appId+method))
                        .build();
                Request request = new Request.Builder()
                        .url(urlPath)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String string = response.body().string();
                    String str = Base64.decode(string);
                    parseJSONWithJSONObject(str);
                    Log.e(TAG, "run() returned: " + str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject =new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("rows");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsobjct = jsonArray.getJSONObject(i);
                String username = jsobjct.getString("username");
                Log.e("MainActivity", "username is " + username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
