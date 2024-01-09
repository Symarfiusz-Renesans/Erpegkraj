package Erpegkraj.Postacie.Akcje;

public enum Atak {
    Atakuj(0, "Atakuj", "Podtawowe    Obrażenia."),
    AtakujZPremią(1, "Atakuj    z    premią", "Większe    Obrażenia!"),
    AtakSpecjalny(2, "Zdolność    specjalna", "Wielkie    Osiągnięcie...");

    private final int id;
    private final String treść;
    private final String opis;
    Atak(int id, String treść, String opis){
        this.id = id;
        this.treść = treść;
        this.opis = opis;
    }
}
