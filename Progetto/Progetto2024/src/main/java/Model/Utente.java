package Model;


import java.util.ArrayList;
import java.util.List;

public class Utente {
    protected String nome;
    protected String cognome;

    protected List<Proposta> listProposteRichieste;


    public Utente(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
        listProposteRichieste = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }
    public void addProposta(Proposta p)
    {
        listProposteRichieste.add(p);
    }
    public void removeProposta(Proposta p)
    {
        listProposteRichieste.remove(p);
    }

    public List<Proposta> getListProposte() {
        return listProposteRichieste;
    }
}
