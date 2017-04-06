package com.bartoszuk.dinnerwise.model;

import java.util.Objects;

/**
 * Created by Maria Bartoszuk on 26/03/2017.
 */

public class Quantity {

    private final int numerator;
    private final int denominator;
    private final String unit;

    // Optional to use with whole numbers.
    public static Quantity fraction(int numerator, int denominator) {
        return new Quantity(numerator, denominator, "");
    }

    // Helper methods in displaying quantities in the grocery list.
    public static Quantity pieces(int howMany) {
        return new Quantity(howMany, 1, "");
    }

    public static Quantity grams(int howMany) {
        return new Quantity(howMany, 1, " g");
    }

    public static Quantity tbsp(int howMany) {
        return new Quantity(howMany, 1, " tbsp");
    }

    public static Quantity tbsp(int fractionNumerator, int fractionDenominator) {
        return new Quantity(fractionNumerator, fractionDenominator, " tbsp");
    }

    public static Quantity tsp(int howMany) {
        return new Quantity(howMany, 1, " tsp");
    }

    public static Quantity tsp(int fractionNumerator, int fractionDenominator) {
        return new Quantity(fractionNumerator, fractionDenominator, " tsp");
    }

    // When there is no point in specifying quantity.
    public static Quantity na() {
        return new Quantity(1, 1, "");
    }

    public static Quantity fromDb(String encoded) {
        String[] parts = encoded.trim().split(",", -1);
        return new Quantity(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2]);
    }

    private Quantity(int numerator, int denominator, String unit) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.unit = unit;
    }

    public String of(Ingredient ingredient) {
        String quantity = "";
        if (denominator > 1) {
            quantity = String.format("%d/%d", numerator, denominator);
        } else if (numerator > 1) {
            quantity = Integer.toString(numerator);
        }
        if (!unit.equals("") && quantity.equals("")) {
            quantity = "1";
        }
        String prefix = quantity + unit;
        if (!prefix.equals("")) {
            prefix = prefix + " ";
        }
        return prefix + ingredient.getName();
    }

    public String dbFormat() {
        return String.format("%d,%d,%s", numerator, denominator, unit);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Quantity)) {
            return false;
        }
        Quantity that = (Quantity) other;
        return this.numerator == that.numerator
                && this.denominator == that.denominator
                && this.unit.equals(that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator, unit);
    }
}
