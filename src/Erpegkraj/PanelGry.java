package Erpegkraj;

import Erpegkraj.Grafika.Menu;
import Erpegkraj.Grafika.MenuGłówne;
import Erpegkraj.Grafika.MenuWalki;
import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.Jednorazówki.przedmioty.*;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Postać;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class PanelGry extends JPanel implements Runnable{

    //Ustawienia okna
    public final int rozmiarKafelek = 75;

    final int ilośćSłupków = 16;
    final int ilośćRzędów = 9;
    final int wysokośćOkna = rozmiarKafelek * ilośćRzędów;
    final int szerokośćOkna = rozmiarKafelek * ilośćSłupków;

    //FPS
    int RNS = 60;

    ObsługiwaczKlawiszy oKlawiszy = new ObsługiwaczKlawiszy();
    Thread wątekGry;
    public final Font czcionka = new Font("Judical", Font.PLAIN, 25);

    //Wrogowie
    public ArrayList<Postać> wrogowie = new ArrayList<Postać>();
    public ArrayList<Postać> WszyscyMożliwiWrogowie = new ArrayList<Postać>(){{
    }};
    //Wszystkie Możliwe Przedmioty
    public ArrayList<Jednorazówki> wszytkieMożliwePrzedmioty = new ArrayList<Jednorazówki>(){{
        add(0, new napójZdrowia(PanelGry.this));
        add(1, new kamień(PanelGry.this));
        add(2, new trutka(PanelGry.this));
    }};
    //Bohater
    public Bohater bohater;

    //MenuGłówne
    Menu MenuGłówne = new MenuGłówne(rozmiarKafelek,ilośćSłupków, ilośćRzędów, this, oKlawiszy);
    //MenuWalki
    Menu MenuWalki;

    public PanelGry() throws IOException {
        this.setPreferredSize(new Dimension(szerokośćOkna, wysokośćOkna));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(oKlawiszy);
        this.setFocusable(true);
    }

    public void zacznijWątekGry(){
        wątekGry = new Thread(this);
        wątekGry.start();
    }

    //pętla rodzaju delta
    @Override
    public void run() {
        double przerwaWRysowaniu = 1000000000 / RNS; // 0. 016 sekund
        double delta = 0;
        long ostatniRaz = System.nanoTime();
        long tenRaz;
        long czasomierz = 0;
        int liczbaRamekNaSekundę = 0;

        while (wątekGry != null){
            tenRaz = System.nanoTime();

            delta += (tenRaz - ostatniRaz) / przerwaWRysowaniu;
            czasomierz += tenRaz - ostatniRaz;
            ostatniRaz = tenRaz;

            if (delta >= 1) {
                if (bohater == null){
                    MenuGłówne.runMenu();
                } else {
                    try {
                        MenuWalki = new MenuWalki(rozmiarKafelek,ilośćSłupków, ilośćRzędów, this, oKlawiszy, wszytkieMożliwePrzedmioty, bohater);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    MenuWalki.runMenu();
                }
                repaint();

                delta--;
                liczbaRamekNaSekundę ++;
            }
            if (czasomierz >= 1000000000){
                System.out.println("RNS: "+ liczbaRamekNaSekundę);
                liczbaRamekNaSekundę = 0;
                czasomierz = 0;
            }
        }
    }

    public String[] odnów() throws IOException {
        if (bohater == null){
            return MenuGłówne.odnów();
        } else {
            return MenuWalki.odnów();
        }
    }
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        Graphics2D płótno = (Graphics2D)graphics;



        if (bohater == null || MenuWalki == null){
            MenuGłówne.runMenu();

            MenuGłówne.ustawPłótno(płótno);
            MenuGłówne.stwórzMenu();
        } else {
            bohater.ustawPłótno(płótno);
            bohater.stwórzPostać();

            for (Postać wróg: wrogowie) {
                wróg.ustawPłótno(płótno);
                wróg.stwórzPostać();
            }

            MenuWalki.ustawPłótno(płótno);
            MenuWalki.stwórzMenu();
        }

    }


    public void ustawBohatera(Bohater bohater){
        this.bohater = bohater;
    }

}
