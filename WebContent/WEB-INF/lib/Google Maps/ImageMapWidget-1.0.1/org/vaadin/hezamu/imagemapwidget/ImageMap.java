package org.vaadin.hezamu.imagemapwidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.AbstractComponent;

/**
 * Server side component for the VImageMap widget.
 */
@com.vaadin.ui.ClientWidget(org.vaadin.hezamu.imagemapwidget.widgetset.client.ui.VImageMap.class)
public class ImageMap extends AbstractComponent {
	private static final long serialVersionUID = -9014046506795933630L;

	private final List<AreaClickListener> listeners = new ArrayList<AreaClickListener>();

	private final List<Area> areas = new ArrayList<Area>();

	private final Resource image;

	public ImageMap(Resource image) {
		this(image, null);
	}

	public ImageMap(Resource image, AreaClickListener listener) {
		this.image = image;
		if (listener != null) {
			addListener(listener);
		}
		clearAreas();
	}

	public void addArea(String areaId, String tooltip, int x, int y, int h,
			int w) throws OverlappingAreasException {

		Area newArea = new Area(areaId, tooltip, x, y, h, w);
		for (Area area : areas) {
			if (area.overlaps(newArea)) {
				throw new OverlappingAreasException(areaId);
			}
		}

		areas.add(newArea);
	}

	public void clearAreas() {
		areas.clear();
	}

	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);

		target.addAttribute("image", image);

		target.startTag("areas");

		for (Area area : areas) {
			target.startTag("area");
			target.addAttribute("id", area.id);
			target.addAttribute("tooltip", area.tooltip);
			target.addAttribute("x", area.x);
			target.addAttribute("y", area.y);
			target.addAttribute("w", area.w);
			target.addAttribute("h", area.h);
			target.endTag("area");
		}

		target.endTag("areas");
	}

	/**
	 * Receive and handle events and other variable changes from the client.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void changeVariables(Object source, Map<String, Object> variables) {
		super.changeVariables(source, variables);

		if (!variables.containsKey("areaClicked")) {
			return;
		}

		String areaId = (String) variables.get("areaClicked");

		fireAreaClick(areaId);
	}

	public interface AreaClickListener {
		public void areaClicked(String areaId);
	}

	public void addListener(AreaClickListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeListener(AreaClickListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	private void fireAreaClick(String areaId) {
		for (AreaClickListener listener : listeners) {
			listener.areaClicked(areaId);
		}
	}

	public class Area {
		String id;
		String tooltip;
		int x;
		int y;
		int w;
		int h;

		public Area(String id, String tooltip, int x, int y, int w, int h) {
			this.id = id;
			this.tooltip = tooltip;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}

		public boolean overlaps(Area other) {
			if (x >= (other.x + other.w) || (x + w) <= other.x) {
				return false;
			} else if (y >= (other.y + other.h) || (y + h) <= other.y) {
				return false;
			} else {
				return true;
			}
		}
	}
}
