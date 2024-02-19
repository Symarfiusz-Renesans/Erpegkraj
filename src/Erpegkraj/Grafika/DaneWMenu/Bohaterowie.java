package Erpegkraj.Grafika.DaneWMenu;

import Erpegkraj.PanelGry;
import Erpegkraj.Postacie.Bohater;
import Erpegkraj.Postacie.Bohaterowie.*;
import Erpegkraj.ZarządcaEnumów;

import java.io.IOException;
import java.util.HashMap;

public enum Bohaterowie implements ZarządcaEnumów {
    Krzyżowiec(0, new Object() {
        Erpegkraj.Postacie.Bohaterowie.Krzyżowiec evaluate() {
            try {
                return new Krzyżowiec(0,0,0, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }.evaluate()),
    Łotr(1, new Object() {
        Erpegkraj.Postacie.Bohaterowie.Łotr evaluate() {
            try {
                return new Łotr(0,0,0, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }.evaluate()),
;
    private final int id;
    private final Bohater bohater;
    Bohaterowie(final int id, Bohater bohater){
        this.id = id;
        this.bohater = bohater;
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

    public  Bohater ustalBohatera() {
        return bohater;}}