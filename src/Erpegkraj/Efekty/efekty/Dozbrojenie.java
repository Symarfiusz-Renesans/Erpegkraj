package Erpegkraj.Efekty.efekty;

import Erpegkraj.Efekty.Efekty;
import Erpegkraj.PanelGry;

public class Dozbrojenie extends Efekty {

    public Dozbrojenie(PanelGry gp, int początkowaIlośćRund) {
        super("Dozbrojenie", gp, początkowaIlośćRund);
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
