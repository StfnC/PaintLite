package ca.qc.bdeb.info203.cours;

import javax.swing.*;
import java.awt.*;

public class ColorLabel extends JLabel {
    public ColorLabel(Color color) {
        super("R: " + color.getRed() + " " + "G: " + color.getGreen() + " " + "B: " + color.getBlue() + " " + "Alpha: " + color.getAlpha());
    }

    public void changeColorValues(Color color) {
        this.setText("R: " + color.getRed() + " " + "G: " + color.getGreen() + " " + "B: " + color.getBlue() + " " + "Alpha: " + color.getAlpha());
        this.repaint();
    }

}
