package Erpegkraj.Jednorazówki;

import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Postać;
import Erpegkraj.Postacie.Wróg;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;

public class Jednorazówki extends Erpegkraj.Grafika.Grafika{
    public String nazwa;
    protected Bohater bohater;
    protected ArrayList<Postać> wrogowie;

    public Jednorazówki(String nazwa, ArrayList<Postać> wrogowie, PanelGry gp){
        super(0,0,0,gp);
        this.nazwa = nazwa;
        this.wrogowie = wrogowie;
        //System.out.println(bohater);
    }

    public void ustawBohatera(Bohater b){
        this.bohater = b;
    }

    public void działanie(int cel){

    }

}
