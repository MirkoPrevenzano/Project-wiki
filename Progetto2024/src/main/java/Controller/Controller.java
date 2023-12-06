package Controller;

import Model.Autore;

public class Controller {
    private Autore autore;
    public Controller() {
    }

    //iscrizione, ritora true se l'iscrizione è andata a buon fine (voglio vedere come si può verificare se un utente è iscritto o no
    public Boolean addNewAuthor(String nome, String cognome, String username, String password) {
        this.autore=new Autore(nome,cognome,username, password);
        if(autore!=null)
            return true;
        return false;
    }
}
