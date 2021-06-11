package com.company;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.awt.*;

/**
 * Klasa GUI korzystająca z klasy JFrame
 * zawiera w sobie deklaracje potrzebych pól dla naszego sapera
 * przechowują one odpowiednie wartości pozwalające na ustalenie rozmiaru okna
 * czy obecnego położenia kursora
 */
public class GUI extends JFrame {

    public boolean restarter = false;

    public boolean flagger = false;

    Date start = new Date();
    Date end;

    int odstep = 2;

    int sas = 0;

    public int myszX = -100;
    public int myszY = -100;

    public int resetX = 478;
    public int resetY = 7;

    public int timerX = 930;
    public int timerY = 7;

    public int infoX = 600;
    public int infoY = 48;

    String info = "Nic";

    public int sec = 0;

    public boolean wygrana = false;

    public boolean przegrana = false;

    Random rand = new Random();

    boolean[][] miny = new boolean[16][9];
    int[][] sasiedzi = new int[16][9];
    boolean[][] odkryte = new boolean[16][9];
    boolean[][] oznaczone = new boolean[16][9];
    /**
     * Metoda GUI w której określony zostaje tytuł okna jeogo rozmiar.
     * Losowane są położenia min oraz liczone jest też ilośc min otaczającyh pole
     * Tworzone są obiekty które obsługują rysowanie okna oraz ruch i kliknięcia myszy.
     */
    public GUI() {
        this.setTitle("Saper");
        this.setSize(1040, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (rand.nextInt(100) < 20) {
                    miny[i][j] = true;
                } else {
                    miny[i][j] = false;
                }
                odkryte[i][j] = false;
            }
        }

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                sas = 0;
                for (int m = 0; m < 16; m++) {
                    for (int n = 0; n < 9; n++) {
                        if (!(m == i && n == j)) {
                            if (isN(i, j, m, n) == true)
                                sas++;
                        }
                    }
                }
                sasiedzi[i][j] = sas;
            }
        }

        Plansza plansza = new Plansza();
        this.setContentPane(plansza);

        Ruch ruch = new Ruch();
        this.addMouseMotionListener(ruch);

        Click click = new Click();
        this.addMouseListener(click);
    }


    /**
     * Funckja ta odpowiada za rysowanie tła, elemntów, min,flag,timera,przycisków, odryktych i zakrtych pól.
     * Wypisuje informacje o wygranej lub przegrnej.
     */
    public class Plansza extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Color.darkGray);
            g.fillRect(0, 0, 1100, 800);
            g.setColor(Color.gray);
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j <9; j++) {
                    if(przegrana != true) {
                        g.setColor(Color.gray);

                        if (odkryte[i][j] == true) {
                            g.setColor(Color.white);
                            if (miny[i][j] == true) {
                                g.setColor(Color.red);
                            }
                        }

                        int[] posX1 = new int[4];
                        int[] posY1 = new int[4];
                        int[] posX2 = new int[4];
                        int[] posY2 = new int[4];
                        int[] posX3 = new int[4];
                        int[] posY3 = new int[4];
                        int[] posX4 = new int[4];
                        int[] posY4 = new int[4];

                        if (myszX >= odstep+i*64+5 && myszX < odstep+i*64+64-2*odstep+10 && myszY >= odstep+j*64+64+26+5 && myszY < odstep+j*64+64+26+64-2*odstep+10) {
                            g.setColor(Color.lightGray);
                        }

                        g.fillRect(odstep+i*64, odstep+j*64+64, 64-2*odstep, 64-2*odstep);

                        if(odkryte[i][j] == false) {
                            posX1[0] = odstep+i*64;
                            posX1[1] = odstep+i*64+64-2*odstep;
                            posX1[3] = odstep+i*64+10;
                            posX1[2] = odstep+i*64+64-2*odstep-10;
                            posY1[0] = odstep+j*64+64;
                            posY1[1] = odstep+j*64+64;
                            posY1[3] = odstep+j*64+64+10;
                            posY1[2] = odstep+j*64+64+10;

                            posX2[0] = odstep+i*64;
                            posX2[1] = odstep+i*64+10;
                            posX2[3] = odstep+i*64;
                            posX2[2] = odstep+i*64+10;
                            posY2[0] = odstep+j*64+64;
                            posY2[1] = odstep+j*64+10+64;
                            posY2[3] = odstep+j*64+64-2*odstep+64;
                            posY2[2] = odstep+j*64+64-2*odstep-10+64;

                            posX3[0] = odstep+i*64+64-2*odstep-10;
                            posX3[1] = odstep+i*64+64-2*odstep-10;
                            posX3[3] = odstep+i*64+64-2*odstep;
                            posX3[2] = odstep+i*64+64-2*odstep;
                            posY3[0] = odstep+j*64+64+10;
                            posY3[1] = odstep+j*64+64-2*odstep+64-10;
                            posY3[3] = odstep+j*64+64;
                            posY3[2] = odstep+j*64+64-2*odstep+64;

                            posX4[0] = odstep+i*64;
                            posX4[1] = odstep+i*64+64-2*odstep;
                            posX4[3] = odstep+i*64+10;
                            posX4[2] = odstep+i*64+64-2*odstep-10;
                            posY4[0] = odstep+j*64+64-2*odstep+64;
                            posY4[1] = odstep+j*64+64-2*odstep+64;
                            posY4[3] = odstep+j*64+64-2*odstep+64-10;
                            posY4[2] = odstep+j*64+64-2*odstep+64-10;

                            g.setColor(new Color(50,50,50));
                            g.fillPolygon(posX1,posY1,4);
                            g.setColor(new Color(80,80,80));
                            g.fillPolygon(posX2,posY2,4);
                            g.setColor(new Color(150,150,150));
                            g.fillPolygon(posX3,posY3,4);
                            g.setColor(new Color(180,180,180));
                            g.fillPolygon(posX4,posY4,4);
                        }

                        if (odkryte[i][j] == true) {
                            g.setColor(Color.black);
                            if (miny[i][j] == false) {
                                if (sasiedzi[i][j] == 1) {
                                    g.setColor(Color.blue);
                                } else if (sasiedzi[i][j] == 2) {
                                    g.setColor(Color.green);
                                } else if (sasiedzi[i][j] == 3) {
                                    g.setColor(Color.red);
                                } else if (sasiedzi[i][j] == 4) {
                                    g.setColor(new Color(0,0,128));
                                } else if (sasiedzi[i][j] == 5) {
                                    g.setColor(new Color(178,34,34));
                                } else if (sasiedzi[i][j] == 6) {
                                    g.setColor(new Color(72,209,204));
                                } else if (sasiedzi[i][j] == 7) {
                                    g.setColor(Color.black);
                                } else if (sasiedzi[i][j] == 8) {
                                    g.setColor(Color.darkGray);
                                } else if (sasiedzi[i][j] == 0) {
                                    g.setColor(Color.magenta);
                                }
                                g.setFont(new Font("Helvetica", Font.BOLD, 40));
                                g.drawString(Integer.toString(sasiedzi[i][j]), i * 64 + 22, j * 64 + 64 + 45);
                            } else  if (miny[i][j] == true) {
                                g.fillRect(i*64+10+12, j*64+64+12, 20, 40);
                                g.fillRect(i*64+12, j*64+64+10+12, 40, 20);
                                g.fillRect(i*64+5+12, j*64+64+5+12, 30, 30);
                            }
                        }
                    } else {
                                if (miny[i][j] == false) {
                                    g.setColor(Color.white);
                                    g.fillRect(odstep+i*64, odstep+j*64+64, 64-2*odstep, 64-2*odstep);
                                    if (sasiedzi[i][j] == 1) {
                                        g.setColor(Color.blue);
                                    } else if (sasiedzi[i][j] == 2) {
                                        g.setColor(Color.green);
                                    } else if (sasiedzi[i][j] == 3) {
                                        g.setColor(Color.red);
                                    } else if (sasiedzi[i][j] == 4) {
                                        g.setColor(new Color(0, 0, 128));
                                    } else if (sasiedzi[i][j] == 5) {
                                        g.setColor(new Color(178, 34, 34));
                                    } else if (sasiedzi[i][j] == 6) {
                                        g.setColor(new Color(72, 209, 204));
                                    } else if (sasiedzi[i][j] == 7) {
                                        g.setColor(Color.black);
                                    } else if (sasiedzi[i][j] == 8) {
                                        g.setColor(Color.darkGray);
                                    } else if (sasiedzi[i][j] == 0) {
                                        g.setColor(Color.magenta);
                                    }
                                    g.setFont(new Font("Helvetica", Font.BOLD, 40));
                                    g.drawString(Integer.toString(sasiedzi[i][j]), i * 64 + 22, j * 64 + 64 + 45);
                                } else if (miny[i][j] == true) {
                                    g.setColor(Color.red);
                                    g.fillRect(odstep+i*64, odstep+j*64+64, 64-2*odstep, 64-2*odstep);
                                    g.setColor(Color.black);
                                    g.fillRect(i * 64 + 10 + 12, j * 64 + 64 + 12, 20, 40);
                                    g.fillRect(i * 64 + 12, j * 64 + 64 + 10 + 12, 40, 20);
                                    g.fillRect(i * 64 + 5 + 12, j * 64 + 64 + 5 + 12, 30, 30);
                                }
                                oznaczone[i][j] = false;
                    }

                    //flagi

                    if (oznaczone[i][j] == true) {
                        g.setColor(Color.black);
                        g.fillRect(i*64+5+15, j*64+64+7+7, 5, 35);
                        g.fillRect(i*64+15, j*64+64+40+7, 20, 5);
                        g.setColor(Color.red);
                        g.fillRect(i*64+20, j*64+64+8+7, 20, 12);
                    }
                }
            }

            //przycisk

            g.setColor(new Color(35,85,35));
            g.fillRect(resetX, resetY, 100, 50);
            g.setFont(new Font("Helvetica", Font.BOLD, 20));
            g.setColor(Color.white);
            g.drawString("START",478+18, 26+14);

            int[] pX1 = new int[4];
            int[] pY1 = new int[4];
            int[] pX2 = new int[4];
            int[] pY2 = new int[4];
            int[] pX3 = new int[4];
            int[] pY3 = new int[4];
            int[] pX4 = new int[4];
            int[] pY4 = new int[4];

            pX1[0] = resetX;
            pX1[1] = resetX+100;
            pX1[3] = resetX+10;
            pX1[2] = resetX+100-10;
            pY1[0] = resetY;
            pY1[1] = resetY;
            pY1[3] = resetY+10;
            pY1[2] = resetY+10;

            pX2[0] = resetX;
            pX2[1] = resetX+10;
            pX2[3] = resetX;
            pX2[2] = resetX+10;
            pY2[0] = resetY;
            pY2[1] = resetY+10;
            pY2[3] = resetY+50;
            pY2[2] = resetY+50-10;

            pX3[0] = resetX+100-10;
            pX3[1] = resetX+100-10;
            pX3[3] = resetX+100;
            pX3[2] = resetX+100;
            pY3[0] = resetY+10;
            pY3[1] = resetY+50-10;
            pY3[3] = resetY;
            pY3[2] = resetY+50;

            pX4[0] = resetX+100;
            pX4[1] = resetX;
            pX4[3] = resetX+100-10;
            pX4[2] = resetX+10;
            pY4[0] = resetY+50;
            pY4[1] = resetY+50;
            pY4[3] = resetY+50-10;
            pY4[2] = resetY+50-10;

            g.setColor(new Color(35,60,35));
            g.fillPolygon(pX1,pY1,4);
            g.setColor(new Color(35,80,35));
            g.fillPolygon(pX2,pY2,4);
            g.setColor(new Color(35,120,35));
            g.fillPolygon(pX3,pY3,4);
            g.setColor(new Color(35,150,35));
            g.fillPolygon(pX4,pY4,4);

            //timer

            g.setColor(Color.black);
            g.fillRect(timerX, timerY, 90, 50);
            if (przegrana == false && wygrana == false) {
                sec = (int) ((new Date().getTime() - start.getTime()) / 1000);
            }
            if (sec > 999) {
                sec = 999;
            }
            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", Font.PLAIN, 50));
            if (sec < 10) {
                g.drawString("00" + Integer.toString(sec), timerX+5, timerY+40);
            } else if (sec < 100) {
                g.drawString("0" + Integer.toString(sec), timerX+5, timerY+40);
            } else {
                g.drawString(Integer.toString(sec), timerX+5, timerY+40);
            }

            //informacja o wygranej

            if (wygrana == true) {
                g.setColor(Color.green);
                info = "WYGRAŁEŚ!";
            } else if (przegrana == true) {
                g.setColor(Color.red);
                info = "PRZEGRAŁEŚ!";
            }

            if (wygrana == true || przegrana == true) {
                g.setFont(new Font("Helvetica", Font.BOLD, 35));
                g.drawString(info, infoX, infoY);
            }
        }

    }

    /**
     * Klasa ruch odpowiada za sprawdzanie pozycji myszy implementuje też klasę MouseMotionListener
     */
    public class Ruch implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            myszX = e.getX();
            myszY = e.getY();
        }
    }

    /**
     * Klasa Click implmentuje klase MouseListener odpowiada ona za sprawdzenie czy oraz w jakim miejscu nastąpiło kliknięcie myszy
     * Sprawdza też czy nastąpiło kliknięcie prawego czy lewego przycisku myszki.
     */
    public class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            myszX = e.getX();
            myszY = e.getY();
            if (SwingUtilities.isLeftMouseButton(e)){
                flagger = false;
            }
            if (SwingUtilities.isRightMouseButton(e)){
                flagger = true;
            }

            if (inBoxX() != -1 && inBoxY() != -1) {
                if (flagger == true && odkryte[inBoxX()][inBoxY()] == false) {
                    if (oznaczone[inBoxX()][inBoxY()] == false) {
                        oznaczone[inBoxX()][inBoxY()] = true;
                    } else {
                        oznaczone[inBoxX()][inBoxY()] = false;
                    }
                } else {
                    if (oznaczone[inBoxX()][inBoxY()] == false) {
                        odkryte[inBoxX()][inBoxY()] = true;
                    }
                }
            }

            if (inPrzycisk() == true) {
                resetAll();
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * Funckja ta sprawdza czy użytkownik wygrał czy przegrał.
     */
    public void sprawdzWygrana() {

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (odkryte[i][j] == true && miny[i][j] == true) {
                    przegrana = true;
                    end = new Date();
                }
            }
        }

        if (iloscOdkrytychPol() >= 144 - iloscMin()) {
            wygrana = true;
            end = new Date();
        }
    }
    /**
     * Funckja obliczna ilość wszystkich min
     */
    public int iloscMin() {
        int total = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (miny[i][j] == true) {
                    total++;
                }
            }
        }
        return total;
    }
    /**
     * Funckja oblicza ilosc odkrytych pól
     */
    public int iloscOdkrytychPol() {
        int total = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (odkryte[i][j] == true) {
                    total++;
                }
            }
        }
        return total;
    }

    /**
     * Funckja ta odpowiedzialna jest za resetowanie gry.
     */
    public void resetAll() {

        restarter = true;

        flagger = false;

        start = new Date();

        wygrana = false;
        przegrana = false;

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (rand.nextInt(100) < 20) {
                    miny[i][j] = true;
                } else {
                    miny[i][j] = false;
                }
                odkryte[i][j] = false;
                oznaczone[i][j] = false;
            }
        }

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                sas = 0;
                for (int m = 0; m < 16; m++) {
                    for (int n = 0; n < 9; n++) {
                        if (!(m == i && n == j)) {
                            if (isN(i, j, m, n) == true)
                                sas++;
                        }
                    }
                }
                sasiedzi[i][j] = sas;
            }
        }

        restarter = false;

    }
    /**
     * Funckja ta sprawdza czy myszka jest w polu START.
     */
    public boolean inPrzycisk() {
        if (myszX >= 478+7 && myszX < 478+77 && myszY >= 26+14 && myszY < 26+14+50) {
            return true;
        }
        return false;
    }


    /**
     * Funckja ta sprawdza czy myszka jest w liniach pola
     */
    public int inBoxX() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j <9; j++) {
                if (myszX >= odstep+i*64+5 && myszX < odstep+i*64+64-2*odstep+10 && myszY >= odstep+j*64+64+26+5 && myszY < odstep+j*64+64+26+64-2*odstep+10) {
                    return i;
                }
            }
        }
        return -1;
    }
    /**
     * Funckja ta sprawdza czy myszka jest w kolumach pola
     */
    public int inBoxY() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j <9; j++) {
                if (myszX >= odstep+i*64+5 && myszX < odstep+i*64+64-2*odstep+10 && myszY >= odstep+j*64+64+26+5 && myszY < odstep+j*64+64+26+64-2*odstep+10) {
                    return j;
                }
            }
        }
        return -1;
    }
    /**
     * Funckja ta sprawdza czy w polach otaczjących dane pole znajdują się miny
     */
    public boolean isN(int mX, int mY, int cX, int cY) {
        if (mX - cX <  2 && mX - cX > -2 && mY - cY < 2 && mY - cY > -2 && miny[cX][cY] == true) {
            return true;
        }
        return false;
    }

}