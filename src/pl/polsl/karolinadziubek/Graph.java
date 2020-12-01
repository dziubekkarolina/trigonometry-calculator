package pl.polsl.karolinadziubek;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JPanel;

public class Graph extends JPanel {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    private BufferedImage bufferedImage;
    private Graphics2D graphics2D;

    private Function<Double, Double> function;

    private double windowX, windowY, windowWidth, windowHeight;

    private String textBox;

    public Graph() {
        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics2D = bufferedImage.createGraphics();

        windowX = 0.0;
        windowY = 0.0;
        windowHeight = 2.0;
        windowWidth = 2.0;

        textBox = "";
    }

    public void setFunction(Function<Double, Double> function, String functionValue){
        this.function = function;
        this.textBox = functionValue;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, WIDTH, HEIGHT);

        List<Double> calculatedValuesX = new ArrayList<>();
        List<Double> calculatedValueY = new ArrayList<>();

        for (int x = 0; x < WIDTH; x++) {
            double currentX = toRealX(x);

            double currentY = 0.0;
            if (function != null)
                currentY = function.apply(currentX);

            double scaledX = x;
            double scaledY = toScreenY(currentY);

            calculatedValuesX.add(scaledX);
            calculatedValueY.add(scaledY);
        }

        int[] displayedValuesX = new int[calculatedValuesX.size()];
        int[] displayedValuesY = new int[calculatedValueY.size()];
        for (int i = 0; i < displayedValuesX.length; i++) {
            displayedValuesX[i] = calculatedValuesX.get(i).intValue();
        }
        for (int i = 0; i < displayedValuesY.length; i++) {
            displayedValuesY[i] = calculatedValueY.get(i).intValue();
        }

        graphics2D.setColor(Color.BLACK);
        int xAxisY = toScreenY(0.0);
        graphics2D.drawLine(0, xAxisY, WIDTH, xAxisY);
        int yAxisX = toScreenX(0.0);
        graphics2D.drawLine(yAxisX, 0, yAxisX, HEIGHT);

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Color.BLUE);
        graphics2D.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        graphics2D.drawPolyline(displayedValuesX, displayedValuesY, displayedValuesX.length);

        graphics2D.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString("f(x) = " + textBox, 0.0f, HEIGHT - 10.0f);

        graphics2D.drawString("x", 0, xAxisY - 10);
        graphics2D.drawString("y", yAxisX + 10, graphics2D.getFontMetrics().getHeight() - 20);

        g.drawImage(bufferedImage, 0, 0, null);
    }

    public void setGraphBoundaries(double xMin, double xMax, double yMin, double yMax){
        if(xMax <= xMin || yMax <= yMin)
            throw  new IllegalArgumentException();
        windowWidth = xMax - xMin;
        windowHeight = yMax - yMin;
        windowX = toWindowX(xMin);
        windowY = toWindowY(yMin);
    }

    private double bottom() {
        return windowY - halfWindowHeight();
    }

    private double right() {
        return windowX - halfWindowWidth();
    }

    private double toRealX(int screenX) {
        return screenX / (double)WIDTH * windowWidth + right();
    }

    private double toRealY(int screenY) {
        return (HEIGHT - screenY) / (double)HEIGHT * windowHeight + bottom();
    }

    private int toScreenX(double realX) {
        return (int) ((realX - right()) / windowWidth * WIDTH);
    }

    private int toScreenY(double realY) {
        return HEIGHT - (int) ((realY - bottom()) / windowHeight * HEIGHT);
    }
    private double toWindowX(double realX) {
        return realX + windowWidth/2.0;
    }
    private double toWindowY(double realY) {
        return realY + windowHeight / 2.0;
    }

    private double halfWindowWidth() {
        return windowWidth / 2.0;
    }

    private double halfWindowHeight() {
        return windowHeight / 2.0;
    }
}