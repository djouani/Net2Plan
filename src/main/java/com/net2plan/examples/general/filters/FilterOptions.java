package com.net2plan.examples.general.filters;

import com.net2plan.interfaces.networkDesign.*;
import com.net2plan.utils.Triple;

import java.util.*;

/**
 * Created by César on 18/12/2016.
 */
public class FilterOptions implements IVisualizationFilter{
    @Override
    public Map<Class<? extends NetworkElement>, Set<NetworkElement>> executeFilter(NetPlan netPlan, NetworkLayer layer, Map<String, String> filtersParameters, Map<String, String> net2PlanParameters) {
        Map<Class<? extends NetworkElement>,Set<NetworkElement>> elem = new HashMap<>();
        Set<NetworkElement> nodes = new HashSet<>();
        String par = filtersParameters.get("Options");
        for(Node n : netPlan.getNodes())
        {
            switch(par){
                case "one":
                    if(n.getIndex() % 1 == 0)
                        nodes.add(n);
                        break;
                case "two":
                    if(n.getIndex() % 2 == 0)
                        nodes.add(n);
                    break;
                case "three":
                    if(n.getIndex() % 3 == 0)
                        nodes.add(n);
                    break;
            }
        }
        elem.put(Node.class,nodes);
        return elem;
    }

    @Override
    public String getDescription() {
        return "Filter with options";
    }

    @Override
    public List<Triple<String, String, String>> getParameters() {
        List<Triple<String, String, String>> param = new LinkedList<>();
        param.add(Triple.of("Options","#select#one,two,three","Proof"));
        return param;
    }

    @Override
    public String getUniqueName() {
        return "Filter with options";
    }
}
