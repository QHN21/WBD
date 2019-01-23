package Model.Entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class RMA
{
    private SimpleIntegerProperty rmaID;
    private SimpleStringProperty status;
    private Date dataUtworzenia;
    private Date dataZakonczenia;
    private SimpleStringProperty nazwaProduktu;

    public RMA(Integer rmaID, String status, Date dataUtworzenia, Date dataZakonczenia, String nazwaProduktu){
        this.rmaID = new SimpleIntegerProperty(rmaID);
        this.status = new SimpleStringProperty(status);
        this.dataUtworzenia = dataUtworzenia;
        this.dataZakonczenia = dataZakonczenia;
        this.nazwaProduktu = new SimpleStringProperty(nazwaProduktu);
    }

    public String getStatus()
    {
        return status.get();
    }

    public SimpleStringProperty statusProperty()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status.set(status);
    }

    public Date getDataUtworzenia()
    {
        return dataUtworzenia;
    }

    public void setDataUtworzenia(Date dataUtworzenia)
    {
        this.dataUtworzenia = dataUtworzenia;
    }

    public Date getDataZakonczenia()
    {
        return dataZakonczenia;
    }

    public void setDataZakonczenia(Date dataZakonczenia)
    {
        this.dataZakonczenia = dataZakonczenia;
    }

    public String getNazwaProduktu()
    {
        return nazwaProduktu.get();
    }

    public SimpleStringProperty nazwaProduktuProperty()
    {
        return nazwaProduktu;
    }

    public void setNazwaProduktu(String nazwaProduktu)
    {
        this.nazwaProduktu.set(nazwaProduktu);
    }

    public int getRmaID()
    {
        return rmaID.get();
    }

    public SimpleIntegerProperty rmaIDProperty()
    {
        return rmaID;
    }

    public void setRmaID(int rmaID)
    {
        this.rmaID.set(rmaID);
    }

}
