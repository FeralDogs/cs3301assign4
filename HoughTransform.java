package javaapplication5;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

// Main class
public class HoughTransform extends Frame implements ActionListener {

    BufferedImage input;
    int width, height, diagonal;
    ImageCanvas source, target;
    TextField texRad, texThres;

    // Constructor
    public HoughTransform(String name) {
        super("Hough Transform");
        // load image
        try {
            input = ImageIO.read(new File("C:\\Users\\Adrian\\Documents\\NetBeansProjects\\JavaApplication5\\src\\javaapplication5\\rectangle.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        width = input.getWidth();
        height = input.getHeight();
        diagonal = (int) Math.sqrt(width * width + height * height);
        // prepare the panel for two images.
        Panel main = new Panel();
        source = new ImageCanvas(input);
        target = new ImageCanvas(input);
        main.setLayout(new GridLayout(1, 2, 10, 10));
        main.add(source);
        main.add(target);
        // prepare the panel for buttons.
        Panel controls = new Panel();
        Button button = new Button("Line Transform");
        button.addActionListener(this);
        controls.add(button);
        controls.add(new Label("Radius:"));
        texRad = new TextField("10", 3);
        controls.add(texRad);
        button = new Button("Circle Transform");
        button.addActionListener(this);
        controls.add(button);
        controls.add(new Label("Threshold:"));
        texThres = new TextField("25", 3);
        controls.add(texThres);
        button = new Button("Search");
        button.addActionListener(this);
        controls.add(button);
        // add two panels
        add("Center", main);
        add("South", controls);
        addWindowListener(new ExitListener());
        setSize(diagonal * 2 + 100, Math.max(height, 360) + 100);
        setVisible(true);
    }

    class ExitListener extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    // Action listener
    public void actionPerformed(ActionEvent e) {
        // perform one of the Hough transforms if the button is clicked.
        if (((Button) e.getSource()).getLabel().equals("Line Transform")) {
            int[][] g = new int[360][diagonal];
            // insert your implementation for straight-line here.
            int value = Integer.parseInt(texThres.getText());

            double p;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    Color clr = new Color(source.image.getRGB(i, j));
                    System.out.println(clr);
                    if (clr.getRed() == 255 && clr.getGreen() == 255 && clr.getBlue() == 255) {
                        for (int k = 0; k < 360; k++) {
                            p = j * Math.cos(Math.toRadians(k)) + i * Math.sin(Math.toRadians(k));
                            int p2=(int)Math.abs(p);
                            g[k][p2]++;

                        }
                    }

                }
            }
            
            
            
            for (int i = 0; i < 360; i++) {
                for (int j = 0; j < diagonal; j++) {
                    System.out.println("valie "  + g[i][j]);
                }
            }

            DisplayTransform(diagonal, 360, g);

        } else if (((Button) e.getSource()).getLabel().equals("Circle Transform")) {
            int[][] g = new int[height][width];
            int radius = Integer.parseInt(texRad.getText());
            // insert your implementation for circle here.
            double p;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    for (int k = 0; k < 360; k++) {

                        p = j * Math.cos(Math.toRadians(k)) + i * Math.sin(Math.toRadians(k));

                    }
                }
            }

            DisplayTransform(width, height, g);
        }
    }
        // display the spectrum of the transform.

    public void DisplayTransform(int wid, int hgt, int[][] g) {
        int text = Integer.parseInt(texThres.getText());
        target.resetBuffer(wid, hgt);
        for (int y = 0, i = 0; y < hgt; y++) {
            for (int x = 0; x < wid; x++, i++) {
                int value = g[y][x] > 255 && g[y][x] >= text ? 255 : g[y][x];

                target.image.setRGB(x, y, new Color(value, value, value).getRGB());
            }
        }
        target.repaint();
    }

    public static void main(String[] args) {
        new HoughTransform(args.length == 1 ? args[0] : "rectangle.png");
    }
}
