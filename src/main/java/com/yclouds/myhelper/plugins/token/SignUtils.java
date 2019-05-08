package com.yclouds.myhelper.plugins.token;

import org.springframework.util.DigestUtils;

/**
 * @author ye17186
 * @version 2019/5/6 14:37
 */
public class SignUtils {

    private SignUtils() {
    }

    /**
     * 签名
     *
     * @param token token
     * @param timestamp 时间戳
     * @param nonceStr 一次性随机串
     * @param secretKey 密钥
     * @return 签名
     */
    public static String signature(String token, String timestamp, String nonceStr,
        String secretKey) {

        String key = token + timestamp + nonceStr + secretKey;
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
