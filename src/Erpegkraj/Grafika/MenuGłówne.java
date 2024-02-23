package Erpegkraj.Grafika;

import Erpegkraj.Grafika.DaneWMenu.Bohaterowie;
import Erpegkraj.Grafika.DaneWMenu.PołożenieWMenuGłównym;
import Erpegkraj.Grafika.DaneWMenu.PołożenieWMenuWalki;
import Erpegkraj.Grafika.DaneWMenu.typAkcjiWMenuGłównym;
import Erpegkraj.ObsługiwaczKlawiszy;
import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Bohaterowie.Krzyżowiec;
import Erpegkraj.ZarządcaArkuszów;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Objects;

public class MenuGłówne extends Menu {
    protected PołożenieWMenuGłównym miejsce = PołożenieWMenuGłównym.WybórAkcji;
    protected int ilośćBohaterów;
    protected ArrayList<Bohaterowie> wszyscyBohaterowie;
    protected Bohater wybranyBohater = Bohaterowie.Krzyżowiec.ustalBohatera();
    protected boolean czyIstniejePoprzedniaGra = czyIstniejePoprzedniaGra();
    protected BufferedImage logo = ImageIO.read(getClass().getResourceAsStream("/Rysy/logo/ErpegkrajLogo.png"));
    protected BufferedImage obwódka = ImageIO.read(getClass().getResourceAsStream("/Rysy/obwódki/MenuGłówne.png"));

    protected int wybórAkcji = -1;
    protected int wybórPodAkcji = -1;
    protected int potwierdź = -1;

    protected int poziomBohatera = 0;

    int raz = 0;


    public MenuGłówne(int rozmiar, int x, int y, PanelGry gp, ObsługiwaczKlawiszy ok) throws IOException {
        super(rozmiar, x, y, gp, ok);
        logo = Przerozmierzacz.przerozmierzanie(logo, 750, 400);
        System.out.println(ilośćSłupków*rozmiarKafelek);
        ilośćBohaterów = ustalIlośćBohaterów();
        wszyscyBohaterowie = new ArrayList<>(ilośćBohaterów);

        for (Bohaterowie bohater:Bohaterowie.values()) {
            wszyscyBohaterowie.add(bohater);
            System.out.println(bohater);
        }
    }

    @Override
    public void runMenu() {

        odczekanieDomyślnegoŻyworysu=wybranyBohater.odnów(odczekanieDomyślnegoŻyworysu);
        if(odczekanieNaKolejneWejście == 0) {
            String[] statut;
            try {
                statut = odnów();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (statut[0].equals("przesunięto")) {
                odczekanieNaKolejneWejście = 20;
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
                    if (!czyIstniejePoprzedniaGra && poziom == 1){
                        poziom+=2;
                        zaznaczenieY -= zaznaczenieWys*2;
                    }else{
                        poziom++;
                        zaznaczenieY -= zaznaczenieWys;
                    }
                    statut[0] = "przesunięto";
                }
            }
            if (ok.dółWciśnięty){
                if (poziom != ilośćPoziomów) {
                    if (!czyIstniejePoprzedniaGra && poziom == 3){
                        poziom-=2;
                        zaznaczenieY += zaznaczenieWys*2;
                    }else{
                        poziom--;
                        zaznaczenieY += zaznaczenieWys;
                    }
                    statut[0] = "przesunięto";
                }
            }
        }
        if (Objects.equals(miejsce.ustalEnumaPrzezId(miejsce.ustalId()), "WybórPodakcji")) {
            if(ok.prawoWciśnięte){
                if(poziomBohatera != ilośćBohaterów-1){
                    poziomBohatera++;
                    wybranyBohater = wszyscyBohaterowie.get(poziomBohatera).ustalBohatera();
                    statut[0] = "przesunięto";
                }
            }
            if(ok.lewoWciśnięte){
                if(poziomBohatera != 0){
                    poziomBohatera--;
                    wybranyBohater = wszyscyBohaterowie.get(poziomBohatera).ustalBohatera();
                    statut[0] = "przesunięto";
                }
            }
        }
        if (ok.potwierdźWciśnięte){
            if (miejsce.ustalId() < 2){
                miejsce = miejsce.valueOf( miejsce.ustalEnumaPrzezId( miejsce.ustalId() + 1));
                System.out.println(miejsce);
                ustalPołożenieZaznaczenia();
                statut[0] = "przesunięto";
                statut[1] = miejsce.ustalEnumaPrzezId( miejsce.ustalId());
            }
        }
        if (ok.cofnijWciśnięte){
            if (miejsce.ustalId() > 0){
                miejsce = miejsce.valueOf( PołożenieWMenuGłównym.ustalEnumaPrzezId(miejsce.ustalId() - 1));
                ustalPołożenieZaznaczenia();
                statut[0] = "przesunięto";
                statut[1] = PołożenieWMenuWalki.ustalEnumaPrzezId( miejsce.ustalId());
            }
        }

        return statut;

    }

