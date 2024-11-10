package com.hotel.system.util;

import cn.hutool.core.codec.Base64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author liuyanzhao
 * @date 2022/04/26
 */
public class AESUtils {

    private static final String AES = "AES";
    private static final String CHAR_SET_NAME1 = "UTF-8";
    private static final String CHAR_SET_NAME2 = "ASCII";
    private static final String CIPHER_KEY = "AES/CBC/PKCS5Padding";

    private static final String IV_PARAMETER = "a0.l954b_107x90l";
    private static final String S_KEY = "ax7x90.3k_10li5u";

    public static String decrypt(String value) throws IOException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec skeySpec = new SecretKeySpec(S_KEY.getBytes(CHAR_SET_NAME2), AES);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(CIPHER_KEY);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes());
        try {
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        // 先用base64解密
        return new String(cipher.doFinal(Base64Decoder.decode(value)), CHAR_SET_NAME1);
    }


}