package com.fcs.payment.adapter.dataprovider.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

public class CryptoUtil {

    private static final String ALGORITHM = "AES";

    // Criptografa o texto usando a chave
    public static String encrypt(String data, SecretKey key){
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            return null;
        }
    }


    // Descriptografa o texto usando a chave
    public static String decrypt(String encryptedData, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        } catch (Exception e) {
            return null;
        }
    }
}
