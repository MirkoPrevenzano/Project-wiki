package controllerPackage;



import Model.Autore;
import Model.Pagina;
import Model.Utente;
import Model.ListinoIscritti;

public class Controller {
    private Autore autore;
    private Utente utente;
    private final ListinoIscritti listinoIscritti=new ListinoIscritti();
    public Controller() {}

    //iscrizione, ritora true se l'iscrizione è andata a buon fine (voglio vedere come si può verificare se un utente è iscritto o no
    public int addNewAuthor(String nome, String cognome, String username, String password) {
        for (Autore a:listinoIscritti.getListAutore()
        ) {
            if(a.getLogin().equals(username))
                return 2;//autore già esistente

        }
        this.autore=new Autore(nome,cognome,username, password);

        listinoIscritti.addListAutore(this.autore);
        if(autore!=null)
            return 1;
        return 0;
    }

    public int addNewUtente(String nome, String cognome) {
        for (Utente u:listinoIscritti.getListUtente()
        ) {
            if(u.getNome().equals(nome)&&u.getCognome().equals(cognome))
                return 2;//utente già esistente

        }
        this.utente=new Utente(nome,cognome);
        listinoIscritti.addListUtente(this.utente);

        if(utente!=null)
            return 1;
        return 0;
    }
    public int accessAutore(String username, String password)
    {
        for (Autore a:listinoIscritti.getListAutore()){
            if(a.getLogin().equals(username))
            {
                if(a.signIn(username, password))
                {
                    return 1; //passwordCorretta
                }
                else
                {
                    return 2; //passwordErrata
                }
            }
        }
        return 0;//Autore non esistente
    }
    
    
    public Boolean accessUtente(String nome, String cognome)
    {
       
        for (Utente utente:listinoIscritti.getListUtente()){
            if(utente.getNome().equals(nome) && utente.getCognome().equals(cognome))
            {
                return true;
            }
            
        }
        return false;

    }

    public void addPage(String titolo, String usernameAutore) {//creo nuova pagina
        Autore a=listinoIscritti.searchAutore(usernameAutore);
        Pagina pagina=new Pagina(titolo,a);
        a.addListPagine(pagina);
    }

    public void gestioneTestoPage(String nuovaFrase) { //aggiungo una frase
        System.out.println(nuovaFrase+"nuova frase\n");
    }
}
