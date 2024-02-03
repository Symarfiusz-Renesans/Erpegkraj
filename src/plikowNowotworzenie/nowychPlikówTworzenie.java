package plikowNowotworzenie;

import Erpegkraj.ZarządcaArkuszów;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class nowychPlikówTworzenie {

    public static void main(String[] args) throws IOException {
        FileInputStream danePrzedmiotów;
        HashMap<String,HashMap<String, String>> mapa;

        try {
            danePrzedmiotów = new FileInputStream("zasoby/Dane/Dane.xlsx");
            mapa= ZarządcaArkuszów.podzielDaneRóżnychWierszy(ZarządcaArkuszów.przeczytajWierszeArkusza(danePrzedmiotów, "Jednorazówki"));
            przydzielWieleDanychJednorazówek(mapa);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            danePrzedmiotów = new FileInputStream("zasoby/Dane/Dane.xlsx");
            mapa= ZarządcaArkuszów.podzielDaneRóżnychWierszy(ZarządcaArkuszów.przeczytajWierszeArkusza(danePrzedmiotów, "EfektyBitwy"));
            przydzielWieleDanychEfektów(mapa);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            danePrzedmiotów = new FileInputStream("zasoby/Dane/Dane.xlsx");
            mapa= ZarządcaArkuszów.podzielDaneRóżnychWierszy(ZarządcaArkuszów.przeczytajWierszeArkusza(danePrzedmiotów, "Bohaterowie"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        przydzielWieleDanychBohaterów(mapa, danePrzedmiotów);
    }

    public static void przydzielWieleDanychJednorazówek(HashMap<String,HashMap<String, String>> mapa) throws IOException {
        for (Map.Entry<String, HashMap<String, String>> m : mapa.entrySet()) {
            String klucz = m.getKey();
            HashMap<String, String> wartość = m.getValue();

            String kod = "package Erpegkraj.Jednorazówki.przedmioty;\n" +
                    "\n" +
                    "import Erpegkraj.Jednorazówki.Jednorazówki;\n" +
                    "import Erpegkraj.PanelGry;\n" +
                    "import Erpegkraj.Postacie.Postać;\n" +
                    "import java.util.ArrayList;\n\n" +
                    "import java.io.IOException;\n" +
                    "import Erpegkraj.Efekty.efekty.*;\n"+
                    "public class "+wartość.get("nazwaKlasy")+" extends Jednorazówki {\n\n" +
                    "PanelGry gp;\n"+
                    wartość.get("Zmienne")+"\n\n"+
                    "    public "+wartość.get("nazwaKlasy")+"(ArrayList<Postać> wrogowie, PanelGry gp) {\n" +
                    "        super(\""+wartość.get("Nazwa")+"\", wrogowie, gp);\n\n" +
                    "        this.gp = gp;\n" +
                    "    }\n\n"
                    +"    @Override\n"+
                    "    public void działanie(int cel) throws IOException {\n"+
                    wartość.get("Działanie")+
                    "\n}}";

            //System.out.println(kod);


            File plik = new File("src/Erpegkraj/Jednorazówki/przedmioty/"+klucz+".java");
            plik.createNewFile();
            try(PrintWriter plikuTworzenie = new PrintWriter (new FileOutputStream(plik, false))){
                plikuTworzenie.print(kod);
            }

        }
    }

    public static void przydzielWieleDanychBohaterów(HashMap<String,HashMap<String, String>> mapa, FileInputStream danePrzedmiotów) throws IOException {

        for (Map.Entry<String, HashMap<String, String>> m : mapa.entrySet()) {
            String klucz = m.getKey();
            HashMap<String, String> wartość = m.getValue();

            String kod = "package Erpegkraj.Postacie.Bohaterowie;\n" +
                    "\n" +
                    "import Erpegkraj.PanelGry;\n" +
                    "import Erpegkraj.Postacie.Bohater;\n" +
                    "import Erpegkraj.Postacie.Postać;\n" +
                    "import Erpegkraj.Postacie.Wróg;\n" +
                    "import java.io.IOException;"+
                    "\n"+
                    "public class "+wartość.get("Nazwa")+" extends Bohater {\n" +
                    "    public String nazwaZdolnościPasywnej = \""+wartość.get("ZdolnośćPasywna")+"\";\n" +
                    "    public String nazwaZdolnościWyjątkowej = \""+wartość.get("ZdolnośćWyjątkowa")+"\";\n" +
                    "\n" +
                    "    public "+wartość.get("Nazwa")+"(int rozmiar, int x, int y, PanelGry gp) throws IOException {\n" +
                    "        super(\"Krzyżowiec\", rozmiar, x, y, gp);\n" +
                    "    }\n"+
                    "\n" +
                    "@Override\n"+
                    "    public void ZdolnośćPasywna(){\n" +
                    "        "+wartość.get("DziałanieZPasywnej") + "\n" +
                    "    }\n" +
                    "\n" +
                    "@Override\n"+
                    "    public void ZdolnośćWyjątkowa(){\n"+
                    "        "+wartość.get("DziałanieZWyjątkowej") + "\n" +
                    "\n" +
                    "    }\n" +
                    "}";

            File plik = new File("src/Erpegkraj/Postacie/Bohaterowie/"+klucz+".java");
            plik.createNewFile();
            try(PrintWriter plikuTworzenie = new PrintWriter (new FileOutputStream(plik, false))){
                plikuTworzenie.print(kod);
            }
        }
    }

    public static void przydzielWieleDanychEfektów(HashMap<String,HashMap<String, String>> mapa) throws IOException {

        for (Map.Entry<String, HashMap<String, String>> m : mapa.entrySet()) {
            String klucz = m.getKey();
            HashMap<String, String> wartość = m.getValue();

            String kod = "package Erpegkraj.Efekty.efekty;\n" +
                    "\n" +
                    "import Erpegkraj.Efekty.Efekty;\n" +
                    "import Erpegkraj.PanelGry;\n" +
                    "\n" +
                    "import java.awt.image.BufferedImage;\n" +
                    "import java.io.IOException;\n" +
                    "import java.util.HashMap;\n"+
                    "public class "+klucz+" extends Efekty {\n" +
                    "\n" +
                    "    public "+klucz+"(PanelGry gp) throws IOException {\n" +
                    "        super(\""+wartość.get("Nazwa")+"\", gp, \""+wartość.get("Ikona")+"\");\n" +
                    "    }\n";

            if (wartość.get("PrzyOtrzymaniu") != null){
                kod += "@Override\n" +
                        "public void działaniePrzyOtrzymaniu(){" +
                        "   "+wartość.get("PrzyOtrzymaniu")+
                        "}\n"  ;
            }
            if (wartość.get("PrzyWyczerpaniu") != null){
                kod += "@Override\n" +
                        "public void działaniePrzyWyczerpaniu(){" +
                        "   "+wartość.get("PrzyWyczerpaniu")+
                        "}\n"  ;
            }
            if (wartość.get("PrzyAtaku") != null){
                kod += "@Override\n" +
                        "public void działaniePrzyAtaku(){" +
                        "   "+wartość.get("PrzyPrzyAtaku")+
                        "}\n"  ;
            }
            if (wartość.get("PrzyUżyciuPrzedmiotu") != null){
                kod += "@Override\n" +
                        "public void działaniePrzyUżyciuPrzedmiotu(){" +
                        "   "+wartość.get("PrzyUżyciuPrzedmiotu")+
                        "}\n"  ;
            }
            if (wartość.get("GdyUpłynieRunda") != null){
                kod += "@Override\n" +
                        "public void działanieGdyUpłynieRunda(){" +
                        "   "+wartość.get("GdyUpłynieRunda")+
                        "}\n"  ;
            }
            if (wartość.get("PrzedOdebraniemObrażeń") != null){
                kod += "@Override\n" +
                        "public void działaniePrzedOdebraniemObrażeń(){" +
                        "   "+wartość.get("PrzedOdebraniemObrażeń")+
                        "}\n"  ;
            }
            kod+="}";

            File plik = new File("src/Erpegkraj/Efekty/efekty/" + klucz + ".java");
            plik.createNewFile();
            try (PrintWriter plikuTworzenie = new PrintWriter(new FileOutputStream(plik, false))) {
                plikuTworzenie.print(kod);
            }
        }
    }
}
