package Erpegkraj.Jednorazówki.przedmioty;

import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Postać;
import java.util.ArrayList;

import java.io.IOException;
import Erpegkraj.Efekty.efekty.*;
public class kamień extends Jednorazówki {

PanelGry gp;
int zadaneObrażenia = 5;

    public kamień(ArrayList<Postać> wrogowie, PanelGry gp) {
        super("Kamień", wrogowie, gp);

        this.gp = gp;
    }

    @Override
    public void działanie(int cel) throws IOException {
wrogowie.get(cel).otrzymajObrażenia(zadaneObrażenia);
}}