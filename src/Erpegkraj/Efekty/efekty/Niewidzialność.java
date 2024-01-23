package Erpegkraj.Efekty.efekty;

import Erpegkraj.Efekty.Efekty;
import Erpegkraj.PanelGry;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
public class Niewidzialność extends Efekty {

    public Niewidzialność(PanelGry gp, int początkowaIlośćRund) throws IOException {
        super("null", gp, "efekty/Trutka.png");
    }
@Override
public void działaniePrzyOtrzymaniu(){   postać.unikUlep = 30;}
@Override
public void działaniePrzyWyczerpaniu(){   postać.unikUlep = postać.unik;}
}