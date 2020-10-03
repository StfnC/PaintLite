package ca.qc.bdeb.info203.cours;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainWindow extends JFrame {
    private final int DEFAULT_FRAME_WIDTH = 800;
    private final int DEFAULT_FRAME_HEIGHT = 600;
    private final int DRAWING_WIDTH = 32;
    private final int DRAWING_HEIGHT = 32;
    private final String SAVED_IMAGE_EXTENSION = "plsc";
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem openImageMenuItem = new JMenuItem("Open...");
    private JMenuItem saveImageMenuItem = new JMenuItem("Save");
    private JMenu canvasMenu = new JMenu("Canvas");
    private JMenuItem clearCanvasMenuItem = new JMenuItem("Clear canvas");
    private JMenuItem fillCanvasMenuItem = new JMenuItem("Fill canvas with selected color");
    private JFileChooser fileChooser = new JFileChooser();
    private FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Custom file extenstion (.plsc)", SAVED_IMAGE_EXTENSION);
    private JPanel mainPanel = new JPanel(new GridLayout(1, 2));
    private DrawingPanel drawingPanel = new DrawingPanel(DRAWING_WIDTH, DRAWING_HEIGHT);
    private JPanel leftPanel = new JPanel(new BorderLayout());
    private JPanel pixelInfoPanel = new JPanel();
    private ColorLabel colorLabel = new ColorLabel(Color.WHITE);
    private JColorChooser colorChooser = new JColorChooser();
    private Color selectedColor = Color.BLACK;

    public MainWindow() {
        fileChooser.setFileFilter(extensionFilter);

        openImageMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userResponse = fileChooser.showOpenDialog(MainWindow.this);

                if (userResponse == JFileChooser.APPROVE_OPTION) {
                    File fileToOpen = fileChooser.getSelectedFile();
                    openImage(fileToOpen);
                }
            }
        });
        fileMenu.add(openImageMenuItem);
        saveImageMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userResponse = fileChooser.showSaveDialog(MainWindow.this);
                if (userResponse == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    saveImage(fileToSave);
                }
            }
        });
        fileMenu.add(saveImageMenuItem);
        menuBar.add(fileMenu);

        clearCanvasMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.resetCanvas();
            }
        });
        canvasMenu.add(clearCanvasMenuItem);
        fillCanvasMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.fillCanvas();
            }
        });
        canvasMenu.add(fillCanvasMenuItem);
        menuBar.add(canvasMenu);

        pixelInfoPanel.add(colorLabel);
        // TODO: Add pixel position label
        leftPanel.add(pixelInfoPanel, BorderLayout.SOUTH);
        leftPanel.add(drawingPanel, BorderLayout.CENTER);
        mainPanel.add(leftPanel);

        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                selectedColor = colorChooser.getColor();
            }
        });
        mainPanel.add(colorChooser);

        this.setJMenuBar(menuBar);
        this.setContentPane(mainPanel);
        this.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("PaintLite");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void openImage(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Clear all the pixels
            drawingPanel.resetCanvas();

            // FIXME: This is prone to errors if there are more or less colors than pixels
            for (Component component : drawingPanel.getComponents()) {
                component.setBackground((Color) ois.readObject());
            }
            drawingPanel.repaint();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Opened: " + file.getName());
    }

    public void saveImage(File file) {
        if (!file.getName().endsWith(SAVED_IMAGE_EXTENSION)) {
            file = new File(file.getPath() + "." + SAVED_IMAGE_EXTENSION);
        }
        // TODO: Verify that only the colors of DrawingPixel are being serialized
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            for (Component pixel : drawingPanel.getComponents()) {
                oos.writeObject(pixel.getBackground());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Saved: " + file.getName());
    }

    public Color getSelectedColor() {
        return this.selectedColor;
    }

    public void updateColorLabel(Color color) {
        this.colorLabel.changeColorValues(color);
    }
}
