package utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {

    private String secretKey;
    private String initVector;

    public AES(String key, String init) {
        this.secretKey = key;
        this.initVector = init;
    }

    public String encrypt(String message) {
        try {
            IvParameterSpec iv = new IvParameterSpec(this.initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(this.secretKey.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");	// CBC mode
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(message.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String decrypted(String cipherText) {
        try {
            IvParameterSpec iv = new IvParameterSpec(this.initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(this.secretKey.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");	// CBC mode
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] decoded = Base64.getDecoder().decode(cipherText);
            byte[] plain = cipher.doFinal(decoded);

            return new String(plain);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
