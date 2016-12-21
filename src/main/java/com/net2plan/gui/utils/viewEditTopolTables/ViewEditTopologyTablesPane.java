package com.net2plan.gui.utils.viewEditTopolTables;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.net2plan.gui.utils.AdvancedJTable;
import com.net2plan.gui.utils.CurrentAndPlannedStateTableSorter.CurrentAndPlannedStateTableCellValue;
import com.net2plan.gui.utils.topologyPane.TopologyPanel;
import com.net2plan.gui.utils.viewEditTopolTables.rightPanelTabs.NetPlanViewTableComponent_layer;
import com.net2plan.gui.utils.viewEditTopolTables.rightPanelTabs.NetPlanViewTableComponent_network;
import com.net2plan.gui.utils.viewEditTopolTables.specificTables.*;
import com.net2plan.gui.utils.FixedColumnDecorator;
import com.net2plan.gui.utils.FullScrollPaneLayout;
import com.net2plan.gui.utils.INetworkCallback;
import com.net2plan.interfaces.networkDesign.NetPlan;
import com.net2plan.interfaces.networkDesign.NetworkLayer;
import com.net2plan.internal.Constants;
import com.net2plan.internal.Constants.NetworkElementType;
import com.net2plan.utils.Constants.RoutingType;
import com.net2plan.utils.Pair;

@SuppressWarnings("unchecked")
public class ViewEditTopologyTablesPane extends JPanel
{
	private final INetworkCallback mainWindow;
    private JTabbedPane netPlanView;
    private Map<NetworkElementType, AdvancedJTableNetworkElement> netPlanViewTable;
    private Map<NetworkElementType, JComponent> netPlanViewTableComponent;
    private JCheckBox showInitialPlan;

