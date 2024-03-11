package Erpegkraj.Efekty;

import Erpegkraj.Grafika.Przerozmierzacz;
import Erpegkraj.Jednorazówki.Jednorazówki;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Postać;
import Erpegkraj.ZarządcaArkuszów;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public abstract class Efekty extends Erpegkraj.Grafika.Grafika {
    public String nazwa;
    protected Postać postać;
    public BufferedImage ikona;

    public Efekty(String nazwa, PanelGry gp, String ikona) throws IOException {
        super(0, 0, 0, gp);
        this.nazwa = nazwa;
        this.ikona = Przerozmierzacz.przerozmierzanie(ImageIO.read(getClass().getResourceAsStream("/Rysy/"+ikona)),30,30);
    }

    public void ustawPostać(Postać p){
        this.postać = p;
    }

    public void działaniePrzyOtrzymaniu(){

    }
    public void działaniePrzyWyczerpaniu(){

    }
    public void działaniePrzyAtaku(){

    }
    public void działaniePrzyUżyciuPrzedmiotu(String użytyPrzedmiot){

    }
    public void działanieGdyUpłynieRunda(){

    }
    public void działaniePrzedOdebraniemObrażeń(){

    }


    public void ustawIlośćRund(int oIle){

    }
    public int ustalIlośćRund(){
        return 0;
    }


}
