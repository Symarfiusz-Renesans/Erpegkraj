package Erpegkraj.Grafika.DaneWMenu;

import Erpegkraj.ZarządcaEnumów;

import java.util.HashMap;

public enum PołożenieWMenuGłównym implements ZarządcaEnumów {


    private final int id;
    PołożenieWMenuGłównym(final int id){
        this.id = id;
    }

    private static final HashMap<Integer, String> słownik;
    static{
        słownik = new HashMap<Integer, String>();
        for (PołożenieWMenuGłównym typ: PołożenieWMenuGłównym.values()) {
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
