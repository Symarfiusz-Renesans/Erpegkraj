package Erpegkraj.Efekty;

import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Postać;
import Erpegkraj.ZarządcaArkuszów;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.Buffer;
import java.util.ArrayList;

public abstract class Efekty extends Erpegkraj.Grafika.Grafika {

    protected FileInputStream daneEfektów = new FileInputStream("zasoby/Dane/Dane.xlsx");

    protected int ilośćRund;
    public String nazwa;
    protected Postać postać;
    public BufferedImage ikona;

    public Efekty(String nazwa, PanelGry gp, BufferedImage ikona, int ilośćRund) throws FileNotFoundException {
        super(0, 0, 0, gp);
        this.nazwa = nazwa;
        this.ilośćRund = ilośćRund;
        this.ikona = ikona;
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
