package com.net2plan.examples.general.filters;

import com.net2plan.interfaces.networkDesign.IVisualizationFilter;
import com.net2plan.interfaces.networkDesign.Demand;
import com.net2plan.interfaces.networkDesign.Link;
import com.net2plan.interfaces.networkDesign.NetworkElement;
import com.net2plan.utils.Pair;
import com.net2plan.utils.Triple;

import java.util.List;

/**
 * @author César
 * @date 20/11/2016
 */
public class EvenIndexFilter implements IVisualizationFilter
{
    boolean active = false;

    @Override
    public boolean executeFilterForNetworkElement(NetworkElement element)
    {
        if(element.getIndex() % 2 == 0) return true;

        return false;
    }

    @Override
    public boolean executeFilterForForwardingRule(Pair<Demand, Link> fRuleKey, Double fRuleValue)
    {
        return false;
    }

    @Override
    public String getDescription()
    {
        return "A network element is visible if its index is even";
    }

    @Override
    public List<Triple<String, String, String>> getParameters()
    {
        return null;
    }

    @Override
    public String getUniqueName()
    {
        return "Filter nº 1";
    }

    @Override
    public boolean isActive()
    {
        return active;
    }

    @Override
    public void setActive(boolean flag)
    {
        active = flag;
    }

    @Override
    public void setParameterValue(String parameterName, String parameterValue) {

    }

    @Override
    public void setDefaultParameters() {

    }
}
