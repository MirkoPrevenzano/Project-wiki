package Model;

import java.util.ArrayList;
import java.util.List;
//metodo per verifica esistenza iscritti. In autore metodo signin
public class ListinoIscritti {
    public ListinoIscritti()
    {}
    private List<Utente> listUtente=new ArrayList<>();
    private List<Autore> listAutore=new ArrayList<>();

    public List<Autore> getListAutore() {
        return listAutore;
    }

    public List<Utente> getListUtente() {
        return listUtente;
    }

    public void addListAutore(Autore a) {
        this.listAutore.add(a);
    }
    public void removeListAutore(Autore a) {
        this.listAutore.remove(a);
    }

    public void setListAutore(List<Autore> listAutore) {
        this.listAutore = listAutore;
    }

    public void addListUtente(Utente u) {
        this.listUtente.add(u);
    }
    public void removeListUtente(Utente u) {
        this.listUtente.remove(u);
    }

    public void setListUtente(List<Utente> listUtente) {
        this.listUtente = listUtente;
    }
}
