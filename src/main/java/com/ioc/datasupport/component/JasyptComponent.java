package com.ioc.datasupport.component;

/**
 * @author: lsw
 * @Date: 2019/12/26 17:28
 */

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JasyptComponent {

    /**
     * 密钥
     */
    @Value("${jasypt.encryptor.password}")
    private String password;

    private BasicTextEncryptor basicTextEncryptor;

    public BasicTextEncryptor getBasicTextEncryptor() {
        if (basicTextEncryptor == null) {
            basicTextEncryptor = new BasicTextEncryptor();
            basicTextEncryptor.setPassword(password);
        }

        return basicTextEncryptor;
    }

    /**
     * 解密
     *
     * @param orgStr 源字符串
     * @return       解密后的字符串
     */
    public String decrypt(String orgStr) {
        return this.getBasicTextEncryptor().decrypt(orgStr);
    }

    /**
     * 加密
     *
     * @param orgStr 源字符串
     * @return       加密后的字符串
     */
    public String encrypt(String orgStr) {
        return this.getBasicTextEncryptor().encrypt(orgStr);
    }
}
