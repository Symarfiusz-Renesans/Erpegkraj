package Erpegkraj;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public interface ZarządcaArkuszów {

    public static String przeczytajWierszArkusza(FileInputStream ścieżkaArkusza,String nazwaArkusza, String nazwa) throws IOException {
        String tekst = "";
        Workbook notatnik = WorkbookFactory.create(ścieżkaArkusza);
        Sheet arkusz = notatnik.getSheet(nazwaArkusza);

        for(Row r: arkusz){
            String wartość = r.getCell(0).getStringCellValue();
            if(Objects.equals(wartość, nazwa)){
                for (Cell c : r){
                    c.setCellType(CellType.STRING);
                    String typ = arkusz.getRow(0).getCell(c.getColumnIndex()).getStringCellValue();
                    if (!Objects.equals(typ, "")){
                        tekst += typ + "#"+ c.getStringCellValue()+"~";
                    }else break;
                }
                break;
            }
            if (Objects.equals(wartość, "")) break;
        }
        notatnik.close();
        return tekst;
    }
    public static String przeczytajWierszeArkusza(FileInputStream ścieżkaArkusza,String nazwaArkusza) throws IOException {
        String tekst = "";
        Workbook notatnik = WorkbookFactory.create(ścieżkaArkusza);
        Sheet arkusz = notatnik.getSheet(nazwaArkusza);

        for(Row r: arkusz){
            if (r.getCell(0) == null) break;
            String wartość = r.getCell(0).getStringCellValue();
            for (Cell c : r){
                c.setCellType(CellType.STRING);
                String typ = arkusz.getRow(0).getCell(c.getColumnIndex()).getStringCellValue();
                if (!Objects.equals(typ, "")){
                    tekst += typ + "#"+ c.getStringCellValue()+"~";
                }else{
                    break;
                }
            }
            tekst += "`";
        }
        notatnik.close();
        return tekst;
    }

    public static HashMap<String, String> podzielDane(String ciągDanych){
        HashMap<String,String> dane = new HashMap<>();
        String[] podzieloneDane = ciągDanych.split("~");

        for(String dana: podzieloneDane){
            String[] podzielonaDana = dana.split("#");
            //System.out.println(podzielonaDana);
            if (podzielonaDana.length <= 1) continue;
            dane.put(podzielonaDana[0], podzielonaDana[1]);
        }

        return dane;
    }
    public static HashMap<String,HashMap<String, String>> podzielDaneRóżnychWierszy(String ciągDanych){
        HashMap<String,HashMap<String, String>> podział = new HashMap<>();
        String[] podzieloneKlasy = ciągDanych.split("`");

        int i = 0;

        for(String dane: podzieloneKlasy){
            if(i == 0){
                i++;
                continue;
            }

            String[] podzieloneDane = dane.split("~");
            System.out.println(Arrays.toString(podzieloneDane));
            int j = 0;
            String klucz = "";
            for (String dana: podzieloneDane){
                String[] podzielonaDana = dana.split("#");
                if (j == 0){
                    if(Objects.equals(podzielonaDana.length, 1)) break;
                    if (Objects.equals(podzielonaDana[1], "Wołchw") || Objects.equals(podzielonaDana[1], "Kupiec") || Objects.equals(podzielonaDana[1], "Boski Sędzia" )) break;
                    podział.put(podzielonaDana[1], new HashMap<String, String>());
                    klucz = podzielonaDana[1];
                    j++;
                }
                if (podzielonaDana.length == 1) {
                    podział.get(klucz).put(podzielonaDana[0], null);
                } else {
                    podział.get(klucz).put(podzielonaDana[0], podzielonaDana[1]);
                }
            }
        }
        System.out.println(podział);
        return podział;
    }

    void przydzielDane(HashMap<String, String> mapa) throws IOException;
}
