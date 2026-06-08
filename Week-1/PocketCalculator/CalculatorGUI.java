import java.awt.*;
import javax.swing.*;

class CalculatorGUI{
    public static void main(String args[]){
        
        //create frame
        JFrame frame = new JFrame("Pocket Calculator");

        // ================
        //    TAB PANE
        // ================

        //define it
        JTabbedPane tabs = new JTabbedPane();

        //make some panels
        JPanel scientificPanel = new JPanel();
        JPanel temperaturePanel = new JPanel();
        JPanel currencyPanel = new JPanel();

        //put these panels into tab pane
        tabs.addTab("Scientific", scientificPanel);
        tabs.addTab("Temperature", temperaturePanel);
        tabs.addTab("Currency", currencyPanel);

        //add tab pane into main frame
        frame.add(tabs);

        // ======================
        //    SCIENTIFIC PANEL
        // ======================

        //define layout
        scientificPanel.setLayout(new BorderLayout());

        // ===== DISPLAY =====

        //define it
        JTextField display = new JTextField();

        //keep it read-only
        display.setEditable(false);

        //set dimensions
        display.setPreferredSize(new Dimension(800, 80));

        //make the font larger and bolder
        display.setFont(new Font("Ariel", Font.BOLD, 60));
        
        //make it aligned to the right
        display.setHorizontalAlignment(JTextField.RIGHT);

        //add this display to the panel with position in the layout
        scientificPanel.add(display, BorderLayout.NORTH);

        // ===== BUTTONS =====

        //define a panel for it
        JPanel buttonPanel = new JPanel();

        //define layout (grid of 6 rows, 4 cols, 5px gaps)
        buttonPanel.setLayout(new GridLayout(6, 4, 5, 5));

        //define the text on the buttons
        String[] buttons = {

            "DEL","C","(",")","/", 
            "^","7","8","9","*",
            "%","4","5","6","-",
            "x²","1","2","3","+",
            "√","00","0",".","="
        };

        //make the buttons
        for(String s : buttons){
            JButton button = new JButton(s);
            button.setFont(new Font("Ariel", Font.BOLD, 20));
            buttonPanel.add(button);

            button.addActionListener((e) -> {

                //is it a digit or operator? show it on the display!
                if(
                    s.matches("[0-9]+")
                    || s.equals(".")
                    || s.equals("+")
                    || s.equals("-")
                    || s.equals("*")
                    || s.equals("/")
                    || s.equals("%")
                    || s.equals("^")
                    || s.equals("(")
                    || s.equals(")")
                    )
                    display.setText(display.getText() + s);

                //is it C? clear the display
                else if(s.equals("C"))
                    display.setText("");

                //is it DEL? show substring on the display
                else if(s.equals("DEL")){
                    String curr = display.getText();
                    if(curr.length() > 0)
                        display.setText(display.getText().substring(0, curr.length() - 1));
                }

                //is it =? get the expression and evaluate it!
                else if(s.equals("=")){
                    try {
                        String expression = display.getText();
                        double res = ExpressionParser.evaluateExpression(expression);
                        display.setText(String.valueOf(res));
                    } catch (Exception ex) {
                        display.setText("ERROR");
                    }
                }

                //is it x^2?
                else if(s.equals("x²")){
                    try {
                        Double x = Double.parseDouble(display.getText());
                        Double res = CalculatorLogic.square(x);
                        display.setText(String.valueOf(res));
                    } catch (Exception ex) {
                        display.setText("ERROR");
                    }
                }

                //is it √?
                else if(s.equals("√")){
                    try {
                        Double x = Double.parseDouble(display.getText());
                        Double res = CalculatorLogic.squareRoot(x);
                        display.setText(String.valueOf(res));
                    } catch (Exception ex) {
                        display.setText("ERROR");
                    }
                }

            });
        }

        //add button panel to main panel
        scientificPanel.add(buttonPanel, BorderLayout.CENTER);

        // ======================
        //    TEMPERATURE PANEL
        // ======================

        //define layout
        temperaturePanel.setLayout(new GridLayout(5, 2, 10, 10));

        //make input area and result area
        JTextField tempInput = new JTextField();
        tempInput.setFont(new Font("Ariel", Font.BOLD, 50));
        JTextField result = new JTextField("");
        result.setEditable(false);
        result.setFont(new Font("Ariel", Font.BOLD, 50));

        //make buttons
        JButton cToF = new JButton("C -> F");
        JButton fToC = new JButton("F -> C");
        JButton cToK = new JButton("C -> K");
        JButton kToC = new JButton("K -> C");

        //add all components to the panel
        temperaturePanel.add(new JLabel("Temperature"));
        temperaturePanel.add(tempInput);
        temperaturePanel.add(cToF);
        temperaturePanel.add(fToC);
        temperaturePanel.add(cToK);
        temperaturePanel.add(kToC);
        temperaturePanel.add(new JLabel("Result: "));
        temperaturePanel.add(result);

        cToF.addActionListener((e) -> {
            try {
                Double t = Double.parseDouble(tempInput.getText());
                result.setText(String.valueOf(CalculatorLogic.celsiusToFahrenheit(t)));
            } catch (Exception ex) {
                result.setText("ERROR");
            }
        });

        fToC.addActionListener((e) -> {
            try {
                Double t = Double.parseDouble(tempInput.getText());
                result.setText(String.valueOf(CalculatorLogic.fahrenheitToCelsius(t)));
            } catch (Exception ex) {
                result.setText("ERROR");
            }
        });

        cToK.addActionListener((e) -> {
            try {
                Double t = Double.parseDouble(tempInput.getText());
                result.setText(String.valueOf(CalculatorLogic.celsiusToKelvin(t)));
            } catch (Exception ex) {
                result.setText("ERROR");
            }
        });

        kToC.addActionListener((e) -> {
            try {
                Double t = Double.parseDouble(tempInput.getText());
                result.setText(String.valueOf(CalculatorLogic.kelvinToCelsius(t)));
            } catch (Exception ex) {
                result.setText("ERROR");
            }
        });

        // ======================
        //    CURRENCY PANEL
        // ======================

        //define layout
        currencyPanel.setLayout(new GridLayout(5, 2, 10, 10));

        //make input area and result area
        JTextField currInput = new JTextField();
        currInput.setFont(new Font("Ariel", Font.BOLD, 50));
        JTextField conv = new JTextField("");
        conv.setEditable(false);
        conv.setFont(new Font("Ariel", Font.BOLD, 50));

        //make buttons
        JButton iToU = new JButton("INR -> USD");
        JButton uToI = new JButton("USD -> INR");
        JButton iToE = new JButton("INR -> EUR");
        JButton eToI = new JButton("EUR -> INR");

        //add all components to the panel
        currencyPanel.add(new JLabel("Currency"));
        currencyPanel.add(currInput);
        currencyPanel.add(iToU);
        currencyPanel.add(uToI);
        currencyPanel.add(iToE);
        currencyPanel.add(eToI);
        currencyPanel.add(new JLabel("Result: "));
        currencyPanel.add(conv);

        iToU.addActionListener((e) -> {
            try {
                double t = Double.parseDouble(currInput.getText());
                conv.setText("" + CalculatorLogic.inrToUsd(t));
            } catch (Exception ex) {
                conv.setText("ERROR");
            }
        });

        uToI.addActionListener((e) -> {
            try {
                double t = Double.parseDouble(currInput.getText());
                conv.setText("" + CalculatorLogic.usdToInr(t));
            } catch (Exception ex) {
                conv.setText("ERROR");
            }
        });

        iToE.addActionListener((e) -> {
            try {
                double t = Double.parseDouble(currInput.getText());
                conv.setText("" + CalculatorLogic.inrToEur(t));
            } catch (Exception ex) {
                conv.setText("ERROR");
            }
        });

        eToI.addActionListener((e) -> {
            try {
                double t = Double.parseDouble(currInput.getText());
                conv.setText("" + CalculatorLogic.eurToInr(t));
            } catch (Exception ex) {
                conv.setText("ERROR");
            }
        });

        //set size - width: 400, height: 300
        frame.setSize(800, 600);

        //add how it closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //make it appear in the center
        frame.setLocationRelativeTo(null);

        //make it visible
        frame.setVisible(true);

    }
}