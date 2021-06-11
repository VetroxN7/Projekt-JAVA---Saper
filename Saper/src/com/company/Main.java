package com.company;

import javax.swing.*;

/**
 * Funkcja main jest opowiedzialna za uruchomienie sapera
 * zawiera ona obiekt klasy GUI.
 * funckja run w której wywołane są funckje sapera.
 *
 */
public class Main implements Runnable {

    GUI gui = new GUI();

    public static void main(String[] args) {
        new Thread(new Main()).start();
    }

    @Override
    public void run() {
        while(true) {
            gui.repaint();
            if (gui.restarter == false) {
                gui.sprawdzWygrana();
            }
        }
    }
}