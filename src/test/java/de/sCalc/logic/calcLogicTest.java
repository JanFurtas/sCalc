package de.sCalc.logic;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;


class calcLogicTest {
    CalcLogic calculator = new CalcLogic();

    @Test
    void testAdd() {
        // 1. Vorbereiten (Given)
        BigDecimal zahl1 = new BigDecimal("2.5");
        BigDecimal zahl2 = new BigDecimal("3.0");

        // 2. Ausführen (When)
        BigDecimal ergebnis = calculator.add(zahl1, zahl2);

        // 3. Prüfen (Then)
        // Wir erwarten 5.5
        assertEquals(new BigDecimal("5.5"), ergebnis, "2.5 + 3.0 sollte 5.5 sein");
    }

    @Test
    void testSubtract() {
        BigDecimal ergebnis = calculator.sub(new BigDecimal("10"), new BigDecimal("4"));
        assertEquals(new BigDecimal("6"), ergebnis);
    }

    @Test
    void testMultiply() {
        BigDecimal ergebnis = calculator.multiply(new BigDecimal("2.0"), new BigDecimal("5.0"));
        // Achtung: Bei BigDecimal ist 10.00 mathematisch gleich 10.0,
        // aber für assertEquals manchmal unterschiedlich (Scale).
        // compareTo ist sicherer: liefert 0, wenn Werte gleich sind.
        assertEquals(0, ergebnis.compareTo(new BigDecimal("10.0")));
    }

    @Test
    void testDivide() {
        BigDecimal ergebnis = calculator.divide(new BigDecimal("10"), new BigDecimal("2"));
        assertEquals(new BigDecimal("5"), ergebnis);
    }

    @Test
    void testDivideByZero() {
        // Hier testen wir, ob der Rechner wirklich abstürzt (bzw. einen Fehler wirft),
        // wenn man durch 0 teilt. Das ist ein gewünschtes Verhalten!
        assertThrows(ArithmeticException.class, () -> {
            calculator.divide(new BigDecimal("1"), new BigDecimal("0"));
        });
    }
}