package Erpegkraj.Uzbrojenie;

import Erpegkraj.Postacie.Bohater;
import Erpegkraj.ZarządcaArkuszów;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Uzbrojenie implements ZarządcaArkuszów{
    public final String nazwa;
    public typyUzbrojenia typUzbrojenia;
    protected Statystyki ulepszanaStatystyka;
    protected int siłaUlepszenia;
    public int premia;

    protected File daneBohatera;

    public Uzbrojenie(String nazwa){
        this.nazwa = nazwa;

        try {
            daneBohatera = new File("zasoby/Dane/Dane.xlsx");
            HashMap<String, String> mapa= ZarządcaArkuszów.podzielDane(ZarządcaArkuszów.przeczytajWierszArkusza(daneBohatera, "Uzbrojenie", nazwa));
            przydzielDane(mapa);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void ustalMoc(Bohater b){
        switch (ulepszanaStatystyka){
            case SIŁA -> b.siła += siłaUlepszenia;
            case ZDROWIE -> b.życie += siłaUlepszenia;
            case OBRONA -> b.odporność += siłaUlepszenia;
        }
    }

    @Override
    public void przydzielDane(HashMap<String, String> mapa) throws IOException {
        for (Map.Entry<String, String> entry : mapa.entrySet()) {
            String klucz = entry.getKey();
            String wartość = entry.getValue();
            switch(klucz){
                case "TypUzbrojenia":{
                    typUzbrojenia= typyUzbrojenia.valueOf(mapa.get(klucz));
                    break;
                }
                case "Moc":{
                    siłaUlepszenia = Integer.parseInt(mapa.get(klucz));
                    break;
                }
                case "Premia":{
                    premia= Integer.parseInt(mapa.get(klucz));
                    break;
                }
                case "doCzego":{
                    ulepszanaStatystyka = Statystyki.valueOf(mapa.get(klucz));
                    break;
                }
            }
        }
    }
}
