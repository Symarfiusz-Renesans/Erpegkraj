package Erpegkraj.Postacie;

import Erpegkraj.Efekty.Efekty;
import Erpegkraj.Grafika.Przerozmierzacz;
import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.PanelGry;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

public abstract class Postać extends Erpegkraj.Grafika.Grafika implements Przerozmierzacz, ActionListener {
    PanelGry panelGry;

    public int postaćX, postaćY, szerokość, wysokość;
    public String nazwa;

    public boolean widoczny = true, czyJegoKolej = true, czyUmiera = false;
    Timer czasZniknięcia = new Timer(20, this);
    public float wartośćAlfa = 1f;

    protected HashMap <String ,BufferedImage> rysy = new HashMap<String, BufferedImage>(){{
        put("d1", null);
        put("d2", null);
        put("a1", null);
        put("a2", null);
        put("o1", null);
        put("o2", null);
        put("oo1", null);
        put("oo2", null);
        put("u1", null);
        put("u2", null);
        put("ś1", null);
        put("ś2", null);
    }};
    public BufferedImage domyślnyRys;

    protected HashMap<Efekty, Integer> efekty = new HashMap<>();

    public int maksŻycia,życie,siła,odporność,unik, odebraneObrażenia = -1;
    public int życieUlep, siłaUlep, odpornośćUlep,  unikUlep;
    protected int czasObrony = 0;
    protected HashMap<String, Integer> czasEfektów = new HashMap<>();

    public boolean czyAtakuje = false;
    public int czyCzasMigania = 10;

    public Postać(int rozmiar, int x, int y, PanelGry gp, int postaćX, int postaćY, int szerokość, int wysokość ){
        super(rozmiar, x, y, gp);
        this.wysokość = wysokość;
        this.szerokość = szerokość;

        this.postaćX = postaćX;
        this.postaćY = postaćY;
    }
    public void otrzymajObrażenia(int obrażenia){
        if (!czyUniknieObrażeń()){
            odebraneObrażenia = (obrażenia-efetkywnośćObrony());
            if (odebraneObrażenia < 0){
                odebraneObrażenia = 0;
            }
            życie -= odebraneObrażenia;
        } else odebraneObrażenia = 0;

        if (życie < 0){
            życie = 0;
            czyUmiera = true;
        }
    }
    public void zadajObrażenia(Postać odbiorcaUsługi, boolean czyPremia){
        if(!czyUmiera){
            czyAtakuje = true;
            odbiorcaUsługi.otrzymajObrażenia(siłaUlep);
        }
    }
    public void dodajEfekt(Efekty jaki, int naIle){
        jaki.ustawPostać(this);
        efekty.put(jaki, naIle);
        jaki.działaniePrzyOtrzymaniu();
    }

    public void ustawRozmiarGracza(){

        for (Map.Entry<String, BufferedImage> mapa: rysy.entrySet()) {
            String klucz = mapa.getKey();
            BufferedImage wartość = mapa.getValue();

            if (wartość != null) rysy.put(klucz, Przerozmierzacz.przerozmierzanie(wartość, 155, 175));
        }

        domyślnyRys = rysy.get("d1");
    }
    public int odnów(int czyJużCzas) {
        if (życie == 0) {
            czasZniknięcia.start();
            czyUmiera = true;
            return 30;
        } else if (czyJużCzas == 0){
            for (Efekty e: efekty){
                e.działanieGdyUpłynieRunda();
            }
            if (czyAtakuje){
                if (domyślnyRys != rysy.get("a1") && domyślnyRys != rysy.get("a2")) {
                    domyślnyRys = rysy.get("a1");
                }
                else if (domyślnyRys == rysy.get("a1")){
                    domyślnyRys = rysy.get("a2");
                    czyAtakuje = false;
                }
            }else {
                if (domyślnyRys == rysy.get("d1")) domyślnyRys = rysy.get("d2");
                else domyślnyRys = rysy.get("d1");
            }
            return 30;
        } else return czyJużCzas-1;
    }
    public void stwórzPostać(){
        płótno.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, wartośćAlfa));
        if (widoczny) płótno.drawImage(domyślnyRys, postaćX, postaćY, null);
        płótno.setColor(Color.RED);
        płótno.fillRect(postaćX, postaćY-30, szerokość, 20);
        płótno.setColor(Color.GREEN);


        płótno.fillRect(postaćX, postaćY-30, (maksŻycia == życie)?szerokość:(int)((szerokość/maksŻycia)*życie), 20);
        płótno.setFont(gp.czcionka);
        płótno.setColor(Color.WHITE);
        płótno.drawString(String.valueOf(życie), postaćX+5, postaćY-20);
        if (odebraneObrażenia >= 0){
            if (odebraneObrażenia == 0){
                płótno.drawString("Unik!", postaćX+szerokość-100, postaćY-20);
            }else {
                płótno.drawString("-"+String.valueOf(odebraneObrażenia),postaćX+szerokość-50, postaćY-20);
            }
            //odebraneObrażenia = -1;
        }
    }
    public void migawka(){
        widoczny = !widoczny;
    }
    private boolean czyUniknieObrażeń(){
        int losowaLiczba = (int)(Math.random()*30+1);
        return ((losowaLiczba - unikUlep) <= 0);
    }
    private int efetkywnośćObrony(){
        int losowaLiczba = (int)(Math.random()*odpornośćUlep);
        return losowaLiczba;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        wartośćAlfa -= 0.05f;
        if (wartośćAlfa <= 0){
            wartośćAlfa = 0;
            czasZniknięcia.stop();
        }
    }
    public void czasDziałaniaEfektów(int jaki, Jednorazówki użytyPrzedmiot){
        for (Map.Entry<Efekty, Integer> m : efekty.entrySet()) {
            Efekty klucz = m.getKey();
            int wartość = m.getValue();
            switch(jaki){
                case 1:
                    klucz.działaniePrzyAtaku();
                    break;
                case 2:
                    klucz.działaniePrzyUżyciuPrzedmiotu(użytyPrzedmiot);
                    break;
                case 3:
                    klucz.działanieGdyUpłynieRunda();
                    break;
                case 4:
                    klucz.działaniePrzedOdebraniemObrażeń();
                    break;
                case 5:
                    klucz.działaniePrzyOtrzymaniu();
                    break;
                case 6:
                    klucz.działaniePrzyWyczerpaniu();
                    break;
            }
            if (wartość != 0) wartość--;
            else efekty.remove(klucz, wartość);
        }
    }
}
