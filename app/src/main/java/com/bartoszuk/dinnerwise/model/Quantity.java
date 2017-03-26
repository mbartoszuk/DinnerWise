package com.bartoszuk.dinnerwise.model;

/**
 * Created by Maria Bartoszuk on 26/03/2017.
 */

public class Quantity {

    private final int howMany;

    public static Quantity pieces(int howMany) {
        return new Quantity(howMany);
    }

    // When there is no point in specifying quantity
    public static Quantity na() {
        return new Quantity(1);
    }

    public static Quantity fromDb(String encoded) {
        return new Quantity(Integer.parseInt(encoded));
    }

    private Quantity(int howMany) {
        this.howMany = howMany;
    }

    public String of(Ingredient ingredient) {
        if (howMany == 1) {
            return ingredient.getName();
        } else {
            return String.format("%d %s", howMany, ingredient.getName());
        }
    }

    public String dbFormat() {
        return Integer.toString(howMany);
    }
}
