package org.vaadin.alump.offlinebuilder.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.addon.touchkit.gwt.client.offlinemode.DefaultOfflineMode;
import com.vaadin.addon.touchkit.gwt.client.offlinemode.OfflineMode;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.VOverlay;
import org.vaadin.alump.offlinebuilder.gwt.client.conn.ORootConnector;
import org.vaadin.alump.offlinebuilder.gwt.client.conn.OfflineConnector;
import org.vaadin.alump.offlinebuilder.gwt.client.offline.OfflineFactory;
import org.vaadin.alump.offlinebuilder.gwt.client.offline.RootOfflineFactory;

import java.util.logging.Logger;

/**
 * Created by alump on 09/06/14.
 */
public class BuilderOfflineMode extends DefaultOfflineMode {

    //protected OfflineRootPanel rootPanel;
    protected ORootConnector rootConnector;
    //protected VOverlay overlay;
    //protected static final int Z_INDEX = 30001;
    //protected boolean active = false;

    private static final Logger logger = Logger.getLogger(BuilderOfflineMode.class.getName());

    @Override
    protected void buildDefaultContent() {
        logger.severe("build default content");
        getPanel().clear();
        if(rootConnector == null) {
            rootConnector = resolveRootFactory().createRoot();

        }
        if(rootConnector != null) {
            Widget rootWidget = rootConnector.getWidget();
            if(rootWidget != null) {
                logger.severe("Adding root widget");
                getPanel().add(rootWidget);
            } else {
                HTML errorLabel = new HTML();
                errorLabel.setHTML("<h1>FAIL no root widget</h1>");
                getPanel().add(errorLabel);
            }
        } else {
            HTML errorLabel = new HTML();
            errorLabel.setHTML("<h1>FAIL to root connector</h1>");
            getPanel().add(errorLabel);
        }
    }

    /*
    public BuilderOfflineMode() {
        overlay = new VOverlay();
        rootPanel = GWT.create(OfflineRootPanel.class);

        overlay.addStyleName("v-window");
        overlay.addStyleName("v-touchkit-offlinemode");

        // Make sure that the outer and containers have 100% sizes
        overlay.getElement().getStyle().setWidth(100, Style.Unit.PCT);
        overlay.getElement().getStyle().setHeight(100, Style.Unit.PCT);
        overlay.setWidth("100%");
        overlay.setHeight("100%");

        // Make sure this is overloading the indicator
        overlay.getElement().getStyle().setZIndex(Z_INDEX);
        overlay.add(rootPanel);
    }

    public void activate(ActivationReason event) {
        active = true;
        overlay.show();

        logger.severe("activate offline mode");

        if(rootConnector == null) {
            rootConnector = resolveRootFactory().createInstance("");
            rootPanel.add(rootConnector.getWidget());
        }
    }

    public boolean deactivate() {

        logger.severe("deactive offline mode");

        active = false;
        overlay.hide();
        if(rootConnector != null) {
            rootPanel.remove(rootConnector.getWidget());
            rootConnector = null;
        }
        return true;
    }

    @Override
    public boolean isActive() {
        return active;
    }
    */

    protected RootOfflineFactory resolveRootFactory() {
        return new RootOfflineFactory();
    }

    public static Widget resolveWidget(OfflineConnector connector) {
        return ((AbstractComponentConnector)connector).getWidget();
    }

}