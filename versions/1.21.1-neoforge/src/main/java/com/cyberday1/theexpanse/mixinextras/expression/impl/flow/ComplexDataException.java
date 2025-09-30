package com.cyberday1.theexpanse.mixinextras.expression.impl.flow;

public class ComplexDataException extends RuntimeException {
    public static final RuntimeException INSTANCE = new ComplexDataException();

    private ComplexDataException() {
        setStackTrace(new StackTraceElement[0]);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }
}
