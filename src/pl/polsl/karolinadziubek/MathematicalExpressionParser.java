package pl.polsl.karolinadziubek;

import java.util.function.Function;

/**
 * Provides functionality of parsing mathematical expressions into lambda expressions
 */
public class MathematicalExpressionParser {
    /**
     * Parses text representation of mathematical expression into lambda expression
     * @param expression text representation of mathematical expression
     * @return lambda expression equivalent of input mathematical expression
     */
    public static Function<Double, Double> parseMathematicalExpression(final String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean skip(int charToSkip) {
                while (ch == ' ') nextChar();
                if (ch == charToSkip) {
                    nextChar();
                    return true;
                }
                return false;
            }

            Function<Double, Double> parse() {
                nextChar();
                Function<Double, Double> x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            Function<Double, Double> parseExpression() {
                Function<Double, Double> x = parseTerm();
                for (;;) {
                    if      (skip('+')) x = add(x, parseTerm());
                    else if (skip('-')) x = subtract(x, parseTerm());
                    else return x;
                }
            }

            Function<Double, Double> parseTerm() {
                Function<Double, Double> x = parseFactor();
                for (;;) {
                    if      (skip('*')) x = multiply(x, parseFactor());
                    else if (skip('/')) x = divide(x, parseFactor());
                    else return x;
                }
            }

            Function<Double, Double> parseFactor() {
                if (skip('+')) return parseFactor();
                if (skip('-')) return negate(parseFactor());

                Function<Double, Double> x;
                int startPos = this.pos;
                if (skip('(')) {
                    x = parseExpression();
                    skip(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = value(Double.parseDouble(expression.substring(startPos, this.pos)));
                } else if(ch == 'π'){
                    nextChar();
                    x = value(Math.PI);
                } else if(ch == 'x'){
                    nextChar();
                    x = parameter();
                }else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = expression.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sin")) x = sin(x);
                    else if (func.equals("cos")) x = cos(x);
                    else if (func.equals("tan")) x = tan(x);
                    else if (func.equals("cot")) x = cot(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (skip('^')) x = power(x, parseFactor());

                return x;
            }
        }.parse();
    }

    /**
     * returns lambda expression representing addition of results from 2 input lambda expressions
     * @param func1 first input lambda expression
     * @param func2 second input lambda expression
     * @return lambda expression representing addition of results from 2 input lambda expressions
     */
    private static Function<Double, Double> add(Function<Double, Double> func1, Function<Double, Double> func2)
    {
        return (d) -> func1.apply(d) + func2.apply(d);
    }

    /**
     * returns lambda expression representing subtraction of results from 2 input lambda expressions
     * @param func1 first input lambda expression
     * @param func2 second input lambda expression
     * @return lambda expression representing subtraction of results from 2 input lambda expressions
     */
    private static Function<Double, Double> subtract(Function<Double, Double> func1, Function<Double, Double> func2)
    {
        return (d) -> func1.apply(d) - func2.apply(d);
    }

    /**
     * returns lambda expression representing multiplication of results from 2 input lambda expressions
     * @param func1 first input lambda expression
     * @param func2 second input lambda expression
     * @return lambda expression representing multiplication of results from 2 input lambda expressions
     */
    private static Function<Double, Double> multiply(Function<Double, Double> func1, Function<Double, Double> func2)
    {
        return (d) -> func1.apply(d) * func2.apply(d);
    }

    /**
     * returns lambda expression representing division of results from input lambda expressions
     * @param func1 first input lambda expression
     * @param func2 second input lambda expression
     * @return lambda expression representing division of results from 2 input lambda expressions
     */
    private static Function<Double, Double> divide(Function<Double, Double> func1, Function<Double, Double> func2)
    {
        return (d) -> func1.apply(d) / func2.apply(d);
    }

    /**
     * returns lambda expression representing exponentiation of results from 2 input lambda expressions
     * @param func1 first input lambda expression
     * @param func2 second input lambda expression
     * @return lambda expression representing exponentiation of results from 2 input lambda expressions
     */
    private static Function<Double, Double> power(Function<Double, Double> func1, Function<Double, Double> func2)
    {
        return (d) -> Math.pow(func1.apply(d), func2.apply(d));
    }

    /**
     * returns lambda expression representing sine of result from input lambda expression
     * @param func input lambda expression
     * @return lambda expression representing sine of result from input lambda expression
     */
    private static Function<Double, Double> sin(Function<Double, Double> func)
    {
        return (d) -> Math.sin(func.apply(d));
    }

    /**
     * returns lambda expression representing cosine of result from input lambda expression
     * @param func input lambda expression
     * @return lambda expression representing cosine of result from input lambda expression
     */
    private static Function<Double, Double> cos(Function<Double, Double> func)
    {
        return (d) -> Math.cos(func.apply(d));
    }

    /**
     * returns lambda expression representing tangent of result from input lambda expression
     * @param func input lambda expression
     * @return lambda expression representing tangent of result from input lambda expression
     */
    private static Function<Double, Double> tan(Function<Double, Double> func)
    {
        return (d) -> Math.tan(func.apply(d));
    }

    /**
     * returns lambda expression representing cotangent of result from input lambda expression
     * @param func input lambda expression
     * @return lambda expression representing cotangent of result from input lambda expression
     */
    private static Function<Double, Double> cot(Function<Double, Double> func)
    {
        return (d) -> 1 / Math.tan(func.apply(d));
    }

    /**
     * returns lambda expression representing negation of result from input lambda expression
     * @param func input lambda expression
     * @return lambda expression representing negation of result from input lambda expression
     */
    private static Function<Double, Double> negate(Function<Double, Double> func)
    {
        return (d) -> -func.apply(d);
    }

    /**
     * returns lambda expression representing parameter of itself
     * @return lambda expression representing parameter of itself
     */
    private static Function<Double, Double> parameter()
    {
        return (d) -> d;
    }

    /**
     * returns lambda expression representing given value
     * @return lambda expression representing given value
     */
    private static Function<Double, Double> value(double value)
    {
        return (d) -> value;
    }
}
