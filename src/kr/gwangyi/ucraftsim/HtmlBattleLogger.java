package kr.gwangyi.ucraftsim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by gwangyi on 2015-07-02.
 */
public class HtmlBattleLogger implements BattleLogger {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("kr.gwangyi.ucraftsim.labels");
    private static final HashMap<String, String> dictionary = new HashMap<>();
    private static final String[] shipnames;
    static {
        dictionary.put("phantom", bundle.getString("ucsim.frigate.phantom"));
        dictionary.put("phantom2", bundle.getString("ucsim.frigate.phantom2"));
        dictionary.put("observer", bundle.getString("ucsim.frigate.observer"));
        dictionary.put("small_transporter", bundle.getString("ucsim.frigate.small_transporter"));
        dictionary.put("miner", bundle.getString("ucsim.frigate.miner"));

        dictionary.put("guardian", bundle.getString("ucsim.cruiser.guardian"));
        dictionary.put("constantine", bundle.getString("ucsim.cruiser.constantine"));
        dictionary.put("nightmare", bundle.getString("ucsim.cruiser.nightmare"));
        dictionary.put("colonizer", bundle.getString("ucsim.cruiser.colonizer"));

        dictionary.put("halpas", bundle.getString("ucsim.battlecruiser.halpas"));
        dictionary.put("patriot", bundle.getString("ucsim.battlecruiser.patriot"));
        dictionary.put("invader", bundle.getString("ucsim.battlecruiser.invader"));
        dictionary.put("kraken", bundle.getString("ucsim.battlecruiser.kraken"));
        dictionary.put("large_transporter", bundle.getString("ucsim.battlecruiser.large_transporter"));

        dictionary.put("valkyrie", bundle.getString("ucsim.battleship.valkyrie"));
        dictionary.put("atlas", bundle.getString("ucsim.battleship.atlas"));
        dictionary.put("karma", bundle.getString("ucsim.battleship.karma"));
        dictionary.put("galactica", bundle.getString("ucsim.battleship.galactica"));
        dictionary.put("gigantes", bundle.getString("ucsim.battleship.gigantes"));
        dictionary.put("aegis", bundle.getString("ucsim.battleship.aegis"));

        dictionary.put("divine_star", bundle.getString("ucsim.carrier.divine_star"));
        dictionary.put("guillotine", bundle.getString("ucsim.carrier.guillotine"));
        dictionary.put("wraith_of_god", bundle.getString("ucsim.carrier.wraith_of_god"));

        dictionary.put("armageddon", bundle.getString("ucsim.titan.armageddon"));
        dictionary.put("harlock", bundle.getString("ucsim.titan.harlock"));
        dictionary.put("odyssey", bundle.getString("ucsim.titan.odyssey"));

        dictionary.put("laser_turret", bundle.getString("ucsim.defence.laser_turret"));
        dictionary.put("photon_turret", bundle.getString("ucsim.defence.photon_turret"));
        dictionary.put("ion_turret", bundle.getString("ucsim.defence.ion_turret"));
        dictionary.put("plasma_turret", bundle.getString("ucsim.defence.plasma_turret"));
        dictionary.put("neutron_turret", bundle.getString("ucsim.defence.neutron_turret"));
        dictionary.put("antimatter_turret", bundle.getString("ucsim.defence.antimatter_turret"));

        ArrayList<String> shiplist = new ArrayList<>();

        shiplist.add("phantom");
        shiplist.add("phantom2");
        shiplist.add("observer");
        shiplist.add("small_transporter");
        shiplist.add("miner");

        shiplist.add("guardian");
        shiplist.add("constantine");
        shiplist.add("nightmare");
        shiplist.add("colonizer");

        shiplist.add("halpas");
        shiplist.add("patriot");
        shiplist.add("invader");
        shiplist.add("kraken");
        shiplist.add("large_transporter");

        shiplist.add("valkyrie");
        shiplist.add("atlas");
        shiplist.add("karma");
        shiplist.add("galactica");
        shiplist.add("gigantes");
        shiplist.add("aegis");

        shiplist.add("divine_star");
        shiplist.add("guillotine");
        shiplist.add("wraith_of_god");

        shiplist.add("armageddon");
        shiplist.add("harlock");
        shiplist.add("odyssey");

        shiplist.add("laser_turret");
        shiplist.add("photon_turret");
        shiplist.add("ion_turret");
        shiplist.add("plasma_turret");
        shiplist.add("neutron_turret");
        shiplist.add("antimatter_turret");
        
        shipnames = shiplist.toArray(new String[]{});
    }
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

    public HtmlBattleLogger(FleetInfo attackerInfo, FleetInfo defenderInfo) {
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
        for (String ship : shipnames) {
            if(!info.getFleet().keySet().contains(ship)) continue;
            log.sanitize(ship);
            result.append("<tr><td>").append(dictionary.get(ship)).append("</td><td style=\"text-align: right\">")
                    .append(info.getFleet().get(ship)).append("</td><td style=\"text-align: right\">")
                    .append(log.totalDestroyed(ship)).append("</td><td style=\"text-align: right\">")
                    .append(log.totalAttack(ship)).append("</td><td style=\"text-align: right\">")
                    .append(log.totalDamage(ship)).append("</td></tr>");
            info.getFleet().put(ship, info.getFleet().get(ship) - log.totalDestroyed(ship));
        }
    }

    @Override
    public void roundEnd() {
        result.append("<h1>Round ");
        result.append(round);
        result.append("</h1>");
        result.append(String.format("<h2>%s</h2>", bundle.getString("ucsim.attacker")));
        result.append("<table style=\"width:100%; border: 1px solid black\">");
        result.append(String.format("<tr><th></th><th>%s</th><th>%s</th><th>%s</th><th>%s</th></tr>",
                bundle.getString("ucsim.result.ships"), bundle.getString("ucsim.result.destroyed"),
                bundle.getString("ucsim.result.firepower"), bundle.getString("ucsim.result.damaged")));
        printLog(result, attackerInfo, attackerRound);
        result.append("</table>");
        result.append(String.format("<h2>%s</h2>", bundle.getString("ucsim.defender")));
        result.append("<table style=\"width:100%; border: 1px solid black\">");
        result.append(String.format("<tr><th></th><th>%s</th><th>%s</th><th>%s</th><th>%s</th></tr>",
                bundle.getString("ucsim.result.ships"), bundle.getString("ucsim.result.destroyed"),
                bundle.getString("ucsim.result.firepower"), bundle.getString("ucsim.result.damaged")));
        printLog(result, defenderInfo, defenderRound);
        result.append("</table>");
    }

    @Override
    public String toString() {
        return String.format("<html>%s</html>", result.toString());
    }
}
