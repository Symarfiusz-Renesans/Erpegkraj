package Erpegkraj.Jednorazówki.przedmioty;

import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Postać;
import java.util.ArrayList;

public class kamień extends Jednorazówki {

int zadaneObrażenia = 5;

    public kamień(ArrayList<Postać> wrogowie, PanelGry gp) {
        super("Kamień", wrogowie, gp);
    }

    @Override
    public void działanie(int cel) {
wrogowie.get(cel).otrzymajObrażenia(zadaneObrażenia);
}}