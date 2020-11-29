package pl.polsl.karolinadziubek;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class TrigonometryCalculator extends JFrame implements ActionListener
{
    
    JPanel[] rows = new JPanel[6];
    Map<CalculatorButton, JButton> buttons = new HashMap<>();


    Dimension displayDimension = new Dimension(ComponentSizes.XXL, ComponentSizes.XS);
    Dimension regularButtonDimension = new Dimension(ComponentSizes.M, ComponentSizes.S);
    Dimension zeroButtonDimension = new Dimension(ComponentSizes.M, ComponentSizes.S);

    JLabel display = new JLabel();
    Font font = new Font("Segoe UI", Font.PLAIN, 14);

    TrigonometryCalculator()
    {
        super("Trigonometry Calculator");
        setDesign();
        setSize(320, 300);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout grid = new GridLayout(rows.length,4);
        setLayout(grid);
        FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
        FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,1,1);
        initializeButtonLabels();
        for(int i = 0; i < rows.length; i++)
            rows[i] = new JPanel();

        rows[0].setLayout(f1);
        
        for(int i = 1; i < rows.length; i++)
            rows[i].setLayout(f2);
        initializeButtons();
        setupLayout();
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
    }
    private void initializeButtons(){
        ButtonLabels.forEach((key, value) -> {
            JButton button = new JButton();
            button.setText(value);
            button.setFont(font);
            button.addActionListener(this);
            button.setPreferredSize(regularButtonDimension);
            buttons.put(key, button);
        });
    }
    private void setupLayout(){
        display.setFont(font);
        display.setOpaque(true);
        display.setBackground(Color.WHITE);
        display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        display.setPreferredSize(displayDimension);

        buttons.get(CalculatorButton.ZERO).setPreferredSize(zeroButtonDimension);

        rows[0].add(display);
        add(rows[0]);

        rows[1].add(buttons.get(CalculatorButton.SEVEN));
        rows[1].add(buttons.get(CalculatorButton.EIGHT));
        rows[1].add(buttons.get(CalculatorButton.NINE));
        rows[1].add(buttons.get(CalculatorButton.DELETE));
        add(rows[1]);

        rows[2].add(buttons.get(CalculatorButton.FOUR));
        rows[2].add(buttons.get(CalculatorButton.FIVE));
        rows[2].add(buttons.get(CalculatorButton.SIX));
        rows[2].add(buttons.get(CalculatorButton.CLEAR));
        add(rows[2]);

        rows[3].add(buttons.get(CalculatorButton.ONE));
        rows[3].add(buttons.get(CalculatorButton.TWO));
        rows[3].add(buttons.get(CalculatorButton.THREE));
        rows[3].add(buttons.get(CalculatorButton.NEGATION));
        add(rows[3]);

        rows[4].add(buttons.get(CalculatorButton.ZERO));
        rows[4].add(buttons.get(CalculatorButton.DECIMAL_POINT));
        add(rows[4]);

        rows[5].add(buttons.get(CalculatorButton.SINUS));
        rows[5].add(buttons.get(CalculatorButton.COSINUS));
        rows[5].add(buttons.get(CalculatorButton.TANGENS));
        rows[5].add(buttons.get(CalculatorButton.COTANGENS));
        add(rows[5]);

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
            if ((currentValue != null) && (currentValue.length() > 0)) {
                currentValue = currentValue.substring(0, currentValue.length() - 1);
            }
            if(!isNumeric(currentValue))
                currentValue = "";
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

    public void getPosNeg() 
    {
        try 
        {
            String currentValue = display.getText();
            double value = Double.parseDouble(currentValue) * -1;
            currentValue = currentValue.contains(".") ? Double.toString(value) : Integer.toString((int)value);
            display.setText(currentValue);
        }
        catch(NumberFormatException e) 
        {
        	display.setText("Negating failed");
        }
    }

    public void addDecimalPoint(){
        try
        {
            String currentValue = display.getText();
            if(currentValue.isEmpty())
                append("0");
            if(currentValue.contains("."))
                return;
            append(".");
        }
        catch(NumberFormatException e)
        {
            display.setText("Putting decimal point failed");
        }
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
        if(!isNumeric(display.getText()))
            display.setText("");
        if(ae.getSource() == buttons.get(CalculatorButton.SEVEN))
            append("7");
        else if(ae.getSource() == buttons.get(CalculatorButton.EIGHT))
            append("8");
        else if(ae.getSource() == buttons.get(CalculatorButton.NINE))
            append("9");
        else if(ae.getSource() == buttons.get(CalculatorButton.DELETE))
            deleteLastDigit();
        else if(ae.getSource() == buttons.get(CalculatorButton.CLEAR))
            clear();
        else if(ae.getSource() == buttons.get(CalculatorButton.FOUR))
            append("4");
        else if(ae.getSource() == buttons.get(CalculatorButton.FIVE))
            append("5");
        else if(ae.getSource() == buttons.get(CalculatorButton.SIX))
            append("6");
        else if(ae.getSource() == buttons.get(CalculatorButton.NEGATION))
            getPosNeg();
        else if(ae.getSource() == buttons.get(CalculatorButton.ONE))
            append("1");
        else if(ae.getSource() == buttons.get(CalculatorButton.TWO))
            append("2");
        else if(ae.getSource() == buttons.get(CalculatorButton.THREE))
            append("3");
        else if(ae.getSource() == buttons.get(CalculatorButton.DECIMAL_POINT))
            addDecimalPoint();
        else if(ae.getSource() == buttons.get(CalculatorButton.ZERO)){
            if(!display.getText().equals("0"))
                append("0");
        }
        else if(ae.getSource() == buttons.get(CalculatorButton.SINUS))
            executeTrigonometricOperation(TrigonometricOperation.SINUS);
        else if(ae.getSource() == buttons.get(CalculatorButton.COSINUS))
            executeTrigonometricOperation(TrigonometricOperation.COSINUS);
        else if(ae.getSource() == buttons.get(CalculatorButton.TANGENS))
            executeTrigonometricOperation(TrigonometricOperation.TANGENS);
        else if(ae.getSource() == buttons.get(CalculatorButton.COTANGENS))
            executeTrigonometricOperation(TrigonometricOperation.COTANGENS);
    }
    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d*)?");

    public static boolean isNumeric(String value) {
        if (value == null)
            return false;
        return pattern.matcher(value).matches();
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
    public enum CalculatorButton{
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
        COTANGENS
    }

    public enum TrigonometricOperation {
        SINUS,
        COSINUS,
        TANGENS,
        COTANGENS
    }

    public void executeTrigonometricOperation(TrigonometricOperation operation){
        try
        {
            double value = Double.parseDouble(display.getText());
            value = calculateTrigonometricFunction(value, operation);
            display.setText(Double.toString(value));
        }
        catch(Exception e)
        {
            display.setText("Enter number");
        }

    }

    public double calculateTrigonometricFunction(double value, TrigonometricOperation operation){
        switch (operation){
            case SINUS:
                return Math.sin(value);
            case COSINUS:
                return Math.cos(value);
            case TANGENS:
                return Math.tan(value);
            case COTANGENS:
                return 1/Math.tan(value);
            default:
                throw new UnsupportedOperationException();
        }
    }
    public static Map<CalculatorButton, String> ButtonLabels  = new HashMap<>();

    public static void main(String[] arguments)
    {
        TrigonometryCalculator c = new TrigonometryCalculator();
    }
}
