package Erpegkraj;

import Erpegkraj.Efekty.Efekty;
import Erpegkraj.Grafika.Menu;
import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.Jednorazówki.przedmioty.*;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Bohaterowie.Krzyżowiec;
import Erpegkraj.Postacie.Postać;
import Erpegkraj.Postacie.Wróg;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

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
    public ArrayList<Postać> wrogowie = new ArrayList<Postać>(){{
        add(0, new Wróg("Ognik",0, rozmiarKafelek,ilośćSłupków, ilośćRzędów, PanelGry.this));
        add(1, new Wróg("Ognik",1, rozmiarKafelek,ilośćSłupków, ilośćRzędów, PanelGry.this));
        add(2, new Wróg("Bagiennik",2, rozmiarKafelek,ilośćSłupków, ilośćRzędów, PanelGry.this));
    }};
    //Wszystkie Możliwe Przedmioty
    public ArrayList<Jednorazówki> wszytkieMożliwePrzedmioty = new ArrayList<Jednorazówki>(){{
        add(0, new napójZdrowia(wrogowie, PanelGry.this));
        add(1, new kamień(wrogowie, PanelGry.this));
        add(2, new trutka(wrogowie, PanelGry.this));
    }};
    //Bohater
    public Bohater bohater = new Krzyżowiec(rozmiarKafelek,ilośćSłupków, ilośćRzędów, this);

    //Menu
    Menu menu = new Menu(rozmiarKafelek,ilośćSłupków, ilośćRzędów, this, oKlawiszy);

    public PanelGry() throws IOException {
        this.setPreferredSize(new Dimension(szerokośćOkna, wysokośćOkna));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(oKlawiszy);
        this.setFocusable(true);

        for (Jednorazówki j: wszytkieMożliwePrzedmioty){
            j.ustawBohatera(bohater);
        }
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
        int odczekanieNaKolejneWejście = 0;
        int odczekanieDomyślnegoŻyworysu = 30;
        boolean razNaRundę = true;

        while (wątekGry != null){
            tenRaz = System.nanoTime();

            delta += (tenRaz - ostatniRaz) / przerwaWRysowaniu;
            czasomierz += tenRaz - ostatniRaz;
            ostatniRaz = tenRaz;

            if (delta >= 1) {
                odczekanieDomyślnegoŻyworysu = bohater.odnów(odczekanieDomyślnegoŻyworysu);
                if (odczekanieDomyślnegoŻyworysu == 30){
                    for (Postać wróg: wrogowie){
                        wróg.odnów(0);
                    }
                    usuńWroga();
                }
                if (bohater.czyJegoKolej) {
                    if (razNaRundę){
                        razNaRundę=false;
                        nowaRunda();
                    }
                    if (odczekanieNaKolejneWejście == 0) {
                        String[] statut = new String[0];
                        try {
                            statut = odnów();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (statut[0].equals("przesunięto")) {
                            odczekanieNaKolejneWejście = 10;
                        }
                        if (statut[1].equals("Akcja")) {
                            for (Postać wróg : wrogowie) {
                                wróg.czyJegoKolej = true;
                            }
                            bohater.czyJegoKolej = false;
                        }
                    } else odczekanieNaKolejneWejście--;
                }else {
                    if (!bohater.czyAtakuje){
                        boolean kontynuować = true;
                        for (Postać wróg: wrogowie){
                            if (wróg.czyAtakuje && !bohater.czyUmiera){
                                kontynuować = false;
                                break;
                            }
                        }
                        if (kontynuować) {
                            for (int i = 0; i < wrogowie.size(); i++) {
                                if (wrogowie.get(i).czyJegoKolej) {
                                    wrogowie.get(i).zadajObrażenia(bohater, false);
                                    wrogowie.get(i).czyJegoKolej = false;
                                    break;
                                }else if (!wrogowie.get(wrogowie.size()-1).czyJegoKolej) {
                                    System.out.println("powtórka");
                                    bohater.czyJegoKolej = true;
                                    razNaRundę = true;
                                    menu.nowaRunda();
                                }

                            }
                        }
                    }
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
        return menu.odnów();
    }
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        Graphics2D płótno = (Graphics2D)graphics;
        
        

        bohater.ustawPłótno(płótno);
        bohater.stwórzPostać();

        for (Postać wróg: wrogowie) {
            wróg.ustawPłótno(płótno);
            wróg.stwórzPostać();
        }

        menu.ustawPłótno(płótno);
        menu.stwórzMenu();

    }

    public void usuńWroga(){
        for (int i = 0; i < wrogowie.size(); i++){
            if (wrogowie.get(i).wartośćAlfa == 0){
                for (int j = i; j < wrogowie.size()-1; j++){
                    wrogowie.set(j, wrogowie.get(j+1));
                    wrogowie.get(j).postaćX = Wróg.ustawPołożenieX(j, ilośćSłupków, rozmiarKafelek);
                }
                wrogowie.remove(wrogowie.size()-1);
            }
        }
        //System.out.println(wrogowie);
    }

    public void nowaRunda(){
        for(Postać w: wrogowie){
            System.out.println(w);
            for (Map.Entry<Efekty,Integer> mapa: w.efekty.entrySet()) {
                System.out.println(mapa);
                Efekty efekt = mapa.getKey();

                efekt.działanieGdyUpłynieRunda();
                mapa.setValue(mapa.getValue()-1);
            }
        }

        for (Map.Entry<Efekty,Integer> mapa: bohater.efekty.entrySet()) {
            Efekty efekt = mapa.getKey();

            efekt.działanieGdyUpłynieRunda();
            mapa.setValue(mapa.getValue()-1);
        }
    }

}
