package com.net2plan.io.excelIO;

import com.google.common.base.Splitter;
import com.net2plan.interfaces.networkDesign.NetPlan;
import com.net2plan.interfaces.networkDesign.Node;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Jorge San Emeterio
 * @date 15-Nov-16
 */
public final class ExcelTools
{
    private ExcelTools()
    {
    }

    public static Set<Node> getSequenceOfNodes(final NetPlan netPlan, final String nodes)
    {
        if (nodes == null || nodes.isEmpty()) return new HashSet<>();

        final Set<Node> nodeList = new HashSet<>();

        final Iterable<String> nodeIter = Splitter.on(ExcelConstants.COMMON_SEPARATOR).split(nodes);

        nodeIter.forEach(node -> nodeList.add(netPlan.getNodeByName(node)));

        return nodeList;
    }

    public static Map<String, String> readAttributes(final String attributes)
    {
        // Structure => key:value;key:value...
        if (attributes == null || attributes.isEmpty()) return null;
        else
            return Splitter.on(ExcelConstants.COMMON_SEPARATOR).withKeyValueSeparator(ExcelConstants.COMMON_PAIR_SEPARATOR).split(attributes);
    }
}
