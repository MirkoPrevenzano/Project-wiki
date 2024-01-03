package controllerPackage;


import DAO.ListinoDAO;
import ImplementazionePostgresDao.ImplementazionePostgresDao;
import Model.*;

import Model.Pagina;

import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Controller {
    private Autore autore;
    private Utente utente;
    private final ListinoIscritti listinoIscritti=new ListinoIscritti();
    public Controller() {


    }

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

    public Boolean addPage(String titolo, String usernameAutore) {//creo nuova pagina
        Autore a=listinoIscritti.searchAutore(usernameAutore);
        List<Pagina> page=a.getListPagine();
        for (Pagina p:page
             ) {
            if(p.getTitolo().equals(titolo))
            {
                return false;
            }

        }

            Pagina p=new Pagina(titolo,a);
            a.addListPagine(p);
            return true;
    }
    public Pagina getPage(String titolo, String usernameAutore)
    {
        Autore a=listinoIscritti.searchAutore(usernameAutore);
        List<Pagina> page=a.getListPagine();
        //uso di stream e filter
        return page.stream().filter(paginaT -> paginaT.getTitolo().equals(titolo)).findFirst().orElse(null);
    }
    public void gestioneTestoPage(String nuovaFrase, String usernameAutore, String titolo) {
        Pagina pagina=getPage(titolo,usernameAutore);
        Frase f=new Frase(nuovaFrase, pagina.getTesto());
        pagina.getTesto().addFrase(f);
    }

    public List<String> caricaTitoli(String usernameAutore) {
        autore=listinoIscritti.searchAutore(usernameAutore);
        List<Pagina> p=autore.getListPagine();
        List<String> tit=new ArrayList<>();
        for (Pagina pp:p
             ) {
            tit.add(pp.getTitolo());
        }
        return tit;
    }
    public List<String> caricaTitoli() {
        List<String> tit=new ArrayList<>();
        for(Autore a : listinoIscritti.getListAutore())
        {
            for(Pagina p : a.getListPagine())
            {
                tit.add((p.getTitolo()));
            }
        }

        return tit;
    }

    public List<String> getTestoPage(String titoloPagina, String usernameAutore) {
        autore=listinoIscritti.searchAutore(usernameAutore);
        List<Pagina> page=autore.getListPagine();
        Pagina pagina = page.stream().filter(paginaT -> paginaT.getTitolo().equals(titoloPagina)).findFirst().orElse(null);
        List<Frase> listFrasTesto  = pagina.getTesto().getFrasi();
        //controllare meglio
        List<String> testoPage=new ArrayList<>();
        for(Frase f:listFrasTesto)
        {
            testoPage.add(f.getTesto());
        }
        return testoPage;
    }

    public List<String> getTestoPage(String titoloPagina)
    {
        List<String> testoPage = new ArrayList<>();
        for(Autore a : listinoIscritti.getListAutore()) {
            List<Pagina> page = a.getListPagine();
            Pagina pagina = page.stream().filter(paginaT -> paginaT.getTitolo().equals(titoloPagina)).findFirst().orElse(null);
            if (pagina != null) {
                List<Frase> listFraseTesto = pagina.getTesto().getFrasi();
                for (Frase f : listFraseTesto) {
                    testoPage.add(f.getTesto());
                }
            }
        }
        return testoPage;
    }
    public void leggiListinoDAO() {
        ListinoDAO l = new ImplementazionePostgresDao();
        List<String> nomeAutore = new ArrayList<String>();
        List<String> CognomeAutore = new ArrayList<>();
        List<String> login = new ArrayList<>();
        List<String> Password = new ArrayList<>();
        l.leggiListinoAutore(nomeAutore, CognomeAutore, login, Password);  //legge listino dal DB
        for (int i = 0; i < nomeAutore.size(); i++) {
            Autore a = new Autore(nomeAutore.get(i), CognomeAutore.get(i), login.get(i), Password.get(i));
            listinoIscritti.addListAutore(a);
        } // costruisce gli oggetti Model a partire dai risultati del db


        List<String> nomeUtente = new ArrayList<>();
        List<String> cognomeUtente = new ArrayList<>();
        l.leggiListinoUtenti(nomeUtente, cognomeUtente);
        for (int i = 0; i < nomeUtente.size(); i++) {
            Utente u = new Utente(nomeUtente.get(i), cognomeUtente.get(i));
            listinoIscritti.addListUtente(u);
        }
        List<String> TitoloPagina = new ArrayList<>();
        List<String> loginAutorePagina = new ArrayList<>();
        List<Date> dataPagina = new ArrayList<>();
        List<Time> oraPagina = new ArrayList<>();

        l.leggiPagineAutori(TitoloPagina,dataPagina,oraPagina, loginAutorePagina);
        for(Autore a : listinoIscritti.getListAutore())
        {
            for(int i = 0; i<TitoloPagina.size(); i++) {
                if (a.getLogin().equals(loginAutorePagina.get(i))) {
                    Pagina p = new Pagina(TitoloPagina.get(i), dataPagina.get(i).toLocalDate(),oraPagina.get(i).toLocalTime(),a);
                    a.addListPagine(p);
                }
            }
        }
        List<String> TitoloPaginaFrase = new ArrayList<>();
        List<String> TestoFrase = new ArrayList<>();
        List<Integer> ordineFrase = new ArrayList<>();
        l.leggiFrasiPagina(TestoFrase,TitoloPaginaFrase,ordineFrase);
        for(Autore a : listinoIscritti.getListAutore()) {
            for (Pagina p : a.getListPagine()) {
                for (int i = 0; i < TitoloPaginaFrase.size(); i++) {
                    if (p.getTitolo().equals(TitoloPaginaFrase.get(i)))
                    {
                        Frase frase = new Frase(TestoFrase.get(i),p.getTesto());
                        p.getTesto().addFrase(frase);
                    }
                }
            }
        }
    }


    public void modificaTesto(List<String> frasi, String titolo, String usernameAutore) {

        Pagina pagina = getPage(titolo,usernameAutore);
        Testo t=new Testo();
        pagina.setTesto(t);
        for(int i=0;i<frasi.size();i++)
        {
            Frase f=new Frase(frasi.get(i), t);
            t.addFrase(f);
        }

      // List<Frase> listFrasi= pagina.getTesto().getFrasi();
    }

    public void saveProposta(List <String> listFrasi, List<String> modifiche, String usernameAutore, String titolo) {
        Autore a=listinoIscritti.searchAutore(usernameAutore);
        Pagina p=getPage(titolo, usernameAutore);
        Testo t=p.getTesto();
        List <Frase> list=t.getFrasi();
        List <Frase> frasiSelezionate=new ArrayList<>();
        System.out.println(listFrasi.get(0));
        for(int i=0;i<listFrasi.size();i++)
        {
           /*int index= list.indexOf(listFrasi.get(i));
           String tff=listFrasi.get(index).replaceAll("\\n$", "");*/

            for (Frase f:list) {
                String tff=listFrasi.get(i).replaceAll("\\n$", "");
                if(tff.equals(f.getTesto()))
                {

                    frasiSelezionate.add(f);
                }
            }



        }
        Proposta proposta=new Proposta(a,a,t,frasiSelezionate,modifiche);
        a.addProposta(proposta);
    }

    public void modificaTesto(List<String> frasiSelezionate, List<String> modifiche, String usernameAutore, String titolo, String usernameUtente) {
        if(usernameUtente.equals(usernameUtente))
        {
            Pagina p= getPage(titolo, usernameAutore);
            for(int i=0;i<frasiSelezionate.size();i++)
            {
                for (Frase f:p.getTesto().getFrasi()) {
                    if(f.getTesto().equals(frasiSelezionate.get(i)))
                    {
                        if(modifiche.get(i).equals(""))
                        {
                            p.getTesto().removeFrase(f);
                        }
                        else
                        {
                            f.setTesto(frasiSelezionate.get(i));
                        }
                    }


                }
            }

        }

    }
}
