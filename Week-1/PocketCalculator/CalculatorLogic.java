
public class CalculatorLogic {

    // ==========================
    // Scientific Calculation
    // ==========================

    //Basic Arithmetic

    public static double add(double x, double y) {
        return x + y;
    }

    public static double subtract(double x, double y) {
        return x - y;
    }

    public static double multiply(double x, double y) {
        return x * y;
    }

    public static double divide(double x, double y) {
        if (y == 0)
            throw new IllegalArgumentException("Division by zero");
        return x / y;
    }

    public static double mod(double x, double y) {
        if (y == 0)
            throw new IllegalArgumentException("Modulus by zero");
        return x % y;
    }

    //Factorial

    public static long factorial(int n) {

        if (n < 0)
            throw new IllegalArgumentException(
                    "Factorial undefined for negative numbers");

        long result = 1;

        for (int i = 2; i <= n; i++) {
            result *= i;
        }

        return result;
    }

    //Reciprocal

    public static double reciprocal(double x) {

        if (x == 0)
            throw new IllegalArgumentException(
                    "Reciprocal of zero is undefined");

        return 1.0 / x;
    }

    //Square

    public static double square(double x) {
        return x * x;
    }

    //Power

    public static double power(double x, double y) {
        return Math.pow(x, y);
    }

    //Square Root

    public static double squareRoot(double x) {

        if (x < 0)
            throw new IllegalArgumentException(
                    "Square root of negative number");

        return Math.sqrt(x);
    }

    //nth Root

    public static double nthRoot(double x, double y) {

        if (y == 0)
            throw new IllegalArgumentException(
                    "Root degree cannot be zero");

        return Math.pow(x, 1.0 / y);
    }

    //Log Base 10

    public static double log(double x) {

        if (x <= 0)
            throw new IllegalArgumentException(
                    "Log undefined for x <= 0");

        return Math.log10(x);
    }

    //Natural Log

    public static double ln(double x) {

        if (x <= 0)
            throw new IllegalArgumentException(
                    "Natural log undefined for x <= 0");

        return Math.log(x);
    }

    //Log Base y

    public static double logBase(double x, double y) {

        if (x <= 0 || y <= 0 || y == 1)
            throw new IllegalArgumentException(
                    "Invalid logarithm values");

        return Math.log(x) / Math.log(y);
    }

    //e^x

    public static double exp(double x) {
        return Math.exp(x);
    }

    //2^x

    public static double power2(double x) {
        return Math.pow(2, x);
    }

    //10^x

    public static double power10(double x) {
        return Math.pow(10, x);
    }

    // ==========================
    // Temperature Conversion
    // ==========================

    public static double celsiusToFahrenheit(double c) {
        return (c * 9.0 / 5.0) + 32;
    }

    public static double fahrenheitToCelsius(double f) {
        return (f - 32) * 5.0 / 9.0;
    }

    public static double celsiusToKelvin(double c) {
        return c + 273.15;
    }

    public static double kelvinToCelsius(double k) {

        if (k < 0)
            throw new IllegalArgumentException(
                    "Kelvin cannot be negative");

        return k - 273.15;
    }

    // ==========================
    // Currency Conversion
    // ==========================

    public static final double INR_PER_USD = 95.41;
    public static final double INR_PER_EUR = 109.39;

    public static double inrToUsd(double inr) {
        return inr / INR_PER_USD;
    }

    public static double usdToInr(double usd) {
        return usd * INR_PER_USD;
    }

    public static double inrToEur(double inr) {
        return inr / INR_PER_EUR;
    }

    public static double eurToInr(double eur) {
        return eur * INR_PER_EUR;
    }
}