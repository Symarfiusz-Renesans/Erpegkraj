package Erpegkraj.Grafika;

import Erpegkraj.PanelGry;

import java.awt.*;

public abstract class Grafika {
    protected int rozmiarKafelek;
    protected int ilośćSłupków;
    protected int ilośćRzędów;
    protected Graphics2D płótno;
    protected PanelGry gp;

    public Grafika(int rozmiar, int x, int y, PanelGry gp){
        this.rozmiarKafelek = rozmiar;
        this.ilośćSłupków = x;
        this.ilośćRzędów = y;
        this.gp = gp;
    }

    public void ustawPłótno(Graphics2D płótno){
        this.płótno = płótno;
    }
    public void ustawPanelGry(PanelGry gp){this.gp = gp;}
}
