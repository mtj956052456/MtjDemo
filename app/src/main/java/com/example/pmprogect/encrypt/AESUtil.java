package com.example.pmprogect.encrypt;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 如果你只是想修改加密参数,那么直接修改下面三个变量就可以了
 * <p>
 * CIPHER_TYPE  加密的类型,方式以及填充模式,比如AES加密的CBC模式,填充方式为PKCS5Padding,那么就改成"AES/CBC/PKCS5Padding"
 * IV_KEY       参数就就是向量的对应参数,接口文档会规定向量部分的参数生成,没有变化的话直接改字符串
 * KEY_TYPE     生成密钥的类型,比如AES加密就改成"AES"
 * KEY_BYTES     加密的密钥
 */
public class AESUtil {

    private static String CIPHER_TYPE = "AES/CBC/PKCS5Padding";//设定参数
    private static String KEY_TYPE = "AES";//生成的key类型

    //客户端的加密密钥和向量
    public static String KEY_BYTES = "bWFwaWVuY29kZQ=="; /*密钥*/
    public static String IV_KEY = "EMNZCGFSBWFWAQ==";//向量,偏移量

    // 注意: 这里的password(秘钥必须是16位的)服务端的 3.0
    public static final String KEY_BYTES_OLD = "cGFsbWNsaWVudA==";//"bWFwaWVuY29kZQ==";
    public static final String IV_KEY_OLD = "Y2xpZW50cGFsbQ==";//"EMNZCGFSBWFWAQ==";

    // 注意: 这里的password(秘钥必须是16位的)服务端的 2.0
    public static final String KEY_OLD_SERVICE = "cGFsbXNlcnZlcg==";//"bWFwaWVuY29kZQ==";
    public static final String IV_OLD_SERVICE = "c2VydmVycGFsbQ==";//"EMNZCGFSBWFWAQ==";

    /**
     * aes加密
     */
    public static String AESEncryption(String info, String ivString, String keyString) {
        try {
            SecretKeySpec key = getKey(keyString);//生成密钥
            IvParameterSpec iv = getIv(ivString);//生成向量
            Cipher cipher = Cipher.getInstance(CIPHER_TYPE);//初始化
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);//模式为加密,输入加密模式,密钥和向量初始化cipher对象
            byte[] bytes = cipher.doFinal(info.getBytes("utf-8"));
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * aes解密
     */
    public static String AESDecryption(String info, String ivString, String keyString) {
        try {
            SecretKeySpec key = getKey(keyString);//生成密钥
            IvParameterSpec iv = getIv(ivString);//生成向量

            Cipher cipher = Cipher.getInstance(CIPHER_TYPE);//初始化
            cipher.init(Cipher.DECRYPT_MODE, key, iv);//模式为解密,输入加密模式,密钥和向量初始化cipher对象
            byte[] bytes = cipher.doFinal(Base64.decode(info, Base64.DEFAULT));//因为数据已经用Base编码过一次,所以需要解码一次
            return new String(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取密码转化成的对应密钥
     * 需要生成的AES密钥
     * 只需要密钥加密后数据的后16位
     */
    private static SecretKeySpec getKey(String pass) {
        String md5 = Md5Util.getMd5(pass);
        String signPass = md5.substring(md5.length() - 16);//截取加密后16位
        return new SecretKeySpec(signPass.getBytes(), KEY_TYPE);
    }

    /**
     * 产生一个向量对象,也就是偏移量
     * 根据后台的协议
     * 向量对象生成的数据取向量参数通过MD5加密的前16位,
     */
    private static IvParameterSpec getIv(String ivString) {
        byte[] bytes = Md5Util.getMd5(ivString).substring(0, 16).getBytes();
        return new IvParameterSpec(bytes);
    }
}
/**
 * 全局的静态变量,想拿就拿
 * 关于Cipher 请看官方文档:https://developer.android.google.cn/reference/javax/crypto/Cipher
 * 上面有完成的Cipher.MODEL说明,所有的加密模式类别,比如AES.DES.所有的加密模式,比如AES里面的CBC
 * <p>
 * 在android 中实现AES加密主要分以下五个过程
 * 第一步:获取Cipher对象,参数设定(加密类别/加密模式/填充方式)     请查看官网文档,这里后台规定是AES/CBC/PKCS5Padding模式
 * 第二步:把秘钥字符串转换成需要的秘钥对象 通过调用函数   getKey();
 * 第三步:生成向量对象.或者说偏移量对象  通过getIv();   真气Api规定把参数MD5加密,然后获取前16位作为向量对象;
 * 第四步:初始化cipher对象, 前面三步其实都是为了给Cipher参数,让他加密的过程达到我们想要的结果
 * 第五步:获得加密结果;
 * <p>
 * PKCS5Padding的blocksize为8字节，而PKCS7Padding的blocksize可以为1到255字节
 * <p>
 */