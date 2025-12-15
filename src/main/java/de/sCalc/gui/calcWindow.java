package de.sCalc.gui;

import de.sCalc.logic.calcLogic;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class calcWindow extends JFrame {

    private JTextField display;
    private calcLogic logic = new calcLogic();

    // Speicher für die Rechnung
    private BigDecimal firstNumber = null;
    private String operator = "";
    private boolean restart = true;

    public calcWindow() {
        setTitle("sCalc");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false); //TODO Erstmal nur eingabe durch Buttons in Zukunft auch durch Tastatur
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        // Beschriftungen für die Tasten
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));

            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();

            if (command.equals("C")){
                display.setText("");
                firstNumber = null;
                operator = "";
                restart = true;
            } else if (command.equals("=")) {
                if (firstNumber != null && !operator.isEmpty()){
                    try{
                        BigDecimal secondNumber = new BigDecimal(display.getText());
                        BigDecimal result = BigDecimal.ZERO;

                        switch (operator){
                            case "+":
                                result = logic.add(firstNumber, secondNumber);
                                break;
                            case "-":
                                result = logic.sub(firstNumber, secondNumber);
                                break;
                            case "*":
                                result = logic.multiply(firstNumber, secondNumber);
                                break;
                            case "/":
                                result = logic.divide(firstNumber, secondNumber);
                                break;
                        }
                        display.setText(result.toString());

                        restart = true;
                        firstNumber = null;
                        operator = "";
                    } catch (Exception ex){
                        display.setText("Error");
                        restart = true;
                    }
                }

            }
            else if ("+-*/".contains(command)){
                if(!display.getText().isEmpty()){
                    firstNumber = new BigDecimal(display.getText());
                    operator = command;
                    restart = true;
                }
            } else {
                if (restart){
                    display.setText(command);
                    restart = false;
                } else display.setText(display.getText() + command);
            }
        }

    }
}