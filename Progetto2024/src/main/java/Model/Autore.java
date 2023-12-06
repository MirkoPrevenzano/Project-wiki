package Model;

import java.util.ArrayList;
import java.util.List;

public class Autore extends Utente {
    private String login;
    private String password;

    private ArrayList<Pagina> pagine_create;
    private ArrayList<Proposta> proposte_gestite;

    public Autore(String nome, String cognome, String login, String password)
    {
        super(nome,cognome);
        this.login = login;
        this.password = password;
        pagine_create = new ArrayList<Pagina>();
        proposte_gestite = new ArrayList<Proposta>();
    }

    public void addListProposta(Proposta p)
    {
        proposte_gestite.add(p);
    }
    public void removeListProposta(Proposta p)
    {
        proposte_gestite.remove(p);
    }
    public List<Proposta> getListProposta()
    {
        return proposte_gestite;
    }
    public void removeListPagine(Pagina p)
    {
        pagine_create.remove(p);
    }
    public void addListPagine(Pagina p)
    {
        pagine_create.add(p);
    }
    public List<Pagina> getListString()
    {
        return pagine_create;
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
