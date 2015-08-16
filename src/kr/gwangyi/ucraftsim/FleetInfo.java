package kr.gwangyi.ucraftsim;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gwangyi on 2015-07-02.
 */
public class FleetInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> fleet;
    private Map<String, Integer> research;
    private Map<String, Float> factor;

    public FleetInfo() {
        fleet = new HashMap<String, Integer>();
        research = new HashMap<String, Integer>();
        factor = new HashMap<String, Float>();
    }

    public Map<String, Integer> getFleet() { return fleet; }
    public Map<String, Integer> getResearch() { return research; }
    public Map<String, Float> getFactor() { return factor; }
}
