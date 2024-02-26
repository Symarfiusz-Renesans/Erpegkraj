package Erpegkraj.Grafika.DaneWMenu;

import Erpegkraj.ZarządcaEnumów;

import java.util.HashMap;

public enum typAkcjiWMenuWalki implements ZarządcaEnumów {
    Atakuj(3,"By zabolało!"),
    BrońSię(2, "By nie zabolało!"),
    Ekwipunek(1, "Pomoc w Walce"),
    Uciekaj(0, "Droga hańby...");

    private final int id;
    private final String opis;
    typAkcjiWMenuWalki(final int id, final String opis){
        this.id = id;
        this.opis = opis;
    }

    public static final HashMap<Integer, String> słownik;
    static{
        słownik = new HashMap<Integer, String>();
        for (typAkcjiWMenuWalki typ: typAkcjiWMenuWalki.values()) {
            słownik.put(typ.id, typ.opis);
        }
    }

    @Override
    public int ustalId(){
        return id;
    }

    public static String ustalEnumaPrzezId(int id) {
        return słownik.get(id);
    }
}
