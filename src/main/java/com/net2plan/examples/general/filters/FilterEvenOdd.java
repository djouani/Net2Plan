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
public class FilterEvenOdd implements IVisualizationFilter{
    @Override
    public Set<NetworkElement> executeFilter(NetPlan netPlan, Map<String, String> filtersParameters, Map<String, String> net2PlanParameters) {

        String flag = filtersParameters.get("Even or Odd");
        Set<NetworkElement> set = new HashSet<>();
        for(Node n : netPlan.getNodes())
        {
            if(flag.equals("odd") && n.getIndex() % 2 == 0)
                set.add(n);
            if(flag.equals("even") && n.getIndex() % 2 != 0)
                set.add(n);

        }

        return set;
    }

    @Override
    public String getDescription() {
        return "Even Odd Filter";
    }

    @Override
    public List<Triple<String, String, String>> getParameters() {

        List<Triple<String, String, String>> param = new LinkedList<>();
            param.add(Triple.of("Even or Odd","#select# even, odd","Filter EvenOdd"));
        return param;
    }

    @Override
    public String getUniqueName() {
        return "Even Odd Filter";
    }
}
