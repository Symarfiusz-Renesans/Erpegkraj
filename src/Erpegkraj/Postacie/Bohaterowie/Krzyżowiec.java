package Erpegkraj.Postacie.Bohaterowie;

import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Postać;
import Erpegkraj.Postacie.Wróg;
import java.io.IOException;
public class Krzyżowiec extends Bohater {
    public String nazwaZdolnościPasywnej = "Śluby Krzyżowca";
    public String nazwaZdolnościWyjątkowej = "Gniew posłannika Boga";

    public Krzyżowiec(int rozmiar, int x, int y, PanelGry gp) throws IOException {
        super("Krzyżowiec", rozmiar, x, y, gp);
    }

@Override
    public void ZdolnośćPasywna(){
        String coś = "jej";
    }

@Override
    public void ZdolnośćWyjątkowa(){
        for(Postać wróg: gp.menuWalki.wrogowie){
    zadajObrażenia(wróg, false);
}

    }
}