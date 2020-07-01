package io.spin.status.dto;

import io.spin.status.enumeration.*;
import lombok.Data;

@Data
public class DynamoDBSearchParam {

    /**
     * Basic Pagination
     */
    protected int limit = 10;
    protected String startKey;

    /**
     * AdClosePublished
     */
    protected String code;
    protected String start;
    protected String end;

    /**
     * StatusChange
     */
    protected String owner;


    /**
     * Partner
     */
    protected String partner;

    /**
     * Invite.List
     */
    protected String from;
    protected String to;

    /**
     * Version.Log
     */
    protected OSType osType;

    protected String type;
}
