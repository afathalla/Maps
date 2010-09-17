/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.maps.client.overlay;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.maps.client.impl.GeoXmlOverlayImpl;
import com.google.gwt.maps.client.impl.EventImpl.VoidCallback;
import com.google.gwt.maps.client.overlay.Overlay.ConcreteOverlay;

/**
 * A map overlay containing geographic content from a publicly accessible GeoRSS
 * XML or KML file.
 */
public final class GeoXmlOverlay extends ConcreteOverlay {

  /**
   * This internal class adds a place holder for the jsoPeer that is returned
   * from the GeoXmlOverlayImpl.impl.construct() call.
   */
  private abstract static class JSOVoidCallback extends VoidCallback {
    protected boolean missedCb = false;
    protected JavaScriptObject storedJso;

    public void setJsoPeer(JavaScriptObject jsoPeer) {
      storedJso = jsoPeer;
    }
  }

  /**
   * Factory method to create a new overlay from a GeoRSS XML or KML file. This
   * provides a callback function that returns the new object when the load
   * completes.
   * 
   * @param url the URL of the XML or KML file to load
   * @param cb the callback to invoke when loading completes.
   */
  public static void load(final String url, final GeoXmlLoadCallback cb) {

    if (url == null) {
      throw new NullPointerException("url parameter must not be null");
    }
    if (cb == null) {
      throw new NullPointerException("callback parameter must not be null");
    }

    JSOVoidCallback voidCb = new JSOVoidCallback() {
      @Override
      public void callback() {
        // If the storedJso is null at this point, the outer load() call
        // may not yet have returned (the callback is being called before
        // constructGeoXmlOverlay() returns.)

        if (storedJso == null) {
          missedCb = true;
          return;
        }
        loadCb(url, cb, storedJso);
      }
    };

    JavaScriptObject outerJsoPeer = GeoXmlOverlayImpl.impl.constructGeoXmlOverlay(
        url, voidCb);

    voidCb.setJsoPeer(outerJsoPeer);

    // A fast callback return could cause the callback() method
    // to execute before this method gets called. If the callback couldn't
    // execute then, invoke it now.
    if (voidCb.missedCb) {
      loadCb(url, cb, outerJsoPeer);
    }
  }

  /**
   * Does the work of actual invoking the user's callback code.
   * 
   * @param url the URL that was passed when the load was initiated.
   * @param cb the user callback to invoke.
   * @param jso the newly constructed GGeoXml instance.
   */
  private static void fireLoadCb(String url, GeoXmlLoadCallback cb,
      JavaScriptObject jso) {

    Throwable caught = null;
    GeoXmlOverlay overlay = null;
    try {
      if (GeoXmlOverlayImpl.impl.loadedCorrectly(jso)) {
        overlay = new GeoXmlOverlay(jso);
      }
    } catch (Throwable e) {
      caught = e;
    }

    if (caught == null && overlay != null) {
      cb.onSuccess(url, overlay);
    } else {
      cb.onFailure(url, caught);
    }
  }

  /**
   * Wraps firing the callback so that an exception handler can be called.
   * 
   * @param handler the uncaught exception handler to call
   * @param url the url made in the load request
   * @param cb callback to use on success/failure. *
   * @param jso the newly constructed GGeoXml instance.
   */
  private static void fireLoadCbAndCatch(UncaughtExceptionHandler handler,
      String url, GeoXmlLoadCallback cb, JavaScriptObject jso) {
    try {
      fireLoadCb(url, cb, jso);
    } catch (Throwable e) {
      handler.onUncaughtException(e);
    }
  }

  private static void loadCb(String url, GeoXmlLoadCallback cb,
      JavaScriptObject jso) {
    UncaughtExceptionHandler handler = GWT.getUncaughtExceptionHandler();

    if (handler != null) {
      fireLoadCbAndCatch(handler, url, cb, jso);
    } else {
      fireLoadCb(url, cb, jso);
    }
  }

  /**
   * Creates a new overlay from a GeoRSS XML or KML file.
   * 
   * @param jsoPeer Create an instance of GeoXmlOverlay from an existing JSO
   */
  GeoXmlOverlay(JavaScriptObject jsoPeer) {
    super(jsoPeer);
  }

  /**
   * Returns the bounding box of the default viewport. This function should only
   * be called after the file has been loaded.
   * 
   * @return the bounding box of the default viewport.
   */
  public LatLngBounds getDefaultBounds() {
    return GeoXmlOverlayImpl.impl.getDefaultBounds(jsoPeer);
  }

  /**
   * Returns the center of the default viewport as a lat/lng. This function
   * should only be called after the file has been loaded. (Since 2.84)
   * 
   * @return point defined at the center of the viewport.
   */
  public LatLng getDefaultCenter() {
    return GeoXmlOverlayImpl.impl.getDefaultCenter(jsoPeer);
  }

  /**
   * Returns the span of the default viewport as a lat/lng. This function should
   * only be called after the file has been loaded.
   * 
   * @return the span of the default viewport.
   */
  public LatLng getDefaultSpan() {
    return GeoXmlOverlayImpl.impl.getDefaultSpan(jsoPeer);
  }

  /**
   * GeoXmlOverlay objects may create a tile overlay for optimization purposes
   * in certain cases. This method returns this tile layer overlay (if
   * available). Note that the tile overlay may be <code>null</code> if not
   * needed, or if the GeoXmlOverlay file has not yet finished loading.
   * 
   * @return a handle to the TileLayerOverlay object
   */
  public TileLayerOverlay getTileLayerOverlay() {
    return GeoXmlOverlayImpl.impl.getTileLayerOverlay(jsoPeer);
  }

  /**
   * Sets the map's viewport to the default viewport of the XML file. (Since
   * 2.84)
   * 
   * @param mapWidget Map to adjust viewport of
   */
  public void gotoDefaultViewport(MapWidget mapWidget) {
    GeoXmlOverlayImpl.impl.gotoDefaultViewport(jsoPeer, mapWidget.getPeer());
  }

  // Intentionally omitting hasLoaded() entry point. If the load() factory
  // method is used to construct, it isn't needed.

  /**
   * Returns <code>true</code> if the GeoXmlOverlay object is currently
   * hidden, as changed by the {@link GeoXmlOverlay#setVisible(boolean)}.
   * Otherwise returns <code>false</code>.
   * 
   * @return <code>true</code> if the overlay is currently hidden.
   */
  public boolean isHidden() {
    return GeoXmlOverlayImpl.impl.isHidden(jsoPeer);
  }

  // Intentionally omitting the loadedCorrectly() entry point. If the load()
  // factory method is used to construct, it isn't needed.

  /**
   * Shows/Hides the child overlays created by the GeoXmlOverlay object if the
   * overlay is both currently visible and the overlay's supportsHide() method
   * returns <code>true</code>. Note that this method will trigger the
   * respective visibility changed event for each child overlay that fires that
   * event. If no overlays are currently visible that return supportsHide() as
   * <code>true</code>, this method has no effect.
   */
  public void setVisible(boolean visible) {
    if (visible) {
      GeoXmlOverlayImpl.impl.show(jsoPeer);
    } else {
      GeoXmlOverlayImpl.impl.hide(jsoPeer);
    }
  }

  /**
   * Always returns <code>true</code>.
   * 
   * @return <code>true</code>
   */
  public boolean supportsHide() {
    return GeoXmlOverlayImpl.impl.supportsHide(jsoPeer);
  }

}
