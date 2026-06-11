package com.fcs.payment.adapter.dataprovider.security;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KeyUtil {

    // Converte String de volta para SecretKey
    public static SecretKey stringToKey(String keyString) {
        byte[] keyBytes = keyString.getBytes();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
    }
}
