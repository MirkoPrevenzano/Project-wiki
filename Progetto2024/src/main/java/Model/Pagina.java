package Model;

import java.sql.Time;
import java.util.Date;

public class Pagina {
    private String titolo;
    private Date data;
    private Time ora;

    private Autore autore;
    private Testo testo;

    public Pagina(String titolo, Date data, Time ora, Autore autore)
    {
        this.titolo = titolo;
        this.data = data;
        this.ora = ora;
        this.autore = autore;
        testo = new Testo();
    }

    public Time getOra() {
        return ora;
    }

    public Autore getAutore() {
        return autore;
    }

    public Testo getTesto() {
        return testo;
    }

    public Date getData() {
        return data;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setOra(Time ora) {
        this.ora = ora;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setAutore(Autore autore) {
        this.autore = autore;
    }

    public void setTesto(Testo testo) {
        this.testo = testo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}
