package Controller;

import Model.Autore;
import Model.ListinoIscritti;
import Model.Utente;

public class Controller {
    private Autore autore;
    private Utente utente;
    private final ListinoIscritti listinoIscritti=new ListinoIscritti();
    public Controller() {}

    //iscrizione, ritora true se l'iscrizione è andata a buon fine (voglio vedere come si può verificare se un utente è iscritto o no
    public Boolean addNewAuthor(String nome, String cognome, String username, String password) {
        this.autore=new Autore(nome,cognome,username, password);
        listinoIscritti.addListAutore(this.autore);
        if(autore!=null)
            return true;
        return false;
    }

    public Boolean addNewUtente(String nome, String cognome) {
        this.utente=new Utente(nome,cognome);
        listinoIscritti.addListUtente(this.utente);
        if(utente!=null)
            return true;
        return false;    }
}
