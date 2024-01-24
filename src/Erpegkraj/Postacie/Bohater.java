package Erpegkraj.Postacie;

import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.PanelGry;
import Erpegkraj.Uzbrojenie.Uzbrojenie;
import Erpegkraj.Uzbrojenie.typyUzbrojenia;
import Erpegkraj.ZarządcaArkuszów;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Bohater extends Postać implements ZarządcaArkuszów{
    protected int skupienie, premiaBroni, premiaTarczy;
    protected String nazwaZdolWyj, nazwaZdolPas;

    protected FileInputStream daneBohatera = new FileInputStream("zasoby/Dane/Dane.xlsx");

    ArrayList<typyUzbrojenia> typyUzbrojenia = new ArrayList<typyUzbrojenia>(4);
    ArrayList<Uzbrojenie> uzbrojenie = new ArrayList<Uzbrojenie>(4);

    public ArrayList<Jednorazówki> ekwipunek = new ArrayList<Jednorazówki>(4);


    public Bohater(String nazwa,int rozmiar, int x, int y, PanelGry gp) throws IOException {
        super(rozmiar, x, y, gp, 150,150, 155, 175);
        this.skupienie = 0;

        przydzielDane(ZarządcaArkuszów.podzielDane(ZarządcaArkuszów.przeczytajWierszArkusza(daneBohatera, "Bohaterowie", nazwa)));

        ekwipunek.add(gp.wszytkieMożliwePrzedmioty.get(0));
        ekwipunek.add(gp.wszytkieMożliwePrzedmioty.get(1));
        ekwipunek.add(gp.wszytkieMożliwePrzedmioty.get(2));

        for (Uzbrojenie u : uzbrojenie){
            u.ustalMoc(this);
            if (u.premia != -1){
                if (Objects.equals(String.valueOf(u.typUzbrojenia), "TARCZA")){
                    premiaTarczy = u.premia;
                } else{
                    premiaBroni = u.premia;
                }
            }
        }

        maksŻycia = życie;

        ustawRozmiarGracza();
    }

    @Override
    public void zadajObrażenia(Postać odbiorcaUsługi, boolean czyPremia){
        czyAtakuje = true;
        if(czyPremia){
            odbiorcaUsługi.otrzymajObrażenia(siła*2);
            premiaBroni--;
        } else {
            odbiorcaUsługi.otrzymajObrażenia(siła);
        }
    }


    public void zdolnośćPasywna() {
        switch(nazwaZdolWyj){
            case "Śluby Krzyżowca":
                if (czyJegoKolej) skupienie += 5;
                break;
        }
    }

    public void zdolnośćWyjątkowa() {
        switch(nazwaZdolWyj){
            case "Gniew posłannika Boga":
                for(Postać wróg: gp.wrogowie){ zadajObrażenia(wróg, false); }
                break;
        }
    }

    @Override
    public void przydzielDane(HashMap<String, String> mapa) throws IOException {
        System.out.println(mapa);
        for (Map.Entry<String, String> entry : mapa.entrySet()) {
            String klucz = entry.getKey();
            switch(klucz){
                case "Nazwa":{
                    nazwa = mapa.get(klucz);
                    break;
                }
                case "ZdolnośćPasywna":{
                    nazwaZdolPas = mapa.get(klucz);
                    break;
                }
                case "ZdolnośćWyjątkowa":{
                    nazwaZdolWyj = mapa.get(klucz);
                    break;
                }
                case "PoczątkoweZdrowie":{
                    życie = Integer.valueOf(mapa.get(klucz));
                    break;
                }
                case "PoczątkowaSiła":{
                    siła = Integer.valueOf(mapa.get(klucz));
                    break;
                }
                case "PoczątkowaObrona":{
                    odporność = Integer.valueOf(mapa.get(klucz));
                    break;
                }
                case "PoczątkowaZwinność":{
                    unik = Integer.valueOf(mapa.get(klucz));
                    break;
                }
                case "RysDomyślny1":{
                    rysy.replace("d1",ImageIO.read(getClass().getResourceAsStream("/Rysy/"+mapa.get(klucz))));
                    break;
                }
                case "RysDomyślny2":{
                    rysy.replace("d2",ImageIO.read(getClass().getResourceAsStream("/Rysy/"+mapa.get(klucz))));
                    break;
                }
                case "RysAtaku1":{
                    rysy.replace("a1",ImageIO.read(getClass().getResourceAsStream("/Rysy/"+mapa.get(klucz))));
                    break;
                }
                case "RysAtaku2":{
                    rysy.replace("a2",ImageIO.read(getClass().getResourceAsStream("/Rysy/"+mapa.get(klucz))));
                    break;
                }
                case "TypUzbrojenia1", "TypUzbrojenia2", "TypUzbrojenia3", "TypUzbrojenia4":{
                    typyUzbrojenia.add(Erpegkraj.Uzbrojenie.typyUzbrojenia.valueOf(mapa.get(klucz)));
                    break;
                }
                case "Uzbrojenie1", "Uzbrojenie2", "Uzbrojenie3", "Uzbrojenie4":{
                    uzbrojenie.add(new Uzbrojenie(mapa.get(klucz)));
                    break;
                }
            }
        }
    }

    public abstract void ZdolnośćPasywna();

    public abstract void ZdolnośćWyjątkowa();
}