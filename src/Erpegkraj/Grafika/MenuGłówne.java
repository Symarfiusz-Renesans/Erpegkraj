package Erpegkraj.Grafika;

import Erpegkraj.Grafika.DaneWMenu.Bohaterowie;
import Erpegkraj.Grafika.DaneWMenu.PołożenieWMenuGłównym;
import Erpegkraj.Grafika.DaneWMenu.typAkcjiWMenuGłównym;
import Erpegkraj.ObsługiwaczKlawiszy;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Bohaterowie.Krzyżowiec;

import java.io.IOException;

public class MenuGłówne extends Menu {
    protected ObsługiwaczKlawiszy ok;
    protected typAkcjiWMenuGłównym miejsce = typAkcjiWMenuGłównym.NowaGra;
    protected Bohaterowie wybranyBohater = Bohaterowie.Krzyżowiec;

    int raz = 0;


    public MenuGłówne(int rozmiar, int x, int y, PanelGry gp, ObsługiwaczKlawiszy ok) {
        super(rozmiar, x, y, gp, ok);
    }

    @Override
    public void runMenu() {

        try {
            odnów();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String[] odnów() throws IOException {

        String[] statut = {"","WybórTypuAkcji"};
        return new String[0];
    }

    @Override
    public void stwórzMenu() {

    }

    @Override
    protected void ustalPołożenieZaznaczenia() {

    }
}
