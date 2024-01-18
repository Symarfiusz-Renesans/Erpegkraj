package Erpegkraj.Efekty.efekty;

import Erpegkraj.Efekty.Efekty;
import Erpegkraj.PanelGry;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Dozbrojenie extends Efekty {

    public Dozbrojenie(PanelGry gp, BufferedImage ikona, int początkowaIlośćRund) throws IOException {
        super("Dozbrojenie", gp, ikona, początkowaIlośćRund);
    }

    @Override
    public void działaniePrzyOtrzymaniu() {
        postać.odpornośćUlep = postać.odporność+5;
    }

    @Override
    public void działaniePrzyWyczerpaniu() {
        postać.odpornośćUlep = postać.odporność;
    }
}
