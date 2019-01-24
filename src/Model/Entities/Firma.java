package Model.Entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Firma {
    private SimpleStringProperty Nazwa;
    private SimpleIntegerProperty Telefon;
    private SimpleStringProperty Email;
    private SimpleStringProperty Adres;
    private int ID;

    public Firma(String Nazwa, Integer Telefon, String Email, String Adres, int ID){
        this.Nazwa = new SimpleStringProperty(Nazwa);
        this.Telefon = new SimpleIntegerProperty(Telefon);
        this.Email = new SimpleStringProperty(Email);
        this.Adres = new SimpleStringProperty(Adres);
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public int getTelefon() {
        return Telefon.get();
    }

    public SimpleIntegerProperty telefonProperty() {
        return Telefon;
    }

    public void setTelefon(int telefon) {
        this.Telefon.set(telefon);
    }

    public String getEmail() {
        return Email.get();
    }

    public SimpleStringProperty emailProperty() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email.set(email);
    }

    public String getAdres() {
        return Adres.get();
    }

    public SimpleStringProperty adresProperty() {
        return Adres;
    }

    public void setAdres(String adres) {
        this.Adres.set(adres);
    }
}
