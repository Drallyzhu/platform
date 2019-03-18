package com.cloudsoft.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Utils {

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBASE64(String key) throws Exception {
        byte[] str = (new BASE64Decoder()).decodeBuffer(key);
        return new String(str);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    public static BASE64Decoder getDecoder(){
        return new BASE64Decoder();
    }

    public static BASE64Encoder getEncoder(){
        return new BASE64Encoder();
    }

}
