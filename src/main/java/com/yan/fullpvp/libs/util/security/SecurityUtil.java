package com.yan.fullpvp.libs.util.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {

    public static String md5(final String string) {
        String sen = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        final BigInteger hash = new BigInteger(1, md.digest(string.getBytes()));
        sen = hash.toString(16);
        return sen;
    }


}
