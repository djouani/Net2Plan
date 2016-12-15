package com.net2plan.examples.general.filters;

import com.net2plan.interfaces.networkDesign.IVisualizationFilter;
import com.net2plan.interfaces.networkDesign.NetPlan;
import com.net2plan.interfaces.networkDesign.NetworkElement;
import com.net2plan.interfaces.networkDesign.Node;
import com.net2plan.utils.Triple;

import java.util.*;

/**
 * Created by César on 15/12/2016.
 */
public class FilterIndex implements IVisualizationFilter {
    @Override
    public Set<NetworkElement> executeFilter(NetPlan netPlan, Map<String, String> filtersParameters, Map<String, String> net2PlanParameters) {

        Set<NetworkElement> set = new HashSet<>();
        int index = Integer.parseInt(filtersParameters.get("Index"));
        List<Node> nodes = netPlan.getNodes();
        for(Node n : nodes)
        {
            if(n.getIndex() < index)
                set.add(n);
        }

        return set;
    }

    @Override
    public String getDescription() {
        return "Index Filter";
    }

    @Override
    public List<Triple<String, String, String>> getParameters() {
        List<Triple<String, String, String>> param = new LinkedList<>();
        param.add(Triple.of("Index","3","Index"));
        return param;
    }

    @Override
    public String getUniqueName() {
        return "Index Filter";
    }
}
