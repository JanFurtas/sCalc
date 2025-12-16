package de.sCalc.gui;

import de.sCalc.logic.calcLogic;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class calcWindow extends JFrame {

    private JLabel display;
    private calcLogic logic = new calcLogic();

    // Speicher für die Rechnung
    private BigDecimal firstNumber = null;
    private String operator = "";
    private boolean restart = true;

    public calcWindow() {
        setTitle("sCalc");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.decode("#5A5A75"));


        display = new JLabel("");
        display.setOpaque(true);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(225,150));
        display.setBackground(Color.decode("#5A5A75"));
        display.setForeground(Color.white);
        display.setHorizontalAlignment(JLabel.RIGHT);
        display.setVerticalAlignment(JLabel.BOTTOM);
        display.setBorder(BorderFactory.createEmptyBorder(0,0,15,15));
        display.setLayout(new BorderLayout());
        add(display, BorderLayout.NORTH);

        JButton historyBtn = new JButton("\uD83D\uDD52");
        historyBtn.setBorderPainted(false);
        historyBtn.setContentAreaFilled(false);
        historyBtn.setFocusPainted(false);
        historyBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        historyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        historyBtn.setForeground(Color.WHITE);
        historyBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Kommt");
        });

        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5,0));
        buttonContainer.setOpaque(false);
        buttonContainer.add(historyBtn);

        display.add(buttonContainer, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));
        panel.setBackground(Color.decode("#2E2E2E"));

        // Beschriftungen für die Tasten
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String text : buttons) {
            RoundedButton button = new RoundedButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setForeground(Color.white);
            button.setBackground(Color.decode("#3D3D4F"));
            button.setBorderPainted(true);

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