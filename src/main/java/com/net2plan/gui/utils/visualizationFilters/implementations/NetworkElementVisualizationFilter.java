package com.net2plan.gui.utils.visualizationFilters.implementations;

import com.net2plan.interfaces.networkDesign.Demand;
import com.net2plan.interfaces.networkDesign.IVisualizationFilter;
import com.net2plan.interfaces.networkDesign.Link;
import com.net2plan.interfaces.networkDesign.NetworkElement;
import com.net2plan.utils.Pair;
import com.net2plan.utils.Triple;

import java.util.List;

/**
 * @author Jorge San Emeterio
 * @date 13-Dec-16
 */
public abstract class NetworkElementVisualizationFilter implements IVisualizationFilter
{
    private boolean isActive = false;

    @Override
    public abstract boolean executeFilterForNetworkElement(NetworkElement element);

    @Override
    public abstract String getDescription();

    @Override
    public abstract List<Triple<String, String, String>> getParameters();

    @Override
    public abstract String getUniqueName();

    @Override
    public boolean executeFilterForForwardingRule(Pair<Demand, Link> fRuleKey, Double fRuleValue)
    {
        return true;
    }

    @Override
    public boolean isActive()
    {
        return isActive;
    }

    @Override
    public void setActive(boolean flag)
    {
        this.isActive = flag;
    }
}
