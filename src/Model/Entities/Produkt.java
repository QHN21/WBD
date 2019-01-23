package Model.Entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class Produkt {
    private SimpleIntegerProperty numerSeryjny;
    private SimpleIntegerProperty numerWersji;
    private SimpleStringProperty nazwa;
    private SimpleStringProperty typ;
    private Date dataProdukcji;
    private Date dataSprzedazy;
    private Date dataAktualizacji;
    private Date dataAwarii;
    private SimpleStringProperty opis;

    public Produkt(SimpleIntegerProperty numerSeryjny, SimpleIntegerProperty numerWersji, SimpleStringProperty nazwa, SimpleStringProperty typ, Date dataProdukcji, Date dataSprzedazy, Date dataAktualizacji, Date dataAwarii, SimpleStringProperty opis) {
        this.numerSeryjny = numerSeryjny;
        this.numerWersji = numerWersji;
        this.nazwa = nazwa;
        this.typ = typ;
        this.dataProdukcji = dataProdukcji;
        this.dataSprzedazy = dataSprzedazy;
        this.dataAktualizacji = dataAktualizacji;
        this.dataAwarii = dataAwarii;
        this.opis = opis;
    }

    public int getNumerSeryjny() {
        return numerSeryjny.get();
    }

    public SimpleIntegerProperty numerSeryjnyProperty() {
        return numerSeryjny;
    }

    public void setNumerSeryjny(int numerSeryjny) {
        this.numerSeryjny.set(numerSeryjny);
    }

    public int getNumerWersji() {
        return numerWersji.get();
    }

    public SimpleIntegerProperty numerWersjiProperty() {
        return numerWersji;
    }

    public void setNumerWersji(int numerWersji) {
        this.numerWersji.set(numerWersji);
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public SimpleStringProperty nazwaProperty() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    public String getTyp() {
        return typ.get();
    }

    public SimpleStringProperty typProperty() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ.set(typ);
    }

    public Date getDataProdukcji() {
        return dataProdukcji;
    }

    public void setDataProdukcji(Date dataProdukcji) {
        this.dataProdukcji = dataProdukcji;
    }

    public Date getDataSprzedazy() {
        return dataSprzedazy;
    }

    public void setDataSprzedazy(Date dataSprzedazy) {
        this.dataSprzedazy = dataSprzedazy;
    }

    public Date getDataAktualizacji() {
        return dataAktualizacji;
    }

    public void setDataAktualizacji(Date dataAktualizacji) {
        this.dataAktualizacji = dataAktualizacji;
    }

    public Date getDataAwarii() {
        return dataAwarii;
    }

    public void setDataAwarii(Date dataAwarii) {
        this.dataAwarii = dataAwarii;
    }

    public String getOpis() {
        return opis.get();
    }

    public SimpleStringProperty opisProperty() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis.set(opis);
    }
}
