package org.example;

import java.sql.Time;
import java.util.Date;

public class Pagina {
    public String Titolo;
    public Date data;
    public Time ora;

    Autore autore;
    Testo testo;

    public Pagina(String Titolo, Date data, Time ora, Autore autore)
    {
        this.Titolo = Titolo;
        this.data = data;
        this.ora = ora;
        this.autore = autore;
        testo = new Testo();
    }

}
