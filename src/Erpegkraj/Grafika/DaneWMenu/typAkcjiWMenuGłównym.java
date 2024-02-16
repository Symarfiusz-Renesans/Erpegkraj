package Erpegkraj.Grafika.DaneWMenu;

import Erpegkraj.ZarządcaEnumów;

import java.util.HashMap;

public enum typAkcjiWMenuGłównym implements ZarządcaEnumów {
    NowaGra(0, "Uwaga!\nPoprzednia gra zostanie usunięta!"),
    Kontynuuj(1, "Powróć do ostatniej rozgrywki,\nPowodzenia!"),
    Ustawienia(2, "Zmień grę dla Ciebie!"),
    Wyjdź(3, "Dziękujemy za grę!");

    private final int id;
    private final String opis;
    typAkcjiWMenuGłównym(final int id, final String opis){
        this.id = id;
        this.opis = opis;
    }

    public static final HashMap<Integer, String> słownik;
    static{
        słownik = new HashMap<Integer, String>();
        for (typAkcjiWMenuGłównym typ: typAkcjiWMenuGłównym.values()) {
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
