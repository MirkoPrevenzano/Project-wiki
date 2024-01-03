package Model;

import java.util.ArrayList;
import java.util.List;

public class Autore extends Utente
{
    private String login;
    private String password;

    private List<Pagina> listPagine;
    private List<Proposta> listProposte;

    public Autore(String nome, String cognome, String login, String password)
    {
        super(nome,cognome);
        this.login = login;
        this.password = password;
        listPagine = new ArrayList<>();
        listProposte = new ArrayList<>();
    }

    public void addListProposta(Proposta p)
    {
        listProposte.add(p);
    }

    public void removeListProposta(Proposta p)
    {
        listProposte.remove(p);
    }

    public List<Proposta> getListProposta()
    {
        return listProposte;
    }

    public void removeListPagine(Pagina p) {listPagine.remove(p);}

    public void addListPagine(Pagina p)
    {
        listPagine.add(p);
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

    public List<Pagina> getListPagine() {return listPagine;}

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean signIn(String username, String password){
        if(this.login.equals(username)&&this.password.equals(password))
            return true;
        return false;
    }
}
