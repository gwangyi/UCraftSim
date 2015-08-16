package kr.gwangyi.ucraftsim;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Created by gwangyi on 2015-07-01.
 */
public class FleetForm implements ActionListener {
    private JPanel fleetPane;
    private JTextArea bulk;
    private JTextField phantom;
    private JTextField phantom2;
    private JTextField observer;
    private JTextField smallTransporter;
    private JTextField miner;
    private JTextField guardian;
    private JTextField constantine;
    private JTextField nightmare;
    private JTextField colonizer;
    private JTextField halpas;
    private JTextField patriot;
    private JTextField invader;
    private JTextField kraken;
    private JTextField largeTransporter;
    private JTextField valkyrie;
    private JTextField atlas;
    private JTextField karma;
    private JTextField galactica;
    private JTextField gigantes;
    private JTextField aegis;
    private JTextField divineStar;
    private JTextField guillotine;
    private JTextField wraithOfGod;
    private JTextField armageddon;
    private JTextField harlock;
    private JTextField odyssey;
    private JTextField laserTurret;
    private JTextField photonTurret;
    private JTextField ionTurret;
    private JTextField plasmaTurret;
    private JTextField neutronTurret;
    private JTextField antimatterTurret;
    private JTextField laserCannon;
    private JTextField smMissile;
    private JTextField cruiseMissile;
    private JTextField photonCannon;
    private JTextField ionCannon;
    private JTextField plasmaCannon;
    private JTextField neutronCannon;
    private JTextField antimatterCannon;
    private JTextField lightArmor;
    private JTextField heavyArmor;
    private JTextField shield;
    private JTextField attackBonus;
    private JTextField armorBonus;
    private JTextField shieldBonus;
    private JButton bulkload;

    private HashMap<String, JTextField> fields = new HashMap<String, JTextField>();

    private static final HashMap<String, String> dictionary = new HashMap<>();
    static {
        dictionary.put("phantom", "ucsim.frigate.phantom");
        dictionary.put("phantom2", "ucsim.frigate.phantom2");
        dictionary.put("observer", "ucsim.frigate.observer");
        dictionary.put("small_transporter", "ucsim.frigate.small_transporter");
        dictionary.put("miner", "ucsim.frigate.miner");

        dictionary.put("guardian", "ucsim.cruiser.guardian");
        dictionary.put("constantine", "ucsim.cruiser.constantine");
        dictionary.put("nightmare", "ucsim.cruiser.nightmare");
        dictionary.put("colonizer", "ucsim.cruiser.colonizer");

        dictionary.put("halpas", "ucsim.battlecruiser.halpas");
        dictionary.put("patriot", "ucsim.battlecruiser.patriot");
        dictionary.put("invader", "ucsim.battlecruiser.invader");
        dictionary.put("kraken", "ucsim.battlecruiser.kraken");
        dictionary.put("large_transporter", "ucsim.battlecruiser.large_transporter");

        dictionary.put("valkyrie", "ucsim.battleship.valkyrie");
        dictionary.put("atlas", "ucsim.battleship.atlas");
        dictionary.put("karma", "ucsim.battleship.karma");
        dictionary.put("galactica", "ucsim.battleship.galactica");
        dictionary.put("gigantes", "ucsim.battleship.gigantes");
        dictionary.put("aegis", "ucsim.battleship.aegis");

        dictionary.put("divine_star", "ucsim.carrier.divine_star");
        dictionary.put("guillotine", "ucsim.carrier.guillotine");
        dictionary.put("wraith_of_god", "ucsim.carrier.wraith_of_god");

        dictionary.put("armageddon", "ucsim.titan.armageddon");
        dictionary.put("harlock", "ucsim.titan.harlock");
        dictionary.put("odyssey", "ucsim.titan.odyssey");

        dictionary.put("laser_turret", "ucsim.defence.laser_turret");
        dictionary.put("photon_turret", "ucsim.defence.photon_turret");
        dictionary.put("ion_turret", "ucsim.defence.ion_turret");
        dictionary.put("plasma_turret", "ucsim.defence.plasma_turret");
        dictionary.put("neutron_turret", "ucsim.defence.neutron_turret");
        dictionary.put("antimatter_turret", "ucsim.defence.antimatter_turret");

        dictionary.put("laser_cannon", "ucsim.research.laser_cannon");
        dictionary.put("sm_missile", "ucsim.research.sm_missile");
        dictionary.put("cruise_missile", "ucsim.research.cruise_missile");
        dictionary.put("photon_cannon", "ucsim.research.photon_cannon");
        dictionary.put("ion_cannon", "ucsim.research.ion_cannon");
        dictionary.put("plasma_cannon", "ucsim.research.plasma_cannon");
        dictionary.put("neutron_cannon", "ucsim.research.neutron_cannon");
        dictionary.put("antimatter_cannon", "ucsim.research.antimatter_cannon");
        dictionary.put("light_armor", "ucsim.research.light_armor");
        dictionary.put("heavy_armor", "ucsim.research.heavy_armor");
        dictionary.put("shield", "ucsim.research.shield");
    }
    
