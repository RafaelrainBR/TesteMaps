package me.rafaelrain.testemaps.util;

import java.util.function.Supplier;

public class TimeCounting {

    public static <T> T countTime(String text, Supplier<T> supplier) {
        System.out.println("INICIANDO TAREFA: " + text);
        final long before = System.currentTimeMillis();

        final T t = supplier.get();

        System.out.println("TAREFA ACABADA: " + text + String.format("(%dms)", System.currentTimeMillis() - before));
        System.out.println();
        return t;
    }

    public static void countTime(String text, Runnable runnable) {
        System.out.println("INICIANDO TAREFA: " + text);
        final long before = System.currentTimeMillis();

        runnable.run();
        System.out.println("TAREFA ACABADA: " + text + String.format("(%dms)", System.currentTimeMillis() - before));
        System.out.println();
    }
}
