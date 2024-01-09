package Erpegkraj.Grafika.DaneWMenu.Akcje;

import java.util.HashMap;

public enum Atak {
    Atakuj(0, "Atakuj", "Podtawowe    Obrażenia."),
    AtakujZPremią(1, "Atakuj    z    premią", "Większe    Obrażenia!"),
    AtakSpecjalny(2, "Zdolność    specjalna", "Wielkie    Osiągnięcie...");

    public final int id;
    private final String treść;
    private final String opis;
    Atak(int id, String treść, String opis){
        this.id = id;
        this.treść = treść;
        this.opis = opis;
    }
    public static final HashMap<Integer, String> słownikOpisów;
    public static final HashMap<Integer, String> słownikTreści;
    static{
        słownikOpisów = new HashMap<Integer, String>();
        for (Atak typ: Atak.values()) {
            słownikOpisów.put(typ.id, typ.opis);
        }
        słownikTreści =  new HashMap<Integer, String>();
        for (Atak typ: Atak.values()) {
            słownikTreści.put(typ.id, typ.treść);
        }
    }

    public static String ustalOpis(int id){
        return słownikOpisów.get(id);
    }
    public String ustalTreść(int id){
        return słownikTreści.get(id);
    }
}
