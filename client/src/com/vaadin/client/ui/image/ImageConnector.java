package com.vaadin.client.ui.image;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.ClickEventHandler;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.AbstractEmbeddedState;
import com.vaadin.shared.ui.Connect;
import com.vaadin.shared.ui.image.ImageServerRpc;
import com.vaadin.shared.ui.image.ImageState;

@Connect(com.vaadin.ui.Image.class)
public class ImageConnector extends AbstractComponentConnector {

    ImageServerRpc rpc;

    @Override
    protected void init() {
        super.init();
        rpc = RpcProxy.create(ImageServerRpc.class, this);
        getWidget().addHandler(new LoadHandler() {

            @Override
            public void onLoad(LoadEvent event) {
                getLayoutManager().setNeedsMeasure(ImageConnector.this);
            }

        }, LoadEvent.getType());
    }

    @Override
    public VImage getWidget() {
        return (VImage) super.getWidget();
    }

    @Override
    public ImageState getState() {
        return (ImageState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        clickEventHandler.handleEventHandlerRegistration();

        getWidget().setUrl(
                getResourceUrl(AbstractEmbeddedState.SOURCE_RESOURCE));
        getWidget().setAltText(getState().alternateText);
    }

    protected final ClickEventHandler clickEventHandler = new ClickEventHandler(
            this) {

        @Override
        protected void fireClick(NativeEvent event,
                MouseEventDetails mouseDetails) {
            rpc.click(mouseDetails);
        }

    };

}
