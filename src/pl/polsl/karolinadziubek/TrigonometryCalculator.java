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

public class TrigonometryCalculator extends JFrame implements ActionListener
{
    JPanel[] rows = new JPanel[9];
    Map<CalculatorButton, JButton> buttons = new HashMap<>();
    Map<JButton, ButtonDescription> buttonsDescriptions = new HashMap<>();

    Dimension displayDimension = new Dimension(ComponentSizes.XXL, ComponentSizes.XS);
    Dimension regularButtonDimension = new Dimension(ComponentSizes.M, ComponentSizes.S);
    Dimension inputDimension = new Dimension(ComponentSizes.L, ComponentSizes.XS);

    JTextPane display = new JTextPane();
    Font font = new Font("Segoe UI", Font.PLAIN, 14);
    GraphWindow graphWindow;

    JFormattedTextField fieldMinX;
    JFormattedTextField fieldMaxX;
    JFormattedTextField fieldMinY;
    JFormattedTextField fieldMaxY;
    TrigonometryCalculator()
    {
        super("Trigonometry Calculator");
        setDesign();
        setSize(330, 400);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout grid = new GridLayout(rows.length,4);
        setLayout(grid);
        FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
        FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,1,1);
        initializeButtonLabels();
        initializeButtonValues();
        for(int i = 0; i < rows.length; i++)
            rows[i] = new JPanel();

        rows[0].setLayout(f1);
        
