package kr.gwangyi.ucraftsim;

import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

import javax.swing.*;

/**
 * Created by gwangyi on 2015-07-01.
 */
public class UCraftSim {
    public static final BattleSim battleSim;
    static {
        ScriptingContainer container = new ScriptingContainer();
        container.runScriptlet(PathType.CLASSPATH, "kr/gwangyi/ucraftsim/rubyscript/ucsim.rb");
        Object bridge = container.runScriptlet(PathType.CLASSPATH, "kr/gwangyi/ucraftsim/rubyscript/java_bridge.rb");
        battleSim = container.getInstance(bridge, BattleSim.class);
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (UnsupportedLookAndFeelException e) {
        }
        UCraftSimForm form = new UCraftSimForm();
    }
}
