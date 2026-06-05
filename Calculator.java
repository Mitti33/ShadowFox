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
                    res = x + y;
                    break;

                case "-":
                    System.out.println("x - y");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();
                    res = x - y;
                    break;

                case "*":
                    System.out.println("x * y");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();
                    res = x * y;
                    break;

                case "/":
                    System.out.println("x / y");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();

                    //is y = 0?
                    if(y == 0){
                        System.out.println("Oops! Division by zero is not allowed.");
                        validOp = false;
                    }
                    else
                        res = x / y;
                    break;

                case "mod":
                    System.out.println("x mod y");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();

                    //is y = 0?
                    if(y == 0){
                        System.out.println("Oops! Modulus by zero is not allowed.");
                        validOp = false;
                    }
                    else
                        res = x % y;
                    break;

                case "!":
                    System.out.println("x!");
                    System.out.print("Please enter x: ");
                    int n = cal.nextInt();

                    //is x = -ve?
                    if(n < 0){
                        System.out.println("Oops! Factorial is not defined for negatives.");
                        validOp = false;
                    }
                    else{
                        res = 1;
                        for(int i = 2;i <= n;i++)
                            res *= i;
                    }
                    
                    break;

                case "reci":
                    System.out.println("1/x");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();

                    //is x = 0?
                    if(x == 0){
                        System.out.println("Oops! Reciprocal of zero is not defined.");
                        validOp = false;
                    }
                    else
                        res = 1.0 / x;
                    break;

                case "sq":
                    System.out.println("x^2");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();
                    res = x * x;
                    break;

                case "e":
                    System.out.println("x ^ y");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();
                    res = Math.pow(x, y);
                    break;

                case "sr":
                    System.out.println("2-root(x)");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();

                    //is x = -ve?
                    if(x < 0){
                        System.out.println("Oops! Square root of negative is not allowed.");
                        validOp = false;
                    }
                    else
                        res = Math.sqrt(x);
                    break;

                case "r":
                    System.out.println("y-root(x)");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();

                    if(x < 0 || y == 0){
                        System.out.println("Oops! Zero root degree is not allowed.");
                        validOp = false;
                    }
                    else
                        res = Math.pow(x, 1.0 / y);
                    break;

                case "l":
                    System.out.println("log x (base 10)");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();

                    //is x <= 0?
                    if(x <= 0){
                        System.out.println("Oops! Logarithm undefined for x <= 0.");
                        validOp = false;
                    }
                    else
                        res = Math.log10(x);
                    break;

                case "ln":
                    System.out.println("ln x (base e)");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();

                    //is x <= 0?
                    if(x <= 0){
                        System.out.println("Oops! Natural logarithm undefined for x <= 0.");
                        validOp = false;
                    }
                    else
                        res = Math.log(x);
                    break;

                case "lxy":
                    System.out.println("log x with base y");
                    System.out.println("Please enter x and y: ");
                    x = cal.nextDouble();
                    y = cal.nextDouble();

                    if(x <= 0 || y <= 0 || y == 1){
                        System.out.println("Oops! Invalid values for logarithm.");
                        validOp = false;
                    }
                    else
                        res = Math.log(x) / Math.log(y);
                    break;

                case "ex":
                    System.out.println("e^x");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();
                    res = Math.exp(x);
                    break;

                case "2x":
                    System.out.println("2^x");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();
                    res = Math.pow(2, x);
                    break;

                case "10x":
                    System.out.println("10^x");
                    System.out.print("Please enter x: ");
                    x = cal.nextDouble();
                    res = Math.pow(10, x);
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
            double t = cal.nextDouble(), res = 0;

            switch(ch){

                case 1:
                    res = (t * 9 / 5) + 32;
                    break;

                case 2:
                    res = (t - 32) * 5 / 9;
                    break;

                case 3: 
                    res = t + 273.15;
                    break;

                case 4:
                    if(t < 0){
                        System.out.println("Negative K is not possible!");
                        continue;
                    }
                    res = t - 273.15;
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
            double c = cal.nextDouble(), res = 0;

            switch(ch){

                case 1:
                    res = c / 95.41;
                    break;

                case 2:
                    res = c * 95.41;
                    break;

                case 3:
                    res = c / 109.39;
                    break;

                case 4:
                    res = c * 109.39;
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