package org.vaadin.hezamu.imagemapwidget;

/**
 * Exception that is thrown if someone tries to add a new area that overlaps
 * with an existing area.
 * 
 * @author Henri Muurimaa
 */
public class OverlappingAreasException extends Exception {

	private static final long serialVersionUID = 6102790988716831770L;

	private String overlappingAreaId;

	public OverlappingAreasException(String areaId) {
		this.overlappingAreaId = areaId;
	}

	public String getOverlappingAreaId() {
		return overlappingAreaId;
	}
}
