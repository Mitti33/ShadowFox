import java.util.*;

public class ExpressionParser {

    //is this an operator?
    private static boolean isOperator(char c) {

        return c == '+'
                || c == '-'
                || c == '*'
                || c == '/'
                || c == '%'
                || c == '^';
    }

    //what is the precedence of this operator?
    private static int precedence(char op) {

        switch(op) {

            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
            case '%':
                return 2;

            case '^':
                return 3;

            default:
                return 0;
        }
    }

    //shunting yard algorithm
    //convert the infix expression to postfix expression
    public static List<String> infixToPostfix(String expression) {

        Stack<Character> operators = new Stack<>();

        List<String> output = new ArrayList<>();

        int i = 0;

        while(i < expression.length()) {

            char c = expression.charAt(i);

            // ignore spaces
            if(Character.isWhitespace(c)) {
                i++;
                continue;
            }

            // number (supports decimals)
            if(Character.isDigit(c) || c == '.') {

                StringBuilder number = new StringBuilder();

                while(i < expression.length()) {

                    char current = expression.charAt(i);

                    if(Character.isDigit(current)
                            || current == '.') {

                        number.append(current);
                        i++;
                    }
                    else {
                        break;
                    }
                }

                output.add(number.toString());

                continue;
            }

            //opening bracket
            if(c == '(') {

                operators.push(c);
            }

            //closing bracket
            else if(c == ')') {

                while(!operators.isEmpty()
                        && operators.peek() != '(') {

                    output.add(
                            String.valueOf(
                                    operators.pop()
                            )
                    );
                }

                if(!operators.isEmpty()) {
                    operators.pop();
                }
            }

            //operator
            else if(isOperator(c)) {

                while(!operators.isEmpty()
                        && precedence(
                                operators.peek()
                        ) >= precedence(c)) {

                    output.add(
                            String.valueOf(
                                    operators.pop()
                            )
                    );
                }

                operators.push(c);
            }

            i++;
        }

        while(!operators.isEmpty()) {

            output.add(
                    String.valueOf(
                            operators.pop()
                    )
            );
        }

        return output;
    }

    //evaluate this postfix expression and find out result
    public static double evaluatePostfix(List<String> postfix) {

        Stack<Double> stack =
                new Stack<>();

        for(String token : postfix) {

            //number
            if(token.length() > 1
                    || Character.isDigit(
                            token.charAt(0))
                    || token.charAt(0) == '.') {

                stack.push(
                        Double.parseDouble(token)
                );
            }

            //operator
            else {

                double b = stack.pop();

                double a = stack.pop();

                char op = token.charAt(0);

                double result = 0;

                switch(op) {

                    case '+':
                        result =
                                CalculatorLogic.add(a,b);
                        break;

                    case '-':
                        result =
                                CalculatorLogic.subtract(a,b);
                        break;

                    case '*':
                        result =
                                CalculatorLogic.multiply(a,b);
                        break;

                    case '/':
                        result =
                                CalculatorLogic.divide(a,b);
                        break;

                    case '%':
                        result =
                                CalculatorLogic.mod(a,b);
                        break;

                    case '^':
                        result =
                                CalculatorLogic.power(a,b);
                        break;
                }

                stack.push(result);
            }
        }

        return stack.pop();
    }

    //final method
    public static double evaluateExpression(String expression) {

        List<String> postfix = infixToPostfix(expression);

        return evaluatePostfix(postfix);
    }
}