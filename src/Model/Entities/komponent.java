package Model.Entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class komponent{
    private SimpleStringProperty Nazwa;
    private SimpleIntegerProperty Nr_seryjny;
    private SimpleStringProperty Typ;

    public komponent(String Nazwa,Integer Nr_seryjny, String Typ){
        this.Nazwa = new SimpleStringProperty(Nazwa);
        this.Nr_seryjny = new SimpleIntegerProperty(Nr_seryjny);
        this.Typ = new SimpleStringProperty(Typ);
    }

    public String getNazwa() {
        return Nazwa.get();
    }

    public SimpleStringProperty nazwaProperty() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        this.Nazwa.set(nazwa);
    }

    public int getNr_seryjny() {
        return Nr_seryjny.get();
    }

    public SimpleIntegerProperty nr_seryjnyProperty() {
        return Nr_seryjny;
    }

    public void setNr_seryjny(int nr_seryjny) {
        this.Nr_seryjny.set(nr_seryjny);
    }

    public String getTyp() {
        return Typ.get();
    }

    public SimpleStringProperty typProperty() {
        return Typ;
    }

    public void setTyp(String typ) {
        this.Typ.set(typ);
    }
}