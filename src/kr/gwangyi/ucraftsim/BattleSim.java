package kr.gwangyi.ucraftsim;

/**
 * Created by gwangyi on 2015-07-02.
 */
public interface BattleSim {
    void setLogger(BattleLogger logger);
    void init(FleetInfo attacker, FleetInfo defender);
    boolean round();
    int compare();
}
