// EventManager.java (completo e padronizado)
package com.mycompany.sistema_transportadora.utils;

import java.util.HashSet;
import java.util.Set;

public class EventManager {
    private static final Set<Runnable> estadoListeners = new HashSet<>();
    private static final Set<Runnable> cidadeListeners = new HashSet<>();

    // Métodos para Estado
    public static void addEstadoListener(Runnable listener) {
        estadoListeners.add(listener);
    }

    public static void removeEstadoListener(Runnable listener) {
        estadoListeners.remove(listener);
    }

    public static void notifyEstadoChanged() {
        for (Runnable listener : estadoListeners) {
            listener.run();
        }
    }

    // Métodos para Cidade
    public static void addCidadeListener(Runnable listener) {
        cidadeListeners.add(listener);
    }

    public static void removeCidadeListener(Runnable listener) {
        cidadeListeners.remove(listener);
    }

    public static void notifyCidadeChanged() {
        for (Runnable listener : cidadeListeners) {
            listener.run();
        }
    }
}