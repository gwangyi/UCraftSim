package kr.gwangyi.ucraftsim;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

/**
 * Created by gwangyi on 2015-07-02.
 */
public class SemiResultViewer extends JScrollPane {
    private JLabel label;
    private Image img = null;

    public SemiResultViewer(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
        initialize();
    }

    public SemiResultViewer() {
        super();
        initialize();
    }

    private void initialize() {
        setPreferredSize(new Dimension(300, -1));
        //setBackground(new Color(0, 0, 0, 0));
        getViewport().setBackground(new Color(0,0,0,0));
        try {
            img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("kr/gwangyi/ucraftsim/semi.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        label = new JLabel();
        label.setVerticalAlignment(JLabel.TOP);
        getViewport().setView(label);
    }

    public void setText(String text) {
        label.setText(text);
        repaint();
    }

    @Override
    protected void paintChildren(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        if(img != null)
            g.drawImage(img, this.getWidth() - img.getWidth(null), this.getHeight() - img.getHeight(null), null);
        super.paintChildren(g);
    }
}
