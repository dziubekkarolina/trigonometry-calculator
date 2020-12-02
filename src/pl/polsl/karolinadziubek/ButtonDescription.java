package pl.polsl.karolinadziubek;

/**
 * Contains values required to describe a button
 */
public class ButtonDescription {
    /**
     * Parametrized constructor
     * @param calculatorButton type of button
     * @param label label of button
     * @param value value of button
     */
    public ButtonDescription(CalculatorButton calculatorButton, String label, String value){
        this.calculatorButton = calculatorButton;
        this.label = label;
        this.value = value;
    }
    CalculatorButton calculatorButton;
    String label;
    String value;
}
