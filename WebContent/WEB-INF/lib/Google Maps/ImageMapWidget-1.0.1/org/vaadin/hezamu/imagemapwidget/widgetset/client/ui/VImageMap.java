package org.vaadin.hezamu.imagemapwidget.widgetset.client.ui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VImageMap extends AbsolutePanel implements Paintable {

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-imagemap";

	/** CSS class name for areas to allow styling. */
	public static final String AREA_CLASSNAME = CLASSNAME + "-area";

	/** The client side widget identifier */
	protected String paintableId;

	/** Reference to the server connection object. */
	protected ApplicationConnection client;

	private Image image = null;

	private boolean iconOnloadHandled = false;

	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VImageMap() {
		setElement(Document.get().createDivElement());

		setStyleName(CLASSNAME);

		image = new Image();

		// We want to reset the size of the AbsolutePanel to the size of the
		// image
		image.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				int width = image.getWidth();
				int height = image.getHeight();

				ApplicationConnection.getConsole().log(
						"Load, size " + width + "x" + height + "px");
				setWidth(width + "px");
				setHeight(height + "px");
			}
		});

		add(image, 0, 0);
	}

	/**
	 * Called whenever an update is received from the server
	 */
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		// This call should be made first.
		// It handles sizes, captions, tooltips, etc. automatically.
		// This call should be made first. Ensure correct implementation,
		// and let the containing layout manage caption, etc.
		if (client.updateComponent(this, uidl, true)) {
			return;
		}

		// Save reference to server connection object to be able to send
		// user interaction later
		this.client = client;

		// Save the UIDL identifier for the component
		paintableId = uidl.getId();

		for (final Iterator<Object> it = uidl.getChildIterator(); it.hasNext();) {
			final UIDL u = (UIDL) it.next();
			if (!u.getTag().equals("areas")) {
				continue;
			}

			for (final Iterator<Object> m = u.getChildIterator(); m.hasNext();) {
				final UIDL mapUIDL = (UIDL) m.next();

				final String aid = mapUIDL.getStringAttribute("id");
				final String tooltip = mapUIDL.getStringAttribute("tooltip");
				final int x = mapUIDL.getIntAttribute("x");
				final int y = mapUIDL.getIntAttribute("y");
				final int w = mapUIDL.getIntAttribute("w");
				final int h = mapUIDL.getIntAttribute("h");

				addArea(aid, tooltip, x, y, w, h);
			}
		}

		if (uidl.hasAttribute("image")) {
			// Point the image component at the defined source
			image.setUrl(client.translateVaadinUri(uidl
					.getStringAttribute("image")));

			// Make sure that the component size corresponds with the image
			setWidth(image.getWidth() + "px");
			setHeight(image.getHeight() + "px");

			Set<Paintable> w = new HashSet<Paintable>();
			w.add(this);
			Util.componentSizeUpdated(w);
		}
	}

	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);

		if (DOM.eventGetType(event) == Event.ONLOAD && !iconOnloadHandled) {
			iconOnloadHandled = true;
			Util.notifyParentOfSizeChange(this, true);
		}
	}

	private void addArea(String aid, String tooltip, int x, int y, int w, int h) {
		// Create a focus panel to get mouse events in this area of the image
		FocusPanel mousePanel = new FocusPanel();
		mousePanel.setHeight(h + "px");
		mousePanel.setWidth(w + "px");
		mousePanel.setTitle(tooltip);
		mousePanel.addMouseUpHandler(new MyMouseUpHandler(aid));

		// To allow styling
		mousePanel.addStyleName(AREA_CLASSNAME);

		add(mousePanel, x, y); // Add the mouse panel to the image panel
	}

	/**
	 * A trivial {@link MouseUpHandler} that notifies the server application
	 * when a area is clicked.
	 */
	private class MyMouseUpHandler implements MouseUpHandler {
		private final String aid;

		public MyMouseUpHandler(String aid) {
			super();
			this.aid = aid;
		}

		public void onMouseUp(MouseUpEvent event) {
			client.updateVariable(paintableId, "areaClicked", aid, true);
		}
	}
}
