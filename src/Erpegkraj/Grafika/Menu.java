package Erpegkraj.Grafika;

import Erpegkraj.Grafika.DaneWMenu.PołożenieWMenuWalki;
import Erpegkraj.ObsługiwaczKlawiszy;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Postać;

import java.io.IOException;

public abstract class Menu extends Grafika{

    protected ObsługiwaczKlawiszy ok;
    protected PołożenieWMenuWalki miejsce = PołożenieWMenuWalki.WybórTypuAkcji;

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
    protected String akcja = "";

    public Menu(int rozmiar, int x, int y, PanelGry gp, ObsługiwaczKlawiszy ok) {
        super(rozmiar, x, y, gp);
        this.ok = ok;
    }

    public abstract void runMenu();

    public abstract String[] odnów() throws IOException;
    public abstract void stwórzMenu();

    protected abstract void ustalPołożenieZaznaczenia();

    public void nowaRunda(){
        miejsce = PołożenieWMenuWalki.WybórTypuAkcji;
        ustalPołożenieZaznaczenia();
    }
}
