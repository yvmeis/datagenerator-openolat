package com.frentix.datagenerator.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionService {
    
    private  String secretPhrase = "0Ss2Pj3!f5OsQh.T!h9";

    public EncryptionService() throws Exception {
        
    }

    /**
     * Creates a SecretKeySpec
     * 
     * @param myKey Secret Pass Phrase
     * @return a secret key
     */
    public SecretKeySpec setKey(final String myKey) {
        MessageDigest sha = null;
        try {
            byte[] key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            return secretKey;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Encrypts a String with AES encryption
     * 
     * @param strToEncrypt unencrypted String
     * @return encrypted String
     */
    public String encrypt(final String strToEncrypt) {
        try {
            SecretKeySpec secretKey = setKey(secretPhrase);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder()
            .encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    /**
     * Decrypts a String with AES decryption
     * 
     * @param strToDecrypt encrypted String
     * @return decrypted String
     */
    public String decrypt(final String strToDecrypt) {
        try {
            SecretKeySpec secretKey = setKey(secretPhrase);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder()
            .decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
