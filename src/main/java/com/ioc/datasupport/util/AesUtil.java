package com.ioc.datasupport.util;

import org.ljdp.secure.cipher.AES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author: lsw
 * @Date: 2019/8/30 17:49
 */
public class AesUtil {
    private static final Logger LOG = LoggerFactory.getLogger(AesUtil.class);

    private static final String S_KEY = "1234567890abcdef";
    private static final String IV_STR = "abcdef1234567890";
    private static final AES aes = new AES(S_KEY, IV_STR);

    /**
     * AES加密
     *
     * @param str 待加密字符串
     * @return    加密后字符串
     * @throws NoSuchPaddingException 异常
     * @throws InvalidAlgorithmParameterException 异常
     * @throws NoSuchAlgorithmException  异常
     * @throws IllegalBlockSizeException 异常
     * @throws BadPaddingException 异常
     * @throws InvalidKeyException 异常
     */
    public static String encrypt(String str) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        LOG.debug("加密前的字符串：" + str);
        //加密后的字符串
        String encryptStr = aes.encryptBase64(str);
        LOG.debug("加密后的字符串：" + encryptStr);

        return encryptStr;
    }

    /**
     * AES解密
     *
     * @param str 待解密字符串
     * @return    解密后字符串
     * @throws NoSuchPaddingException 异常
     * @throws InvalidAlgorithmParameterException 异常
     * @throws NoSuchAlgorithmException  异常
     * @throws IllegalBlockSizeException 异常
     * @throws BadPaddingException 异常
     * @throws InvalidKeyException 异常
     */
    public static String decrypt(String str) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        LOG.debug("解密前的字符串：" + str);
        //解密后的字符串
        String decryptStr = aes.decryptBase64(str);
        LOG.debug("解密后的字符串：" + decryptStr);

        return decryptStr;
    }
}
