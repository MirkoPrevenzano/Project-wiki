package org.example;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Proposta {
    public Date data;
    public Time ora;
    private boolean stato;
    public Utente utente;
    public Autore autore;
    public Testo testo;

    private List<Frase> frasi_selezionate;

    private List<String> modifica;

    public Proposta(Date data, Time ora, boolean stato, Utente utente, Autore autore, Testo testo, Frase frase, String modifica)
    {
        this.data = data;
        this.ora = ora;
        this.stato = stato;
        this.autore = autore;
        this.utente = utente;
        this.testo = testo;
        frasi_selezionate = new ArrayList<Frase>();
        frasi_selezionate.add(frase);
        this.modifica = new ArrayList<>();
        this.modifica.add(modifica);
    }
    public Proposta(Date data, Time ora, boolean stato, Utente utente, Autore autore, Testo testo, List<Frase> frase, List<String> modifica)
    {
        this.data = data;
        this.ora = ora;
        this.stato = stato;
        this.autore = autore;
        this.utente = utente;
        this.testo = testo;
        if(frasi_selezionate.size()!= modifica.size())
        {
            throw new IllegalArgumentException("Numero frasi selezionate diverso dalle modifiche proposte");
        }
        frasi_selezionate= frase;
        this.modifica = modifica;

    }

    public boolean isStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }
    public void addListString(String propModify)
    {

    }
}
