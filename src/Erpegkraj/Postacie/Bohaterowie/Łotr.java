package Erpegkraj.Postacie.Bohaterowie;

import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Postać;
import Erpegkraj.Postacie.Wróg;
import java.io.IOException;
public class Łotr extends Bohater {
    public String nazwaZdolnościPasywnej = " ";
    public String nazwaZdolnościWyjątkowej = " ";

    public Łotr(int rozmiar, int x, int y, PanelGry gp) throws IOException {
        super("Łotr", rozmiar, x, y, gp);
    }

@Override
    public void ZdolnośćPasywna(){
         
    }

@Override
    public void ZdolnośćWyjątkowa(){
         

    }
}