package shahbaz4311.fun2learn;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//class to encrypt and decrypt passwords using SHA512
public class PASSWORD {

    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            return new String(md.digest(password.getBytes()));

        } catch (NoSuchAlgorithmException e) {
            return password;
        }
    }

    public static boolean match(String encryptedPassword, String s) {
        return encryptedPassword.equals(encrypt(s));
    }
}

