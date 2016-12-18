package com.net2plan.examples.general.filters;

import com.net2plan.interfaces.networkDesign.*;
import com.net2plan.utils.Triple;
import sun.nio.ch.Net;

import java.util.*;

/**
 * Created by César on 16/12/2016.
 */
public class FilterBoolean implements IVisualizationFilter {
    @Override
    public Map<Class<? extends NetworkElement>, Set<NetworkElement>> executeFilter(NetPlan netPlan, NetworkLayer layer, Map<String, String> filtersParameters, Map<String, String> net2PlanParameters) {

        Map<Class<? extends NetworkElement>,Set<NetworkElement>> elem = new HashMap<>();
        Set<NetworkElement> nodes = new HashSet<>();
        boolean flag = Boolean.parseBoolean(filtersParameters.get("Even or odd"));
        for(Node n : netPlan.getNodes())
        {
            if(flag && n.getIndex() % 2 == 0)
                nodes.add(n);
            if(!flag && n.getIndex() % 2 != 0)
                nodes.add(n);
        }

        elem.put(Node.class,nodes);
        return elem;
    }

    @Override
    public String getDescription() {
        return "Filter with boolean parameter";
    }

    @Override
    public List<Triple<String, String, String>> getParameters() {
        List<Triple<String, String, String>> param = new LinkedList<>();
        param.add(Triple.of("Even or odd","#boolean# true","Even or odd"));
        return param;
    }

    @Override
    public String getUniqueName() {
        return "Filter Boolean";
    }
}
