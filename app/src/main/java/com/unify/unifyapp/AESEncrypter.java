package com.unify.unifyapp;

import android.util.Base64;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncrypter {

    private static AESEncrypter instance;

    private static final String ENCRYPTER_ALGO = "AES";

    private final String key = "jQABzXbxoRAVIGjKGph4GBFKgoNxfmNK";

    private final String SENSITIVE_FIELD_ENCRYPTER_ALGO = "AES/CBC/PKCS5Padding";
    private final String senditiveFieldKey = "(H+MbQeThWmZq4t7w!z%C*F)J@NcRfUj";
    private final byte[] iv = {' '};
    private final IvParameterSpec ivspec = new IvParameterSpec(iv);

    public static AESEncrypter getInstance() {

        if (instance == null) {
            instance = new AESEncrypter();
        }
        return AESEncrypter.instance;
    }

    public String encrypt(String sData) {

        return encrypt(sData, key);
    }

    public String decrypt(String sData) {
        return decrypt(sData, key);
    }

    public String encrypt(String sData, String sKey) {
        try {
            byte[] values = sKey.getBytes();
            Key key = new SecretKeySpec(values, ENCRYPTER_ALGO);
            Cipher c = Cipher.getInstance(ENCRYPTER_ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(sData.getBytes(Charset.defaultCharset()));

//        String encoded = Base64.encodeToString(encVal, Base64.DEFAULT);
            return Base64.encodeToString(encVal, Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String encryptSensitiveField(String sData) {

        try {
            Key skeySpec = new SecretKeySpec(senditiveFieldKey.getBytes(), SENSITIVE_FIELD_ENCRYPTER_ALGO);
            Cipher cipher = Cipher.getInstance(SENSITIVE_FIELD_ENCRYPTER_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);
            byte[] encVal = cipher.doFinal(sData.getBytes(Charset.defaultCharset()));
            return Base64.encodeToString(encVal, Base64.NO_WRAP);
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";

    }

    public String decrypt(String sData, String sKey) {
        try {
            byte[] values = sKey.getBytes();
            Key key = new SecretKeySpec(values, ENCRYPTER_ALGO);
            Cipher c = null;

            c = Cipher.getInstance(ENCRYPTER_ALGO);

            c.init(Cipher.DECRYPT_MODE, key);
//        byte[] decordedValue = Base64.decode(sData, Base64.DEFAULT);
            byte[] decordedValue = Base64.decode(sData, Base64.NO_WRAP);
            byte[] decValue = c.doFinal(decordedValue);
            return new String(decValue);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String decryptSensitiveField(String sData) {

        try {
            Key skeySpec = new SecretKeySpec(senditiveFieldKey.getBytes(), SENSITIVE_FIELD_ENCRYPTER_ALGO);
            Cipher cipher = Cipher.getInstance(SENSITIVE_FIELD_ENCRYPTER_ALGO);

            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivspec);

            byte[] decordedValue = Base64.decode(sData, Base64.NO_WRAP);
            byte[] decValue = cipher.doFinal(decordedValue);
            return new String(decValue);
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }


   /* public String getKey() {
        String encryptedDate = Base64.encodeToString(HelperUtils.getDateTime().getBytes(), Base64.DEFAULT);
        int KEY_LENGTH = 16;
        return encryptedDate.substring(0, KEY_LENGTH);
    }*/
}