    private static String[] FLEET = {
            "ucsim.frigate.phantom",
            "ucsim.frigate.phantom2",
            "ucsim.frigate.observer",
            "ucsim.frigate.small_transporter",
            "ucsim.frigate.miner",
            "ucsim.cruiser.guardian",
            "ucsim.cruiser.constantine",
            "ucsim.cruiser.nightmare",
            "ucsim.cruiser.colonizer",
            "ucsim.battlecruiser.halpas",
            "ucsim.battlecruiser.patriot",
            "ucsim.battlecruiser.invader",
            "ucsim.battlecruiser.kraken",
            "ucsim.battlecruiser.large_transporter",
            "ucsim.battleship.valkyrie",
            "ucsim.battleship.atlas",
            "ucsim.battleship.karma",
            "ucsim.battleship.galactica",
            "ucsim.battleship.gigantes",
            "ucsim.battleship.aegis",
            "ucsim.carrier.divine_star",
            "ucsim.carrier.guillotine",
            "ucsim.carrier.wraith_of_god",
            "ucsim.titan.armageddon",
            "ucsim.titan.harlock",
            "ucsim.titan.odyssey",
            "ucsim.defence.laser_turret",
            "ucsim.defence.photon_turret",
            "ucsim.defence.ion_turret",
            "ucsim.defence.plasma_turret",
            "ucsim.defence.neutron_turret",
            "ucsim.defence.antimatter_turret"
    }, RESEARCH = {
            "ucsim.research.laser_cannon",
            "ucsim.research.sm_missile",
            "ucsim.research.cruise_missile",
            "ucsim.research.photon_cannon",
            "ucsim.research.ion_cannon",
            "ucsim.research.plasma_cannon",
            "ucsim.research.neutron_cannon",
            "ucsim.research.antimatter_cannon",
            "ucsim.research.light_armor",
            "ucsim.research.heavy_armor",
            "ucsim.research.shield"
    };

    public FleetForm() {
        fields.put("ucsim.frigate.phantom", phantom);
        fields.put("ucsim.frigate.phantom2", phantom2);
        fields.put("ucsim.frigate.observer", observer);
        fields.put("ucsim.frigate.small_transporter", smallTransporter);
        fields.put("ucsim.frigate.miner", miner);
        fields.put("ucsim.cruiser.guardian", guardian);
        fields.put("ucsim.cruiser.constantine", constantine);
        fields.put("ucsim.cruiser.nightmare", nightmare);
        fields.put("ucsim.cruiser.colonizer", colonizer);
        fields.put("ucsim.battlecruiser.halpas", halpas);
        fields.put("ucsim.battlecruiser.patriot", patriot);
        fields.put("ucsim.battlecruiser.invader", invader);
        fields.put("ucsim.battlecruiser.kraken", kraken);
        fields.put("ucsim.battlecruiser.large_transporter", largeTransporter);
        fields.put("ucsim.battleship.valkyrie", valkyrie);
        fields.put("ucsim.battleship.atlas", atlas);
        fields.put("ucsim.battleship.karma", karma);
        fields.put("ucsim.battleship.galactica", galactica);
        fields.put("ucsim.battleship.gigantes", gigantes);
        fields.put("ucsim.battleship.aegis", aegis);
        fields.put("ucsim.carrier.divine_star", divineStar);
        fields.put("ucsim.carrier.guillotine", guillotine);
        fields.put("ucsim.carrier.wraith_of_god", wraithOfGod);
        fields.put("ucsim.titan.armageddon", armageddon);
        fields.put("ucsim.titan.harlock", harlock);
        fields.put("ucsim.titan.odyssey", odyssey);
        fields.put("ucsim.defence.laser_turret", laserTurret);
        fields.put("ucsim.defence.photon_turret", photonTurret);
        fields.put("ucsim.defence.ion_turret", ionTurret);
        fields.put("ucsim.defence.plasma_turret", plasmaTurret);
        fields.put("ucsim.defence.neutron_turret", neutronTurret);
        fields.put("ucsim.defence.antimatter_turret", antimatterTurret);
        fields.put("ucsim.research.laser_cannon", laserCannon);
        fields.put("ucsim.research.sm_missile", smMissile);
        fields.put("ucsim.research.cruise_missile", cruiseMissile);
        fields.put("ucsim.research.photon_cannon", photonCannon);
        fields.put("ucsim.research.ion_cannon", ionCannon);
        fields.put("ucsim.research.plasma_cannon", plasmaCannon);
        fields.put("ucsim.research.neutron_cannon", neutronCannon);
        fields.put("ucsim.research.antimatter_cannon", antimatterCannon);
        fields.put("ucsim.research.light_armor", lightArmor);
        fields.put("ucsim.research.heavy_armor", heavyArmor);
        fields.put("ucsim.research.shield", shield);

        FocusListener resetter = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                try {
                    JTextComponent field = (JTextComponent) e.getSource();
                    field.selectAll();
                } catch(ClassCastException e1) {
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        };

        for(JTextField field : fields.values()) field.addFocusListener(resetter);
        attackBonus.addFocusListener(resetter);
        armorBonus.addFocusListener(resetter);
        shieldBonus.addFocusListener(resetter);
        bulk.addFocusListener(resetter);

        bulkload.addActionListener(this);
    }

