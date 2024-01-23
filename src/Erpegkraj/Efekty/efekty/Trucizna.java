package Erpegkraj.Efekty.efekty;

import Erpegkraj.Efekty.Efekty;
import Erpegkraj.PanelGry;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
public class Trucizna extends Efekty {

    public Trucizna(PanelGry gp, int początkowaIlośćRund) throws IOException {
        super("null", gp, "efekty/Trutka.png", początkowaIlośćRund);
    }
@Override
public void działanieGdyUpłynieRunda(){   postać.życie-=2;}
}