package com.nova.mom.utils;

public class MoMConstants {

    /**
     * Instantiates a new mom constants.
     */
    private MoMConstants(){
        throw new IllegalStateException("Constants class");
    }

    public static final String CONFIG_PATH_VERSION="/api/mommanagement/v1";

    public static final String RELEASE_DETAILS="/releasedetails";

    public static final String RELEASE_ID="/{releaseid}";

    public static final String CUSTOMER_DETAILS="/customerdetails";

    public static final String CUSTOMER_ID="/{customerid}";

    public static final String DEVICE_GROUP_DETAILS="/devicegroupdetails";

    public static final String DEVICE_GROUP_ID="/{devicegroupid}";
}
