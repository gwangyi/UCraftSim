package kr.gwangyi.ucraftsim;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Created by gwangyi on 2015-07-01.
 */
public class UCraftSimForm extends JFrame implements ActionListener {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("kr.gwangyi.ucraftsim.labels");

    private JPanel rootPane;
    private FleetForm attacker;
    private FleetForm defender;
    private JButton go;
    private SemiResultViewer result;
    private JRadioButton attackerPreset1;
    private JRadioButton attackerPreset2;
    private JRadioButton attackerPreset3;
    private JRadioButton attackerPreset4;
    private JButton attackerPresetSave;
    private JRadioButton defenderPreset1;
    private JRadioButton defenderPreset2;
    private JRadioButton defenderPreset3;
    private JRadioButton defenderPreset4;
    private JButton defenderPresetSave;
    private JButton attackerClear;
    private JButton defenderClear;
    private JLabel battleResult;

    private class PresetHandler implements ActionListener {
        private FleetForm form;
        private String tag;
        private ButtonGroup group = new ButtonGroup();
        private JButton saveBtn = null;

        public PresetHandler(FleetForm form, String tag, JButton saveBtn) {
            this.form = form;
            this.tag = tag;
            this.saveBtn = saveBtn;
            saveBtn.addActionListener(this);
        }

        public void add(JRadioButton btn) {
            btn.setActionCommand(Integer.toString(group.getButtonCount()));
            group.add(btn);
            btn.addActionListener(this);
        }

        private void save(int idx) {
            Preferences pref = Preferences.userNodeForPackage(UCraftSim.class);
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(form.getParameters());
                pref.putByteArray(tag + idx, baos.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void load(int idx) {
            Preferences pref = Preferences.userNodeForPackage(UCraftSim.class);
            byte[] preset = pref.getByteArray(tag + idx, null);
            if (preset != null) {
                try {
                    ByteArrayInputStream bais = new ByteArrayInputStream(preset);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    FleetInfo info = (FleetInfo) ois.readObject();
                    form.setParameters(info);
                } catch (IOException | ClassNotFoundException e1) {
                    form.setParameters(new FleetInfo());
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(group.getSelection() != null) {
                int idx = Integer.parseInt(group.getSelection().getActionCommand());
                if(e.getSource() == saveBtn) {
                    save(idx);
                } else {
                    load(idx);
                }
            }
        }
    }

    public UCraftSimForm() {
        super(ResourceBundle.getBundle("kr.gwangyi.ucraftsim.labels").getString("ucsim.title"));
        setContentPane(rootPane);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        try {
            setIconImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("kr/gwangyi/ucraftsim/semiicon.png")));
        } catch (IOException e) {
        }

        go.addActionListener(this);

        PresetHandler attackerPreset = new PresetHandler(attacker, "attacker", attackerPresetSave);
        attackerPreset.add(attackerPreset1);
        attackerPreset.add(attackerPreset2);
        attackerPreset.add(attackerPreset3);
        attackerPreset.add(attackerPreset4);
        PresetHandler defenderPreset = new PresetHandler(defender, "defender", defenderPresetSave);
        defenderPreset.add(defenderPreset1);
        defenderPreset.add(defenderPreset2);
        defenderPreset.add(defenderPreset3);
        defenderPreset.add(defenderPreset4);

        attackerClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attacker.setParameters(new FleetInfo());
            }
        });
        defenderClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defender.setParameters(new FleetInfo());
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int i;
        FleetInfo ainfo = attacker.getParameters();
        if(ainfo == null) return;
        FleetInfo binfo = defender.getParameters();
        if(binfo == null) return;
        HtmlBattleLogger logger = new HtmlBattleLogger(ainfo, binfo);
        UCraftSim.battleSim.init(ainfo, binfo);
        UCraftSim.battleSim.setLogger(logger);
        for(i = 0; i < 6; i++) {
            if(!UCraftSim.battleSim.round()) break;
        }
        int r = UCraftSim.battleSim.compare();
        if(r == 0) battleResult.setText(String.format("%s (%d)", bundle.getString("ucsim.battle.tie"), i + 1));
        else if(r < 0) battleResult.setText(String.format("%s (%d)", bundle.getString("ucsim.battle.defender"), i + 1));
        else battleResult.setText(String.format("%s (%d)", bundle.getString("ucsim.battle.attacker"), i + 1));
        result.setText(logger.toString());
    }
}
