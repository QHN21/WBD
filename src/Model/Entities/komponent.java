package Model.Entities;

import javafx.beans.property.SimpleStringProperty;

public class komponent{
    private SimpleStringProperty Id;
    private SimpleStringProperty Nazwa;

    public komponent(String ID, String Nazwa){
        this.Id = new SimpleStringProperty(ID);
        this.Nazwa = new SimpleStringProperty(Nazwa);
    }
    public String getId() {
        return Id.get();
    }

    public SimpleStringProperty IdProperty() {
        return Id;
    }

    public void setId(String ID) {
        this.Id.set(ID);
    }

    public String getNazwa() {
        return Nazwa.get();
    }

    public SimpleStringProperty NazwaProperty() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        this.Nazwa.set(nazwa);
    }
}