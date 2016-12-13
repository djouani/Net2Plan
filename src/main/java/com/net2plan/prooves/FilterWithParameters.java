package com.net2plan.prooves;

import com.net2plan.gui.utils.visualizationFilters.IVisualizationFilter;
import com.net2plan.interfaces.networkDesign.Demand;
import com.net2plan.interfaces.networkDesign.Link;
import com.net2plan.interfaces.networkDesign.NetworkElement;
import com.net2plan.utils.Pair;
import com.net2plan.utils.Triple;

import java.util.List;

/**
 * Created by César on 13/12/2016.
 */
public class FilterWithParameters implements IVisualizationFilter {
    @Override
    public boolean isVisibleNetworkElement(NetworkElement element) {
        return false;
    }

    @Override
    public boolean isVisibleForwardingRules(Pair<Demand, Link> fRuleKey, Double fRuleValue) {
        return false;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public List<Triple<String, String, String>> getParameters() {
        return null;
    }

    @Override
    public String getUniqueName() {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setActive(boolean flag) {

    }

    @Override
    public void setParameterValue(String parameterName, String parameterValue) {

    }

    @Override
    public void setDefaultParameters() {

    }
}
