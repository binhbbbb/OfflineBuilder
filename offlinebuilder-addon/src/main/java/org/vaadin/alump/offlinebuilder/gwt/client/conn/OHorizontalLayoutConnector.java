package org.vaadin.alump.offlinebuilder.gwt.client.conn;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.orderedlayout.HorizontalLayoutConnector;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.shared.ui.Connect;
import com.vaadin.shared.ui.orderedlayout.HorizontalLayoutState;
import com.vaadin.shared.ui.orderedlayout.VerticalLayoutState;
import com.vaadin.ui.HorizontalLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alump on 09/07/14.
 */
@Connect(value = org.vaadin.alump.offlinebuilder.OfflineHorizontalLayout.class, loadStyle = Connect.LoadStyle.EAGER)
public class OHorizontalLayoutConnector extends HorizontalLayoutConnector implements OfflineContainerConnector {
    private static final Logger logger = Logger.getLogger(OHorizontalLayoutConnector.class.getName());
    private HorizontalLayoutState offlineState;
    private List<ComponentConnector> offlineChildren;
    private boolean offlineMode = false;

    public HorizontalLayoutState getState() {
        if(offlineState != null) {
            return offlineState;
        } else {
            return (HorizontalLayoutState) super.getState();
        }
    }

    @Override
    public void onOfflineState(SharedState state) {
        offlineState = (HorizontalLayoutState)state;
        StateChangeEvent event = new StateChangeEvent(this, null, true);
        this.onStateChanged(event);
    }

    @Override
    public void onOfflineHierarchy(List<OfflineConnector> children) {
        List<ComponentConnector> componentConnectors = new ArrayList<ComponentConnector>();
        for(OfflineConnector connector : children) {
            ComponentConnector cc = (ComponentConnector)connector;
            cc.setParent(this);
            componentConnectors.add(cc);
        }

        offlineChildren = componentConnectors;
        ConnectorHierarchyChangeEvent event = new ConnectorHierarchyChangeEvent();
        event.setOldChildren(new ArrayList<ComponentConnector>());
        event.setParent(this);
        event.setConnector(this);
        onConnectorHierarchyChange(event);
    }

    @Override
    public void setOffline(boolean offline) {
        offlineMode = offline;
    }

    @Override
    public List<ComponentConnector> getChildComponents() {
        if(offlineChildren != null) {
            return offlineChildren;
        } else {
            return super.getChildComponents();
        }
    }
}