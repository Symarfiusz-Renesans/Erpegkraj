package Erpegkraj.Efekty.efekty;

import Erpegkraj.Efekty.Efekty;
import Erpegkraj.PanelGry;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
public class Dozbrojenie extends Efekty {

    public Dozbrojenie(PanelGry gp, int początkowaIlośćRund) throws IOException {
        super("null", gp, "efekty/Trutka.png", początkowaIlośćRund);
    }
@Override
public void działaniePrzyOtrzymaniu(){   postać.odpornośćUlep = postać.odporność+5}@Override
public void działaniePrzyWyczerpaniu(){   postać.odpornośćUlep = postać.odporność;}}