package Erpegkraj.Grafika;

import Erpegkraj.Grafika.DaneWMenu.Bohaterowie;
import Erpegkraj.Grafika.DaneWMenu.PołożenieWMenuGłównym;
import Erpegkraj.Grafika.DaneWMenu.PołożenieWMenuWalki;
import Erpegkraj.Grafika.DaneWMenu.typAkcjiWMenuGłównym;
import Erpegkraj.ObsługiwaczKlawiszy;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Bohaterowie.Krzyżowiec;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MenuGłówne extends Menu {
    protected PołożenieWMenuGłównym miejsce = PołożenieWMenuGłównym.WybórAkcji;
    protected Bohaterowie wybranyBohater = Bohaterowie.Krzyżowiec;
    protected boolean czyIstniejePoprzedniaGra = false;
    protected BufferedImage logo = ImageIO.read(getClass().getResourceAsStream("/Rysy/logo/ErpegkrajLogo.png"));

    int raz = 0;


    public MenuGłówne(int rozmiar, int x, int y, PanelGry gp, ObsługiwaczKlawiszy ok) throws IOException {
        super(rozmiar, x, y, gp, ok);
        logo = Przerozmierzacz.przerozmierzanie(logo, 750, 400);
        System.out.println(ilośćSłupków*rozmiarKafelek);
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
            }
        } else {
            odczekanieNaKolejneWejście--;
        }

    }

    @Override
    public String[] odnów() throws IOException {

        String[] statut = {"","WybórAkcji"};

        if(Objects.equals(miejsce.ustalEnumaPrzezId(miejsce.ustalId()), "WybórAkcji")){
            if(ok.góraWciśnięta){
                if(poziom != 3){
                    zaznaczenieY -= zaznaczenieWys;
                    poziom++;
                    statut[0] = "przesunięto";
                }
            }
            if (ok.dółWciśnięty){
                if (poziom != ilośćPoziomów) {
                    zaznaczenieY += zaznaczenieWys;
                    poziom--;
                    statut[0] = "przesunięto";
                }
            }
        }

        return statut;

    }

    @Override
    public void stwórzMenu() {
        płótno.setFont(gp.czcionka);

        płótno.setColor(Color.ORANGE);
        płótno.fillRect(obwódkaX+(obwódkaSzer/3), obwódkaY, obwódkaSzer/3, obwódkaWys);

        płótno.setColor(Color.RED);
        płótno.fillRect(obwódkaX+(obwódkaSzer/3), zaznaczenieY,obwódkaSzer/3, obwódkaWys/4);

        płótno.setColor(Color.WHITE);
        płótno.drawString(typAkcjiWMenuGłównym.NowaGra.name(), obwódkaX+(obwódkaSzer/3) + 10, obwódkaY + 30);
        płótno.drawString(typAkcjiWMenuGłównym.Kontynuuj.name(), obwódkaX +(obwódkaSzer/3)+ 10, obwódkaY + 30 + zaznaczenieWys);
        płótno.drawString(typAkcjiWMenuGłównym.Ustawienia.name(), obwódkaX +(obwódkaSzer/3)+ 10, obwódkaY + 30 + zaznaczenieWys * 2);
        płótno.drawString(typAkcjiWMenuGłównym.Wyjdź.name(), obwódkaX +(obwódkaSzer/3)+ 10, obwódkaY + 30 + zaznaczenieWys * 3);

        płótno.drawImage(logo, (ilośćSłupków*rozmiarKafelek-750)/2, (int) (rozmiarKafelek*0.5), null);
    }

    @Override
    protected void ustalPołożenieZaznaczenia() {

    }
}
