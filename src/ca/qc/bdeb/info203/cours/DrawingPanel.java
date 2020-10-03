package ca.qc.bdeb.info203.cours;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DrawingPanel extends JPanel {
    private boolean mousePressed;

    public DrawingPanel(int width, int height) {
        super(new GridLayout(width, height));
        this.populateGrid(width, height);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                mousePressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    /**
     * Generate pixels to populate the grid.
     *
     * @param width  Width of the grid
     * @param height Height of the grid
     */
    public void populateGrid(int width, int height) {
        for (int i = 0; i < (width * height); i++) {
            DrawingPixel pixel = new DrawingPixel();
            this.add(pixel);
        }
    }

    /**
     * Reset each DrawingPixel to it's default color.
     */
    public void resetCanvas() {
        for (Component component : this.getComponents()) {
            if (component instanceof DrawingPixel) {
                ((DrawingPixel) component).resetToDefault();
            }
        }
    }

    /**
     * Fill each DrawingPixel with the selected color.
     */
    public void fillCanvas() {
        for (Component component : this.getComponents()) {
            if (component instanceof DrawingPixel) {
                ((DrawingPixel) component).changeColor();
            }
        }
    }

    public boolean isMousePressed() {
        return this.mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
