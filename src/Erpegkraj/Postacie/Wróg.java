package Erpegkraj.Postacie;

import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Postać;
import Erpegkraj.ZarządcaArkuszów;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Wróg extends Postać implements ZarządcaArkuszów {

    protected FileInputStream daneWroga;

    public Wróg(String nazwa,int miejsce, int rozmiar, int x, int y, PanelGry gp){
        super(rozmiar, x, y, gp, 0, 150, 155, 175);
        postaćX = ustawPołożenieX(miejsce, x, rozmiar);

        try {
            daneWroga = new FileInputStream("zasoby/Dane/Dane.xlsx");
            HashMap<String, String> mapa= ZarządcaArkuszów.podzielDane(ZarządcaArkuszów.przeczytajWierszArkusza(daneWroga, "Wrogowie", nazwa));
            przydzielDane(mapa);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        maksŻycia = życie;

        ustawRozmiarGracza();
    }

    public static int ustawPołożenieX(int miejsce, int x, int rozmiar){
        int szerokość = x * rozmiar;
        return switch (miejsce) {
            case 0 -> (szerokość - 200);
            case 1 -> (szerokość - 360);
            case 2 -> (szerokość - 520);
            default -> 0;
        };
    }

    @Override
    public void przydzielDane(HashMap<String, String> mapa) throws IOException {
        for (Map.Entry<String, String> entry : mapa.entrySet()) {
            String klucz = entry.getKey();
            switch(klucz){
                case "Nazwa":{
                    nazwa = mapa.get(klucz);
                    break;
                }
                case "PoczątkoweZdrowie":{
                    życie = Integer.valueOf(mapa.get(klucz));
                    break;
                }
                case "PoczątkowaSiła":{
                    siła = Integer.valueOf(mapa.get(klucz));
                    break;
                }
                case "PoczątkowaObrona":{
                    odporność = Integer.valueOf(mapa.get(klucz));
                    break;
                }
                case "PoczątkowaZwinność":{
                    unik = Integer.valueOf(mapa.get(klucz));
                    break;
                }
                case "RysDomyślny1":{
                    rysy.replace("d1",ImageIO.read(getClass().getResourceAsStream("/Rysy/"+mapa.get(klucz))));
                    break;
                }
                case "RysDomyślny2":{
                    rysy.replace("d2",ImageIO.read(getClass().getResourceAsStream("/Rysy/"+mapa.get(klucz))));
                    break;
                }
                case "RysAtaku1":{
                    rysy.replace("a1",ImageIO.read(getClass().getResourceAsStream("/Rysy/"+mapa.get(klucz))));
                    break;
                }
                case "RysAtaku2":{
                    rysy.replace("a2",ImageIO.read(getClass().getResourceAsStream("/Rysy/"+mapa.get(klucz))));
                    break;
                }
            }
        }
    }
}
