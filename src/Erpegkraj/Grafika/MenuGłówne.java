package Erpegkraj.Grafika;

import Erpegkraj.Grafika.DaneWMenu.PołożenieWMenu;
import Erpegkraj.Grafika.Grafika;
import Erpegkraj.ObsługiwaczKlawiszy;
import Erpegkraj.PanelGry;

import java.io.IOException;

public class MenuGłówne extends Menu {
    protected ObsługiwaczKlawiszy ok;
    protected PołożenieWMenu miejsce = PołożenieWMenu.WybórTypuAkcji;


    public MenuGłówne(int rozmiar, int x, int y, PanelGry gp, ObsługiwaczKlawiszy ok) {
        super(rozmiar, x, y, gp, ok);
    }

    @Override
    public void runMenu() {

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
