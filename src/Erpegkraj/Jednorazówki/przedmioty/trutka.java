package Erpegkraj.Jednorazówki.przedmioty;

import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Postać;
import java.util.ArrayList;

import java.io.IOException;
import Erpegkraj.Efekty.efekty.*;
public class trutka extends Jednorazówki {

PanelGry gp;
int naIle = 3;

    public trutka(ArrayList<Postać> wrogowie, PanelGry gp) {
        super("Trutka", wrogowie, gp);

        this.gp = gp;
    }

    @Override
    public void działanie(int cel) throws IOException {
wrogowie.get(cel).dodajEfekt(new Trucizna(gp),naIle);
}}