package Erpegkraj.Grafika;

import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Grafika.DaneWMenu.PołożenieWMenu;
import Erpegkraj.Grafika.DaneWMenu.typAkcji;
import Erpegkraj.ObsługiwaczKlawiszy;
import Erpegkraj.PanelGry;
import Erpegkraj.Grafika.DaneWMenu.Akcje.*;
import Erpegkraj.Postacie.Postać;
import Erpegkraj.Postacie.Wróg;

import java.awt.*;
import java.util.Objects;

public class Menu extends Grafika{

    protected ObsługiwaczKlawiszy ok;
    protected PołożenieWMenu miejsce = PołożenieWMenu.WybórTypuAkcji;

    final int obwódkaSzer = rozmiarKafelek*(ilośćSłupków-1);
    final int obwódkaWys = rozmiarKafelek*3;
    final int obwódkaX = (int) (rozmiarKafelek*0.5);
    final int obwódkaY = (int) (rozmiarKafelek * (ilośćRzędów-3.5));

    final int zaznaczenieSzer = obwódkaSzer/3;
    final int zaznaczenieWys = obwódkaWys/4;
    protected int zaznaczenieY = (int) (rozmiarKafelek * (ilośćRzędów-3.5));
    protected int zaznaczenieX = obwódkaX;

    protected int ilośćPoziomów = 0;
    protected int ilośćWrogów = 0;
    protected int poziom = 3;
    protected int poziomCEL;
    protected int wybórTypuAkcji = -1;
    protected int wybórAkcji = -1;
    protected int wybórCelu = -1;
    protected String akcja = "";

    public Menu(int rozmiar, int x, int y, PanelGry gp, ObsługiwaczKlawiszy ok) {
        super(rozmiar, x, y, gp);
        this.ok = ok;

        ilośćWrogów=ustalIlośćWrogów(gp);
        //System.out.println(ilośćWrogów);
    }


    public String[] odnów(){

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

                            break;
                        case "ObronaZPremią":
                            break;
                        default:
                            for (Jednorazówki j: gp.wszytkieMożliwePrzedmioty){
                                if(Objects.equals(akcja, j.nazwa)){
                                    j.działanie(poziomCEL);
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
    public void stwórzMenu(){
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

    public void nowaRunda(){
        miejsce = PołożenieWMenu.WybórTypuAkcji;
        ustalPołożenieZaznaczenia();
    }

    protected void ustalPołożenieZaznaczenia(){

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
