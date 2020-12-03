package pl.polsl.karolinadziubek;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static pl.polsl.karolinadziubek.ButtonProperties.ButtonLabels;
import static pl.polsl.karolinadziubek.ButtonProperties.ButtonValues;

/**
 * Represents frame containing trigonometry calculator with separate frame used to display graph
 */
public class CalculatorFrame extends JFrame implements ActionListener
{
    /**
     * Array of containers used for arranging UI in grid layout
     */
    private JPanel[] rows = new JPanel[9];

    /**
     * Map of button elements assigned to button type
     */
    private Map<CalculatorButton, JButton> buttons = new HashMap<>();

    /**
     * Map of button descriptions assigned to button elements
     */
    private Map<JButton, ButtonDescription> buttonsDescriptions = new HashMap<>();

    /**
     * Dimensions of calculator's display
     */
    private Dimension displayDimension = new Dimension(ComponentSizes.XXL, ComponentSizes.XS);

    /**
     * Dimensions of button elements
     */
    private Dimension buttonDimension = new Dimension(ComponentSizes.M, ComponentSizes.S);

    /**
     * dimensions of input elements
     */
    private Dimension inputDimension = new Dimension(ComponentSizes.L, ComponentSizes.XS);

    /**
     * Element used to display mathematical expression provided by useer
     */
    private JTextPane display = new JTextPane();

    /**
     * Frame element responsible for rendering Graph
     */
    private GraphFrame graphFrame;

    /**
     * input field for minimal value displayed on horizontal axis
     */
    private JFormattedTextField fieldMinX;

    /**
     * input field for maximal value displayed on horizontal axis
     */
    private JFormattedTextField fieldMaxX;

    /**
     * input field for minimal value displayed on vertical axis
     */
    private JFormattedTextField fieldMinY;

    /**
     * input field for maximal value displayed on vertical axis
     */
    private JFormattedTextField fieldMaxY;

    /**
     * Default parameterless constructor
     */
    CalculatorFrame()
    {
        super("Trigonometry Calculator");
        setDesign();
        setSize(330, 400);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout grid = new GridLayout(rows.length,5);
        setLayout(grid);
        FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
        FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,1,1);
        for(int i = 0; i < rows.length; i++)
            rows[i] = new JPanel();

        rows[0].setLayout(f1);
        
