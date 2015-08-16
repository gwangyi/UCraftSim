package kr.gwangyi.ucraftsim;

import java.util.HashMap;

/**
 * Created by gwangyi on 2015-07-02.
 */
public class TextBattleLogger implements BattleLogger {
    private static class Round {
        private HashMap<String, Integer> totalAttack;
        private HashMap<String, Integer> totalDamage;
        private HashMap<String, Integer> totalDestroyed;

        void initialize() {
            totalAttack = new HashMap<String, Integer>();
            totalDamage = new HashMap<String, Integer>();
            totalDestroyed = new HashMap<String, Integer>();
        }

        static private void incr(HashMap<String, Integer> hash, String ship, int i) {
            if (!hash.containsKey(ship))
                hash.put(ship, 0);
            hash.put(ship, hash.get(ship) + i);
        }

        public void sanitize(String ship) {
            incr(totalAttack, ship, 0);
            incr(totalDamage, ship, 0);
            incr(totalDestroyed, ship, 0);
        }

        public void attack(String ship, int damage) {
            incr(totalAttack, ship, damage);
        }

        public void damage(String ship, int damage) {
            incr(totalDamage, ship, damage);
        }

        public void destroyed(String ship) {
            incr(totalDestroyed, ship, 1);
        }

        public int totalAttack(String ship) {
            return this.totalAttack.get(ship);
        }

        public int totalDamage(String ship) {
            return this.totalDamage.get(ship);
        }

        public int totalDestroyed(String ship) {
            return this.totalDestroyed.get(ship);
        }
    }

    final private FleetInfo attackerInfo, defenderInfo;
    final private Round attackerRound = new Round(), defenderRound = new Round();
    final StringBuilder result = new StringBuilder();
    private int round = 0;

    public TextBattleLogger(FleetInfo attackerInfo, FleetInfo defenderInfo) {
        this.attackerInfo = attackerInfo; this.defenderInfo = defenderInfo;
    }

    @Override
    public void roundStart() {
        attackerRound.initialize();
        defenderRound.initialize();
        round ++;
    }

    @Override
    public void attack(boolean attacker, String attackerClass, String targetClass, int damage) {
        if (attacker) {
            attackerRound.attack(attackerClass, damage);
            defenderRound.damage(targetClass, damage);
        } else {
            defenderRound.attack(attackerClass, damage);
            attackerRound.damage(targetClass, damage);
        }
    }

    @Override
    public void destroyed(boolean attacker, String ship) {
        if (attacker) attackerRound.destroyed(ship);
        else defenderRound.destroyed(ship);
    }

    static private void printLog(StringBuilder result, FleetInfo info, Round log) {
        for (String ship : info.getFleet().keySet()) {
            log.sanitize(ship);
            result.append(String.format("%s: %d(%d) A %d, D %d\n", ship, info.getFleet().get(ship), log.totalDestroyed(ship), log.totalAttack(ship), log.totalDamage(ship)));
            info.getFleet().put(ship, info.getFleet().get(ship) - log.totalDestroyed(ship));
        }
    }

    @Override
    public void roundEnd() {
        result.append("Round ");
        result.append(round);
        result.append('\n');
        result.append("Attacker side:\n");
        printLog(result, attackerInfo, attackerRound);
        result.append("Defender side:\n");
        printLog(result, defenderInfo, defenderRound);
    }

    @Override
    public String toString() {
        return result.toString();
    }
}
