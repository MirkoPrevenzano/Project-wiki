package Model;

import Model.Proposta;

import java.util.ArrayList;

public class Utente {
    protected String nome;
    protected String cognome;

    protected ArrayList<Proposta> proposte_richieste;

    public Utente(String nome, String cognome)
    {
        this.nome = nome;
        this.cognome = cognome;
        proposte_richieste = new ArrayList<Proposta>();
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }
    public void addProposta(Proposta p)
    {
        proposte_richieste.add(p);
    }
    public void removeProposta(Proposta p)
    {
        proposte_richieste.remove(p);
    }

    public ArrayList<Proposta> getListProposte() {
        return proposte_richieste;
    }
}
