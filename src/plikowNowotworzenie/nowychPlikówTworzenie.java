package plikowNowotworzenie;

import Erpegkraj.ZarządcaArkuszów;

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
                    "import java.util.ArrayList;\n\n"+
                    "public class "+wartość.get("nazwaKlasy")+" extends Jednorazówki {\n\n"+
                    wartość.get("Zmienne")+"\n\n"+
                    "    public "+wartość.get("nazwaKlasy")+"(ArrayList<Postać> wrogowie, PanelGry gp) {\n" +
                    "        super(\""+wartość.get("Nazwa")+"\", wrogowie, gp);\n" +
                    "    }\n\n"
                    +"    @Override\n"+
                    "    public void działanie(int cel) {\n"+
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
}
