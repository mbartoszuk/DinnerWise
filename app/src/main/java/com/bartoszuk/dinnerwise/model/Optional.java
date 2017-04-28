package com.bartoszuk.dinnerwise.model;

import java.util.Objects;

/**
 * Created by Maria Bartoszuk on 28/04/2017.
 */

// To avoid ClassNotFoundException for Java8's java.util.Optional.
class Optional<T> {

    private T instance;

    static <T> Optional<T> of(T instance) {
        return new Optional<>(instance);
    }

    static <T> Optional<T> empty() {
        return new Optional<>(null);
    }

    Optional(T instance) {
        this.instance = instance;
    }

    T get() {
        return instance;
    }

    boolean isPresent() {
        return instance != null;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Optional)) {
            return false;
        }
        Optional<?> that = (Optional<?>) other;
        return Objects.equals(this.instance, that.instance);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(instance);
    }
}
