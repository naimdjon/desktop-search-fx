package org.desktopsearch.utils;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public final class Stopwatch {
    private boolean isRunning;
    private long elapsedNanos;
    private long startedNanos;

    public static Stopwatch start() {
        final Stopwatch stopwatch=new Stopwatch();
        stopwatch.isRunning = true;
        stopwatch.startedNanos = System.nanoTime();
        return stopwatch;
    }

    public Stopwatch stop() {
        long nanoTime = System.nanoTime();
        isRunning = false;
        elapsedNanos += nanoTime - startedNanos;
        return this;
    }

    private long elapsed() {
        return isRunning ? System.nanoTime() - startedNanos + elapsedNanos : elapsedNanos;
    }

    public long elapsed(TimeUnit desiredUnit) {
        return desiredUnit.convert(elapsed(), NANOSECONDS);
    }


    public String toString() {
        long nanos = elapsed();
        TimeUnit unit = chooseUnit(nanos);
        double value = (double) nanos / NANOSECONDS.convert(1, unit);
        return String.format("%." + 4 + "g %s",value, toStr(unit));
    }

    private static TimeUnit chooseUnit(long nanos) {
        if (TimeUnit.SECONDS.convert(nanos, NANOSECONDS) > 0) {
            return TimeUnit.SECONDS;
        }
        if (TimeUnit.MILLISECONDS.convert(nanos, NANOSECONDS) > 0) {
            return TimeUnit.MILLISECONDS;
        }
        if (TimeUnit.MICROSECONDS.convert(nanos, NANOSECONDS) > 0) {
            return TimeUnit.MICROSECONDS;
        }
        return NANOSECONDS;
    }

    private static String toStr(TimeUnit unit) {
        switch (unit) {
            case NANOSECONDS:
                return "ns";
            case MICROSECONDS:
                return "\u03bcs"; // Î¼s
            case MILLISECONDS:
                return "ms";
            case SECONDS:
                return "s";
            default:
                throw new AssertionError();
        }
    }
}