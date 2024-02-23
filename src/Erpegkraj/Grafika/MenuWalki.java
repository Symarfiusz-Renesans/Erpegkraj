package Erpegkraj.Grafika;

import Erpegkraj.Efekty.Efekty;
import Erpegkraj.Efekty.efekty.Dozbrojenie;
import Erpegkraj.Efekty.efekty.DozbrojeniePlus;
import Erpegkraj.Grafika.DaneWMenu.Akcje.Atak;
import Erpegkraj.Grafika.DaneWMenu.Akcje.Obrona;
import Erpegkraj.Grafika.DaneWMenu.PołożenieWMenuWalki;
import Erpegkraj.Grafika.DaneWMenu.typAkcjiWMenuWalki;
import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.ObsługiwaczKlawiszy;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Postać;
import Erpegkraj.Postacie.Wróg;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MenuWalki extends Menu{

    protected PanelGry gp;

    protected PołożenieWMenuWalki miejsce = PołożenieWMenuWalki.WybórTypuAkcji;
    protected int poziomCEL;
    protected int wybórTypuAkcji = -1;
    protected int wybórAkcji = -1;
    protected int wybórCelu = -1;

    boolean razNaRundę = true;

    public ArrayList<Postać> wrogowie = new ArrayList<Postać>();

    public Bohater bohater;

    public MenuWalki(int rozmiar, int x, int y, PanelGry gp, ObsługiwaczKlawiszy ok, ArrayList<Jednorazówki> jednorazówki, Bohater bohater) throws IOException {
        super(rozmiar, x, y, gp, ok);

        this.gp = gp;

        this.bohater = bohater;
        bohater.zmieńPołożeniePostaci(1);

        System.out.println(miejsce);

        wrogowie.add(0, new Wróg("Ognik",0, rozmiarKafelek,ilośćSłupków, ilośćRzędów, gp));
        wrogowie.add(1, new Wróg("Ognik",1, rozmiarKafelek,ilośćSłupków, ilośćRzędów, gp));
        wrogowie.add(2, new Wróg("Bagiennik",2, rozmiarKafelek,ilośćSłupków, ilośćRzędów, gp));

        for (Jednorazówki j: jednorazówki){
            j.ustawBohatera(bohater);
            j.ustawWrogów(wrogowie);
        }

    }

    @Override
    public void runMenu() {
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
                String[] statut;
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
                            for (Map.Entry<Efekty,Integer> mapa: wrogowie.get(i).efekty.entrySet()) {
                                Efekty efekt = mapa.getKey();

                                efekt.działaniePrzyAtaku();
                            }
                            wrogowie.get(i).zadajObrażenia(bohater, false);
                            wrogowie.get(i).czyJegoKolej = false;
                            break;
                        }else if (!wrogowie.get(wrogowie.size()-1).czyJegoKolej) {
                            bohater.czyJegoKolej = true;
                            razNaRundę = true;
                            miejsce = PołożenieWMenuWalki.WybórTypuAkcji;
                        }

                    }
                }
            }
        }
    }

    @Override
    public String[] odnów() throws IOException {

        ilośćWrogów=ustalIlośćWrogów(gp);

        String[] statut = {"","WybórTypuAkcji"};

        if (!Objects.equals(miejsce.ustalEnumaPrzezId(miejsce.ustalId()), "WybórCelu")){
            if (ok.góraWciśnięta){
                if (poziom != 3) {
                    zaznaczenieY -= zaznaczenieWys;
                    poziom++;
                    statut[0] = "przesunięto";
                }
            }
            if (ok.dółWciśnięty){
                if (poziom != ilośćPoziomów) {
                    zaznaczenieY += zaznaczenieWys;
                    poziom--;
                    statut[0] = "przesunięto";
                }
            }
        }
        if (Objects.equals(miejsce.ustalEnumaPrzezId(miejsce.ustalId()), "WybórCelu")){
            ilośćWrogów=ustalIlośćWrogów(gp);
            if (ok.lewoWciśnięte){
                if (poziomCEL != ilośćWrogów-1) {
                    wrogowie.get(poziomCEL).widoczny =true;
                    poziomCEL++;
                    statut[0] = "przesunięto";
                }
            }
            if (ok.prawoWciśnięte){
                if (poziomCEL != 0) {
                    wrogowie.get(poziomCEL).widoczny =true;
                    poziomCEL--;
                    statut[0] = "przesunięto";
                }
            }
        }
        if (ok.potwierdźWciśnięte)
            if (miejsce.ustalId() < 3) {
                //System.out.println( PołożenieWMenu.valueOf( miejsce.ustalEnumaPrzezId( miejsce.ustalId() + 1) ) );
                miejsce = miejsce.valueOf( miejsce.ustalEnumaPrzezId( miejsce.ustalId() + 1));
                poziomCEL = ilośćWrogów-1;
                wrogowie.get(poziomCEL).widoczny =true;
                ustalPołożenieZaznaczenia();
                statut[0] = "przesunięto";
                statut[1] = miejsce.ustalEnumaPrzezId( miejsce.ustalId());
                if (Objects.equals(statut[1], "Akcja")){
                    System.out.println(akcja);
                    switch (akcja){
                        case "Atakuj":
                            bohater.zadajObrażenia(wrogowie.get(poziomCEL), false);
                            break;
                        case "AtakujZPremią":
                            bohater.zadajObrażenia(wrogowie.get(poziomCEL), true);
                            break;
                        case "AtakSpecjalny":
                            bohater.ZdolnośćWyjątkowa();
                            break;
                        case "Obrona":
                            bohater.dodajEfekt(new Dozbrojenie(gp), 3);
                            break;
                        case "ObronaZPremią":
                            bohater.dodajEfekt(new DozbrojeniePlus(gp), 3);
                            break;
                        default:
                            for (Jednorazówki j: gp.wszytkieMożliwePrzedmioty){
                                if(Objects.equals(akcja, j.nazwa)){
                                    j.działanie(poziomCEL);
                                    for(Map.Entry<Efekty, Integer> e: bohater.efekty.entrySet()){
                                        Efekty klucz = e.getKey();

                                        klucz.działaniePrzyUżyciuPrzedmiotu(j.nazwa);
                                    }
                                }
                            }
                    }
                }
            }
        if (ok.cofnijWciśnięte){
            if (miejsce.ustalId() > 0){
                //System.out.println(PołożenieWMenu.valueOf( miejsce.ustalEnumaPrzezId(miejsce.ustalId() - 1)));
                miejsce = miejsce.valueOf( PołożenieWMenuWalki.ustalEnumaPrzezId(miejsce.ustalId() - 1));
                wrogowie.get(poziomCEL).widoczny =true;
                ustalPołożenieZaznaczenia();
                statut[0] = "przesunięto";
                statut[1] = PołożenieWMenuWalki.ustalEnumaPrzezId( miejsce.ustalId());
            }
        }
        return statut;
    }

    @Override
    public void stwórzMenu(Graphics2D płótno) {
        płótno.setFont(gp.czcionka);
        płótno.drawImage(tło, 0, 0, null);
        if (wrogowie.size() == 0) {
            płótno.setColor(Color.WHITE);
            płótno.drawString("Wygrałeś!", obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
        } else {
            for(Postać wróg: wrogowie){
                wróg.ustawPłótno(płótno);
                wróg.stwórzPostać();
            }
            bohater.ustawPłótno(płótno);
            bohater.stwórzPostać();

            płótno.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            płótno.setColor(Color.orange);
            płótno.fillRect(obwódkaX, obwódkaY, obwódkaSzer, obwódkaWys);
            płótno.fillRect(obwódkaX + obwódkaSzer / 3, obwódkaY - zaznaczenieWys-15, zaznaczenieSzer, zaznaczenieWys);
            if (wybórCelu != -1) {
                płótno.setColor(Color.WHITE);
                płótno.drawString("Runda    wrogów...", obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
            } else if (wybórAkcji != -1) {
                Postać wróg = wrogowie.get(poziomCEL);
                if (wróg.czyCzasMigania == 0) {
                    wróg.migawka();
                    wróg.czyCzasMigania = 10;
                } else wróg.czyCzasMigania--;

                płótno.setColor(Color.WHITE);

                płótno.drawString("Wybierz    cel:", obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);

            } else if (wybórTypuAkcji != -1) {
                płótno.setColor(Color.RED);
                płótno.fillRect(zaznaczenieX, zaznaczenieY, zaznaczenieSzer, zaznaczenieWys);
                płótno.setColor(Color.WHITE);

                int ilośćPoziomów = 3;
                switch (wybórTypuAkcji) {
                    case 0: {
                    }
                    case 1: {
                        for (Jednorazówki j : bohater.ekwipunek) {
                            płótno.drawString(j.nazwa, obwódkaX + (obwódkaSzer / 3) + 10, obwódkaY + (zaznaczenieWys * (ilośćPoziomów - 3) * (-1) + 30));
                            ilośćPoziomów--;
                        }
                        płótno.drawString(String.valueOf(Obrona.ustalOpis((poziom - 3) * (-1))), obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
                        break;
                    }
                    case 2: {
                        for (Obrona obrona : Obrona.values()) {
                            płótno.drawString(obrona.ustalTreść((ilośćPoziomów - 3) * (-1)), obwódkaX + (obwódkaSzer / 3) + 10, obwódkaY + (zaznaczenieWys * (ilośćPoziomów - 3) * (-1) + 30));
                            ilośćPoziomów--;
                        }
                        płótno.drawString(String.valueOf(Obrona.ustalOpis((poziom - 3) * (-1))), obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
                        break;
                    }
                    case 3: {
                        for (Atak atak : Atak.values()) {
                            płótno.drawString(atak.ustalTreść((ilośćPoziomów - 3) * (-1)), obwódkaX + (obwódkaSzer / 3) + 10, obwódkaY + (zaznaczenieWys * (ilośćPoziomów - 3) * (-1) + 30));
                            ilośćPoziomów--;
                        }
                        płótno.drawString(String.valueOf(Atak.ustalOpis((poziom - 3) * (-1))), obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
                        break;
                    }
                }
                ilośćPoziomów++;

            } else {
                płótno.setColor(Color.RED);
                płótno.fillRect(zaznaczenieX, zaznaczenieY, zaznaczenieSzer, zaznaczenieWys);
                płótno.setColor(Color.WHITE);
                ilośćPoziomów = 0;
                switch (poziom) {
                    case 3: {
                        płótno.drawString(String.valueOf(typAkcjiWMenuWalki.ustalEnumaPrzezId(3)), obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
                        break;
                    }
                    case 2: {
                        płótno.drawString(typAkcjiWMenuWalki.ustalEnumaPrzezId(2), obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
                        break;
                    }
                    case 1: {
                        płótno.drawString(typAkcjiWMenuWalki.ustalEnumaPrzezId(1), obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
                        break;
                    }
                    case 0: {
                        płótno.drawString(typAkcjiWMenuWalki.ustalEnumaPrzezId(0), obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
                        break;
                    }
                }
            }


            płótno.setFont(gp.czcionka);
            płótno.setColor(Color.WHITE);
            płótno.drawString(typAkcjiWMenuWalki.Atakuj.name(), obwódkaX + 10, obwódkaY + 30);
            płótno.drawString(typAkcjiWMenuWalki.BrońSię.name(), obwódkaX + 10, obwódkaY + 30 + zaznaczenieWys);
            płótno.drawString(typAkcjiWMenuWalki.Ekwipunek.name(), obwódkaX + 10, obwódkaY + 30 + zaznaczenieWys * 2);
            płótno.drawString(typAkcjiWMenuWalki.Uciekaj.name(), obwódkaX + 10, obwódkaY + 30 + zaznaczenieWys * 3);

            płótno.drawImage(obwódka, obwódkaX-25, obwódkaY-80, null);

            if (miejsce == PołożenieWMenuWalki.WybórAkcji) {

            } else if (miejsce == PołożenieWMenuWalki.WybórCelu) {

            }
        }
    }

    @Override
    protected void ustalPołożenieZaznaczenia() {
        ilośćWrogów = ustalIlośćWrogów(gp);
        switch(miejsce){
            case WybórTypuAkcji:{
                poziomCEL = ilośćWrogów-1;
                zaznaczenieY = obwódkaY;
                zaznaczenieX = obwódkaX;
                wybórTypuAkcji = -1;
                wybórAkcji = -1;
                wybórCelu = -1;
                poziom = 3;
                break;
            }
            case WybórAkcji:{
                zaznaczenieY = obwódkaY;
                zaznaczenieX = obwódkaX + (obwódkaSzer / 3);
                wybórTypuAkcji = poziom;
                wybórAkcji = -1;
                wybórCelu = -1;
                poziom = 3;
                akcja = "";
                break;
            }
            case WybórCelu:{
                poziomCEL = ilośćWrogów-1;
                wybórAkcji = poziom;
                wybórCelu = -1;
                switch (wybórTypuAkcji){
                    case 3:{
                        for (Atak atak: Atak.values()) {
                            if (atak.id == (poziom - 3) * -1){
                                akcja = atak.name();
                                break;
                            }
                        }
                        break;
                    }
                    case 2:{
                        for (Obrona obrona: Obrona.values()){
                            if(obrona.id == (poziom - 3) * -1){
                                akcja = obrona.name();
                                miejsce = miejsce.Akcja;
                                break;
                            }
                        }
                        break;
                    }
                    case 1:{
                        for (int i = 3; i >= (bohater.ekwipunek.size()-4)*-1 ; i--){
                            if(i == poziom){
                                akcja = bohater.ekwipunek.get(((i-4)*-1)-1).nazwa;
                            }
                        }
                    }
                }
                System.out.println(akcja);
                if (miejsce != PołożenieWMenuWalki.Akcja) break;
            }
            case Akcja:{
                wybórTypuAkcji = 3;
                wybórAkcji = 0;
                wybórCelu = poziomCEL;
            }
        }
    }

    public int ustalIlośćWrogów(PanelGry gp){
        int ilość = 0;

        for (Postać wróg: wrogowie){
            ilość++;
        }

        return ilość;
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
    }

    public void nowaRunda(){
        for(Postać w: wrogowie) {
            for (Map.Entry<Efekty,Integer> mapa: w.efekty.entrySet()) {
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