        for(int i = 1; i < rows.length; i++)
            rows[i].setLayout(f2);
        initializeButtons();
        setupLayout();
        this.graphFrame = new GraphFrame();
    }

    /**
     * Called after action is performed. Perform specific action depending on button that was pressed
     */
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        JButton source = (JButton) ae.getSource();
        String value = buttonsDescriptions.get(source).value;
        if(value != null && !value.isEmpty()){
            append(value);
        }
        else if(source == buttons.get(CalculatorButton.DELETE))
            deleteLastCharacter();
        else if(source == buttons.get(CalculatorButton.CLEAR))
            clear();
        else if(source == buttons.get(CalculatorButton.EQUALITY)){
            try{
                String functionLiteral = display.getText();
                Function<Double, Double> function = MathematicalExpressionParser.parseMathematicalExpression(functionLiteral);
                graphFrame.graph.setGraphBoundaries(
                        (double)fieldMinX.getValue(),
                        (double)fieldMaxX.getValue(),
                        (double)fieldMinY.getValue(),
                        (double)fieldMaxY.getValue());
                graphFrame.graph.setFunction(function, functionLiteral);
            }catch (Exception e){ }
        }
    }

    /**
     * Initializes buttons used in program
     */
    private void initializeButtons(){
        ButtonLabels.forEach((k, v) -> {
            JButton button = new JButton();
            button.setText(v);
            button.addActionListener(this);
            button.setPreferredSize(buttonDimension);
            buttons.put(k, button);
            String value = ButtonValues.get(k);
            buttonsDescriptions.put(button, new ButtonDescription(k, v, value));
        });
    }

    /**
     * Sets up layout of window
     */
    private void setupLayout(){
        display.setOpaque(true);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setPreferredSize(displayDimension);
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setAlignment(attr, StyleConstants.ALIGN_RIGHT);
        display.setParagraphAttributes(attr, true);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setMaximum(Double.MAX_VALUE);

        fieldMinX = new JFormattedTextField(formatter);
        fieldMaxX = new JFormattedTextField(formatter);
        fieldMinY = new JFormattedTextField(formatter);
        fieldMaxY = new JFormattedTextField(formatter);

        fieldMinX.setPreferredSize(inputDimension);
        fieldMaxX.setPreferredSize(inputDimension);
        fieldMinY.setPreferredSize(inputDimension);
        fieldMaxY.setPreferredSize(inputDimension);

        fieldMinX.setValue(-10.0);
        fieldMaxX.setValue(10.0);
        fieldMinY.setValue(-10.0);
        fieldMaxY.setValue(10.0);

        rows[0].add(display);
        add(rows[0]);

        rows[1].add(buttons.get(CalculatorButton.SEVEN));
        rows[1].add(buttons.get(CalculatorButton.EIGHT));
        rows[1].add(buttons.get(CalculatorButton.NINE));
        rows[1].add(buttons.get(CalculatorButton.ADDITION));
        rows[1].add(buttons.get(CalculatorButton.DELETE));
        add(rows[1]);

        rows[2].add(buttons.get(CalculatorButton.FOUR));
        rows[2].add(buttons.get(CalculatorButton.FIVE));
        rows[2].add(buttons.get(CalculatorButton.SIX));
        rows[2].add(buttons.get(CalculatorButton.SUBTRACTION));
        rows[2].add(buttons.get(CalculatorButton.CLEAR));
        add(rows[2]);

        rows[3].add(buttons.get(CalculatorButton.ONE));
        rows[3].add(buttons.get(CalculatorButton.TWO));
        rows[3].add(buttons.get(CalculatorButton.THREE));
        rows[3].add(buttons.get(CalculatorButton.MULTIPLICATION));
        rows[3].add(buttons.get(CalculatorButton.NEGATION));
        add(rows[3]);

        rows[4].add(buttons.get(CalculatorButton.ZERO));
        rows[4].add(buttons.get(CalculatorButton.OPEN_BRACKET));
        rows[4].add(buttons.get(CalculatorButton.CLOSE_BRACKET));
        rows[4].add(buttons.get(CalculatorButton.DIVISION));
        rows[4].add(buttons.get(CalculatorButton.DECIMAL_POINT));
        add(rows[4]);

        rows[5].add(buttons.get(CalculatorButton.POWER));
        rows[5].add(buttons.get(CalculatorButton.PARAMETER));
        rows[5].add(buttons.get(CalculatorButton.PI));
        rows[5].add(buttons.get(CalculatorButton.EQUALITY));
        add(rows[5]);

        rows[6].add(buttons.get(CalculatorButton.SINE));
        rows[6].add(buttons.get(CalculatorButton.COSINE));
        rows[6].add(buttons.get(CalculatorButton.TANGENT));
        rows[6].add(buttons.get(CalculatorButton.COTANGENT));
        add(rows[6]);

        rows[7].add(new JLabel("Min X:"));
        rows[7].add(fieldMinX);
        rows[7].add(new JLabel("Max X:"));
        rows[7].add(fieldMaxX);
        add(rows[7]);

        rows[8].add(new JLabel("Min Y:"));
        rows[8].add(fieldMinY);
        rows[8].add(new JLabel("Max Y:"));
        rows[8].add(fieldMaxY);
        add(rows[8]);

        setVisible(true);
    }

    /**
     * clears display
     */
    private void clear()
    {
        display.setText("");
    }

    /**
     * removes last character from display
     */
    private void deleteLastCharacter() {
        String currentValue = display.getText();
        if ((currentValue != null) && (currentValue.length() > 0))
            currentValue = currentValue.substring(0, currentValue.length() - 1);
        display.setText(currentValue);
    }

    /**
     * Appends given suffix to already displayed value
     * @param suffix suffix to append
     */
    private void append(String suffix){
        display.setText(display.getText().concat(suffix));
    }

    /**
     * sets design of application
     */
    private void setDesign() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) { }
    }
}