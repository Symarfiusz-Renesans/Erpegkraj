package Erpegkraj.Jednorazówki.przedmioty;

import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Postać;
import java.util.ArrayList;

import java.io.IOException;
public class napójZdrowia extends Jednorazówki {

PanelGry gp;
int ilośćLeczonegoZdrowia = 10;

    public napójZdrowia(ArrayList<Postać> wrogowie, PanelGry gp) {
        super("Napój Zdrowia", wrogowie, gp);

        this.gp = gp;
    }

    @Override
    public void działanie(int cel) throws IOException {
    if(bohater.życie + ilośćLeczonegoZdrowia <= bohater.maksŻycia){
        bohater.życie += 10;
    } else {
        bohater.życie = bohater.maksŻycia;
    }
}}