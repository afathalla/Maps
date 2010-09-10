package org.vaadin.hezamu.imagemapwidget;

import org.vaadin.hezamu.imagemapwidget.ImageMap.AreaClickListener;

import com.vaadin.Application;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

public class ImageMapApplication extends Application {
	private static final long serialVersionUID = 6134688737625538172L;

	@Override
	public void init() {
		setMainWindow(new Window("ImageMapApplication"));

		ImageMap imageMap = new ImageMap(new ExternalResource(
				"http://imgs.xkcd.com/comics/going_west.png"),
				new AreaClickListener() {
					/**
					 * Trivial area click handler. Just show a tray notification
					 * with the ID of the area.
					 */
					public void areaClicked(String areaId) {
						getMainWindow().showNotification("Area click",
								"Image map area '" + areaId + "' clicked",
								Notification.TYPE_TRAY_NOTIFICATION);
					}
				});

		// Add some areas
		try {
			imageMap.addArea("panel 1", "p1", 0, 0, 165, 237);
			imageMap.addArea("panel 2", "p2", 180, 0, 120, 237);
			imageMap.addArea("panel 3", "p3", 315, 0, 167, 237);
			imageMap.addArea("panel 4", "p4", 483, 0, 120, 237);
			imageMap.addArea("panel 5", "p5", 605, 0, 134, 237);
		} catch (OverlappingAreasException e) {
			e.printStackTrace();
			getMainWindow()
					.showNotification(
							"Overlapping area",
							"Trying to add a area that would overlap with an existing area",
							Notification.TYPE_ERROR_MESSAGE);
		}

		TabSheet ts = new TabSheet();
		ts.addTab(imageMap);

		getMainWindow().addComponent(ts);
	}
}
