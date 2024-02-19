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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class PanelGry extends JPanel implements Runnable{

    //Ustawienia okna
    public final int rozmiarKafelek = 75;

    final int ilośćSłupków = 16;
    final int ilośćRzędów = 9;
    //Okienko
    final int wysokośćOkna = rozmiarKafelek * ilośćRzędów;
    final int szerokośćOkna = rozmiarKafelek * ilośćSłupków;
    //Pełnoekranowy
    int wysokośćOknaPełnoekranowego = wysokośćOkna;
    int szerokośćOknaPełnoekranowego = szerokośćOkna;
    BufferedImage tymczasoweOkno;
    Graphics2D płótno;

    //FPS
    int RNS = 60;

    ObsługiwaczKlawiszy oKlawiszy = new ObsługiwaczKlawiszy();
    Thread wątekGry;
    public final Font czcionka = new Font("Judical", Font.PLAIN, 25);

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

    //menuGłówne
    public MenuGłówne menuGłówne;
    //MenuWalki
    public MenuWalki menuWalki;

    public PanelGry() throws IOException {
        this.setPreferredSize(new Dimension(szerokośćOkna, wysokośćOkna));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(oKlawiszy);
        this.setFocusable(true);

        menuGłówne = new MenuGłówne(rozmiarKafelek,ilośćSłupków, ilośćRzędów, this, oKlawiszy);
    }
    public void przygotujGrę(){
        tymczasoweOkno = new BufferedImage(szerokośćOkna, wysokośćOkna, BufferedImage.TYPE_INT_ARGB);
        płótno = (Graphics2D)tymczasoweOkno.getGraphics();
        ustawTrybPełnoekranowy();
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
                    menuGłówne.runMenu();
                } else {
                    if (menuWalki == null) {
                        try {
                            menuWalki = new MenuWalki(rozmiarKafelek, ilośćSłupków, ilośćRzędów, this, oKlawiszy, wszytkieMożliwePrzedmioty, bohater);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    menuWalki.runMenu();
                }
                //repaint();
                rysujDoTymczasowegoOkna();
                rysujDoOkna();

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
    public void rysujDoTymczasowegoOkna(){
        płótno.setColor(Color.BLACK);
        płótno.fillRect(0,0,szerokośćOkna,wysokośćOkna);
        if (bohater == null || menuWalki == null){
            menuGłówne.runMenu();
            menuGłówne.stwórzMenu(płótno);
        } else {
            menuWalki.stwórzMenu(płótno);
        }
    }
    public void rysujDoOkna(){
        Graphics g = getGraphics();
        g.drawImage(tymczasoweOkno, 0, 0,szerokośćOknaPełnoekranowego, wysokośćOknaPełnoekranowego, null);
        g.dispose();
    }
    public void ustawTrybPełnoekranowy(){
        //pobranie rozmiaru okna
        GraphicsEnvironment środowisko = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice urządzenie = środowisko.getDefaultScreenDevice();
        urządzenie.setFullScreenWindow(Interfejs.okno);
        szerokośćOknaPełnoekranowego = Interfejs.okno.getWidth();
        wysokośćOknaPełnoekranowego = Interfejs.okno.getHeight();

        Interfejs.ustawOkno(true);
    }

    /*public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D płótno = (Graphics2D)graphics;

        if (bohater == null || menuWalki == null){
            menuGłówne.runMenu();

            menuGłówne.ustawPłótno(płótno);
            menuGłówne.stwórzMenu();
        } else {

            menuWalki.ustawPłótno(płótno);
            menuWalki.stwórzMenu();
        }

    }*/

}
