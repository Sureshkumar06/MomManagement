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

    public static final String RELEASE_ID="/{releaseId}";

    public static final String CUSTOMER_DETAILS="/customerdetails";

    public static final String CUSTOMER_ID="/{customerId}";

    public static final String DEVICE_GROUP_DETAILS="/devicegroupdetails";

    public static final String DEVICE_GROUP_ID="/{deviceGroupId}";

    public static final String DEVICE_DETAILS="/devicedetails";

    public static final String DEVICE_MASTER_ID="/{deviceMasterId}";
}
