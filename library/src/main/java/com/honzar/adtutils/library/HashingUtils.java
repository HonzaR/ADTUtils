package com.honzar.adtutils.library;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Honza Rychnovský on 03.04.18.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class HashingUtils extends Utils {


    /**
     * Returns MD5 hash of input string
     *
     * @param input String with input data
     *
     * @return MD5 string hash or null in case of error
     */
    public static String md5(String input)
    {
        if (input == null || input.isEmpty())
            return null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger md5Data = new BigInteger(1, md.digest(input.getBytes()));
            return String.format("%032X", md5Data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
