package Erpegkraj.Efekty.efekty;

import Erpegkraj.Efekty.Efekty;
import Erpegkraj.PanelGry;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
public class Trucizna extends Efekty {

    public Trucizna(PanelGry gp) throws IOException {
        super("Trucizna", gp, "efekty/Trutka.png");
    }
@Override
public void działanieGdyUpłynieRunda(){   postać.życie-=2;}
}