package org.insa.graphics.drawing;

public interface OverlayTracker {

    /**
     * Show or hide this marker - A marker should be visible when created.
     * 
     * @param visible true to show the marker, false to hide.
     */
    public void setVisible(boolean visible);

    /**
     * Delete this marker.
     */
    public void delete();

}
