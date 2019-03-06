package com.example.pmprogect.encrypt;

import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by C4BOM on 2018/6/29.
 * GoodLuck No Bug
 */
public class EncryptionUtil {
    /**
     * 新版接口的加密3.0
     * 重点!!通俗的说HasMap本身是会自动根据abcd这样的顺序排序的
     */
    public static String getRequestMsg(String method, Object mapInfo) {
        String infoJson = JSONObject.toJSONString(mapInfo);//直接转成字符串作为一个参数
        String timeTemp = String.valueOf(new Date().getTime());//直接转成字符串作为时间参数

        HashMap<String, Object> parameter = new HashMap<>();
        parameter.put("appId", "1d77fc5874c972062dc72fd949ad5304");
        parameter.put("method", method);
        parameter.put("timestamp", timeTemp);
        parameter.put("clienttype", "Android");
        parameter.put("object", mapInfo);
        parameter.put("secret", Md5Util.getMd5("1d77fc5874c972062dc72fd949ad5304" + method + timeTemp + "Android" + infoJson));

        String parameterJson = JSON.toJSONString(parameter);
        return DESUtil.encrypt(parameterJson);
    }

    /**
     * 新版接口的解密3.0
     */
    public static String getResult(String result) {
        String des = DESUtil.decrypt(result, DESUtil.SERVICE_IV, DESUtil.SERVICE_KEY);
        String aesDecryption = AESUtil.AESDecryption(des, AESUtil.IV_KEY, AESUtil.KEY_BYTES);
        byte[] decode = Base64.decode(aesDecryption.getBytes(), Base64.DEFAULT);
        return new String(decode);
    }


    /**
     * 旧版接口的加密2.0
     */
    public static String getRequestMsgOld(String method, Object mapInfo) {
        String infoJson = JSONObject.toJSONString(mapInfo);
        String timeTemp = String.valueOf(new Date().getTime());
        HashMap<String, Object> parameter = new HashMap<>();
        parameter.put("appId", "1d77fc5874c972062dc72fd949ad5304");
        parameter.put("method", method);
        parameter.put("timestamp", timeTemp);
        parameter.put("clienttype", "Android");
        parameter.put("object", mapInfo);
        parameter.put("secret", Md5Util.getMd5("1d77fc5874c972062dc72fd949ad5304" + method + timeTemp + "Android" + infoJson));
        String parameterJson = JSON.toJSONString(parameter);
        return AESUtil.AESEncryption(parameterJson, AESUtil.IV_KEY_OLD, AESUtil.KEY_BYTES_OLD);
    }

    /**
     * 旧版接口的解密2.0
     */
    public static String getResultOld(String result) {
        String aesDecryption = AESUtil.AESDecryption(result, AESUtil.IV_OLD_SERVICE, AESUtil.KEY_OLD_SERVICE);
        String decrypt = DESUtil.decrypt(aesDecryption, DESUtil.SERVICE_IV_OLD, DESUtil.SERVICE_KEY_OLD);
        byte[] decode = Base64.decode(decrypt.getBytes(), Base64.DEFAULT);
        return new String(decode);
    }
}
