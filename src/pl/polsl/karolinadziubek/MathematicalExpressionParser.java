package pl.polsl.karolinadziubek;

import java.util.function.Function;

public class MathematicalExpressionParser {
    public static Function<Double, Double> eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            Function<Double, Double> parse() {
                nextChar();
                Function<Double, Double> x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            Function<Double, Double> parseExpression() {
                Function<Double, Double> x = parseTerm();
                for (;;) {
                    if      (eat('+')) x = add(x, parseTerm());
                    else if (eat('-')) x = subtract(x, parseTerm());
                    else return x;
                }
            }

            Function<Double, Double> parseTerm() {
                Function<Double, Double> x = parseFactor();
                for (;;) {
                    if      (eat('*')) x = multiply(x, parseFactor());
                    else if (eat('/')) x = divide(x, parseFactor());
                    else return x;
                }
            }

            Function<Double, Double> parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return negate(parseFactor());

                Function<Double, Double> x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = value(Double.parseDouble(str.substring(startPos, this.pos)));
                } else if(ch == 'π'){
                    nextChar();
                    x = value(Math.PI);
                } else if(ch == 'x'){
                    nextChar();
                    x = parameter();
                }else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sin")) x = sin(x);
                    else if (func.equals("cos")) x = cos(x);
                    else if (func.equals("tan")) x = tan(x);
                    else if (func.equals("cot")) x = cot(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = power(x, parseFactor());

                return x;
            }
        }.parse();
    }

    static Function<Double, Double> add(Function<Double, Double> func1, Function<Double, Double> func2)
    {
        return (d) -> func1.apply(d) + func2.apply(d);
    }
    static Function<Double, Double> subtract(Function<Double, Double> func1, Function<Double, Double> func2)
    {
        return (d) -> func1.apply(d) - func2.apply(d);
    }
    static Function<Double, Double> multiply(Function<Double, Double> func1, Function<Double, Double> func2)
    {
        return (d) -> func1.apply(d) * func2.apply(d);
    }
    static Function<Double, Double> divide(Function<Double, Double> func1, Function<Double, Double> func2)
    {
        return (d) -> func1.apply(d) / func2.apply(d);
    }
    static Function<Double, Double> power(Function<Double, Double> func1, Function<Double, Double> func2)
    {
        return (d) -> Math.pow(func1.apply(d), func2.apply(d));
    }
    static Function<Double, Double> sin(Function<Double, Double> func)
    {
        return (d) -> Math.sin(func.apply(d));
    }
    static Function<Double, Double> cos(Function<Double, Double> func)
    {
        return (d) -> Math.cos(func.apply(d));
    }
    static Function<Double, Double> tan(Function<Double, Double> func)
    {
        return (d) -> Math.tan(func.apply(d));
    }
    static Function<Double, Double> cot(Function<Double, Double> func)
    {
        return (d) -> 1 / Math.tan(func.apply(d));
    }
    static Function<Double, Double> negate(Function<Double, Double> func)
    {
        return (d) -> -func.apply(d);
    }
    static Function<Double, Double> parameter()
    {
        return (d) -> d;
    }
    static Function<Double, Double> value(double value)
    {
        return (d) -> value;
    }
}
