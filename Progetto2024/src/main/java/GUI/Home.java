package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//ATTENZIONE: vedere metodo AccessoAutore iterator e campo password
public class Home {
    private final Controller controller; //nel caso di futuri errori levare final
    private static JFrame frame;
    private JButton Accesso_Autore;
    private JButton Accesso_Utente;
    private JButton Iscriviti;
    private JPanel panel1;
    private JPanel panelAutore;
    private JPanel panelUtente;
    private JPanel panelIscriviti;

    public Home() {
        controller = new Controller(); //creo oggetto controller altrimenti non posso usare metodi implementati da lì
        Accesso_Autore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccessoAutore accessoAutore= new AccessoAutore(Home.this.controller, Home.frame);
                AccessoAutore.frame.setVisible(true);
                Home.frame.setVisible(false);
            }
        });
        /*passo al frame per l'iscrizione (l'ho fatta per autore, per iniziare. La mia idea era quella di mettere un opzione che se selezionata sblocca la possibilità
        di inserire anche login e password, il tipo di opzione è l'oggetto jRadioButton e mettere un actionListener che quando l'utente ci clicca sblocca quelle due caselle
        ovviamente poi il metodo Iscizione controlla se sono stati inseriti anche login e password e decide poi se creare oggetto utente o autore.)
         */
        Iscriviti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iscrizione iscrizione = new Iscrizione(Home.this.controller, Home.frame);
                Iscrizione.frame.setVisible(true);
                Home.frame.setVisible(false);
            }
        });
        Accesso_Utente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accessoUtente accessoUt= new accessoUtente(Home.this.controller, Home.frame);
                accessoUtente.frame.setVisible(true);
                Home.frame.setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("Home");
        Home home = new Home();
        frame.setContentPane(home.panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

}
