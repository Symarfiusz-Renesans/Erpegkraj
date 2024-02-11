package Erpegkraj.Grafika;

import Erpegkraj.Efekty.Efekty;
import Erpegkraj.Efekty.efekty.Dozbrojenie;
import Erpegkraj.Efekty.efekty.DozbrojeniePlus;
import Erpegkraj.Grafika.DaneWMenu.Akcje.Atak;
import Erpegkraj.Grafika.DaneWMenu.Akcje.Obrona;
import Erpegkraj.Grafika.DaneWMenu.PołożenieWMenu;
import Erpegkraj.Grafika.DaneWMenu.typAkcji;
import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.ObsługiwaczKlawiszy;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Postać;

import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class MenuWalki extends Menu{

    public MenuWalki(int rozmiar, int x, int y, PanelGry gp, ObsługiwaczKlawiszy ok) {
        super(rozmiar, x, y, gp, ok);

        ilośćWrogów=ustalIlośćWrogów(gp);
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
                    gp.wrogowie.get(poziomCEL).widoczny =true;
                    poziomCEL++;
                    statut[0] = "przesunięto";
                }
            }
            if (ok.prawoWciśnięte){
                if (poziomCEL != 0) {
                    gp.wrogowie.get(poziomCEL).widoczny =true;
                    poziomCEL--;
                    statut[0] = "przesunięto";
                }
            }
        }
        if (ok.potwierdźWciśnięte)
            if (miejsce.ustalId() < 3) {
                //System.out.println( PołożenieWMenu.valueOf( miejsce.ustalEnumaPrzezId( miejsce.ustalId() + 1) ) );
                miejsce = miejsce.valueOf( miejsce.ustalEnumaPrzezId( miejsce.ustalId() + 1));
                gp.wrogowie.get(poziomCEL).widoczny =true;
                ustalPołożenieZaznaczenia();
                statut[0] = "przesunięto";
                statut[1] = miejsce.ustalEnumaPrzezId( miejsce.ustalId());
                if (Objects.equals(statut[1], "Akcja")){
                    System.out.println(akcja);
                    switch (akcja){
                        case "Atakuj":
                            gp.bohater.zadajObrażenia(gp.wrogowie.get(poziomCEL), false);
                            break;
                        case "AtakujZPremią":
                            gp.bohater.zadajObrażenia(gp.wrogowie.get(poziomCEL), true);
                            break;
                        case "AtakSpecjalny":
                            gp.bohater.ZdolnośćWyjątkowa();
                            break;
                        case "Obrona":
                            gp.bohater.dodajEfekt(new Dozbrojenie(gp), 3);
                            break;
                        case "ObronaZPremią":
                            gp.bohater.dodajEfekt(new DozbrojeniePlus(gp), 3);
                            break;
                        default:
                            for (Jednorazówki j: gp.wszytkieMożliwePrzedmioty){
                                if(Objects.equals(akcja, j.nazwa)){
                                    j.działanie(poziomCEL);
                                    for(Map.Entry<Efekty, Integer> e: gp.bohater.efekty.entrySet()){
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
                miejsce = miejsce.valueOf( PołożenieWMenu.ustalEnumaPrzezId(miejsce.ustalId() - 1));
                gp.wrogowie.get(poziomCEL).widoczny =true;
                ustalPołożenieZaznaczenia();
                statut[0] = "przesunięto";
                statut[1] = PołożenieWMenu.ustalEnumaPrzezId( miejsce.ustalId());
            }
        }
        return statut;
    }

    @Override
    public void stwórzMenu() {

        if (gp.wrogowie.size() == 0) {
            płótno.drawString("Wygrałeś!", obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
        } else {
            płótno.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            płótno.setColor(Color.orange);
            płótno.fillRect(obwódkaX, obwódkaY, obwódkaSzer, obwódkaWys);
            płótno.fillRect(obwódkaX + obwódkaSzer / 3, obwódkaY - zaznaczenieWys, zaznaczenieSzer, zaznaczenieWys);

            if (wybórCelu != -1) {
                płótno.setColor(Color.WHITE);
                płótno.drawString("Runda    wrogów...", obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
            } else if (wybórAkcji != -1) {
                Postać wróg = gp.wrogowie.get(poziomCEL);
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
                        for (Jednorazówki j : gp.bohater.ekwipunek) {
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
                this.ilośćPoziomów = ilośćPoziomów + 1;

            } else {
                płótno.setColor(Color.RED);
                płótno.fillRect(zaznaczenieX, zaznaczenieY, zaznaczenieSzer, zaznaczenieWys);
                płótno.setColor(Color.WHITE);
                ilośćPoziomów = 0;
                switch (poziom) {
                    case 3: {
                        płótno.drawString(String.valueOf(typAkcji.ustalEnumaPrzezId(3)), obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
                        break;
                    }
                    case 2: {
                        płótno.drawString(typAkcji.ustalEnumaPrzezId(2), obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
                        break;
                    }
                    case 1: {
                        płótno.drawString(typAkcji.ustalEnumaPrzezId(1), obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
                        break;
                    }
                    case 0: {
                        płótno.drawString(typAkcji.ustalEnumaPrzezId(0), obwódkaX + obwódkaSzer / 3 + 10, obwódkaY - obwódkaWys / 3 + 50);
                        break;
                    }
                }
            }

            płótno.setFont(gp.czcionka);
            płótno.setColor(Color.WHITE);
            płótno.drawString(typAkcji.Atakuj.name(), obwódkaX + 10, obwódkaY + 30);
            płótno.drawString(typAkcji.BrońSię.name(), obwódkaX + 10, obwódkaY + 30 + zaznaczenieWys);
            płótno.drawString(typAkcji.Ekwipunek.name(), obwódkaX + 10, obwódkaY + 30 + zaznaczenieWys * 2);
            płótno.drawString(typAkcji.Uciekaj.name(), obwódkaX + 10, obwódkaY + 30 + zaznaczenieWys * 3);

            if (miejsce == PołożenieWMenu.WybórAkcji) {

            } else if (miejsce == PołożenieWMenu.WybórCelu) {

            }

            płótno.dispose();
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
                        for (int i = 3; i >= (gp.bohater.ekwipunek.size()-4)*-1 ; i--){
                            if(i == poziom){
                                akcja = gp.bohater.ekwipunek.get(((i-4)*-1)-1).nazwa;
                            }
                        }
                    }
                }
                System.out.println(akcja);
                if (miejsce != PołożenieWMenu.Akcja) break;
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

        for (Postać wróg: gp.wrogowie){
            ilość++;
        }

        return ilość;
    }
}
