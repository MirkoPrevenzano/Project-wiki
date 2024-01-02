package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Pagina {
    private String titolo;
    private LocalDate data;
    private LocalTime ora;

    private Autore autore;
    private Testo testo;

    public Pagina(String titolo, Autore autore)
    {
        this.titolo = titolo;
        this.data = LocalDate.now();
        this.ora = LocalTime.now();
        this.autore = autore;
        testo = new Testo();
    }
    public Pagina(String titolo, LocalDate data, LocalTime ora, Autore autore)
    {
        this.titolo = titolo;
        this.data = data;
        this.ora = ora;
        this.autore = autore;
        testo = new Testo();
    }

    public LocalTime getOra() {
        return ora;
    }

    public Autore getAutore() {
        return autore;
    }

    public Testo getTesto() {
        return testo;
    }

    public LocalDate getData() {
        return data;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public void setData(LocalDate data) {
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
