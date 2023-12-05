package org.example;

import java.util.ArrayList;

public class Utente {
    public String nome;
    public String cognome;

    ArrayList<Proposta> proposte_richieste;

    public Utente(String nome, String cognome)
    {
        this.nome = nome;
        this.cognome = cognome;
        proposte_richieste = new ArrayList<Proposta>();
    }
}
