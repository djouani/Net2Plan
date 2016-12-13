package com.net2plan.examples.general.filters;

import com.net2plan.gui.utils.visualizationFilters.implementations.AbstractNetworkVisualizationFilter;
import com.net2plan.interfaces.networkDesign.IVisualizationFilter;
import com.net2plan.interfaces.networkDesign.Demand;
import com.net2plan.interfaces.networkDesign.Link;
import com.net2plan.interfaces.networkDesign.NetworkElement;
import com.net2plan.utils.Pair;
import com.net2plan.utils.Triple;

import java.util.List;

/**
 * @author Jorge San Emeterio
 * @date 13-Dec-16
 */
public class TestFilter extends AbstractNetworkVisualizationFilter
{
    @Override
    public boolean executeFilterForNetworkElement(NetworkElement element)
    {
        return false;
    }

    @Override
    public String getDescription()
    {
        return "Test Filter...";
    }

    @Override
    public List<Triple<String, String, String>> getParameters()
    {
        return null;
    }

    @Override
    public String getUniqueName()
    {
        return null;
    }
}
