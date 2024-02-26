package Erpegkraj.Grafika.DaneWMenu.Akcje;

import java.util.HashMap;
public enum Obrona {
    Obrona(0, "Broń się", "Mniej Obrażeń."),
    ObronaZPremią(1, "Broń się z premią", "Jeszcze Mniej Obrażeń!");

    public final int id;
    private final String treść;
    private final String opis;
    Obrona(int id, String treść, String opis){
        this.id = id;
        this.treść = treść;
        this.opis = opis;
    }
    public static final HashMap<Integer, String> słownikOpisów;
    public static final HashMap<Integer, String> słownikTreści;
    static{
        słownikOpisów = new HashMap<Integer, String>();
        for (Obrona typ: Obrona.values()) {
            słownikOpisów.put(typ.id, typ.opis);
        }
        słownikTreści =  new HashMap<Integer, String>();
        for (Obrona typ: Obrona.values()) {
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
