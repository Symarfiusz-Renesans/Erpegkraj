package Erpegkraj.Grafika.DaneWMenu;

import Erpegkraj.ZarządcaEnumów;

import java.util.HashMap;

public enum PołożenieWMenu implements ZarządcaEnumów {
    WybórTypuAkcji(0),
    WybórAkcji(1),
    WybórCelu(2),
    Akcja(3);

    private final int id;
    PołożenieWMenu(final int id){
        this.id = id;
    }

    private static final HashMap<Integer, String> słownik;
    static{
        słownik = new HashMap<Integer, String>();
        for (PołożenieWMenu typ: PołożenieWMenu.values()) {
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
