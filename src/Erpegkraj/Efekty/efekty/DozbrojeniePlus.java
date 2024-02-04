package Erpegkraj.Efekty.efekty;

import Erpegkraj.Efekty.Efekty;
import Erpegkraj.PanelGry;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
public class DozbrojeniePlus extends Efekty {

    public DozbrojeniePlus(PanelGry gp) throws IOException {
        super("DozbrojeniePlus", gp, "efekty/Dozbrojenie.png");
    }
@Override
public void działaniePrzyOtrzymaniu(){   postać.odpornośćUlep = postać.odporność+5;
postać.unikUlep = postać.unik + 5;}
@Override
public void działaniePrzyWyczerpaniu(){   postać.odpornośćUlep = postać.odporność;
postać.unikUlep = postać.unik;}
}