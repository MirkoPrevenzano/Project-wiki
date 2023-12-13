package Model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Proposta {
    private Date data;
    private Time ora;
    private boolean stato;
    private Utente utente;
    private Autore autore;
    private Testo testo;

    private List<Frase> frasiSelezionate;

    private List<String> modifica;

    public Proposta( Utente utente, Autore autore, Testo testo, Frase frase, String modifica)
    {
        //data, ora e stato da inserire in automatico

        this.autore = autore;
        this.utente = utente;
        this.testo = testo;
        frasiSelezionate = new ArrayList<>();
        frasiSelezionate.add(frase);
        this.modifica = new ArrayList<>();
        this.modifica.add(modifica);
    }
    public Proposta( Utente utente, Autore autore, Testo testo, List<Frase> frase, List<String> modifica)
    {
        //data, ora e stato da inserire in automatico
        this.autore = autore;
        this.utente = utente;
        this.testo = testo;
        if(frasiSelezionate.size()!= modifica.size())
        {
            throw new IllegalArgumentException("Numero frasi selezionate diverso dalle modifiche proposte");
        }
        frasiSelezionate= frase;
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
        modifica.add(propModify);
    }

    public void removeListString(String propModify)
    {
        modifica.remove(propModify);
    }

    public List<String> getListString()
    {
        return modifica;
    }

    public void addListFrase(Frase f)
    {
        frasiSelezionate.add(f);
    }

    public void removeListString(Frase f)
    {
        frasiSelezionate.remove(f);
    }

    public List<Frase> getListFrase()
    {
        return frasiSelezionate;
    }

    public Autore getAutore() {
        return autore;
    }

    public Date getData() {
        return data;
    }

    public Testo getTesto() {
        return testo;
    }

    public Time getOra() {
        return ora;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setTesto(Testo testo) {
        this.testo = testo;
    }

    public void setAutore(Autore autore) {
        this.autore = autore;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setOra(Time ora) {
        this.ora = ora;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }


}
