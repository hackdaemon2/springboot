package com.lace.constants;

/**
 *
 * @author hackdaemon
 */
public interface ApplicationConstants {

    static int INIT = 1;
    static String SYSTEM_USER = "system";
    static final int EXPIRATION = 60 * 24;
    static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    static final short STRING_SIZE = 7;
    static final String TOKEN_INVALID = "invalid token";
    static final String TOKEN_EXPIRED = "expired";
    static final String TOKEN_VALID = "valid";
    static final String UTF_8 = "UTF-8";
}