	public ViewEditTopologyTablesPane (INetworkCallback mainWindow , LayoutManager layout)
	{
		super (layout);
		
		this.mainWindow = mainWindow;

        netPlanViewTable = new EnumMap<NetworkElementType, AdvancedJTableNetworkElement>(NetworkElementType.class);
        netPlanViewTableComponent = new EnumMap<NetworkElementType, JComponent>(NetworkElementType.class);


//        mainWindow.allowDocumentUpdate = mainWindow.isEditable();
        netPlanViewTable.put(NetworkElementType.NODE, new AdvancedJTable_node(mainWindow));
        netPlanViewTable.put(NetworkElementType.LINK, new AdvancedJTable_link(mainWindow));
        netPlanViewTable.put(NetworkElementType.DEMAND, new AdvancedJTable_demand(mainWindow));
        netPlanViewTable.put(NetworkElementType.ROUTE, new AdvancedJTable_route(mainWindow));
        netPlanViewTable.put(NetworkElementType.PROTECTION_SEGMENT, new AdvancedJTable_segment(mainWindow));
        netPlanViewTable.put(NetworkElementType.FORWARDING_RULE, new AdvancedJTable_forwardingRule(mainWindow));
        netPlanViewTable.put(NetworkElementType.MULTICAST_DEMAND, new AdvancedJTable_multicastDemand(mainWindow));
        netPlanViewTable.put(NetworkElementType.MULTICAST_TREE, new AdvancedJTable_multicastTree(mainWindow));
        netPlanViewTable.put(NetworkElementType.SRG, new AdvancedJTable_srg(mainWindow));
        netPlanViewTable.put(NetworkElementType.LAYER, new AdvancedJTable_layer(mainWindow));
        netPlanViewTable.put(NetworkElementType.RESOURCE, new AdvancedJTable_resource(mainWindow));

        netPlanView = new JTabbedPane();

        for (NetworkElementType elementType : Constants.NetworkElementType.values()) {
            if (elementType == NetworkElementType.NETWORK) {
                netPlanViewTableComponent.put(elementType, new NetPlanViewTableComponent_network(mainWindow, (AdvancedJTable_layer) netPlanViewTable.get(NetworkElementType.LAYER)));
            } else if (elementType == NetworkElementType.LAYER) {
                netPlanViewTableComponent.put(elementType, new NetPlanViewTableComponent_layer(mainWindow, (AdvancedJTable_layer) netPlanViewTable.get(NetworkElementType.LAYER)));
            } else {
                JScrollPane scrollPane = netPlanViewTable.get(elementType).getScroll();
                scrollPane.setLayout(new FullScrollPaneLayout());
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                netPlanViewTable.get(elementType).getFixedTable().getColumnModel().getColumn(0).setMinWidth(50);
                netPlanViewTableComponent.put(elementType, scrollPane);
            }
        }

        this.add(netPlanView, BorderLayout.CENTER);

        if (mainWindow.inOnlineSimulationMode()) {
            showInitialPlan = new JCheckBox("Toggle show/hide planning information", true);
            showInitialPlan.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    RowFilter<TableModel, Integer> rowFilter = e.getStateChange() == ItemEvent.SELECTED ? null : new RowFilter<TableModel, Integer>() {
                        @Override
                        public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                            if (entry.getIdentifier() == 0) return true;

                            if (entry.getValue(0) instanceof CurrentAndPlannedStateTableCellValue)
                                return ((CurrentAndPlannedStateTableCellValue) entry.getValue(0)).value != null;
                            else
                                return entry.getValue(0) != null;
                        }
                    };

                    for (NetworkElementType elementType : Constants.NetworkElementType.values()) {
                        if (elementType == NetworkElementType.NETWORK) continue;

                        ((TableRowSorter) netPlanViewTable.get(elementType).getRowSorter()).setRowFilter(rowFilter);
                    }
                    mainWindow.getTopologyPanel().getCanvas().refresh();
                }
            });

            showInitialPlan.setSelected(false);
            add(showInitialPlan, BorderLayout.NORTH);
        }
        
        
	}

	public Map<NetworkElementType,AdvancedJTableNetworkElement> currentTables(){

	    return netPlanViewTable;
    }

	public void resetTables()
    {
        netPlanViewTable.clear();
        netPlanViewTableComponent.clear();

        netPlanViewTable.put(NetworkElementType.NODE, new AdvancedJTable_node(mainWindow));
        netPlanViewTable.put(NetworkElementType.LINK, new AdvancedJTable_link(mainWindow));
        netPlanViewTable.put(NetworkElementType.DEMAND, new AdvancedJTable_demand(mainWindow));
        netPlanViewTable.put(NetworkElementType.ROUTE, new AdvancedJTable_route(mainWindow));
        netPlanViewTable.put(NetworkElementType.PROTECTION_SEGMENT, new AdvancedJTable_segment(mainWindow));
        netPlanViewTable.put(NetworkElementType.FORWARDING_RULE, new AdvancedJTable_forwardingRule(mainWindow));
        netPlanViewTable.put(NetworkElementType.MULTICAST_DEMAND, new AdvancedJTable_multicastDemand(mainWindow));
        netPlanViewTable.put(NetworkElementType.MULTICAST_TREE, new AdvancedJTable_multicastTree(mainWindow));
        netPlanViewTable.put(NetworkElementType.SRG, new AdvancedJTable_srg(mainWindow));
        netPlanViewTable.put(NetworkElementType.LAYER, new AdvancedJTable_layer(mainWindow));
        netPlanViewTable.put(NetworkElementType.RESOURCE, new AdvancedJTable_resource(mainWindow));

        for (NetworkElementType elementType : Constants.NetworkElementType.values()) {
            if (elementType == NetworkElementType.NETWORK) {
                netPlanViewTableComponent.put(elementType, new NetPlanViewTableComponent_network(mainWindow, (AdvancedJTable_layer) netPlanViewTable.get(NetworkElementType.LAYER)));
            } else if (elementType == NetworkElementType.LAYER) {
                netPlanViewTableComponent.put(elementType, new NetPlanViewTableComponent_layer(mainWindow, (AdvancedJTable_layer) netPlanViewTable.get(NetworkElementType.LAYER)));
            } else {
                JScrollPane scrollPane = new JScrollPane(netPlanViewTable.get(elementType));
                scrollPane.setLayout(new FullScrollPaneLayout());
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                if(netPlanViewTable.get(elementType) instanceof AdvancedJTableNetworkElement)
                {
                    scrollPane.setRowHeaderView(((AdvancedJTableNetworkElement) netPlanViewTable.get(elementType)).getFixedTable());
                    scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, ((AdvancedJTableNetworkElement) netPlanViewTable.get(elementType)).getFixedTable().getTableHeader());
                    scrollPane.getRowHeader().addChangeListener(new ChangeListener(){

                        @Override
                        public void stateChanged(ChangeEvent e)
                        {
                            JViewport viewport = (JViewport) e.getSource();
                            scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
                        }
                    });
                }
                netPlanViewTableComponent.put(elementType, scrollPane);
            }
        }

        if (mainWindow.inOnlineSimulationMode()) {
            showInitialPlan = new JCheckBox("Toggle show/hide planning information", true);
            showInitialPlan.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    RowFilter<TableModel, Integer> rowFilter = e.getStateChange() == ItemEvent.SELECTED ? null : new RowFilter<TableModel, Integer>() {
                        @Override
                        public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                            if (entry.getIdentifier() == 0) return true;

                            if (entry.getValue(0) instanceof CurrentAndPlannedStateTableCellValue)
                                return ((CurrentAndPlannedStateTableCellValue) entry.getValue(0)).value != null;
                            else
                                return entry.getValue(0) != null;
                        }
                    };

                    for (NetworkElementType elementType : Constants.NetworkElementType.values()) {
                        if (elementType == NetworkElementType.NETWORK) continue;

                        ((TableRowSorter) netPlanViewTable.get(elementType).getRowSorter()).setRowFilter(rowFilter);
                    }
                    mainWindow.getTopologyPanel().getCanvas().refresh();
                }
            });

    }
    }

	public JTabbedPane getNetPlanView () { return netPlanView; }
	
    public Map<NetworkElementType, AdvancedJTableNetworkElement> getNetPlanViewTable () { return netPlanViewTable; }

    public boolean isInitialNetPlanShown () { return (showInitialPlan != null) && showInitialPlan.isSelected(); } 
    
    public void updateView ()
    {
		/* Load current network state */
        NetPlan currentState = mainWindow.getDesign();
        NetworkLayer layer = currentState.getNetworkLayerDefault();
        currentState.checkCachesConsistency();

        final RoutingType routingType = currentState.getRoutingType();
        Component selectedTab = netPlanView.getSelectedComponent();
        netPlanView.removeAll();
        for (NetworkElementType elementType : Constants.NetworkElementType.values()) {
            if (routingType == RoutingType.SOURCE_ROUTING && elementType == NetworkElementType.FORWARDING_RULE)
                continue;
            if (routingType == RoutingType.HOP_BY_HOP_ROUTING && (elementType == NetworkElementType.PROTECTION_SEGMENT || elementType == NetworkElementType.ROUTE))
                continue;
            netPlanView.addTab(elementType == NetworkElementType.NETWORK ? "Network" : netPlanViewTable.get(elementType).getTabName(), netPlanViewTableComponent.get(elementType));
        }

        for (int tabId = 0; tabId < netPlanView.getTabCount(); tabId++) {
            if (netPlanView.getComponentAt(tabId).equals(selectedTab)) {
                netPlanView.setSelectedIndex(tabId);
                break;
            }
        }

        NetPlan initialState = null;
        if (showInitialPlan != null && mainWindow.getInitialDesign().getNetworkLayerFromId(layer.getId()) != null)
            initialState = mainWindow.getInitialDesign();

        currentState.checkCachesConsistency();

        for (AdvancedJTableNetworkElement table : netPlanViewTable.values())
        {
            table.updateView(currentState, initialState);
        }
        ((NetPlanViewTableComponent_layer) netPlanViewTableComponent.get(NetworkElementType.LAYER)).updateNetPlanView(currentState);
        ((NetPlanViewTableComponent_network) netPlanViewTableComponent.get(NetworkElementType.NETWORK)).updateNetPlanView(currentState);
    }

    
    /**
     * Shows the tab corresponding associated to a network element.
     *
     * @param type    Network element type
     * @param itemId  Item identifier (if null, it will just show the tab)
     */
    public void selectViewItem (NetworkElementType type, Object itemId)
    {
        AdvancedJTableNetworkElement table = netPlanViewTable.get(type);
        int tabIndex = netPlanView.getSelectedIndex();
        int col = 0;
        if (netPlanView.getTitleAt(tabIndex).equals(type == NetworkElementType.NETWORK ? "Network" : table.getTabName())) {
            col = table.getSelectedColumn();
            if (col == -1) col = 0;
        } else {
            netPlanView.setSelectedComponent(netPlanViewTableComponent.get(type));
        }

        if (itemId == null) {
            table.clearSelection();
            return;
        }

        TableModel model = table.getModel();
        int numRows = model.getRowCount();
        for (int row = 0; row < numRows; row++) {
            Object obj = model.getValueAt(row, 0);
            if (obj == null) continue;

            if (type == NetworkElementType.FORWARDING_RULE) {
                obj = Pair.of(Integer.parseInt(model.getValueAt(row, 1).toString().split(" ")[0]), Integer.parseInt(model.getValueAt(row, 2).toString().split(" ")[0]));
                if (!obj.equals(itemId)) continue;
            } else if ((long) obj != (long) itemId) {
                continue;
            }

            row = table.convertRowIndexToView(row);
            table.changeSelection(row, col, false, true);
            return;
        }

        throw new RuntimeException(type + " " + itemId + " does not exist");
    }

    
}
