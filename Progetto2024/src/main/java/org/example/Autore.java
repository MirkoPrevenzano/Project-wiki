package org.example;

import java.util.ArrayList;

public class Autore extends Utente{
    private String login;
    private String password;

    ArrayList<Pagina> pagine_create;
    ArrayList<Proposta> proposte_gestite;

    public Autore(String nome, String cognome, String login, String password)
    {
        super(nome,cognome);
        this.login = login;
        this.password = password;
        pagine_create = new ArrayList<Pagina>();
        proposte_gestite = new ArrayList<Proposta>();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
