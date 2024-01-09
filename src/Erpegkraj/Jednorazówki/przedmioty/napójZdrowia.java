package Erpegkraj.Jednorazówki.przedmioty;

import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Postać;
import java.util.ArrayList;

public class napójZdrowia extends Jednorazówki {

int ilośćLeczonegoZdrowia = 10;

    public napójZdrowia(ArrayList<Postać> wrogowie, PanelGry gp) {
        super("Napój Zdrowia", wrogowie, gp);
    }

    @Override
    public void działanie(int cel) {
    if(bohater.życie + ilośćLeczonegoZdrowia <= bohater.maksŻycia){
        bohater.życie += 10;
    } else {
        bohater.życie = bohater.maksŻycia;
    }
}}