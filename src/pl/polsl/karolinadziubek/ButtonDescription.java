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

    /**
     * Specific type of button used for distinction
     */
    CalculatorButton calculatorButton;

    /**
     * Label displayed for button
     */
    String label;

    /**
     * Value represented by button
     */
    String value;
}
