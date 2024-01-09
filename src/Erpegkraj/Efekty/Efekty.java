package Erpegkraj.Efekty;

import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Postać;

import java.util.ArrayList;

public abstract class Efekty extends Erpegkraj.Grafika.Grafika{

    protected int ilośćRund;
    public String nazwa;
    protected Postać postać;

    public Efekty(String nazwa, PanelGry gp, int ilośćRund) {
        super(0, 0, 0, gp);
        this.nazwa = nazwa;
        this.ilośćRund = ilośćRund;
    }

    public void ustawBohatera(Postać p){
        this.postać = p;
    }

    public void działaniePrzyOtrzymaniu(){

    }
    public void działaniePrzyWyczerpaniu(){

    }
    public void działaniePrzyAtaku(){

    }
    public void działaniePrzyUżyciuPrzedmiotu(Jednorazówki użytyPrzedmiot){

    }
    public void działanieGdyUpłynieRunda(){

    }
    public void działaniePrzedOdebraniemObrażeń(){

    }


    public void ustawIlośćRund(int oIle){
        ilośćRund+=oIle;
    }
    public int ustalIlośćRund(){
        return ilośćRund;
    }

}
