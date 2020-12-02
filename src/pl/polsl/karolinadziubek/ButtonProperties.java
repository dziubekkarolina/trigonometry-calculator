package pl.polsl.karolinadziubek;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains maps of button properties prescribed to button type
 */
public class ButtonProperties {
    public static Map<CalculatorButton, String> ButtonLabels;
    public static Map<CalculatorButton, String> ButtonValues;
    static {
        ButtonLabels = Collections.unmodifiableMap(initializeButtonLabels());
        ButtonValues = Collections.unmodifiableMap(initializeButtonValues());
    }

    /**
     * Initializes map containing labels of buttons
     * @return initialized map of button labels
     */
    private static Map<CalculatorButton, String> initializeButtonLabels(){
        Map<CalculatorButton, String> labels  = new HashMap<>();
        labels.put(CalculatorButton.SEVEN, "7");
        labels.put(CalculatorButton.EIGHT, "8");
        labels.put(CalculatorButton.NINE, "9");
        labels.put(CalculatorButton.CLEAR, "AC");
        labels.put(CalculatorButton.FOUR, "4");
        labels.put(CalculatorButton.FIVE, "5");
        labels.put(CalculatorButton.SIX, "6");
        labels.put(CalculatorButton.DELETE, "DEL");
        labels.put(CalculatorButton.ONE, "1");
        labels.put(CalculatorButton.TWO, "2");
        labels.put(CalculatorButton.THREE, "3");
        labels.put(CalculatorButton.NEGATION, "-/+");
        labels.put(CalculatorButton.ZERO, "0");
        labels.put(CalculatorButton.DECIMAL_POINT, ".");
        labels.put(CalculatorButton.SINE, "Sin");
        labels.put(CalculatorButton.COSINE, "Cos");
        labels.put(CalculatorButton.TANGENT, "Tan");
        labels.put(CalculatorButton.COTANGENT, "Cot");
        labels.put(CalculatorButton.OPEN_BRACKET, "(");
        labels.put(CalculatorButton.CLOSE_BRACKET, ")");
        labels.put(CalculatorButton.MULTIPLICATION, "*");
        labels.put(CalculatorButton.DIVISION, "/");
        labels.put(CalculatorButton.ADDITION, "+");
        labels.put(CalculatorButton.SUBTRACTION, "-");
        labels.put(CalculatorButton.PI, "π");
        labels.put(CalculatorButton.POWER, "^");
        labels.put(CalculatorButton.PARAMETER, "x");
        labels.put(CalculatorButton.EQUALITY, "=");
        return labels;
    }

    /**
     * Initializes map containing values of buttons
     * @return initialized map of button values
     */
    private static Map<CalculatorButton, String> initializeButtonValues(){
        Map<CalculatorButton, String> values  = new HashMap<>();
        values.put(CalculatorButton.ZERO, "0");
        values.put(CalculatorButton.ONE, "1");
        values.put(CalculatorButton.TWO, "2");
        values.put(CalculatorButton.THREE, "3");
        values.put(CalculatorButton.FOUR, "4");
        values.put(CalculatorButton.FIVE, "5");
        values.put(CalculatorButton.SIX, "6");
        values.put(CalculatorButton.SEVEN, "7");
        values.put(CalculatorButton.EIGHT, "8");
        values.put(CalculatorButton.NINE, "9");
        values.put(CalculatorButton.NEGATION, "-(");
        values.put(CalculatorButton.DECIMAL_POINT, ".");
        values.put(CalculatorButton.SINE, "sin(");
        values.put(CalculatorButton.COSINE, "cos(");
        values.put(CalculatorButton.TANGENT, "tan(");
        values.put(CalculatorButton.COTANGENT, "cot(");
        values.put(CalculatorButton.OPEN_BRACKET, "(");
        values.put(CalculatorButton.CLOSE_BRACKET, ")");
        values.put(CalculatorButton.MULTIPLICATION, "*");
        values.put(CalculatorButton.DIVISION, "/");
        values.put(CalculatorButton.ADDITION, "+");
        values.put(CalculatorButton.SUBTRACTION, "-");
        values.put(CalculatorButton.PI, "π");
        values.put(CalculatorButton.POWER, "^");
        values.put(CalculatorButton.PARAMETER, "x");
        return  values;
    }
}
