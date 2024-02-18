package Erpegkraj.Grafika.DaneWMenu;

import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Bohaterowie.Krzyżowiec;
import Erpegkraj.ZarządcaEnumów;

import java.util.HashMap;

public enum Bohaterowie implements ZarządcaEnumów {
    Krzyżowiec(0),
    Łotr(1),
    Kupiec(2),
    Wołchw(3),
    BoskiSędzia(4);

    private final int id;
    Bohaterowie(final int id){
        this.id = id;
    }

    private static final HashMap<Integer, String> słownik;
    static{
        słownik = new HashMap<Integer, String>();
        for (Bohaterowie typ: Bohaterowie.values()) {
            słownik.put(typ.id, typ.name());
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