    @Override
    public void stwórzMenu(Graphics2D płótno) {
        płótno.setFont(gp.czcionka);
        płótno.drawImage(tło, 0, 0, null);



        if (wybórAkcji == -1){
            płótno.setColor(Color.ORANGE);
            płótno.fillRect(obwódkaX+(obwódkaSzer/3), obwódkaY, obwódkaSzer/3, obwódkaWys);

            płótno.setColor(Color.RED);
            płótno.fillRect(obwódkaX+(obwódkaSzer/3), zaznaczenieY,obwódkaSzer/3, obwódkaWys/4);

            płótno.setColor(Color.WHITE);
            płótno.drawString(typAkcjiWMenuGłównym.NowaGra.name(), obwódkaX+(obwódkaSzer/3) + 20, obwódkaY + 30);
            if (czyIstniejePoprzedniaGra) {
                płótno.drawString(typAkcjiWMenuGłównym.Kontynuuj.name(), obwódkaX + (obwódkaSzer / 3) + 20, obwódkaY + 30 + zaznaczenieWys);
            }else{
                płótno.setColor(Color.GRAY);
                płótno.drawString(typAkcjiWMenuGłównym.Kontynuuj.name(), obwódkaX + (obwódkaSzer / 3) + 20, obwódkaY + 30 + zaznaczenieWys);
                płótno.setColor(Color.WHITE);
            }
            płótno.drawString(typAkcjiWMenuGłównym.Ustawienia.name(), obwódkaX +(obwódkaSzer/3)+ 20, obwódkaY + 30 + zaznaczenieWys * 2);
            płótno.drawString(typAkcjiWMenuGłównym.Wyjdź.name(), obwódkaX +(obwódkaSzer/3)+ 20, obwódkaY + 30 + zaznaczenieWys * 3);

            płótno.drawImage(logo, (ilośćSłupków*rozmiarKafelek-750)/2, (int) (rozmiarKafelek*0.5), null);
            płótno.drawImage(obwódka, obwódkaX+obwódkaSzer/3-25, obwódkaY-25, null);
        } else if (wybórPodAkcji == -1) {
            switch (wybórAkcji){
                case 0:
                    System.exit(0);
                case 1:
                    płótno.setColor(Color.ORANGE);
                    płótno.fillRect(obwódkaX+(obwódkaSzer/3), rozmiarKafelek*(ilośćRzędów/2-1), obwódkaSzer/3, obwódkaWys/2);

                    płótno.setColor(Color.RED);
                    płótno.fillRect(obwódkaX+(obwódkaSzer/3), rozmiarKafelek*(ilośćRzędów/2-1),obwódkaSzer/3, obwódkaWys/4);

                    płótno.setColor(Color.WHITE);
                    płótno.drawString("Tryb Pełnoekranowy", obwódkaX+(obwódkaSzer/3)+10, rozmiarKafelek*(ilośćRzędów/2-1)+45);

                    break;
                case 2:

                    break;
                case 3:
                    int początekX = ((rozmiarKafelek*ilośćSłupków)-(ilośćBohaterów*50+(ilośćBohaterów-1)*20))/2;
                    int początekY = (int) (rozmiarKafelek * (ilośćRzędów-1));

                    płótno.setColor(Color.ORANGE);
                    płótno.fillRect(obwódkaX+(obwódkaSzer/3), (int) (rozmiarKafelek*0.5), obwódkaSzer/3, obwódkaWys/3);

                    płótno.setColor(Color.WHITE);
                    płótno.drawString("Wybierz Postać:", obwódkaX+(obwódkaSzer/3)+10, (int) (rozmiarKafelek*0.5) + 50);
                    płótno.drawString(wybranyBohater.nazwa,obwódkaX+(obwódkaSzer/3)+10, (int) (rozmiarKafelek*1.25) + 75);

                    for (int i = 0; i < ilośćBohaterów; i++) {

                        płótno.setColor(Color.ORANGE);
                        płótno.fillRect(początekX + i*70, początekY,50,50);

                        płótno.setColor(Color.RED);
                        płótno.fillRect(początekX + poziomBohatera*70, początekY,50,50);

                        wybranyBohater.ustawPłótno(płótno);
                        wybranyBohater.ustawPanelGry(gp);
                        wybranyBohater.zmieńPołożeniePostaci(0);
                        wybranyBohater.stwórzPostać();

                    }
                    break;
            }

        } else if (potwierdź == -1){
            gp.bohater = wybranyBohater;
        }
    }

    @Override
    protected void ustalPołożenieZaznaczenia() {
        ilośćBohaterów = ustalIlośćBohaterów();
        switch(miejsce){
            case WybórAkcji:{
                zaznaczenieY = obwódkaY;
                zaznaczenieX = obwódkaX + (obwódkaSzer / 3);
                wybórAkcji = -1;
                wybórPodAkcji = -1;
                potwierdź = -1;
                break;
            }
            case WybórPodakcji:{
                wybórAkcji = poziom;
                wybórPodAkcji = -1;
                potwierdź = -1;
                break;
            }
            case Potwierdzenie:{
                wybórPodAkcji = poziom;
                potwierdź = -1;
                break;
            }
        }
    }

    protected int ustalIlośćBohaterów(){
        return Bohaterowie.values().length;
    }

    protected boolean czyIstniejePoprzedniaGra(){
        return new File("zasoby/poprzedniaRozgrywka").list().length != 0;
    }
}
