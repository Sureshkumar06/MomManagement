package com.nova.mom.utils;

public class MoMConstants {

    /**
     * Instantiates a new mom constants.
     */
    private MoMConstants(){
        throw new IllegalStateException("Constants class");
    }

    public static final String CONFIG_PATH_VERSION="/api/mommanagement/v1";

    public static final String RELEASE_DETAILS="/releaseDetails";

    public static final String RELEASE_ID="/{releaseId}";

    public static final String CUSTOMER_DETAILS="/customerDetails";

    public static final String CUSTOMER_ID="/{customerId}";
}
