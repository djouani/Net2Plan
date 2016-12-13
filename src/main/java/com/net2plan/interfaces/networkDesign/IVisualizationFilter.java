package com.net2plan.interfaces.networkDesign;

import com.net2plan.interfaces.networkDesign.Demand;
import com.net2plan.interfaces.networkDesign.Link;
import com.net2plan.interfaces.networkDesign.NetworkElement;
import com.net2plan.internal.IExternal;
import com.net2plan.utils.Pair;


import java.util.Map;

/**
 * Created by César on 15/11/2016.
 */
   /*Forwarding Rules are defined in a Map
    //Key: Pair<Demand,Link>
    //Value: Double*/
public interface IVisualizationFilter extends IExternal
{
    public boolean active = false;
    public boolean executeFilterForNetworkElement(NetworkElement element);
    public boolean executeFilterForForwardingRule(Pair<Demand,Link> fRuleKey, Double fRuleValue);
    public String getDescription();
    public String getUniqueName();
    public boolean isActive();
    public void setActive(boolean flag);
    public void setParameterValue(String parameterName, String parameterValue);
    public void setDefaultParameters();
}
