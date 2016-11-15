package com.net2plan.io.excelIO;

/**
 * @author Jorge San Emeterio
 * @date 14-Nov-16
 */
public final class ExcelConstants
{
    //@formatter:off
    private ExcelConstants() {}

    public static final String SHEET_LAYERS                 = "Layers";
    public static final String SHEET_NODES                  = "Nodes";
    public static final String SHEET_LINKS                  = "Links";
    public static final String SHEET_DEMANDS                = "Demands";
    public static final String SHEET_MULTICAST_DEMANDS      = "Multicast_Demands";
    public static final String SHEET_ROUTES                 = "Routes";
    public static final String SHEET_MULTICAST_TREES        = "Multicast_Trees";
    public static final String SHEET_PROTECTION_SEGMENTS    = "Protection_Segments";
    public static final String SHEET_RISK_GROUPS            = "Shared_risk_Groups";
    public static final String SHEET_FORWARDING_RULES       = "Forwarding_Rules";

    private static final String COMMON_ATTRIBUTES       = "Attributes";

    public static final String COMMON_SEPARATOR          = ";";
    public static final String COMMON_PAIR_SEPARATOR     = ":";

    public static final String LAYER_NAME           = "Name";
    public static final String LAYER_DESCRIPTION    = "Description";
    public static final String LAYER_LINK_UNIT      = "Capacity_Units";
    public static final String LAYER_DEMAND_UNIT    = "Demand_Units";
    public static final String LAYER_ATTRIBUTES     = COMMON_ATTRIBUTES;

    public static final String NODE_NAME            = "Name";
    public static final String NODE_X               = "xCoord";
    public static final String NODE_Y               = "yCoord";
    public static final String NODE_ATTRIBUTES      = COMMON_ATTRIBUTES;


    public static final String LINK_ORIGIN      = "Origin_Node";
    public static final String LINK_DESTINATION = "Destination_Node";
    public static final String LINK_CAPACITY    = "Capacity";
    public static final String LINK_LENGTH      = "Length";
    public static final String LINK_PROPAGATION = "Propagation";
    public static final String LINK_ATTRIBUTES  = COMMON_ATTRIBUTES;

    public static final String DEMAND_INGRESS_NODE      = "Ingress_Node";
    public static final String DEMAND_EGRESS_NODE       = "Egress_Node";
    public static final String DEMAND_OFFERED_TRAFFIC   = "Offered_Traffic";
    public static final String DEMAND_ATTRIBUTES        = COMMON_ATTRIBUTES;

    public static final String MULTICAST_INGRESS_NODE       = "Ingress_Node";
    public static final String MULTICAST_EGRESS_NODES       = "Egress Nodes";
    public static final String MULTICAST_OFFERED_TRAFFIC    = "Offered_Traffic";
    public static final String MULTICAST_ATTRIBUTES         = COMMON_ATTRIBUTES;

    public static final String ROUTE_DEMAND                 = "Demand";
    public static final String ROUTE_CARRIED_TRAFFIC        = "Carried_Traffic";
    public static final String ROUTE_OCCUPIED_CAPACITY      = "Occupied";
    public static final String ROUTE_SEQ_LINKS              = "Sequence_Of_Links";
    public static final String ROUTE_ATTRIBUTES             = COMMON_ATTRIBUTES;


}
