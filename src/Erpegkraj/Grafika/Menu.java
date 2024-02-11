package Erpegkraj.Grafika;

import Erpegkraj.Efekty.Efekty;
import Erpegkraj.Efekty.efekty.*;
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
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public abstract class Menu extends Grafika{

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
    }


    public abstract String[] odnów() throws IOException;
    public abstract void stwórzMenu();

    protected abstract void ustalPołożenieZaznaczenia();

    public void nowaRunda(){
        miejsce = PołożenieWMenu.WybórTypuAkcji;
        ustalPołożenieZaznaczenia();
    }
}
