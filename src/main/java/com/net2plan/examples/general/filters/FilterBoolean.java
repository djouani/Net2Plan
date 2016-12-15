package com.net2plan.examples.general.filters;

import com.net2plan.interfaces.networkDesign.IVisualizationFilter;
import com.net2plan.interfaces.networkDesign.NetPlan;
import com.net2plan.interfaces.networkDesign.NetworkElement;
import com.net2plan.interfaces.networkDesign.Node;
import com.net2plan.utils.Triple;

import java.util.*;

/**
 * Created by César on 16/12/2016.
 */
public class FilterBoolean implements IVisualizationFilter {
    @Override
    public Set<NetworkElement> executeFilter(NetPlan netPlan, Map<String, String> filtersParameters, Map<String, String> net2PlanParameters) {

        Set<NetworkElement> set = new HashSet<>();
        Boolean flag = Boolean.parseBoolean(filtersParameters.get("Population flag"));
        int population = Integer.parseInt(filtersParameters.get("Population"));
        for(Node n : netPlan.getNodes())
        {
            if(flag && (Integer.parseInt(n.getAttribute("population"))< population))
                set.add(n);

        }

        return set;
    }

    @Override
    public String getDescription() {
        return "Filter with boolean parameter";
    }

    @Override
    public List<Triple<String, String, String>> getParameters() {

        List<Triple<String, String, String>> param = new LinkedList<>();
        param.add(Triple.of("Population flag","#boolean# false","true (show nodes whose population is higher than population value), false (nothing)"));
        param.add(Triple.of("Population","200000","Population threshold"));
        return param;
    }

    @Override
    public String getUniqueName() {
        return "Population Filter";
    }
}
