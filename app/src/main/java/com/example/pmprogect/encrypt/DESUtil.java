package com.example.pmprogect.encrypt;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by C4BOM on 2018/6/28.
 * GoodLuck No Bug
 */
public class DESUtil {
    /**
     * 客户端的向量参数
     */
    public static String CLIENTS_IV = "cGFsbWRI";               //向量/偏移量
    /**
     * 老版本客户端的密钥
     * 老版本服务端的密钥,向量
     */
    public static String CLIENTS_KEY_OLD = "emhlbnFpcGFsbQ==";          //加密密钥
    public static String SERVICE_KEY_OLD = "emhlbnFpcGFsbQ==";          //解密密钥
    public static String SERVICE_IV_OLD = "emhlbnFp";               //向量/偏移量
    /**
     * 新版本客户端的密钥
     * 新版本服务端的密钥,向量
     */
    public static String CLIENTS_KEY = "cGFsbWFwaWVuTQ==";          //加密密钥
    public static String SERVICE_KEY = "ZGVzcGFsbURCREVT";          //解密密钥
    public static String SERVICE_IV = "RGRFU2Rl";               //向量/偏移量

    private static String CIPHER_TYPE = "DES/CBC/PKCS7Padding"; //设定参数
    private static String EN_TYPE = "DES";
    private static String coding = "utf-8";

    /**
     * 解密流程:
     * <p>
     * 获取向量对象
     * 获取密钥对象
     * 初始化Cipher对象
     * 设置Cipher的参数
     * base64反编需要解密的密文获取密文字节数组
     * DES解密获取明文字节数组
     * 把明文字节数组生成字符串对象返回
     *
     * @param info 需要解密的内容
     */
    public static String decrypt(String info, String ivString, String keyString) {
        try {
            IvParameterSpec iv = getIv(ivString);
            SecretKey key = getKey(keyString);
            Cipher cipher = Cipher.getInstance(CIPHER_TYPE, "BC");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] decode = Base64.decode(info.getBytes(coding), Base64.DEFAULT);
            byte[] bytes = cipher.doFinal(decode);
            return new String(bytes, coding);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密代码流程:
     * <p>
     * 获取向量对象
     * 获取密钥对象
     * 初始化Cipher
     * 初始化加密方式
     * 获取DES加密后的字符数组
     * base64编码加密后的密文数组
     * 密文数组生成字符串对象返回
     *
     * @param info 需要加密的内容
     */
    public static String encrypt(String info) {
        try {
            IvParameterSpec iv = getIv(CLIENTS_IV);
            SecretKey key = getKey(CLIENTS_KEY);
            Cipher cipher = Cipher.getInstance(CIPHER_TYPE, "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] bytes = cipher.doFinal(info.getBytes(coding));
            byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
            return new String(encode, coding);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向量生成流程:
     * <p>
     * 向量md5加密
     * 截取后8位->这个就是偏移量
     * 生成向量对象返回
     */
    private static IvParameterSpec getIv(String ivString) {
        String md5 = Md5Util.getMd5(ivString);
        md5 = md5.substring(md5.length() - 8);
        return new IvParameterSpec(md5.getBytes());
    }

    /**
     * 密钥生成流程:
     * <p>
     * 密钥md5加密,取前16位--->这个才是真的密钥
     * 初始化 DESKeySpec 对象
     * 设置密钥工厂为DES加密
     * 加工成密钥对象返回
     */
    private static SecretKey getKey(String pass) {
        try {
            String enKey = Md5Util.getMd5(pass).substring(0, 16);
            DESKeySpec dks = new DESKeySpec(enKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(EN_TYPE);
            return keyFactory.generateSecret(dks);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}