package ca.qc.bdeb.info203.cours;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DrawingPixel extends JButton {
    private final Color DEFAULT_COLOR = Color.WHITE;

    public DrawingPixel() {
        this.setPreferredSize(new Dimension(20, 20));
        this.setBorderPainted(false);
        this.setBackground(DEFAULT_COLOR);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeColor();
            }
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeColor();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                ((DrawingPanel) DrawingPixel.this.getParent()).setMousePressed(true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ((DrawingPanel) DrawingPixel.this.getParent()).setMousePressed(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (((DrawingPanel) DrawingPixel.this.getParent()).isMousePressed()) {
                    changeColor();
                }
                MainWindow mainWindow = (MainWindow) SwingUtilities.getWindowAncestor(DrawingPixel.this);
                mainWindow.updateColorLabel(DrawingPixel.this.getBackground());
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }


    /**
     * Reset the DrawingPixel's color to the default one.
     */
    public void resetToDefault() {
        this.setBackground(DEFAULT_COLOR);
    }

    /**
     * Change the color of the pixel to the selected one.
     */
    public void changeColor() {
        MainWindow mainWindow = (MainWindow) SwingUtilities.getWindowAncestor(this);
        this.setBackground(mainWindow.getSelectedColor());
    }
}
