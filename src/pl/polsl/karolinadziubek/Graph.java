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


/**
 * Represents Panel in which graph is displayed
 */
public class Graph extends JPanel {
    /**
     * Width of displaying area
     */
    public static final int WIDTH = 1024;

    /**
     * Height of displaying area
     */
    public static final int HEIGHT = 768;

    /**
     * Image buffer
     */
    private BufferedImage bufferedImage;

    /**
     * Graphics context
     */
    private Graphics2D graphics2D;

    /**
     * Function represented on graph
     */
    private Function<Double, Double> function;

    /**
     * helper variables used for determining scopes of displayed axes
     */
    private double windowX, windowY, windowWidth, windowHeight;

    /**
     * Text representation of displayed function
     */
    private String functionTextValue;

    /**
     * Default parameterless constructor
     */
    public Graph() {
        //set size:
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        //initialize graphics:
        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics2D = bufferedImage.createGraphics();

        //initialize properties:
        windowX = 0.0;
        windowY = 0.0;
        windowHeight = 2.0;
        windowWidth = 2.0;
        functionTextValue = "";
    }

    /**
     * Sets function used in process of plotting graph
     * @param function Function used for plotting graph
     * @param functionTextValue Text representation of function
     */
    public void setFunction(Function<Double, Double> function, String functionTextValue){
        this.function = function;
        this.functionTextValue = functionTextValue;
        repaint();
    }

    /**
     * Method used in process of rendering UI element.
     * Contains logic responsible for calculation of values used in process of plotting graph.
     * @param g Graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        //call base method:
        super.paintComponent(g);

        //paint background:
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, WIDTH, HEIGHT);

        //initialize lists for calculated values:
        List<Integer> calculatedValuesX = new ArrayList<>();
        List<Integer> calculatedValuesY = new ArrayList<>();

        //iterate over every pixel in width:
        for (int x = 0; x < WIDTH; x++) {
            //get actual value in certain pixel:
            double currentX = toActualX(x);

            //calculate y for current x:
            double currentY = 0.0;
            if (function != null)
                currentY = function.apply(currentX);

            //find coordinates of point on graph:
            int panelY = toPanelY(currentY);

            //add calculated values to list of points to draw:
            calculatedValuesX.add(x);
            calculatedValuesY.add(panelY);
        }

        //map lists to int[] for drawPolyline required argument type:
        int[] displayedValuesX = calculatedValuesX.stream().mapToInt(i->i).toArray();
        int[] displayedValuesY = calculatedValuesY.stream().mapToInt(i->i).toArray();

        //draw axis:
        graphics2D.setColor(Color.BLACK);
        int xAxisY = toPanelY(0.0);
        graphics2D.drawLine(0, xAxisY, WIDTH, xAxisY);
        int yAxisX = toPanelX(0.0);
        graphics2D.drawLine(yAxisX, 0, yAxisX, HEIGHT);

        //draw graph with antialiasing and rounding on:
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Color.BLUE);
        graphics2D.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        graphics2D.drawPolyline(displayedValuesX, displayedValuesY, displayedValuesX.length);

        //draw text representation of function in left lower corner:
        graphics2D.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString("f(x) = " + functionTextValue, 0.0f, HEIGHT - 10.0f);

        //draw names of axis:
        graphics2D.drawString("x", 0, xAxisY - 10);
        graphics2D.drawString("y", yAxisX + 10, graphics2D.getFontMetrics().getHeight() - 20);

        //draw image:
        g.drawImage(bufferedImage, 0, 0, null);
    }

    /**
     * Sets boundaries of plotted graph
     * @param xMin smallest value displayed on horizontal axis
     * @param xMax largest value displayed on horizontal axis
     * @param yMin smallest value displayed on vertical axis
     * @param yMax largest value displayed on vertical axis
     */
    public void setGraphBoundaries(double xMin, double xMax, double yMin, double yMax){
        if(xMax <= xMin || yMax <= yMin)
            throw  new IllegalArgumentException();
        windowWidth = xMax - xMin;
        windowHeight = yMax - yMin;
        windowX = xMin + halfWindowWidth();
        windowY = yMin + halfWindowHeight();
    }

    /**
     * Returns smallest value displayed on vertical axis
     * @return value displayed on vertical axis
     */
    private double bottom() {
        return windowY - halfWindowHeight();
    }

    /**
     * Returns largest value displayed on horizontal axis
     * @return largest value displayed on horizontal axis
     */
    private double right() {
        return windowX - halfWindowWidth();
    }

    /**
     * Maps x value in context of panel to actual value
     * @param panelX x value in context of panel
     * @return actual value of x
     */
    private double toActualX(int panelX) {
        return panelX / (double)WIDTH * windowWidth + right();
    }

    /**
     * Maps y value in context of panel to actual value
     * @param panelY y value in context of panel
     * @return actual value of y
     */
    private double toActualY(int panelY) {
        return (HEIGHT - panelY) / (double)HEIGHT * windowHeight + bottom();
    }

    /**
     * Maps actual x value to context of panel
     * @param actualX actual value of x
     * @return x value in context of panel
     */
    private int toPanelX(double actualX) {
        return (int) ((actualX - right()) / windowWidth * WIDTH);
    }

    /**
     * Maps actual y value to context of panel
     * @param actualY actual value of y
     * @return y value in context of panel
     */
    private int toPanelY(double actualY) {
        return HEIGHT - (int) ((actualY - bottom()) / windowHeight * HEIGHT);
    }

    /**
     * Returns half of horizontal axis scope
     * @return half of horizontal axis scope
     */
    private double halfWindowWidth() {
        return windowWidth / 2.0;
    }

    /**
     * Returns half of vertical axis scope
     * @return half of vertical axis scope
     */
    private double halfWindowHeight() {
        return windowHeight / 2.0;
    }
}