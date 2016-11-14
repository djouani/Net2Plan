package com.net2plan.io.excelIO;

/**
 * @author Jorge San Emeterio
 * @date 14-Nov-16
 */
public final class ExcelConstants
{
    //@formatter:off
    private ExcelConstants() {}

    public static final String SHEET_NODES          = "Nodes";
    public static final String SHEET_LINKS          = "Links";

    public static final String NODE_NAME            = "Name";
    public static final String NODE_X               = "xCoord";
    public static final String NODE_Y               = "yCoord";
    public static final String NODE_ATTRIBUTES      = "Attributes";

    public static final String ATTRIBUTE_SEPARATOR          = ";";
    public static final String ATTRIBUTE_PAIR_SEPARATOR     = ":";

    public static final String LINK_ORIGIN      = "Origin";
    public static final String LINK_DESTINATION = "Destination";
    public static final String LINK_CAPACITY    = "Capacity";
    public static final String LINK_LENGTH      = "Length";
    public static final String LINK_PROPAGATION = "Propagation";
    public static final String LINK_ATTRIBUTES  = "Attributes";
}
