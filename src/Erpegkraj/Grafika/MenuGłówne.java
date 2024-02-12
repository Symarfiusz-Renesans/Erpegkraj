package Erpegkraj.Grafika;

import Erpegkraj.Grafika.DaneWMenu.Bohaterowie;
import Erpegkraj.Grafika.DaneWMenu.PołożenieWMenuGłównym;
import Erpegkraj.ObsługiwaczKlawiszy;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Bohaterowie.Krzyżowiec;

import java.io.IOException;

public class MenuGłówne extends Menu {
    protected ObsługiwaczKlawiszy ok;
    protected PołożenieWMenuGłównym miejsce = PołożenieWMenuGłównym.NowaGra;
    protected Bohaterowie wybranyBohater = Bohaterowie.Krzyżowiec;


    public MenuGłówne(int rozmiar, int x, int y, PanelGry gp, ObsługiwaczKlawiszy ok) {
        super(rozmiar, x, y, gp, ok);
    }

    @Override
    public void runMenu() {

        try {
            gp.ustawBohatera(new Krzyżowiec(rozmiarKafelek,ilośćSłupków, ilośćRzędów, gp));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String[] odnów() throws IOException {
        return new String[0];
    }

    @Override
    public void stwórzMenu() {

    }

    @Override
    protected void ustalPołożenieZaznaczenia() {

    }
}
