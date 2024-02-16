package Erpegkraj.Grafika;

import Erpegkraj.Grafika.DaneWMenu.Bohaterowie;
import Erpegkraj.Grafika.DaneWMenu.PołożenieWMenuGłównym;
import Erpegkraj.Grafika.DaneWMenu.typAkcjiWMenuGłównym;
import Erpegkraj.ObsługiwaczKlawiszy;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Bohaterowie.Krzyżowiec;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuGłówne extends Menu {
    protected ObsługiwaczKlawiszy ok;
    protected typAkcjiWMenuGłównym miejsce = typAkcjiWMenuGłównym.NowaGra;
    protected Bohaterowie wybranyBohater = Bohaterowie.Krzyżowiec;
    protected boolean czyIstniejePoprzedniaGra = false;
    protected BufferedImage logo = ImageIO.read(getClass().getResourceAsStream("/Rysy/logo/ErpegkrajLogo.png"));

    int raz = 0;


    public MenuGłówne(int rozmiar, int x, int y, PanelGry gp, ObsługiwaczKlawiszy ok) throws IOException {
        super(rozmiar, x, y, gp, ok);
        logo = Przerozmierzacz.przerozmierzanie(logo, obwódkaSzer, obwódkaWys);
    }

    @Override
    public void runMenu() {

        if (odczekanieDomyślnegoŻyworysu == 30){

        }
        if(odczekanieNaKolejneWejście == 0) {
            String[] statut;
            try {
                statut = odnów();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (statut[0].equals("przesunięto")) {
                odczekanieNaKolejneWejście = 10;
            } else {
                odczekanieNaKolejneWejście--;
            }
        }

    }

    @Override
    public String[] odnów() throws IOException {

        String[] statut = {"","WybórAkcji"};
        return statut;
    }

    @Override
    public void stwórzMenu() {
        płótno.setFont(gp.czcionka);

        płótno.setColor(Color.ORANGE);
        płótno.fillRect(obwódkaX+(obwódkaSzer/3), obwódkaY, obwódkaSzer/3, obwódkaWys);

        płótno.drawImage(logo, obwódkaX, (int) (rozmiarKafelek*0.5), null);
    }

    @Override
    protected void ustalPołożenieZaznaczenia() {

    }
}
