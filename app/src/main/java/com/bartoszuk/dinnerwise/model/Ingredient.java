package com.bartoszuk.dinnerwise.model;

import android.content.Intent;

import java.util.Objects;

/**
 * Created by Maria Bartoszuk on 26/03/2017.
 */

public class Ingredient {

    private final String category;
    private final String name;
    private final Quantity quantity;

    public static Ingredient vegetable(String name, Quantity quantity) {
        return new Ingredient("vegetable", name, quantity);
    }

    public static Ingredient grain(String name, Quantity quantity) {
        return new Ingredient("grain", name, quantity);
    }

    public static Ingredient diary(String name, Quantity quantity) {
        return new Ingredient("diary", name, quantity);
    }

    public static Ingredient userInput(String name) {
        return new Ingredient("user input", name, Quantity.na());
    }

    public static Ingredient fromDb(String encoding) {
        String category = encoding.substring(0, 20).trim();
        String quantity = encoding.substring(20, 40).trim();
        String name = encoding.substring(40).trim();
        return new Ingredient(category, name, Quantity.fromDb(quantity));
    }

    private Ingredient(String category, String name, Quantity quantity) {
        this.category = category;
        this.name = name;
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return quantity.of(this);
    }

    public String dbFormat() {
        return String.format("%1$20s%2$20s%3$s", category, quantity.dbFormat(), name);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Ingredient)) {
            return false;
        }
        Ingredient that = (Ingredient) other;
        return this.category.equals(that.category)
                && this.name.equals(that.name)
                && this.quantity.equals(that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, name, quantity);
    }
}
