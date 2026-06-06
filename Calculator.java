import java.util.*;

class Calculator{

    static Scanner cal = new Scanner(System.in);

    static ArrayList<Double> history = new ArrayList<>();

    public static void main(String[] args) {

        while (true) { 
            System.out.println("\n===== POCKET CALCULATOR =====");
            System.out.println("Please enter:");
            System.out.println("'s' to open Scientific Calculator");
            System.out.println("'t' to open Temperature Converter");
            System.out.println("'c' to open Currency Converter");
            System.out.println("'q' to quit");

            char ch = cal.next().charAt(0);

            if(ch == 'q'){
                System.out.println("Pocket Calculator closed.");
                break;
            }

            switch(ch){

                case 's':
                    sciCalculator();
                    break;

                case 't':
                    tempConverter();
                    break;

                case 'c':
                    currencyConverter();
                    break;

                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
        
        cal.close();
    }

    public static void sciCalculator(){

        while (true) { 
            System.out.println("\n===== SCIENTIFIC CALCULATOR =====");
            System.out.println("Please enter operation: ");
            System.out.println("'+' for addition");
            System.out.println("'-' for subtraction");
            System.out.println("'*' for multiplication");
            System.out.println("'/' for division");
            System.out.println("'mod' for mod");
            System.out.println("'!' for factorial");
            System.out.println("'reci' for reciprocal (1/x)");
            System.out.println("'sq' for square (x^2)");
            System.out.println("'e' for x^y");
            System.out.println("'sr' for 2-root(x)");
            System.out.println("'r' for y-root(x)");
            System.out.println("'l' for log x");
            System.out.println("'ln' for ln x");
            System.out.println("'lxy' for log x with base y");
            System.out.println("'ex' for e^x");
            System.out.println("'2x' for 2^x");
            System.out.println("'10x' for 10^x");
            System.out.println("'h' for history");
            System.out.println("'q' for quit");

            String op = cal.next();

            //wants to quit
            if(op.equals("q")){
                System.out.println("Scientific Calculator closed.");
                break;
            }

            //wants to see history
            if(op.equals("h")){
                if(history.isEmpty())
                    System.out.println("Oops! No calculations yet.");
                else{
                    System.out.println("Calculation history:");

                    for(Double d : history) //unboxing
                        System.out.println(d);
                }
                continue;
            }

            double x, y, res = 0;
            boolean validOp = true; //is it a valid operation?

            switch(op){

                case "+":
                    System.out.println("x + y");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();
                    res = CalculatorLogic.add(x, y);
                    break;

                case "-":
                    System.out.println("x - y");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();
                    res = CalculatorLogic.subtract(x, y);
                    break;

                case "*":
                    System.out.println("x * y");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();
                    res = CalculatorLogic.multiply(x, y);
                    break;

                case "/":
                    System.out.println("x / y");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();
                    res = CalculatorLogic.divide(x, y);
                    break;

                case "mod":
                    System.out.println("x mod y");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();
                    res = CalculatorLogic.mod(x, y);
                    break;

                case "!":
                    System.out.println("x!");
                    System.out.print("Please enter x: ");
                    int n = cal.nextInt();

                    res = CalculatorLogic.factorial(n);
                    
                    break;

                case "reci":
                    System.out.println("1/x");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();
                    res = CalculatorLogic.reciprocal(x);
                    break;

                case "sq":
                    System.out.println("x^2");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();
                    res = CalculatorLogic.square(x);
                    break;

                case "e":
                    System.out.println("x ^ y");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();
                    res = CalculatorLogic.power(x, y);
                    break;

                case "sr":
                    System.out.println("2-root(x)");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();
                    res = CalculatorLogic.squareRoot(x);
                    break;

                case "r":
                    System.out.println("y-root(x)");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();
                    res = CalculatorLogic.nthRoot(x, y);
                    break;

                case "l":
                    System.out.println("log x (base 10)");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();
                    res = CalculatorLogic.log(x);
                    break;

                case "ln":
                    System.out.println("ln x (base e)");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();
                    res = CalculatorLogic.ln(x);
                    break;

                case "lxy":
                    System.out.println("log x with base y");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();
                    res = CalculatorLogic.logBase(x, y);
                    break;

                case "ex":
                    System.out.println("e^x");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();
                    res = CalculatorLogic.exp(x);
                    break;

                case "2x":
                    System.out.println("2^x");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();
                    res = CalculatorLogic.power2(x);
                    break;

                case "10x":
                    System.out.println("10^x");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();
                    res = CalculatorLogic.power10(x);
                    break;

                default:
                    System.out.println("Invalid operation!");
                    validOp = false;
            }

            if(validOp){
                System.out.println("Result = " + res);

                //add res to history
                history.add(res); //autoboxing
            }
        }
    }

    public static void tempConverter(){
        
        while (true) { 
            System.out.println("\n===== TEMPERATURE CONVERTER =====");
            System.out.println("Please enter: ");
            System.out.println("1 for C -> F");
            System.out.println("2 for F -> C");
            System.out.println("3 for C -> K");
            System.out.println("4 for K -> C");
            System.out.println("5 for history");
            System.out.println("0 for exit");

            int ch = cal.nextInt();

            if(ch == 0){
                System.out.println("Temperature Converter closed.");
                break;
            }

            if(ch == 5){
                if(history.isEmpty())
                    System.out.println("Oops! No calculations yet.");
                else{
                    System.out.println("Calculation history:");

                    for(Double d : history) 
                        System.out.println(d);
                }
                continue;
            }

            System.out.print("Enter value: ");
            double t = cal.nextDouble(), res;

            switch(ch){

                case 1:
                    res = CalculatorLogic.celsiusToFahrenheit(t);
                    break;

                case 2:
                    res = CalculatorLogic.fahrenheitToCelsius(t);
                    break;

                case 3: 
                    res = CalculatorLogic.celsiusToKelvin(t);
                    break;

                case 4:
                    res = CalculatorLogic.kelvinToCelsius(t);
                    break;

                default:
                    System.out.println("Invalid choice!");
                    continue;

            }

            System.out.println("Result = " + res);
            history.add(res);
        }
    }

    public static void currencyConverter(){

        while (true) { 
            System.out.println("\n===== CURRENCY CONVERTER =====");
            System.out.println("Please enter: ");
            System.out.println("1 for INR -> USD");
            System.out.println("2 for USD -> INR");
            System.out.println("3 for INR -> EUR");
            System.out.println("4 for EUR -> INR");
            System.out.println("5 for history");
            System.out.println("0 for exit");

            int ch = cal.nextInt();

            if(ch == 0){
                System.out.println("Currency Converter closed.");
                break;
            }

            if(ch == 5){
                if(history.isEmpty())
                    System.out.println("Oops! No calculations yet.");
                else{
                    System.out.println("Calculation history:");

                    for(Double d : history) 
                        System.out.println(d);
                }
                continue;
            }

            System.out.println("Enter amount: ");
            double c = cal.nextDouble(), res;

            switch(ch){

                case 1:
                    res = CalculatorLogic.inrToUsd(c);
                    break;

                case 2:
                    res = CalculatorLogic.usdToInr(c);
                    break;

                case 3:
                    res = CalculatorLogic.inrToEur(c);
                    break;

                case 4:
                    res = CalculatorLogic.eurToInr(c);
                    break;

                default:
                    System.out.println("Invalid choice!");
                    continue;
            }

            System.out.println("Result = " + res);
            history.add(res);
        }
    }
}