package Erpegkraj.Grafika;

import Erpegkraj.ObsługiwaczKlawiszy;
import Erpegkraj.PanelGry;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Menu extends Grafika{

    protected ObsługiwaczKlawiszy ok;

    final int obwódkaSzer = rozmiarKafelek*(ilośćSłupków-1);
    final int obwódkaWys = rozmiarKafelek*3;
    final int obwódkaX = (int) (rozmiarKafelek*0.5);
    final int obwódkaY = (int) (rozmiarKafelek * (ilośćRzędów-3.5));

    int odczekanieNaKolejneWejście = 0;
    int odczekanieDomyślnegoŻyworysu = 30;

    final int zaznaczenieSzer = obwódkaSzer/3;
    final int zaznaczenieWys = obwódkaWys/4;
    protected int zaznaczenieY = (int) (rozmiarKafelek * (ilośćRzędów-3.5));
    protected int zaznaczenieX = obwódkaX;

    protected int ilośćPoziomów = 0;
    protected int ilośćWrogów = 0;
    protected int poziom = 3;
    protected String akcja = "";

    protected BufferedImage tło;
    protected BufferedImage obwódka;

    public Menu(int rozmiar, int x, int y, PanelGry gp, ObsługiwaczKlawiszy ok) {
        super(rozmiar, x, y, gp);
        this.ok = ok;
        try {
            tło = ImageIO.read(getClass().getResourceAsStream("/Rysy/tło/las.png"));
            obwódka = ImageIO.read(getClass().getResourceAsStream("/Rysy/obwódki/MenuWalki.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void runMenu();

    public abstract String[] odnów() throws IOException;
    public abstract void stwórzMenu(Graphics2D płótno);

    protected abstract void ustalPołożenieZaznaczenia();
}