        for(int i = 1; i < rows.length; i++)
            rows[i].setLayout(f2);
        initializeButtons();
        setupLayout();
        this.graphWindow = new GraphWindow();
    }

    private void initializeButtonLabels(){
        ButtonLabels.put(CalculatorButton.SEVEN, "7");
        ButtonLabels.put(CalculatorButton.EIGHT, "8");
        ButtonLabels.put(CalculatorButton.NINE, "9");
        ButtonLabels.put(CalculatorButton.CLEAR, "AC");
        ButtonLabels.put(CalculatorButton.FOUR, "4");
        ButtonLabels.put(CalculatorButton.FIVE, "5");
        ButtonLabels.put(CalculatorButton.SIX, "6");
        ButtonLabels.put(CalculatorButton.DELETE, "DEL");
        ButtonLabels.put(CalculatorButton.ONE, "1");
        ButtonLabels.put(CalculatorButton.TWO, "2");
        ButtonLabels.put(CalculatorButton.THREE, "3");
        ButtonLabels.put(CalculatorButton.NEGATION, "-/+");
        ButtonLabels.put(CalculatorButton.ZERO, "0");
        ButtonLabels.put(CalculatorButton.DECIMAL_POINT, ".");
        ButtonLabels.put(CalculatorButton.SINUS, "Sin");
        ButtonLabels.put(CalculatorButton.COSINUS, "Cos");
        ButtonLabels.put(CalculatorButton.TANGENS, "Tan");
        ButtonLabels.put(CalculatorButton.COTANGENS, "Cot");
        ButtonLabels.put(CalculatorButton.OPEN_BRACKET, "(");
        ButtonLabels.put(CalculatorButton.CLOSE_BRACKET, ")");
        ButtonLabels.put(CalculatorButton.MULTIPLICATION, "*");
        ButtonLabels.put(CalculatorButton.DIVISION, "/");
        ButtonLabels.put(CalculatorButton.ADDITION, "+");
        ButtonLabels.put(CalculatorButton.SUBTRACTION, "-");
        ButtonLabels.put(CalculatorButton.PI, "π");
        ButtonLabels.put(CalculatorButton.POWER, "^");
        ButtonLabels.put(CalculatorButton.PARAMETER, "x");
        ButtonLabels.put(CalculatorButton.EQUALITY, "=");
    }
    private void initializeButtonValues(){
        ButtonValues.put(CalculatorButton.ZERO, "0");
        ButtonValues.put(CalculatorButton.ONE, "1");
        ButtonValues.put(CalculatorButton.TWO, "2");
        ButtonValues.put(CalculatorButton.THREE, "3");
        ButtonValues.put(CalculatorButton.FOUR, "4");
        ButtonValues.put(CalculatorButton.FIVE, "5");
        ButtonValues.put(CalculatorButton.SIX, "6");
        ButtonValues.put(CalculatorButton.SEVEN, "7");
        ButtonValues.put(CalculatorButton.EIGHT, "8");
        ButtonValues.put(CalculatorButton.NINE, "9");
        ButtonValues.put(CalculatorButton.NEGATION, "-(");
        ButtonValues.put(CalculatorButton.DECIMAL_POINT, ".");
        ButtonValues.put(CalculatorButton.SINUS, "sin(");
        ButtonValues.put(CalculatorButton.COSINUS, "cos(");
        ButtonValues.put(CalculatorButton.TANGENS, "tan(");
        ButtonValues.put(CalculatorButton.COTANGENS, "cot(");
        ButtonValues.put(CalculatorButton.OPEN_BRACKET, "(");
        ButtonValues.put(CalculatorButton.CLOSE_BRACKET, ")");
        ButtonValues.put(CalculatorButton.MULTIPLICATION, "*");
        ButtonValues.put(CalculatorButton.DIVISION, "/");
        ButtonValues.put(CalculatorButton.ADDITION, "+");
        ButtonValues.put(CalculatorButton.SUBTRACTION, "-");
        ButtonValues.put(CalculatorButton.PI, "π");
        ButtonValues.put(CalculatorButton.POWER, "^");
        ButtonValues.put(CalculatorButton.PARAMETER, "x");
    }
    private void initializeButtons(){
        ButtonLabels.forEach((k, v) -> {
            JButton button = new JButton();
            button.setText(v);
            button.setFont(font);
            button.addActionListener(this);
            button.setPreferredSize(regularButtonDimension);
            buttons.put(k, button);
            String label = ButtonLabels.get(k);
            String value = ButtonValues.get(k);
            buttonsDescriptions.put(button, new ButtonDescription(k, label, value));
        });
    }
    private void setupLayout(){
        display.setFont(font);
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

        rows[6].add(buttons.get(CalculatorButton.SINUS));
        rows[6].add(buttons.get(CalculatorButton.COSINUS));
        rows[6].add(buttons.get(CalculatorButton.TANGENS));
        rows[6].add(buttons.get(CalculatorButton.COTANGENS));
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
    public void clear() 
    {
        display.setText("");
    }

    public void deleteLastDigit() {
        try
        {
            String currentValue = display.getText();
            if ((currentValue != null) && (currentValue.length() > 0))
                currentValue = currentValue.substring(0, currentValue.length() - 1);
            display.setText(currentValue);
        }
        catch(NumberFormatException e)
        {
            display.setText("Deleting last digit failed");
        }
    }
    {
        display.setText("");
    }
    
    public final void setDesign() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) { }
    }
    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        JButton source = (JButton) ae.getSource();
        String value = buttonsDescriptions.get(source).value;
        if(value != null && !value.isEmpty()){
            append(value);
        }
        else if(source == buttons.get(CalculatorButton.DELETE))
            deleteLastDigit();
        else if(source == buttons.get(CalculatorButton.CLEAR))
            clear();
        else if(source == buttons.get(CalculatorButton.EQUALITY)){
            try{
                String functionLiteral = display.getText();
                Function<Double, Double> function = MathematicalExpressionParser.eval(functionLiteral);
                graphWindow.graph.setGraphBoundaries(
                        (double)fieldMinX.getValue(),
                        (double)fieldMaxX.getValue(),
                        (double)fieldMinY.getValue(),
                        (double)fieldMaxY.getValue());
                graphWindow.graph.setFunction(function, functionLiteral);
            }catch (Exception e){
                int a = 12;
            }
        }
    }

    public void append(String suffix){
        display.setText(display.getText().concat(suffix));
    }

    public static class ComponentSizes {
        public static final int XS = 35;
        public static final int S = 40;
        public static final int M = 60;
        public static final int L = 90;
        public static final int XL = 100;
        public static final int XXL = 300;
    }

    public static Map<CalculatorButton, String> ButtonLabels  = new HashMap<>();
    public static Map<CalculatorButton, String> ButtonValues  = new HashMap<>();

}
enum CalculatorButton{
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    CLEAR,
    DELETE,
    NEGATION,
    DECIMAL_POINT,
    SINUS,
    COSINUS,
    TANGENS,
    COTANGENS,
    OPEN_BRACKET,
    CLOSE_BRACKET,
    DIVISION,
    MULTIPLICATION,
    ADDITION,
    SUBTRACTION,
    PI,
    POWER,
    PARAMETER,
    EQUALITY
}

class ButtonDescription {
    public ButtonDescription(CalculatorButton calculatorButton, String label, String value){
        this.calculatorButton = calculatorButton;
        this.label = label;
        this.value = value;
    }
    CalculatorButton calculatorButton;
    String label;
    String value;
}