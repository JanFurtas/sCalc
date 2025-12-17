package de.sCalc.logic;

import java.math.BigDecimal;
import java.math.MathContext;

public class CalcLogic {
    public BigDecimal output;

    public BigDecimal add(BigDecimal input1, BigDecimal input2){
        return output = input1.add(input2);
    }
    public BigDecimal sub(BigDecimal input1, BigDecimal input2){
        return output = input1.subtract(input2);
    }
    public BigDecimal multiply(BigDecimal input1, BigDecimal input2){
        return output = input1.multiply(input2);
    }
    public BigDecimal divide(BigDecimal input1, BigDecimal input2){
        if (input2.compareTo(BigDecimal.ZERO) == 0){
            throw new ArithmeticException("Nicht durch 0 teilen");
        }
        return output = input1.divide(input2, MathContext.DECIMAL128);
    }
}
