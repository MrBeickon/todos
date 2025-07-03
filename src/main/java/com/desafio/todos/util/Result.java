package com.desafio.todos.util;

import lombok.Getter;

import java.util.function.Supplier;


@Getter
public class Result<T, E extends Exception> {
    private final T data;
    private final E error;

    private Result(T data, E error) {
        this.data = data;
        this.error = error;
    }

    public static <T, E extends Exception> Result<T, E> success(T data) {
        return new Result<>(data, null);
    }

    public static <T, E extends Exception> Result<T, E> failure(E error) {
        return new Result<>(null, error);
    }

    public boolean isSuccess() {
        return error == null;
    }

    public boolean isFailure() {
        return error != null;
    }

    // Wrapper function
    public static <T> Result<T, Exception> tryCatch(Supplier<T> supplier) {
        try {
            return success(supplier.get());
        } catch (Exception e) {
            return failure(e);
        }
    }
}
