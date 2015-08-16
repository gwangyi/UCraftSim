package kr.gwangyi.ucraftsim;

/**
 * Created by gwangyi on 2015-07-02.
 */
public interface BattleLogger {
    void roundStart();
    void attack(boolean attacker, String attackerClass, String targetClass, int damage);
    void destroyed(boolean attacker, String ship);
    void roundEnd();
}