    public void setParameters(FleetInfo info) {
        for(JTextField field : fields.values()) {
            field.setText("");
        }
        attackBonus.setText("");
        armorBonus.setText("");
        shieldBonus.setText("");
        for(Map.Entry<String, Integer> entry : info.getFleet().entrySet()) {
            String longKey = dictionary.get(entry.getKey());
            fields.get(longKey).setText(entry.getValue().toString());
        }
        for(Map.Entry<String, Integer> entry : info.getResearch().entrySet()) {
            String longKey = dictionary.get(entry.getKey());
            fields.get(longKey).setText(entry.getValue().toString());
        }
        if(info.getFactor().get("attack") != null)
            attackBonus.setText(info.getFactor().get("attack").toString());
        if(info.getFactor().get("armor") != null)
            armorBonus.setText(info.getFactor().get("armor").toString());
        if(info.getFactor().get("shield") != null)
            shieldBonus.setText(info.getFactor().get("shield").toString());
    }

    public FleetInfo getParameters() {
        ResourceBundle bundle = ResourceBundle.getBundle("kr.gwangyi.ucraftsim.labels");
        FleetInfo res = new FleetInfo();
        for(String key : FLEET) {
            try {
                if(!fields.get(key).getText().equals("")) {
                    String shortKey = key.substring(key.lastIndexOf('.') + 1);
                    res.getFleet().put(shortKey, Integer.parseInt(fields.get(key).getText()));
                }
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(fleetPane, String.format(bundle.getString("ucsim.error.mustbeinteger"), bundle.getString(key)));
                return null;
            }
        }
        for(String key : RESEARCH) {
            try {
                if(!fields.get(key).getText().equals("")) {
                    String shortKey = key.substring(key.lastIndexOf('.') + 1);
                    res.getResearch().put(shortKey, Integer.parseInt(fields.get(key).getText()));
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(fleetPane, String.format(bundle.getString("ucsim.error.mustbeinteger"), bundle.getString(key)));
                return null;
            }
        }
        try {
            if(!attackBonus.getText().equals("")) res.getFactor().put("attack", Float.parseFloat(attackBonus.getText()));
            if(!armorBonus.getText().equals("")) res.getFactor().put("armor", Float.parseFloat(armorBonus.getText()));
            if(!shieldBonus.getText().equals("")) res.getFactor().put("shield", Float.parseFloat(shieldBonus.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(fleetPane, String.format(bundle.getString("ucsim.error.mustbefloat"), bundle.getString("ucsim.factor")));
            return null;
        }
        return res;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ResourceBundle bundle = ResourceBundle.getBundle("kr.gwangyi.ucraftsim.labels");
        HashMap<String, String> inverseMap = new HashMap<String, String>();
        for(String key : bundle.keySet()) {
            if(fields.keySet().contains(key))
                inverseMap.put(bundle.getString(key), key);
        }
        for(JTextField field : fields.values()) {
            field.setText("");
        }
        StringTokenizer tokenizer = new StringTokenizer(bulk.getText(), "[\t\n]");
        String word = "";
        String category = "";
        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if(token.matches("^[\\d,]+$")) {
                if(!(category.equals("관제 센터") || category.equals("Control Center") || category.equals("管制センター"))) {
                    String key = inverseMap.get(word.toString());
                    if(key != null) {
                        fields.get(key).setText(token.replace(",", ""));
                    }
                }
                word = "";
            } else if(!word.equals("")) {
                category = word;
                word = token;
            } else {
                word = token;
            }
        }
        bulk.setText("");
    }
}
