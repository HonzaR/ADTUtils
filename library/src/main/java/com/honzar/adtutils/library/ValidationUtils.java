package com.honzar.adtutils.library;

import android.util.Patterns;

/**
 * Created by Honza Rychnovský on 09.05.17.
 * AppsDevTeam
 * honzar@appsdevteam.com
 */

public class ValidationUtils extends Utils {


    // VALIDATION

    /**
     * Validates email address
     *
     * @param email
     *
     * @return true/false
     */
    public static boolean isEmailAddressValid(String email)
    {
        if (StringUtils.checkEmptyString(email)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Validates web url address
     *
     * @param webUrl
     *
     * @return true/false
     */
    public static boolean isWebUrlValid(String webUrl)
    {
        if (StringUtils.checkEmptyString(webUrl)) {
            return false;
        }
        return Patterns.WEB_URL.matcher(webUrl).matches();
    }

    /**
     * Validates phone number address
     *
     * @param phoneNumber
     *
     * @return true/false
     */
    public static boolean isPhoneNumberValid(String phoneNumber)
    {
        if (StringUtils.checkEmptyString(phoneNumber)) {
            return false;
        }
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }

    /**
     * Validates domain name address
     *
     * @param domainName
     *
     * @return true/false
     */
    public static boolean isDomainNameValid(String domainName)
    {
        if (StringUtils.checkEmptyString(domainName)) {
            return false;
        }
        return Patterns.DOMAIN_NAME.matcher(domainName).matches();
    }

    /**
     * Validates ip address address
     *
     * @param ipAddress
     *
     * @return true/false
     */
    public static boolean isIpAddressValid(String ipAddress)
    {
        if (StringUtils.checkEmptyString(ipAddress)) {
            return false;
        }
        return Patterns.IP_ADDRESS.matcher(ipAddress).matches();
    }
}
